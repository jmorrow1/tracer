package tests.graphical;

import processing.core.PApplet;
import tracer.paths.Path;

public class SetCenterTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("tests.graphical.SetCenterTest");
    }
    
    Path[] paths = Path.getOneOfEachPathType(100);
    int currPathIndex = 0;
    
    public void settings() {
        size(800, 600, P2D);
    }
    
    public void setup() {
        System.out.println(paths[currPathIndex]);
    }
    
    public void draw() {
        background(255);
        paths[currPathIndex].draw(g);
    }
    
    public void mouseMoved() {
        paths[currPathIndex].setCenter(mouseX, mouseY);
    }
    
    public void mouseDragged() {
        paths[currPathIndex].setCenter(mouseX, mouseY);
    }
    
    public void mousePressed() {
        currPathIndex = (currPathIndex+1) % paths.length;
        paths[currPathIndex].setCenter(mouseX, mouseY);
        System.out.println(paths[currPathIndex]);
    }
}
