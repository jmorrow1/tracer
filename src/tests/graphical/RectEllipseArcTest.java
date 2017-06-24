package tests.graphical;

import processing.core.PApplet;
import processing.event.MouseEvent;
import tracer.Point;
import tracer.paths.Arc;
import tracer.paths.Ellipse;
import tracer.paths.Path;
import tracer.paths.Rect;

public class RectEllipseArcTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main(RectEllipseArcTest.class);
    }
    
    private int[] modes = new int[] {CENTER, CORNER, CORNERS, RADIUS};
    private final int RECT = 0, ELLIPSE = 1, ARC = 2;
    
    private Point ab, cd; //control with left and right mouse buttons (mac users can use shift+left click instead of right click)
    private int modeIndex = 0; //control with mouse wheel or left and right keys
    private int pathMode = RECT; //control with space bar    
    
    private Path path;
    private String mouseBehavior = "";
    private boolean shift;

    public void settings() {
        size(400, 400);
    }
    
    public void setup() {
        ab = new Point(width/2, height/2);
        cd = new Point(150, 125);
        
        createPath();
        
        textSize(16);
    }
    
    private void createPath() {
        switch (pathMode) {
            case RECT :
                path = new Rect(ab, cd, modes[modeIndex]);
                break;
            case ELLIPSE :
                path = new Ellipse(ab, cd, modes[modeIndex]);
                break;
            case ARC :
                path = new Arc(ab, cd, 0.1f * TWO_PI, 0.7f * TWO_PI, modes[modeIndex]);
                break;
        }
        style(path);
    }
    
    private void style(Path path) {
        path.setStrokeColor(color(20));
        path.setStrokeWeight(4);
        path.setFill(false);
    }
    
    public void draw() {
        background(255);
        
        //display ellipse/rect mode
        textAlign(CENTER, TOP);
        fill(0);
        text("< " + Ellipse.ellipseModeToString(modes[modeIndex]) + " Mode"+ " >", width/2, 10);
        
        //draw the path
        path.draw(g);
        
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
                modeIndex = (int) Path.remainder(modeIndex-1, modes.length);
                createPath();
            }
            else if (keyCode == RIGHT) {
                modeIndex = (int) Path.remainder(modeIndex+1, modes.length);
                createPath();
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
        else if (key == ' ') {
            pathMode = (pathMode + 1) % 3;
            createPath();
        }
    }
    
    public void mouseWheel(MouseEvent e) {
        modeIndex = (int) Path.remainder(modeIndex + e.getCount(), modes.length);
        createPath();
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
        switch (modes[modeIndex]) {
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
        switch (modes[modeIndex]) {
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
