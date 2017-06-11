import tracer.*;
import tracer.paths.*;

//parameters
final int INFINITY_COUNT = 5;
boolean penMode = false;
float initRadius = 125; //the radius of the innermost infinity
float radiusDecay = 2.0 / 3.0; //change in radius going from the innermost infinity to the outermost infinity
float initTraceSpeed = 0.0002; //trace speed for the innermost tracer
float dTraceSpeed = 0.5 * initTraceSpeed; //change in trace speed going from the innermost tracer to the outermost tracer

//tracer
Tracer[] tracers = new Tracer[INFINITY_COUNT];
Path[] paths = new Path[INFINITY_COUNT];

//time
int prevt;

void setup() {
  size(600, 400, P2D);
  createPathsAndTracers();
  prevt = millis();
  background(255);
}

void createPathsAndTracers() {
  float radius = initRadius;
  float traceSpeed = initTraceSpeed;
  
  paths[0] = new InfinitySymbol(width/2, height/2, radius);
  paths[0].setStrokeWeight(1.5f);
  tracers[0] = new Tracer(paths[0], 0, traceSpeed);
  
  for (int i=1; i<INFINITY_COUNT; i++) {
    traceSpeed += dTraceSpeed;
    radius *= radiusDecay;
    paths[i] = new InfinitySymbol(tracers[i-1], radius);
    paths[i].setStrokeWeight(1.5f);
    tracers[i] = new Tracer(paths[i], 0, traceSpeed);
  }
}

void draw() {
  //update
  int dt = millis() - prevt;
  prevt = millis();  
  for (Tracer t : tracers) {
    t.step(dt);
  }
  
  //draw
  if (!penMode) {
    background(255);
    for (Path p : paths) {
      p.draw(g);
    }
  }
  else {
    Tracer t = tracers[tracers.length-1];
    strokeWeight(5);
    stroke(0);
    point(t.x, t.y);
  }
}

void mousePressed() {
  penMode = !penMode;
  background(255);
  println(penMode ? "Pen mode" : "Normal mode");
}