package tracer.paths;

import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;
import tracer.Point;

public class MultiShape extends Path {
    private ArrayList<Shape> shapes = new ArrayList<Shape>();
    private ArrayList<Float> shapeEndPts1D = new ArrayList<Float>();
    private int vertexCount;

    /**************************
     ***** Initialization *****
     **************************/
    
    public MultiShape() {}
    
    public MultiShape(float x, float y, float r) {
        Random rng = new Random();
        
        int segmentCount = 5;
        float dRadius = r / (1.25f * segmentCount);
        float samplesPerLengthInPixels = 0.25f;
        
        //set looping variables
        float radius = r;
        
        for (int i=0; i<segmentCount; i++) {
            //compute segment
            float startAngle = rng.nextFloat() * TWO_PI;
            float endAngle = startAngle + PApplet.lerp(0.25f * PI, 0.75f * PI, rng.nextFloat());
      
            //compute how many samples to take of segment
            float circum = PApplet.sq(PI * radius);
            float arcLength = circum * ((endAngle - startAngle) / TWO_PI);
            float samplesPerThisSegment = arcLength * samplesPerLengthInPixels;
            
            //take samples of segment
            for (int j=0; j<samplesPerThisSegment; j++) {
                float angle = PApplet.map(j, 0, samplesPerThisSegment, startAngle, endAngle);
                Point sample = new Point(x + radius * PApplet.cos(angle), y + radius * PApplet.sin(angle));
                addVertex(sample);
            }
            
            //add a gap between segments
            boolean notLastSegment = (i != segmentCount-1);
            if (notLastSegment) {
                addGap();
            }
            
            radius -= dRadius;
        }
    }
    
    public MultiShape(MultiShape multiShape) {
        for (Shape s : multiShape.shapes) {
            shapes.add(s.clone());
        }
        
        for (Float f : multiShape.shapeEndPts1D) {
            shapeEndPts1D.add(f);
        }
        
        this.vertexCount = multiShape.vertexCount;
    }
    
    private void computePathEndPts1D() {
        shapeEndPts1D.clear();
        
        float totalLength = 0;
        for (Shape s : shapes) {
            totalLength += s.getLength();
        }
        
        float prevShapeEndPt1D = 0;
        shapeEndPts1D.add(prevShapeEndPt1D);
        for (Shape s : shapes) {
            float shapeEndPt1D = s.getLength() / totalLength;
            shapeEndPt1D += prevShapeEndPt1D;
            shapeEndPts1D.add(shapeEndPt1D);
            prevShapeEndPt1D = shapeEndPt1D;
        }
    }

    /********************
     ***** Behavior *****
     ********************/
    
    @Override
    public void trace(Point target, float u) {
        u = Path.remainder(u, 1.0f);
        
        for (int i = 1; i < shapeEndPts1D.size(); i++) {
            float coord = shapeEndPts1D.get(i);
            if (u < coord) {
                float prevCoord = shapeEndPts1D.get(i - 1);
                float v = PApplet.map(u, prevCoord, coord, 0, 1);

                Shape shape = shapes.get(i-1);

                shape.trace(target, v);
                break;
            }
        }
    }

    /******************
     ***** Events *****
     ******************/
    
    public void makeProportional() {
        computePathEndPts1D();
    }

    @Override
    public void translate(float dx, float dy) {
        for (Shape s : shapes) {
            s.translate(dx, dy);
        }
    }
    
    public void clear() {
        shapes.clear();
        shapeEndPts1D.clear();
        vertexCount = 0;
    }
    
    public void addVertex(Point pt) {
        if (shapes.size() == 0) {
            shapes.add(new Shape());
        }
        
        Shape lastShape = shapes.get(shapes.size()-1);
        lastShape.addVertex(pt);
        
        vertexCount++;
        
        computePathEndPts1D();
    }
    
    public void addGap() {
        shapes.add(new Shape());
        computePathEndPts1D();
    }
    
    public void removeVertex(int i) {
        for (Shape s : shapes) {
            if (i >= s.getVertexCount()) {
                i -= s.getVertexCount();
            }
            else {
                s.removeVertex(i);
                vertexCount--;
                computePathEndPts1D();
                return;
            }
        }
        
        throw new IndexOutOfBoundsException("Index " + i + " Size " + vertexCount);
    }
    
    public void setVertex(int i, Point pt) {
        for (Shape s : shapes) {
            if (i >= s.getVertexCount()) {
                i -= s.getVertexCount();
            }
            else {
                s.setVertex(i, pt);
                computePathEndPts1D();
                return;
            }
        }
        
        throw new IndexOutOfBoundsException("Index " + i + " Size " + vertexCount);
    }

    /*******************
     ***** Getters *****
     *******************/
    
    public int getVertexCount() {
        return vertexCount;
    }
    
    public Point getVertex2D(int i) {
        for (Shape s : shapes) {
            if (i >= s.getVertexCount()) {
                i -= s.getVertexCount();
            }
            else {
                return s.getVertex2D(i);
            }
        }
        
        throw new IndexOutOfBoundsException("Index " + i + " Size " + vertexCount);
    }
    
    public float getVertex1D(int i) {
        for (Shape s : shapes) {
            if (i >= s.getVertexCount()) {
                i -= s.getVertexCount();
            }
            else {
                return s.getVertex1D(i);
            }
        }
        
        throw new IndexOutOfBoundsException("Index " + i + " Size " + vertexCount);
    }
    
    public float getLength() {
        float length = 0;
        for (Shape s : shapes) {
            length += s.getLength();
        }
        return length;
    }
    
    @Override
    public MultiShape clone() {
        return new MultiShape(this);
    }

    @Override
    public int getGapCount() {
        return shapes.size();
    }

    @Override
    public float getGap(int i) {
        return shapeEndPts1D.get(i);
    }
}
