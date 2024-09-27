package game;

import java.awt.Graphics;

/**
 * Defines a polygon and has mutlple methods such as paint and contains that all
 * charecters in the game need and use. It also has getters and setters for the
 * cordinates and it has a printCordinates method for diagnostics.
 * 
 * @author cmsc132_student
 *
 */
public class Charecter extends Polygon {
	protected Point position;
	protected Point[] shape;
	protected Integer xChange;
	protected Integer yChange;

	/**
	 * Takes in shape, rotation and sets the coordinates (xChange, yChange) too
	 * zero. Calls the super method too create the actual object. Sets the 
	 * position too 0,0.
	 * 
	 * @param shape    The shape of wanted charecter
	 * @param rotation The intial rotation of object
	 */
	public Charecter(Point[] shape, double rotation) {
		super(shape, new Point(0, 0), rotation);
		this.shape = shape;
		xChange = 0;
		yChange = 0;
		this.rotation = rotation;
	}

	/**
	 * Takes in a brush and paints the object by using the initial position and 
	 * the coordinates too find where in the screen it is supposed too be.
	 * 
	 * @param brush Gives access too painting the on the screen
	 */
	public void paint(Graphics brush) {
		Point[] point = super.getPoints();

		int[] xs = new int[point.length];
		int[] ys = new int[point.length];

		for (int i = 0; i < point.length; i++) {
			xs[i] = (int) point[i].x + xChange;
			ys[i] = (int) point[i].y + yChange;
		}

		brush.fillPolygon(xs, ys, 4);
	}

	/**
	 * Takes in a shape which is four points and an object. It then finds the 
	 * max and min x and y coordinates of the object using its shape. Uses 
	 * these points and checks if any of the corner points of the object that 
	 * called this method are within the object.If the object that calls has the 
	 * object that was passed into this method inside of it the method will 
	 * return true otherwise false.
	 * 
	 * @param shape The object shape that is checked if it is colliding with the
	 *              object that called this method.
	 * @param obj   The object that is checked if it is colliding with the object.
	 *              that called this method
	 * @return boolean True or False depedning on if the obejcts are colliding.
	 */
	public boolean contains(Point[] shape, Charecter obj) {

		int[] xs = new int[shape.length];
		int[] ys = new int[shape.length];

		for (int i = 0; i < shape.length; i++) {
			xs[i] = (int) shape[i].x + obj.xChange;
			ys[i] = (int) shape[i].y + obj.yChange;
		}
		int maxX = 0;
		int maxY = 0;
		int minX = xs[1];
		int minY = ys[1];
		for (int i = 0; i < 4; i++) {
			if (maxX < xs[i]) {
				maxX = xs[i];
			}
			if (maxY < ys[i]) {
				maxY = ys[i];
			}
			if (minX > xs[i]) {
				minX = xs[i];
			}
			if (minY > ys[i]) {
				minY = ys[i];
			}
		}

		int[] xs2 = new int[this.shape.length];
		int[] ys2 = new int[this.shape.length];

		for (int i = 0; i < this.shape.length; i++) {
			xs2[i] = (int) this.shape[i].x + this.xChange;
			ys2[i] = (int) this.shape[i].y + this.yChange;
		}

		for (int i = 0; i < 4; i++) {
			if (xs2[i] <= maxX && xs2[i] >= minX) {
				if (ys2[i] <= maxY && ys2[i] >= minY) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns the current rotation of the object using the superclass variable
	 * rotation.
	 * 
	 * @return int Current rotation.
	 */
	public int getRotation() {
		return (int) super.rotation;
	}

	/**
	 * Sets the rotation of the object by changed the rotation variable in the 
	 * super class.
	 * 
	 * @param input The new roation.
	 * 
	 */
	public void setRotation(double input) {
		super.rotation = input;
	}

	/**
	 * Prints the coordinates of the object that called this method
	 */
	public void printCordinates() {
		System.out.println("x: " + xChange);
		System.out.println("y: " + yChange);
	}

}
