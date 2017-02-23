import tracer.*;
import paths.*;
import render.*;
import ease.*;

//parameters
int n = 5;
boolean penMode = false; 
float radiusDecay = 2.0 / 3.0;
float initRadius = 125;
float initTraceSpeed = 0.0002;
float dTraceSpeed = 0.5 * initTraceSpeed;

//tracer
Tracer[] tracers = new Tracer[n];
Path[] paths = new Path[n];

//time
int prevt;

void setup() {
  size(600, 400, P2D);
  
  float radius = initRadius;
  float traceSpeed = initTraceSpeed;
  
  paths[0] = new InfinitySymbol(width/2, height/2, radius);
  paths[0].setStrokeWeight(1.5f);
  tracers[0] = new Tracer(paths[0], 0, traceSpeed);
  
  for (int i=1; i<n; i++) {
    traceSpeed += dTraceSpeed;
    radius *= radiusDecay;
    paths[i] = new InfinitySymbol(tracers[i-1], radius);
    paths[i].setStrokeWeight(1.5f);
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