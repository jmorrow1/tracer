package paths;

import processing.core.PApplet;

/**
 * 
 * @author James Morrow
 *
 */
public class Arc extends GranularPath {
	private ArcDefinition arcDef;
	private int numVertices;
	
	private Arc(ArcDefinition arcDef, int numVertices) {
		super(arcDef, numVertices);
		this.arcDef = arcDef;
		this.numVertices = numVertices;
	}
	
	public Arc(Arc arc) {
		this(arc.arcDef.clone(), arc.numVertices);
	}
	
	/**
	 * Creates an Arc analogously to Processing's native arc() function.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param startAngle The angle at which the arc starts, in radians.
	 * @param endAngle The angle at which the arc ends, in radians.
	 * @param ellipseMode Determines the meaning of a, b, c, and d. The ellipseMode can be CENTER, RADIUS, CORNER, or CORNERS.
	 * @param numVertices Determines how precisely the arc is approximated.
	 * @return The Arc.
	 */
	public static Arc createArc(float a, float b, float c, float d, float startAngle, float endAngle, int ellipseMode, int numVertices) {
		return new Arc(new ArcDefinition(a, b, c, d, startAngle, endAngle, ellipseMode), numVertices);
	}
	
	private static class ArcDefinition implements PathDefinition {
		private float cenx, ceny, xRadius, yRadius, startAngle, endAngle;

		public ArcDefinition(float a, float b, float c, float d, float startAngle, float endAngle, int ellipseMode) {
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
		public void trace(Point pt, float amt) {
			float angle = PApplet.map(amt % 1, 0, 1, startAngle, endAngle);
			pt.x = cenx + xRadius*PApplet.cos(angle);
			pt.y = ceny + yRadius*PApplet.sin(angle);
		}
		
		@Override
		public ArcDefinition clone() {
			return new ArcDefinition(cenx, ceny, xRadius, yRadius, startAngle, endAngle, PApplet.RADIUS);
		}

		@Override
		public String toString() {
			return "Arc [cenx=" + cenx + ", ceny=" + ceny + ", xRadius=" + xRadius + ", yRadius=" + yRadius
					+ ", startAngle=" + startAngle + ", endAngle=" + endAngle + "]";
		}
	}

	@Override
	public Arc clone() {
		return new Arc(this);
	}
	
	@Override
	public String toString() {
		return arcDef.toString();
	}
}