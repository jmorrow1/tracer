package tests.graphical;

import processing.core.PApplet;
import processing.event.MouseEvent;
import tracer.Point;
import tracer.paths.Rect;
import tracer.paths.Path;

public class RectModeTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main(RectModeTest.class);
    }
    
    private Point ab, cd; //control with left and right mouse buttons (mac users can use shift+left click instead of right click)
    private Rect rect;
    private int[] rectModes = new int[] {CENTER, CORNER, CORNERS, RADIUS};
    private int rectModeIndex = 0; //control with mouse wheel or left and right keys
    private String mouseBehavior = "";
    private boolean shift;

    public void settings() {
        size(400, 400);
    }
    
    public void setup() {
        ab = new Point(width/2, height/2);
        cd = new Point(150, 125);
        
        createRect();
        
        textSize(16);
    }
    
    private void createRect() {
        rect = new Rect(ab, cd, rectModes[rectModeIndex]);
        style(rect);
    }
    
    private void style(Path path) {
        path.setStrokeColor(color(20));
        path.setStrokeWeight(4);
        path.setFill(false);
    }
    
    public void draw() {
        background(255);
        
        //display rect mode
        textAlign(CENTER, TOP);
        fill(0);
        text("< " + Rect.rectModeToString(rectModes[rectModeIndex]) + " Mode"+ " >", width/2, 10);
        
        //draw the rect
        rect.draw(g);
        
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
                rectModeIndex = (int) Path.remainder(rectModeIndex-1, rectModes.length);
                createRect();
            }
            else if (keyCode == RIGHT) {
                rectModeIndex = (int) Path.remainder(rectModeIndex+1, rectModes.length);
                createRect();
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
        rectModeIndex = (int) Path.remainder(rectModeIndex + e.getCount(), rectModes.length);
        createRect();
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
        switch (rectModes[rectModeIndex]) {
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
        switch (rectModes[rectModeIndex]) {
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
