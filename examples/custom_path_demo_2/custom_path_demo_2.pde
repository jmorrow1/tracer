import tracer.paths.*;
import tracer.*;

Spiral spiral;
Tracer tracer;

void setup() {
  size(300, 300, P2D);
  spiral = new Spiral(150, 150, 100, 12, 3);
  spiral.setStrokeColor(#222222);
  spiral.setStrokeWeight(3);
  tracer = new Tracer(spiral, 0, 0.004);
}

void draw() {
  //update
  tracer.step();
  
  //draw
  background(255);
  spiral.draw(g);
  drawTracer(tracer);
}

void drawTracer(Tracer t) {
  strokeWeight(12);
  stroke(#222222);
  point(t.x, t.y);
}