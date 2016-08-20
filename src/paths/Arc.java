package paths;

import processing.core.PApplet;

/**
 * 
 * @author James Morrow
 *
 */
public class Arc extends Path {
	private float cenx, ceny, xRadius, yRadius, startAngle, endAngle;
	
	/**************************
	 ***** Initialization *****
	 **************************/
	
	public Arc(Arc arc) {
		this(arc.cenx, arc.ceny, arc.xRadius, arc.yRadius, arc.startAngle, arc.endAngle, PApplet.RADIUS);
	}
	
	public Arc(float a, float b, float c, float d, float startAngle, float endAngle, int ellipseMode) {
		switch (ellipseMode) {
			case PApplet.RADIUS:
				this.cenx = a;
				this.ceny = b;
				this.xRadius = c;
				this.yRadius = d;
				break;
			case PApplet.CENTER:
				this.cenx = a;
				this.ceny = b;
				this.xRadius = c/2f;
				this.yRadius = d/2f;
				break;
			case PApplet.CORNER:
				this.xRadius = c/2f;
				this.yRadius = d/2f;
				this.cenx = a + xRadius;
				this.ceny = b + yRadius;
				break;
			case PApplet.CORNERS:
				this.xRadius = (c-a)/2f;
				this.yRadius = (d-b)/2f;
				this.cenx = a + xRadius;
				this.ceny = b + yRadius;
				break;
		}
		this.startAngle = startAngle;
		this.endAngle = endAngle;
	}
	
	/*************************
	 ***** Functionality *****
	 *************************/
	
	@Override
	public void display(PApplet pa) {
		pa.ellipseMode(pa.RADIUS);
		pa.arc(cenx, ceny, xRadius, yRadius, startAngle, endAngle);
	}

	@Override
	public void trace(Point pt, float amt) {
		if (reversed) amt = PApplet.map(amt, 0, 1, 1, 0);
		float angle = PApplet.map(amt % 1, 0, 1, startAngle, endAngle);
		pt.x = cenx + xRadius*PApplet.cos(angle);
		pt.y = ceny + yRadius*PApplet.sin(angle);
	}
	
	@Override
	public void translate(float dx, float dy) {
		cenx += dx;
		ceny += dy;
	}
	
	@Override
	public String toString() {
		return "Arc [cenx=" + cenx + ", ceny=" + ceny + ", xRadius=" + xRadius + ", yRadius=" + yRadius
				+ ", startAngle=" + startAngle + ", endAngle=" + endAngle + "]";
	}
	
	/*******************************
	 ***** Getters and Setters *****
	 *******************************/
	
	public float getCenx() {
		return cenx;
	}

	public void setCenx(float cenx) {
		this.cenx = cenx;
	}

	public float getCeny() {
		return ceny;
	}

	public void setCeny(float ceny) {
		this.ceny = ceny;
	}

	public float getWidth() {
		return 2*xRadius;
	}

	public void setWidth(float width) {
		this.xRadius = width/2f;
	}

	public float getHeight() {
		return 2f*yRadius;
	}

	public void setHeight(float height) {
		this.yRadius = height/2f;
	}

	public float getStartAngle() {
		return startAngle;
	}

	public void setStartAngle(float startAngle) {
		this.startAngle = startAngle;
	}

	public float getEndAngle() {
		return endAngle;
	}

	public void setEndAngle(float endAngle) {
		this.endAngle = endAngle;
	}
	
	public Arc clone() {
		return new Arc(this);
	}
}