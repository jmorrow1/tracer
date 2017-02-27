import tracer.*;
import paths.*;
import render.*;
import ease.*;

//constants
public final static int CIRCLE_PATH = 0, SQUARE_PATH = 1, INFINITY_PATH = 2, LISSAJOUS_PATH = 3, ROSE_PATH = 4;
public final static int WIDTH = 700, HEIGHT = 700;

//parameters
public static int componentCount = 5;
public static float radiusDecay = 0.25;
public static float baseRadius = 180;
public static float baseTraceSpeed = 0.0001;
public static int traceSpeedMultiplier = -4;
public static int spirographColor = #C83232;
public static int pathType = LISSAJOUS_PATH;

//contextual parameters (may not be relevant depending on the path type)
public static int freqX = 5, freqY = 3; //used for lissajous paths and rose paths
public static float phi = radians(60); //used for lissajous paths

//draw mode
public static boolean drawComponents = true;
public static int stepsPerFrame = 10;

//paths
public static Path[] paths;
public static Shape spirograph;
public static boolean spirographComplete;

//animation
public static Tracer[] tracers;
public static float u = 0;

public static void main(String[] args) {
  PApplet.main("fractal_spirograph_construction_tool");
}

void settings() {
  size(WIDTH, HEIGHT, P2D);
}

void setup() {
  Gui.main(new String[] {});
  
  //stringToSketch("5 0.25 180.0 1.0E-4 -4 -3657166 2");
  startOver();
}

public static void startOver() {
  initComponentPaths();
  spirograph = new Shape();
  spirograph.setFill(false);
  spirograph.setStrokeColor(spirographColor);
  spirograph.setStrokeWeight(1.5f);
  u = 0;
  spirographComplete = false;
}

static void initComponentPaths() {
  float radius = baseRadius;
  float traceSpeed = baseTraceSpeed;
  
  paths = new Path[componentCount];
  tracers = new Tracer[componentCount];
  paths[0] = makePath(pathType, new Point(WIDTH/2, HEIGHT/2), radius);  
  tracers[0] = new Tracer(paths[0], 0, traceSpeed); 

  for (int i=1; i<componentCount; i++) {
    traceSpeed *= traceSpeedMultiplier;
    radius *= radiusDecay;
    paths[i] = makePath(pathType, tracers[i-1], radius);
    tracers[i] = new Tracer(paths[i], 0, traceSpeed);
  }
}

static Path makePath(int pathType, Point pt, float radius) {
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
  try {
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
  catch (Exception e) {
    //this is here to cope with the errors that can crop up if the Gui manipulates this sketch while execution is in the draw loop
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

String sketchToString() {
  return componentCount + " " + radiusDecay + " " + baseRadius + " " + baseTraceSpeed
    + " " + traceSpeedMultiplier + " " + spirographColor + " " + pathType;
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
}

void keyPressed() {
  if (key == 's' || key == 'S') {
    String sketchName = sketchToString() + ".png";
    save(sketchName);
    println("saved " + sketchName);
  }
}