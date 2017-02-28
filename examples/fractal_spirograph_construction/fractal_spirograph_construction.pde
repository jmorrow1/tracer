import tracer.*;
import paths.*;
import render.*;
import ease.*;

//constants
final static int CIRCLE_PATH = 0, SQUARE_PATH = 1, INFINITY_PATH = 2, LISSAJOUS_PATH = 3, ROSE_PATH = 4;

//parameters
int componentCount = 5;
float radiusDecay = 0.25;
float baseRadius = 180;
float baseTraceSpeed = 0.0001;
int traceSpeedMultiplier = -4;
int spirographColor = #C83232;
int pathType = LISSAJOUS_PATH;

//contextual parameters (may not be relevant depending on the path type)
int freqX = 5, freqY = 3; //used for lissajous paths and rose paths
float phi = radians(60); //used for lissajous paths

//draw mode
boolean drawComponents = true;
int stepsPerFrame = 10;

//paths
Path[] paths = new Path[componentCount];
Shape spirograph;
boolean spirographComplete = false;

//animation
Tracer[] tracers = new Tracer[componentCount];
float u = 0;

void setup() {
  size(600, 600, P2D);

  stringToSketch("5 0.25 180.0 1.0E-4 -4 -3657166 3 3 4 1.0471976");
  initComponentPaths();
  spirograph = new Shape();
  spirograph.setFill(false);
  spirograph.setStrokeColor(spirographColor);
  spirograph.setStrokeWeight(1.5f);
}

void initComponentPaths() {
  float radius = baseRadius;
  float traceSpeed = baseTraceSpeed;
  
  paths[0] = makePath(pathType, new Point(width/2, height/2), radius);  
  tracers[0] = new Tracer(paths[0], 0, traceSpeed); 

  for (int i=1; i<componentCount; i++) {
    traceSpeed *= traceSpeedMultiplier;
    radius *= radiusDecay;
    paths[i] = makePath(pathType, tracers[i-1], radius);
    tracers[i] = new Tracer(paths[i], 0, traceSpeed);
  }
}

Path makePath(int pathType, Point pt, float radius) {
  switch (pathType) {
    case CIRCLE_PATH : return new Circle(pt, radius);
    case SQUARE_PATH : return new Rect(pt, radius, radius, RADIUS);
    case INFINITY_PATH : return new InfinitySymbol(pt, radius, 0.75*radius, 100);
    case LISSAJOUS_PATH : return new Lissajous(100, pt, radius, radius, freqX, freqY, phi);
    case ROSE_PATH : return new Rose(pt, radius, freqX, freqY, 100);
    default : return new Circle(pt, radius);
  }
}

void draw() {
  //update
  for (int i=0; i<stepsPerFrame; i++) {
    step();
  }
  
  //draw
  background(255);
  if (drawComponents) {
    //draw component paths
    for (Path p : paths) {
      p.draw(g);
    }
  }
 
  //draw the spirograph
  spirograph.draw(g);
  
  if (drawComponents) {
    //draw dot at position of last tracer
    Point pt = tracers[componentCount-1];
    strokeWeight(6);
    stroke(spirographColor);
    point(pt.x, pt.y);
  }
}

void step() {
  //update tracers by the time step
  for (Tracer t : tracers) {
    t.step();
  }
  u += baseTraceSpeed;
  if (u > 1) {
    u -= 1;
    spirographComplete = true;
  }
  
  Point pt = new Point(tracers[componentCount-1]);
  if (!spirographComplete) {
    //add the position of last tracer to the spirograph
    spirograph.addVertex(pt);
  }
}

void mousePressed() {
  drawComponents = !drawComponents;
}

String sketchToString() {
  return componentCount + " " + radiusDecay + " " + baseRadius + " " + baseTraceSpeed
    + " " + traceSpeedMultiplier + " " + spirographColor + " " + pathType + " " +
    freqX + " " + freqY + " " + phi;
}

void stringToSketch(String s) {
  String[] ss = s.split(" ");
  componentCount = int(ss[0]);
  radiusDecay = float(ss[1]);
  baseRadius = float(ss[2]);
  baseTraceSpeed = float(ss[3]);
  traceSpeedMultiplier = int(ss[4]);
  spirographColor = int(ss[5]);
  pathType = int(ss[6]);
  freqX = int(ss[7]);
  freqY = int(ss[8]);
  phi = float(ss[9]);
}

void keyPressed() {
  if (key == 's' || key == 'S') {
    String sketchName = sketchToString() + ".png";
    save(sketchName);
    println("saved " + sketchName);
  }
}