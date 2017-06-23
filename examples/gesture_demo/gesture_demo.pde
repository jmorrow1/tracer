import tracer.*;
import tracer.paths.*;

Gesture gesture;
ArrayList<Path> paths = new ArrayList<Path>();
ArrayList<Trail> trails = new ArrayList<Trail>();
int prevt, t;
int strokeColor = #C83232;
int strokeWeight = 6;

void setup() {
  size(600, 600);
  prevt = millis();
}

void draw() {  
  int dt = millis() - prevt;
  prevt = millis();
  
  t += dt;

  background(255);
  for (Path p : paths) {
    p.draw(g);
  }
  
  for (Trail t : trails) {
    t.step();
    t.draw(g);
  }
}

void mousePressed() {
  gesture = new Gesture(new Point[] {}, new float[] {});
  gesture.setStrokeColor(strokeColor);
  gesture.setStrokeWeight(strokeWeight);
  paths.add(gesture);
  gesture.setFill(false);
  t = 0;
  gesture.addVertex(new Point(mouseX, mouseY), t);
}

void mouseDragged() {
  gesture.addVertex(new Point(mouseX, mouseY), t);
}

void mouseReleased() {
  float du = 3f / gesture.getDuration();
  Tracer tracer = new Tracer(gesture, 0, du);
  trails.add(new Trail(tracer, 100, strokeColor, strokeWeight));
  paths.remove(gesture);
}