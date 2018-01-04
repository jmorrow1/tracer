package demos;

import java.util.ArrayList;

import processing.core.PApplet;
import tracer.Tracer;
import tracer.paths.Circle;
import tracer.paths.Path;
import tracer.renders.Render;
import tracer.renders.RenderMesh;

public class RenderDemo extends PApplet {
    
    public static void main(String[] args) {
        PApplet.main("demos.RenderDemo");
    }
    
    ArrayList<Tracer> tracers = new ArrayList<Tracer>();
    ArrayList<Tracer> metatracers = new ArrayList<Tracer>();
    ArrayList<Path> paths = new ArrayList<Path>();
    Render render;
    
    int prevt;
    
    public void settings() {
        size(600, 600);
    }
    
    public void setup() {
        
        //create paths and tracers
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
        
        //create render
        render = new RenderMesh(metatracers, 100);
//        render = new RenderVoronoi(metatracers);
        render.setFillColor(color(40));
        render.setStroke(false);
        render.setStrokeWeight(2);
        
        //initialize record of time
        prevt = millis();
    }

    /********************
     ***** Behavior *****
     ********************/
    
    public void draw() {
        //update
        int dt = millis() - prevt;
        prevt = millis();
        update(tracers, dt);
        update(metatracers, dt);
        
        //draw
        background(255);  
//        drawPaths();
//        draw(tracers);
        draw(metatracers);
        render.draw(g);
    }
    
    private void update(ArrayList<Tracer> ts, int dt) {
        for (Tracer t : ts) {
            t.step(dt);
        }
    }
    
    private void drawPaths() {
        for (Path p : paths) {
            p.draw(g);
        }
    }

    private void draw(ArrayList<Tracer> ts) {
        stroke(10);
        strokeWeight(6);
        for (Tracer t : ts) {
            point(t.x, t.y);
        }
    }
    
    /******************
     ***** Events *****
     ******************/

    /*******************
     ***** Getters *****
     *******************/
}
