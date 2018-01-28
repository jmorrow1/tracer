import tracer.*;
import tracer.paths.*;
import tracer.renders.*;

//paths
public static Rose path;
public static ArrayList<Point> tracers;
public static ArrayList<Path> metapaths;

//render
public static Render render;

//parameters
public static int freq1 = 3;
public static int freq2 = 5;
public static int tracerCount = 20;
public static float speed = 0.0004;

//draw mode
public final static int MESH = 0, CLIQUE = 1, SHP = 2, VORONOI = 3;
public static int renderMode = MESH;
public static boolean drawMetapaths = false;
public static boolean drawRender = true;

public static void main(String[] args) {
  PApplet.main("roses_tool");
}

void settings() {
  size(500, 500, P2D);
}

void setup() {
  Gui.main(new String[0]);
  startOver();
}

static void startOver() {
  tracers = new ArrayList<Point>();
  metapaths = new ArrayList<Path>();
  
  path = new Rose(250, 250, 200);
  path.setFreq1(freq1);
  path.setFreq2(freq2);
  path.setSampleCount(200);

  for (int i=0; i<tracerCount; i++) {
    Tracer tracer = new Tracer(path, i * (1.0 / tracerCount), speed);
    Path metapath = new Circle(tracer, 10);
    metapath.setStroke(false);
    metapath.setSampleCount(75);
    tracers.add(tracer);
    metapaths.add(metapath);
  }
  
  switchRenderMode();
}

static void switchRenderMode() {
  switch (renderMode) {
    case MESH :
      RenderMesh r = new RenderMesh(tracers, 90); //TODO paramaterize
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
  try {
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
  catch (Exception e) {
    //this is here to cope with the errors that can crop up if the Gui manipulates this sketch while execution is in the draw loop
  }
}

void mousePressed() {
  renderMode = (renderMode+1) % 4;
  switchRenderMode();
}