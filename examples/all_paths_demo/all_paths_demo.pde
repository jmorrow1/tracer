import tracer.*;
import paths.*;
import render.*;
import ease.*;

//paths
ArrayList<Path> paths = new ArrayList<Path>(); 

//tracers
ArrayList<Tracer> tracers = new ArrayList<Tracer>();

//memory
Point pt = new Point(0, 0);

//animation
float u = 0;
float du = 0.005;

//layout
int cellSize = 100;

//draw mode
boolean synchronizeTracing = false;
boolean drawSegments = false;

void settings() {
  size(600, 600, P2D);
}

void setup() {
  paths = createPaths(0.4 * cellSize);
  for (Path p : paths) {
    p.setStrokeWeight(1.5f);
    p.setStrokeColor(0);
    p.setFill(false);
    if (p instanceof Plot) {
      System.out.println(p.getTotalDistance());
    }
  }
  reposition(paths, cellSize);
  createTracers(paths);
}

void createTracers(ArrayList<Path> paths) {
  for (Path p : paths) {
    float tracerSpeed = (synchronizeTracing) ? du : (du*200)/p.getTotalDistance();
    tracers.add(new Tracer(p, 0, tracerSpeed));
  }
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
      p.draw(g, u, (u+0.5) % 1);
    }
  }

  //draw tracers
  strokeWeight(6);
  stroke(0);
  for (Tracer t : tracers) {
    point(t.x, t.y);
  }
}