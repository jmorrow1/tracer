package tracer.paths;

import processing.core.PApplet;
import tracer.Point;

/**
 * 
 * Functions for creating polygonal paths.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Polygonize {
    
    //this file is intended to group a bunch of static functions, so this class need never be initialized
    private Polygonize() {} 


    /**
     * Makes a regular polygon, a polygon circumscribed in a circle with its
     * angles equally spaced apart.
     * 
     * @param c
     * @param numPoints
     * @param startAngle
     * @return
     */
    public static Shape makeRegularPolygon(Circle c, int numPoints, float startAngle) {
        return makePolygon(c.getCenx(), c.getCeny(), c.getRadius(), c.getRadius(), numPoints, startAngle);
    }

    /**
     * Makes a regular polygon, a polygon circumscribed in a circle with its
     * angles equally spaced apart.
     * 
     * @param cenx
     * @param ceny
     * @param radius
     * @param numPoints
     * @param startAngle
     */
    public static Shape makeRegularPolygon(float cenx, float ceny, float radius, int numPoints, float startAngle) {
        return makePolygon(cenx, ceny, radius, radius, numPoints, startAngle);
    }

    /**
     * Makes a polygon circumscribed in an ellipse, with its angles equally
     * spaced from each other (analogous to a regular polygon).
     * 
     * @param e
     * @param numPoints
     * @param startAngle
     * @return
     */
    public static Shape makePolygon(Ellipse e, int numPoints, float startAngle) {
        return makePolygon(e.getCenx(), e.getCeny(), 0.5f * e.getWidth(), 0.5f * e.getHeight(), numPoints, startAngle);
    }

    /**
     * Makes a polygon circumscribed in an ellipse, with its angles equally
     * spaced from each other (analogous to a regular polygon).
     * 
     * @param cenx
     * @param ceny
     * @param halfWidth
     * @param halfHeight
     * @param numVertices
     * @param startAngle
     * @return
     */
    public static Shape makePolygon(float cenx, float ceny, float halfWidth,
            float halfHeight, int numVertices, float startAngle) {
        if (numVertices > 0) {
            float theta = startAngle;
            float dTheta = PApplet.TWO_PI / numVertices;
            Point[] vertices = new Point[numVertices + 1];
            for (int i = 0; i < numVertices; i++) {
                vertices[i] = new Point(cenx + halfWidth * PApplet.cos(theta),
                        ceny + halfHeight * PApplet.sin(theta));
                theta += dTheta;
            }
            vertices[numVertices] = vertices[0].clone();
            return new Shape(vertices);
        } 
        else {
            return new Shape(new Point[] {});
        }
    }

    /**
     * Makes a polygon circumscribed in a circle, with its vertices positioned
     * along the circle at the given circles.
     * 
     * @param c
     * @param angles
     * @return
     */
    public static Shape makePolygon(Circle c, float[] angles) {
        return makePolygon(c.getCenx(), c.getCeny(), c.getRadius(), c.getRadius(), angles);
    }

    /**
     * Makes a polygon circumscribed in a circle, with its vertices positioned
     * along the circle at the given circles.
     * 
     * @param cenx
     * @param ceny
     * @param radius
     * @param angles
     * @return
     */
    public static Shape makePolygon(float cenx, float ceny, float radius, float[] angles) {
        return makePolygon(cenx, ceny, radius, radius, angles);
    }

    /**
     * Makes a polygon circumscribed in an ellipse, with its vertices positioned
     * along the ellipse at the given angles.
     * 
     * @param e
     * @param angles
     * @return
     */
    public static Shape makePolygon(Ellipse e, float[] angles) {
        return makePolygon(e.getCenx(), e.getCeny(), 0.5f * e.getWidth(), 0.5f * e.getHeight(), angles);
    }

    /**
     * Makes a polygon circumscribed in an ellipse, with its vertices positioned
     * along the ellipse at the given angles.
     * 
     * @param cenx
     * @param ceny
     * @param halfWidth
     * @param halfHeight
     * @param angles
     * @return
     */
    public static Shape makePolygon(float cenx, float ceny, float halfWidth, float halfHeight, float[] angles) {
        if (angles.length != 0) {
            angles = PApplet.sort(angles);
            int n = angles.length;
            Point[] vertices = new Point[n + 1];

            int i = 0;
            while (i < n) {
                vertices[i] = new Point(cenx + halfWidth * PApplet.cos(angles[i]),
                        ceny + halfHeight * PApplet.sin(angles[i]));
                i++;
            }
            vertices[i] = vertices[0].clone();
            return new Shape(vertices);
        } else {
            return new Shape(new Point[] {});
        }
    }
}
