import tracer.*;
import paths.*;
import render.*;
import ease.*;

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
int drawMode = 2;
boolean synchronizeTracing;
boolean drawSegments;

void settings() {
  size(rowSize*cellSize, colSize*cellSize, P2D);
}

void setup() {
  applyDrawMode();
  printDrawMode();
  
  paths = createPaths(0.4 * cellSize);
  for (Path p : paths) {
    p.setStrokeWeight(1.5f);
    p.setStrokeColor(0);
    p.setFill(false);
  }
  reposition(paths, cellSize);
  tracers = createTracers(paths);
}

ArrayList<Tracer> createTracers(ArrayList<Path> paths) {
  ArrayList<Tracer> tracers = new ArrayList<Tracer>();
  for (Path p : paths) {
    float tracerSpeed = (synchronizeTracing) ? du : (du*200)/p.getLength();
    tracers.add(new Tracer(p, 0, tracerSpeed));
  }
  return tracers;
}

ArrayList<Path> createPaths(float r) {
  ArrayList<Path> paths = new ArrayList<Path>();
  paths.add(new Arc(0, 0, r));
  paths.add(new Blender(new Circle(0, 0, r), new InfinitySymbol(0, 0, r), 0.7, 100));
  paths.add(new Circle(0, 0, r));
  paths.add(new Composite(new Rect(-r, -r, 0.75*r, 0.75*r, CORNER), new Rect(0, 0, 0.75*r, 0.75*r, CORNER)));
  paths.add(new CubicBezier(0, 0, r));
  paths.add(new Ellipse(0, 0, r));
  paths.add(new Gesture(0, 0, r));
  paths.add(new InfinitySymbol(0, 0, r));
  paths.add(new Line(0, 0, r));
  paths.add(new Lissajous(0, 0, r));
  paths.add(new Plot(0, 0, r));
  paths.add(new Rect(0, 0, r));
  paths.add(new Rose(0, 0, r));
  paths.add(new Segment(0, 0, r));
  paths.add(new Shape(0, 0, r));
  paths.add(new Superellipse(0, 0, r));
  paths.add(new Supershape(0, 0, r));
  return paths;
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
      float start = Path.remainder(t.getU()-dist, 1);
      float end = t.getU();
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

void mousePressed() {
  drawMode = (drawMode+1) % 4;
  setup();
}

void applyDrawMode() {
  switch (drawMode) {
    case 0 : 
      synchronizeTracing = true;
      drawSegments = true;
      break;
    case 1 : 
      synchronizeTracing = true;
      drawSegments = false;
      break;
    case 2 : 
      synchronizeTracing = false;
      drawSegments = false;
      break;
    case 3 :
      synchronizeTracing = false;
      drawSegments = true;
      break;
  }
}

void printDrawMode() {
  print(synchronizeTracing ? "Tracing speeds are synchronized " : "Tracing speeds are dependent on Path lengths ");
  println(drawSegments ? "and Path segments are being drawn." : "and Paths are being drawn.");
}