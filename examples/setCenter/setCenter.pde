import tracer.paths.*;

//style
int bgColor = #ffffff;
int strokeColor = #000000;
int fillColor = #444444;
float strokeWeight = 4;
int strokeCap = ROUND;

//paths
Path[] paths = Path.getOneOfEachPathType(100);
int currPathIndex = 0;

void settings() {
  size(800, 600, P2D);
}

void setup() {
  System.out.println(paths[currPathIndex]);
  style(paths);
}

void style(Path[] paths) {
  for (Path p : paths) {
    p.setFill(false);
    p.setStrokeColor(strokeColor);
    p.setStrokeWeight(strokeWeight);
    p.setStrokeCap(strokeCap);
  }
}

void draw() {
  background(bgColor);
  paths[currPathIndex].draw(g);
}

void mouseMoved() {
  paths[currPathIndex].setCenter(mouseX, mouseY);
}

void mouseDragged() {
  paths[currPathIndex].setCenter(mouseX, mouseY);
}

void mousePressed() {
  currPathIndex = (currPathIndex+1) % paths.length;
  paths[currPathIndex].setCenter(mouseX, mouseY);
  System.out.println(paths[currPathIndex]);
}