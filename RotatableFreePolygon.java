/**Defines a rotatable polygon defined by a set of points at set angles from an origin
 * centered at a point specified by rectangular coordinates
 * where the points may be at different distances and angles from the center
 * 
 * All methods (except getPathIterator) inherited from the Shape interface DO NOT WORK! Actually. Don't use them.
 * as of 2013-03-05.
 * 
 * @author Andrew Chen
 */

import java.awt.Dimension;

public class RotatableFreePolygon extends RotatablePolygon{
	
	/**Class constructor.
	 */
	public RotatableFreePolygon(){
	}
	
	/**Constructs a RotatableFreePolygon
	 * @param centerX x-coordinate of the midpoint
	 * @param centerY y-coordinate of the midpoint
	 * @param lengths an array of lengths from the center to the point
	 * @param angles an array of angles in normal position to the point
	 */
	public RotatableFreePolygon(int centerX, int centerY, int[] lengths, double[] angles){
		this.centerX = centerX;
		this.centerY = centerY;
		this.radii = lengths;
		this.thetas = angles;
		this.numSides = this.radii.length;
	}
	
	/**Constructs a RotatableShape defined by two points of a diagnol
	 * @param X1 x-coordinate of the first endpoint
	 * @param Y1 y-coordinate of the first endpoint
	 * @param X2 x-coordinate of the latter endpoint
	 * @param Y2 y-coordinate of the latter endpoint
	 */
	public RotatableFreePolygon(int x1, int y1, int x2, int y2) {
		this(	(x1+x2)/2,
				(y1+y2)/2,
				new int[]{1, 1, 1},
				new double[]{0, FULL_CIRCLE * 1/3, FULL_CIRCLE * 2/3}
		);
	}

	/**Constructs a RotatableShape which forms the diagonal defined by the dimensions of a rectangle
	 * @param dimension the dimensions
	 */
	public RotatableFreePolygon(Dimension dimension) {
		this(0, 0 , (int) dimension.getWidth(), (int) dimension.getHeight());
	}
}
