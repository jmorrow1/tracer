import tracer.*;
import paths.*;
import render.*;
import ease.*;

//parameters
int n = 4;
float radiusDecay = 0.5;
boolean penMode = false;
float initRadius = 75;
float traceSpeed = 0.0002;
float dTraceSpeed = 5f * traceSpeed / 6f;

//tracer
Tracer[] tracers = new Tracer[n];
Path[] paths = new Path[n];

//time
int prevt;

void setup() {
  size(600, 600, P2D);
  
  float radius = initRadius;
  
  paths[0] = new Circle(width/2, height/2, radius);
  tracers[0] = new Tracer(paths[0], 0, traceSpeed);
  
  for (int i=1; i<n; i++) {
    traceSpeed += dTraceSpeed;
    radius *= radiusDecay;
    paths[i] = new Circle(tracers[i-1], radius);
    tracers[i] = new Tracer(paths[i], 0, traceSpeed);
  }
  
  prevt = millis();
  
  background(255);
}

void draw() {
  int dt = millis() - prevt;
  prevt = millis();
  
  for (Tracer t : tracers) {
    t.step(dt);
  }
  
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
}