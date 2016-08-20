package paths;

import processing.core.PApplet;

/**
 * 
 * @author James Morrow
 *
 */
public abstract class Path implements IPath {
	protected boolean reversed;
	protected int granularity = -1;
	
	public Path() {}
	public Path(int granularity) {
		this.granularity = granularity;
	}
	
	public void display(PApplet pa) {
		if (granularity != -1) {
			display(pa, granularity);
		}
		else {
			System.err.println("Need to either override this Path's display method or set the granularity of this Path so it can be displayed automatically.");
		}
	}
	
	public boolean isReversed() {
		return reversed;
	}
	
	@Override
	public void reverse() {
		reversed = !reversed;
	}
	
	public abstract Path clone();
}
