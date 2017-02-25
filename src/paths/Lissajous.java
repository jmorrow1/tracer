package paths;

import processing.core.PApplet;
import tracer.Point;

/**
 * 
 * A complex form created by two sinusoidal motions.
 * 
 * @author James Morrow [jamesmorrowdesign.com]
 *
 */
public class Lissajous extends Path {
    private Point cen;
    private float xRadius, yRadius, freqX, freqY, phi;

    /**
     * Copy constructor.
     * @param l The path to copy
     */
    public Lissajous(Lissajous l) {
        this(l.sampleCount, l.cen.clone(), l.xRadius, l.yRadius, l.freqX, l.freqY, l.phi);
    }
    
    /**
     * 
     * @param sampleCount The number of sample points to take
     * @param x The center x-coordinate
     * @param y The center y-coordinate
     * @param xRadius The radius in the x-direction
     * @param yRadius The radius in the y-direction
     * @param freqX The frequency in the x-direction
     * @param freqY The frequency in the y-direction
     * @param phi An offset anngle
     */
    public Lissajous(int sampleCount, float x, float y, float xRadius, float yRadius, float freqX, float freqY, float phi) {
        this(sampleCount, new Point(x, y), xRadius, yRadius, freqX, freqY, phi);
    }
    
    /**
     * 
     * @param sampleCount The number of sample points to take
     * @param center The center point
     * @param xRadius The radius in the x-direction
     * @param yRadius The radius in the y-direction
     * @param freqX The frequency in the x-direction
     * @param freqY The frequency in the y-direction
     * @param phi An offset anngle
     */
    public Lissajous(int sampleCount, Point center, float xRadius, float yRadius, float freqX, float freqY, float phi) {
        super(sampleCount);
        this.cen = center;
        this.xRadius = xRadius;
        this.yRadius = yRadius;
        this.freqX = freqX;
        this.freqY = freqY;
        this.phi = phi;
    }

    /**
     * Easy constructor.
     * 
     * @param x The x-coordinate of the path.
     * @param y The y-coordinate of the path.
     * @param r The radius of the path.
     */
    public Lissajous(float x, float y, float r) {
        this(200, x, y, r, r, 3, 5, PApplet.QUARTER_PI);
    }
    
    /**
     * Easy constructor
     * 
     * @param cen The center of the path
     * @param r The radius of the path
     */
    public Lissajous(Point cen, float r) {
        this(200, cen, r, r, 3, 5, PApplet.QUARTER_PI);
    }

    @Override
    public void trace(Point pt, float u) {
        if (u < 0 || u >= 1) {
            throw new IllegalArgumentException(Lissajous.class.getName() + ".trace(pt, " + u + ") called where the second argument is outside the range 0 (inclusive) to 1 (exclusive).");
        }
        
        if (reversed) {
            u *= -1;
        }
        
        float angle = u * PApplet.TWO_PI;
        pt.x = cen.x + xRadius * PApplet.sin(angle * freqX + phi);
        pt.y = cen.y + yRadius * PApplet.sin(angle * freqY);
    }

    @Override
    public void translate(float dx, float dy) {
        cen.translate(dx, dy);
    }

    /**
     * Gives the x-coordinate of the center of the Path.
     * @return the x-coordinate
     */
    public float getX() {
        return cen.x;
    }

    /**
     * Sets the x-coordinate of the center of the Path.
     * @param x the x-coordinate
     */
    public void setX(float x) {
        this.cen.x = x;
    }

    /**
     * Gives the y-coordinate of the center of the Path.
     * @return the y-coordinate
     */
    public float getY() {
        return cen.y;
    }

    /**
     * Sets the y-coordinate of the center of the Path.
     * @param y the y-coordinate
     */
    public void setY(float y) {
        this.cen.y = y;
    }

    /**
     * Gives the width of the Path.
     * @return The width
     */
    public float getWidth() {
        return xRadius;
    }

    /**
     * Sets the width of the Path.
     * @param width The width
     */
    public void setWidth(float width) {
        this.xRadius = width/2f;
    }

    /**
     * Gives the height of the Path.
     * @return The height
     */
    public float getHeight() {
        return 2f * yRadius;
    }
    
    /**
     * Sets the height of the Path.
     * @param height The height
     */
    public void setHeight(float height) {
        this.yRadius = 0.5f * height;
    }

    /**
     * Gives the x frequency
     * @return The x frequency
     */
    public float getFreqX() {
        return freqX;
    }

    /**
     * Sets the x frequency
     * @param freqX The x frequency
     */
    public void setFreqX(float freqX) {
        this.freqX = freqX;
    }

    /**
     * Gives the y frequency
     * @return The y frequency
     */
    public float getFreqY() {
        return freqY;
    }

    /**
     * Sets the y frequency
     * @param freqY The y frequency
     */
    public void setFreqY(float freqY) {
        this.freqY = freqY;
    }

    /**
     * Gives the phi angle.
     * @return The phi angle
     */
    public float getPhi() {
        return phi;
    }

    /**
     * Sets the phi angle.
     * @param phi The phi angle
     */
    public void setPhi(float phi) {
        this.phi = phi;
    }

    @Override
    public Lissajous clone() {
        return new Lissajous(this);
    }

    @Override
    public int getGapCount() {
        if (isClosed()) {
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public float getGap(int i) {
        if (isClosed() || i != 0) {
            return -1;
        }
        else {
            return 0;
        }
    }
    
    /**
     * Tells whether or not the Path is closed.
     * @return True if the path is closed, false otherwise
     */
    public boolean isClosed() {
        return (int)freqX == freqX && (int)freqY == freqY;
    }

    @Override
    public String toString() {
        return "Lissajous [cen=" + cen + ", xRadius=" + xRadius + ", yRadius=" + yRadius + ", freqX=" + freqX
                + ", freqY=" + freqY + ", phi=" + phi + "]";
    }
}