import tracer.Point;
import tracer.paths.Arc;
import tracer.paths.Ellipse;
import tracer.paths.Path;
import tracer.paths.Rect;

int[] modes = new int[] {CENTER, CORNER, CORNERS, RADIUS};
final int RECT = 0, ELLIPSE = 1, ARC = 2;

Point ab, cd; //control with left and right mouse buttons (mac users can use shift+left click instead of right click)
int modeIndex = 0; //control with mouse wheel or left and right keys
int pathMode = RECT; //control with space bar

Path path;
String mouseBehavior = "";
boolean shift;