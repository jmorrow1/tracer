package paths;

import processing.core.PGraphics;

/**
 * A continuous sequence of points in 2D space.
 * 
 * <br><br>
 * 
 * <a href="IPath.html">More information on paths</a>
 * 
 * @author James Morrow
 *
 */
public abstract class Path implements IPath {
	protected boolean reversed;
	protected int granularity = -1;
	
	public Path() {}
	
	/**
	 * 
	 * @param granularity the number of sample points
	 */
	public Path(int granularity) {
		this.granularity = granularity;
	}
	
	@Override
	public void display(PGraphics g) {
		if (granularity != -1) {
			display(g, granularity);
		}
		else {
			System.err.println("Need to either override this Path's display method or set the granularity of this Path so it can be displayed automatically.");
		}
	}
	
	@Override
	public abstract void trace(Point pt, float amt);

	/**
	 * 
	 * @return true, if the path is set to reversed and false otherwise
	 */
	public boolean isReversed() {
		return reversed;
	}
	
	@Override
	public void reverse() {
		reversed = !reversed;
	}
	
	@Override
	public abstract Path clone();
}
