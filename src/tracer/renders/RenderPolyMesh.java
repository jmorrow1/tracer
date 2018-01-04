package tracer.renders;

import java.util.ArrayList;

import processing.core.PGraphics;
import tracer.Point;

public class RenderPolyMesh extends Render {
    ArrayList<Point>[][] buckets;
    
    /**************************
     ***** Initialization *****
     **************************/

    /********************
     ***** Behavior *****
     ********************/
    
    @Override
    public void draw(PGraphics g) {
        // TODO Sort points into buckets
        // TODO For each pt, recursively check for close neighbors, 
            //add them to the polygon, and remove them from the buckets
        // TODO Sort the polygons vertices by psuedo-angle
        // TODO Draw the polygon
    }

    /******************
     ***** Events *****
     ******************/

    /*******************
     ***** Getters *****
     *******************/

    /*******************
     ***** Statics *****
     *******************/
}
