package paths;

import processing.core.PApplet;

/**
 * 
 * @author James Morrow
 *
 */
public class Arc implements Traceable {
	private float cenx, ceny, xRadius, yRadius, startAngle, endAngle;
	
	public Arc(float a, float b, float c, float d, float startAngle, float endAngle, int ellipseMode, int granularity) {
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
	
	@Override
	public void display(PApplet pa) {
		pa.ellipseMode(pa.RADIUS);
		pa.arc(cenx, ceny, xRadius, yRadius, startAngle, endAngle);
	}

	@Override
	public void trace(Point pt, float amt) {
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
}