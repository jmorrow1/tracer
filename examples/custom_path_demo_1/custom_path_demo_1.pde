import tracer.paths.*;
import tracer.*;

Square square;
Tracer tracer;

void setup() {
  size(300, 300);
  square = new Square(150, 150, 200);
  square.setFillColor(#FFF36A);
  square.setStrokeColor(#222222);
  square.setStrokeWeight(3);
  tracer = new Tracer(square, 0, 0.004);
}

void draw() {
  //update
  tracer.step();
  
  //draw
  background(255);
  square.draw(g);
  drawTracer(tracer);
}

void drawTracer(Tracer t) {
  strokeWeight(12);
  stroke(#222222);
  point(t.x, t.y);
}