import tracer.*;
import tracer.paths.*;
import tracer.renders.*;
import java.util.Collection;

CustomRender render;

void setup() {
  size(600, 600);
  render = new CustomRender();
  render.setStrokeWeight(10);
  
  Path path = new Rect(width/2, height/2, 200, 200, RADIUS);
  
  for (int i=0; i<100; i++) {
    float traceSpeed = random(-0.001, 0.001);
    render.add(new Tracer(path, 0, traceSpeed));
  }
}

void draw() {
  background(255);
  render.draw(g);
  render.step();
}