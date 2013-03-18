/**Defines a rotatable regular polygon defined by a set of points at set angles from an origin
 * centered at a point specified by rectangular coordinates
 * 
 * All methods (except getPathIterator) inherited from the Shape interface DO NOT WORK! Actually. Don't use them.
 * as of 2013-03-05.
 * 
 * @author Andrew Chen
 */

import java.awt.Dimension;

public class RotatableRegularPolygon extends RotatablePolygon{

	/**Class constructor.
	 */
	public RotatableRegularPolygon(){
		super();
	}

	/**Constructs a RotatableRegularPolygon 
	 * @param center an array containing a pair of int designating the center point
	 * @param lengths an array of lengths from the center to the point
	 * @param angles an array of angles in normal position to the point
	 */
	public RotatableRegularPolygon(int[] center, int numSides, int length){
		this.centerX = center[0];
		this.centerY = center[1];
		this.numSides = numSides;
		this.radii = new int[numSides];
		for(int i=0; i<numSides; i++){
			this.radii[i] = length;
		}
		this.thetas = new double[numSides];
		for(int i=0; i<numSides; i++){
			this.thetas[i] = i * (FULL_CIRCLE/numSides);
		}
	}

	/**Constructs a RotatableShape defined by two points of a diagnol
	 * @param X1 x-coordinate of the first endpoint
	 * @param Y1 y-coordinate of the first endpoint
	 * @param X2 x-coordinate of the latter endpoint
	 * @param Y2 y-coordinate of the latter endpoint
	 */
	public RotatableRegularPolygon(int x1, int y1, int x2, int y2){
		this(new int[]{(x1+x2)/2, (y1+y2)/2}, 3, 100);
	}

	/**Constructs a RotatableShape which forms the diagonal defined by the dimensions of a rectangle
	 * @param dimension the dimensions
	 */
	public RotatableRegularPolygon(Dimension dimension){
		super(dimension);
	}
}
