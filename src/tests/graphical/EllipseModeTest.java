package tests.graphical;

import processing.core.PApplet;
import processing.event.MouseEvent;
import tracer.Point;
import tracer.paths.Ellipse;
import tracer.paths.Path;

public class EllipseModeTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main(EllipseModeTest.class);
    }
    
    private Point ab, cd; //control with left and right mouse buttons (mac users can use shift+left click instead of right click)
    private Ellipse ellipse;
    private int[] ellipseModes = new int[] {CENTER, CORNER, CORNERS, RADIUS};
    private int ellipseModeIndex = 0; //control with mouse wheel or left and right keys
    private String mouseBehavior = "";
    private boolean shift;

    public void settings() {
        size(400, 400);
    }
    
    public void setup() {
        ab = new Point(width/2, height/2);
        cd = new Point(150, 125);
        
        createEllipse();
        
        textSize(16);
    }
    
    private void createEllipse() {
        ellipse = new Ellipse(ab, cd, ellipseModes[ellipseModeIndex]);
        style(ellipse);
    }
    
    private void style(Path path) {
        path.setStrokeColor(color(20));
        path.setStrokeWeight(4);
        path.setFill(false);
    }
    
    public void draw() {
        background(255);
        
        //display ellipse mode
        textAlign(CENTER, TOP);
        fill(0);
        text("< " + Ellipse.ellipseModeToString(ellipseModes[ellipseModeIndex]) + " Mode"+ " >", width/2, 10);
        
        //draw the ellipse
        ellipse.draw(g);
        
        //draw the control points
        drawDot(ab);
        drawDot(cd);
        
        //display information about what the mouse is doing
        textAlign(CENTER, BOTTOM);
        fill(0);
        text(mouseBehavior, width/2, height - 10);
    }
    
    private void drawDot(Point pt) {
        strokeWeight(8);
        stroke(0);
        point(pt.x, pt.y);
    }
    
    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == LEFT) {
                ellipseModeIndex = (int) Path.remainder(ellipseModeIndex-1, ellipseModes.length);
                createEllipse();
            }
            else if (keyCode == RIGHT) {
                ellipseModeIndex = (int) Path.remainder(ellipseModeIndex+1, ellipseModes.length);
                createEllipse();
            }
            else if (keyCode == SHIFT) {
                shift = true;
            }
        }
    }
    
    public void keyReleased() {
        if (key == CODED) {
            if (keyCode == SHIFT) {
                shift = false;
            }
        }
    }
    
    public void mouseWheel(MouseEvent e) {
        ellipseModeIndex = (int) Path.remainder(ellipseModeIndex + e.getCount(), ellipseModes.length);
        createEllipse();
    }
    
    public void mousePressed() {
        if (mouseButton == LEFT && !shift) {
            ab.set(mouseX, mouseY);
            mouseBehavior = "Moving " + abToString();
        }
        else if (mouseButton == RIGHT || (shift && mouseButton == LEFT)) {
            cd.set(mouseX, mouseY);
            mouseBehavior = "Moving " + cdToString();
        }
    }
    
    public void mouseDragged() {
        if (mouseButton == LEFT && !shift) {
            ab.set(mouseX, mouseY);
            mouseBehavior = "Moving " + abToString();
        }
        else if (mouseButton == RIGHT || (shift && mouseButton == LEFT)) {
            cd.set(mouseX, mouseY);
            mouseBehavior = "Moving " + cdToString();
        }
    }
    
    public void mouseReleased() {
        mouseBehavior = "";
    }
    
    private String abToString() {
        switch (ellipseModes[ellipseModeIndex]) {
            case CORNER:
            case CORNERS:
                return "left corner";
            case CENTER:
            case RADIUS:
                return "center";
            default :
                return "nothing";
        }
    }
    
    private String cdToString() {
        switch (ellipseModes[ellipseModeIndex]) {
            case CORNERS:
                return "right corner";
            case CORNER:
            case CENTER:
                return "width and height";
            case RADIUS:
                return "x-radius and y-radius";
            default:
                return "nothing";
        }
    }
    
}
