import tracer.*;
import tracer.paths.*;
import tracer.renders.*;

//flags:
boolean drawTracers = false; //toggle with '1' key
boolean drawMetatracers = true; //toggle with '2' key
boolean drawPaths = false; //toggle with '3' key

//tracer stuff:
ArrayList<Tracer> tracers = new ArrayList<Tracer>();
ArrayList<Tracer> metatracers = new ArrayList<Tracer>();
ArrayList<Path> paths = new ArrayList<Path>();
Render render;

//time record:
int prevt;

void setup() {
  size(600, 600);
  createPathsAndTracers();
  createRender(metatracers); 
  prevt = millis();
}

void createRender(ArrayList<Tracer> ts) {
  render = new RenderMesh(ts, 100);
  render.setFillColor(color(40));
  render.setStroke(false);
  render.setStrokeWeight(1.5f);
}

void createPathsAndTracers() {
  Path orbit = new Circle(width/2, height/2, 200);
  float speed1 = 0.00003f;
  float r = 100;    
  for (int i=0; i<50; i++) {
    float direction1 = (random(1) < 0.5 ? -1 : 1);
    Tracer t1 = new Tracer(orbit, random(1), speed1 * direction1);
    tracers.add(t1);
    
    Path p = new Circle(t1, r);
    p.setFill(false);
    paths.add(p);
    
    float speed2 = speed1 * floor(random(2, 10));
    float direction2 = (random(1) < 0.5 ? -1 : 1);
    Tracer t2 = new Tracer(p, random(1), speed2 * direction2);
    metatracers.add(t2);        
  }
}

void draw() {
  //update
  int dt = millis() - prevt;
  prevt = millis();
  update(tracers, dt);
  update(metatracers, dt);
  
  //draw
  background(255);
  if (drawTracers) draw(tracers);
  if (drawMetatracers) draw(metatracers);
  if (drawPaths) drawPaths();
  render.draw(g);
  
}

void drawPaths() {
  for (Path p : paths) {
    p.draw(g);
  }
}

void update(ArrayList<Tracer> ts, int dt) {
  for (Tracer t : ts) {
    t.step(dt);
  }
}

void draw(ArrayList<Tracer> ts) {
  stroke(10);
  strokeWeight(6);
  for (Tracer t : ts) {
    point(t.x, t.y);
  }
}

void keyPressed() {
  switch (key) {
    case '1' : 
      drawTracers = !drawTracers;
      println(drawTracers ? "drawing tracers" : "not drawing tracers");
      break;
    case '2' : 
      drawMetatracers = !drawMetatracers;
      println(drawMetatracers ? "drawing metatracers" : "not drawing metatracers");
      break;
    case '3' :
      drawPaths = !drawPaths;
      println(drawPaths ? "drawing paths" : "not drawing paths");
      break;
  }
}