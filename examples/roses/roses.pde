import tracer.*;
import tracer.paths.*;
import tracer.renders.*;

//paths
Rose path;
ArrayList<Point> tracers = new ArrayList<Point>();
ArrayList<Path> metapaths = new ArrayList<Path>();

//render
Render render;

//parameters
int freq1 = 3;
int freq2 = 5;
int n = 20;
float speed = 0.0004;
int minDist = 90;

//draw mode
final static int MESH = 0, CLIQUE = 1, SHP = 2, VORONOI = 3;
int renderMode = SHP;
boolean drawMetapaths = false;
boolean drawRender = true;

void settings() {
  size(500, 500, P2D);
}

void setup() {
  path = new Rose(250, 250, 200);
  path.setFreq1(freq1);
  path.setFreq2(freq2);
  path.setSampleCount(200);

  for (int i=0; i<n; i++) {
    Tracer tracer = new Tracer(path, i * (1.0 / n), speed);
    Path metapath = new Circle(tracer, 10);
    metapath.setStroke(false);
    metapath.setSampleCount(75);
    tracers.add(tracer);
    metapaths.add(metapath);
  }
  
  switchRenderMode();
}

void switchRenderMode() {
  switch (renderMode) {
    case MESH :
      RenderMesh r = new RenderMesh(tracers, minDist);
      r.setStrokeRamp(80);
      r.setStrokeWeight(4);
      render = r;
      break;
    case CLIQUE :
      render = new RenderClique(tracers);
      render.setStrokeWeight(2);
      break;
    case SHP :
      render = new RenderShape(tracers);
      render.setStrokeWeight(4);
      break;
    case VORONOI :
      //render = new RenderVoronoi(tracers);
      break;
  }
  
  render.setStrokeColor(10);
  render.setFill(false);
}

void draw() {
  background(200, 100, 100);

  render.step();

  if (drawRender) {    
    render.draw(g);
  }

  if (drawMetapaths) {
    for (Path p : metapaths) {
      p.draw(g);
    }
  }
}

void mousePressed() {
  renderMode = (renderMode+1) % 4;
  switchRenderMode();
}