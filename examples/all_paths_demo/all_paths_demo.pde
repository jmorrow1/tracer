import tracer.*;
import tracer.paths.*;

//paths
ArrayList<Path> paths;

//tracers
ArrayList<Tracer> tracers;

//memory
Point pt = new Point(0, 0);

//animation
float u = 0;
float du = 0.005;

//layout
int cellSize = 125;
int rowSize = 5;
int colSize = 4;

//draw mode
boolean synchronizeTracing; //control with '1' key
boolean drawSegments; //control with '2' keys

void settings() {
  size(rowSize*cellSize, colSize*cellSize, P2D);
}

void setup() {
  printDrawMode(); 
  createPaths();
  tracers = createTracers(paths);
}

void createPaths() {
  paths = new ArrayList<Path>();
  float pathRadius = 0.4 * cellSize;
  Path.addOneOfEachPathType(pathRadius, paths);
  for (Path p : paths) {
    p.setStrokeWeight(1.5f);
    p.setStrokeColor(0);
    p.setFill(false);
  }
  reposition(paths, cellSize);
}

ArrayList<Tracer> createTracers(ArrayList<Path> paths) {
  ArrayList<Tracer> tracers = new ArrayList<Tracer>();
  for (Path p : paths) {
    float tracerSpeed = (synchronizeTracing) ? du : (du*200)/p.getLength();
    tracers.add(new Tracer(p, 0, tracerSpeed));
  }
  return tracers;
}

void reposition(ArrayList<Path> paths, int cellSize) {
  int x = cellSize / 2;
  int y = cellSize / 2;

  for (Path p : paths) {
    p.translate(x, y);

    x += cellSize;
    if (x >= width) {
      x = cellSize / 2;
      y += cellSize;
    }
  }
}

void draw() {  
  //update tracers
  for (Tracer t : tracers) {
    t.step();
  }

  //draw
  background(255);


  if (!drawSegments) {
    //draw paths    
    for (Path p : paths) {
      p.draw(g);
    }
  }

  if (drawSegments) {
    //draw segments
    for (Tracer t : tracers) {
      Path p = t.getPath();
      float dist = (synchronizeTracing) ? 0.4 : (50 / p.getLength());
      float start = Path.remainder(t.getInput1D()-dist, 1);
      float end = t.getInput1D();
      strokeWeight(1);
      p.draw(g, start, end);
    }
  }

  //draw tracers
  strokeWeight(6);
  stroke(0);
  for (Tracer t : tracers) {
    point(t.x, t.y);
  }
}

String lastInfoString = "";
void mouseMoved() {
  int i = min(floor(map(mouseX, 0, width, 0, rowSize)), rowSize-1);
  int j = min(floor(map(mouseY, 0, height, 0, colSize)), colSize-1);
  int cellIndex = j*rowSize + i;
  if (0 <= cellIndex && cellIndex < paths.size()) {
    Path path = paths.get(cellIndex);
    String pathName = path.getClass().getSimpleName();
    String newInfoString = "The mouse is hovered over a " + pathName + ".";
    if (!lastInfoString.equals(newInfoString)) {
      println(newInfoString);
      lastInfoString = newInfoString;
    }
  }
}


void keyPressed() {
  switch (key) {
    case '1' : 
      synchronizeTracing = !synchronizeTracing;
      println(synchronizeTracing ? "Tracing speeds are synchronized " : "Tracing speeds are asynchronized (dependent on path length)");
      break;
    case '2' : 
      drawSegments = !drawSegments;
      println(drawSegments ? "Drawing segments." : "Drawing paths");
      break;
  }
}

void printDrawMode() {
  print(synchronizeTracing ? "Tracing speeds are synchronized " : "Tracing speeds are asynchronized (dependent on path length)");
  println(drawSegments ? "and Path segments are being drawn." : "and Paths are being drawn.");
}