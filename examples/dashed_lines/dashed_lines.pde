import tracer.paths.*;

int pathIndex = 0; //control with left and right keys
Path[] paths;
Path currPath;
Segment[] dashes = new Segment[20];

void setup() {
  size(400, 400, P2D);

  initPaths(); 
  initCurrPath();
}

void initCurrPath() {
  currPath = paths[pathIndex];
  println(currPath.getClass().getName());
  println("use left and right keys to change path type");
  initDashedLines();
}

void initPaths() {
  paths = Path.getOneOfEachPathType(150);
  for (Path path : paths) {
    path.setCenter(width/2, height/2);
  }
}

void initDashedLines() {
  //create dashes (dashed lines)
  float dashLength = 1.0/ dashes.length;
  for (int i=0; i<dashes.length; i++) {
    float dashStart = i * dashLength;
    float dashEnd = dashStart + 0.35f * dashLength;
    dashes[i] = new Segment(currPath, dashStart, dashEnd);
    dashes[i].setStrokeColor(color(10));
    dashes[i].setStrokeWeight(5);
  }
}

void draw() {
  //update
  for (Segment dash : dashes) {
    dash.translate(0.002);
  }
  
  //draw
  background(255);
  for (Segment dash : dashes) {
    dash.draw(g);
  }
}

void keyPressed() {
  if (key == CODED) {
    if (keyCode == LEFT) {
      pathIndex = (int)Path.remainder(pathIndex-1, paths.length);
      initCurrPath();
    }
    else if (keyCode == RIGHT) {
      pathIndex = (int)Path.remainder(pathIndex+1, paths.length);
      initCurrPath();
    }
  }
}