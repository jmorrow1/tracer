package tests.graphical;

import processing.core.PApplet;
import tracer.paths.Circle;
import tracer.paths.Path;

public class ProportionalityTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("tests.graphical.ProportionalityTest");
    }
    
    int rowSize = 2;
    int colSize = 2;
    int pathRadius = 75;
    Path[][] pathMatrix = new Path[rowSize][colSize];
    Path[] paths = Path.getOneOfEachPathType(pathRadius);
    int pathIndex = 0;
    
    public void settings() {
        size(600, 600, P2D);
    }
    
    public void setup() {
        initPaths();
        positionPaths();
        stylePaths();
        noLoop();
    }
    
    private void initPaths() {
        for (int i=0; i<rowSize; i++) {
            for (int j=0; j<colSize; j++) {
                pathMatrix[i][j] = paths[pathIndex].clone();
            }
        }
    }
    
    private void positionPaths() {
        int cellWidth = width / rowSize;
        int cellHeight = height / colSize;
        for (int i=0; i<rowSize; i++) {
            for (int j=0; j<colSize; j++) {
                float x = i*cellWidth + pathRadius + 0.5f * (cellWidth - 2*pathRadius);
                float y = j*cellHeight + pathRadius + 0.5f * (cellHeight - 2*pathRadius);
                pathMatrix[i][j].setCenter(x, y);
            }
        }
    }
    
    private void stylePaths() {
        for (int i=0; i<rowSize; i++) {
            for (int j=0; j<colSize; j++) {
                pathMatrix[i][j].setFill(false);
                pathMatrix[i][j].setStrokeWeight(1);
            }
        }
    }
    
    public void draw() {
        background(255);
        drawGrid();
        drawPaths();
        drawSegments();
    }
    
    public void mousePressed() {
        redraw();
    }
    
    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == LEFT) {
                pathIndex = (int)Path.remainder(pathIndex-1, paths.length);
            }
            else if (keyCode == RIGHT) {
                pathIndex = (int)Path.remainder(pathIndex+1, paths.length);
            }
        }
        initPaths();
        positionPaths();
        redraw();
    }
    
    private void drawGrid() {
        strokeWeight(2);
        for (int i=1; i<rowSize; i++) {
            int y = (height / rowSize) * i;
            line(0, y, width, y);
        }       
        for (int i=1; i<colSize; i++) {
            int x = (width / rowSize) * i;
            line(x, 0, x, width);
        }
    }
    
    private void drawPaths() {
        for (int i=0; i<rowSize; i++) {
            for (int j=0; j<colSize; j++) {
                pathMatrix[i][j].draw(g);
            }
        }
    }
    
    private void drawSegments() {        
        int cellWidth = width / rowSize;
        int cellHeight = height / colSize;
        for (int i=0; i<rowSize; i++) {
            for (int j=0; j<colSize; j++) {
                //compute segment
                float u1 = random(1);
                float u2 = random(1);
                
                if (u1 > u2) {
                    float temp = u1;
                    u1 = u2;
                    u2 = temp;
                }
                
                float segLength2D = pathMatrix[i][j].compute2DSegmentLength(u1, u2);
                float segLength1D = Path.compute1DSegmentLength(u1, u2);
                float ratio = segLength2D / segLength1D;
                
                //draw segment
                strokeWeight(4);
                noFill();
                pathMatrix[i][j].draw(g, u1, u2, true);
                
                //draw text segment
                fill(0);
                float x = i*cellWidth + pathRadius + 0.5f * (cellWidth - 2*pathRadius);
                float y = j*cellHeight + pathRadius + 0.5f * (cellHeight - 2*pathRadius) + pathRadius;
                String printOut = "segLength1D = " + segLength1D + "\n" + "segLength2D = " + segLength2D + "\n" + "segLength1D * path.getLength() = " + (segLength1D * pathMatrix[i][j].getLength()) + "\n" + "(segLength2D / segLength1D) = " + ratio;
                textAlign(CENTER, TOP);
                textSize(12);
                text(printOut, x, y);
            }
        }
    }
}
