import tracer.*;
import paths.*;
import render.*;
import ease.*;

Gesture gesture;
ArrayList<Path> paths = new ArrayList<Path>();
ArrayList<Tracer> ts = new ArrayList<Tracer>();
int prevt, t;

void setup() {
  size(600, 600);
  prevt = millis();
}

void draw() {  
  int dt = millis() - prevt;
  prevt = millis();
  
  t += dt;
  
  for (Tracer tracer : ts) {
    tracer.step(dt);
  }

  background(255);
  for (Path p : paths) {
    p.draw(g);
  }
  
  stroke(0);
  strokeWeight(8);
  for (Tracer tracer : ts) {
    point(tracer.x, tracer.y);
  }
}

void mousePressed() {
  gesture = new Gesture(new Point[] {}, new float[] {});
  paths.add(gesture);
  gesture.setFill(false);
  t = 0;
  gesture.addVertex(new Point(mouseX, mouseY), t);
}

void mouseDragged() {
  gesture.addVertex(new Point(mouseX, mouseY), t);
}

void mouseReleased() {
  float du = 1f / gesture.getTotalTime();
  float u = 0;
  for (int i=0; i<5; i++) {
    Tracer tracer = new Tracer(gesture, u, du);
    ts.add(tracer);
    u += 0.2;
  }
  paths.remove(gesture);
}