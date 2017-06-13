import tracer.*;
import tracer.paths.*;
import tracer.easings.*;

Path pathA;
Tracer tracerA;

void setup() {
  size(400, 200);
  
  float traceStart = 0;
  float traceSpeed = 0.005;
  
  pathA = new Line(100, 100, 300, 100);
  CustomEasing easingA = new CustomEasing(3);
  tracerA = new Tracer(pathA, traceStart, traceSpeed, easingA);
}

void draw() {
  //update
  tracerA.step();

  //draw
  background(255);
  pathA.draw(g);
  drawDot(tracerA);
}

void drawDot(Tracer t) {
  ellipseMode(RADIUS);
  ellipse(t.x, t.y, 8, 8);
}