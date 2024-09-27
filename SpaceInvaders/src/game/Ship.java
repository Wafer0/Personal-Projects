package game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Describes a charecter called ship that the player holds. This calss contains
 * a class called bullet that is an action ship can perfeorm. This class
 * contains methods that give it the ability too move based on keyboard input
 * and fire bullets.
 * 
 * @author cmsc132_student
 *
 */
public class Ship extends Charecter implements KeyListener {
	protected boolean shootOn;

	/**
	 * 
	 * Default constructor that calls the superclass that creates the shape and
	 * places the ship object at 400,500 which is the starting index.
	 */
	public Ship() {
		super(new Point[] { new Point(45, 0), new Point(55, 0), 
				new Point(100, 50), new Point(0, 50) }, 0);
		rotation = 0;
		xChange = 400;
		yChange = 500;
	}

	/**
	 * 
	 * Inner class that gives the ship the ability too have its own bullet
	 *  object.
	 */
	public class Bullets extends Charecter {
		/**
		 * 
		 * Default constructor that calls the superclass that creates the shape
		 *  and places the bullet at the same position as the ship too start.
		 */
		public Bullets() {
			super(new Point[] { new Point(200, 200), new Point(210, 200), 
					new Point(210, 250), new Point(200, 250) },
					0.0);
			xChange = 400;
			yChange = 500;
		}

		/**
		 * 
		 * Checks if the bullet is off screen and if it is it changes the 
		 * position of the bullet to the same position as the ship. 
		 * Returns true if it changed the position of the bullet. False 
		 * otherwise
		 * 
		 * @param brush Needed too paint on the Screen.
		 * @return boolean If the method was successful.
		 */
		public boolean shoot(Graphics brush) {
			if (!(this.yChange + 500 > 0) || !(this.xChange + 400 > 0) || 
					this.xChange > 800) {
				this.xChange = change()[0] + 22;
				this.yChange = change()[1] + 22;
				return true;
			}
			return false;
		}

		/**
		 * 
		 * Removes bullet from screen by placing it very far in the negative 
		 * direction (above the screen).
		 */
		public void removeBulletFromScreen() {
			this.xChange = -1000000;
			this.yChange = -1000000;
		}

		/**
		 * 
		 * Moves bullet 5 pixels per call to the up
		 */
		public void moveUp() {
			this.yChange -= 5;
		}

	}

	/**
	 * Listens too keyboard for the up, down, right, left and space keys. Once a 
	 * key is pressed that matches these it calls the proper method too move the 
	 * ship too the right position. When pressing the space bar the shootOn 
	 * variable is turned too true which is later used in the SpaceInvaders 
	 * class.
	 * 
	 * @param e Needed too check keyboard.
	 */
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_UP:
			yChange -= 15;
			break;
		case KeyEvent.VK_DOWN:
			yChange += 15;
			break;
		case KeyEvent.VK_LEFT:
			xChange -= 15;
			break;
		case KeyEvent.VK_RIGHT:
			xChange += 15;
			break;
		case KeyEvent.VK_SPACE:
			shootOn = true;
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * Inherited method from interface keyListener. Unused
	 * 
	 * @param e Needed too check keybaord.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * 
	 * Inherited method from interface keyListener. Unused
	 * 
	 * @param e Needed too check keybaord.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * 
	 * Returns an array of integers that holds the coordinates of object
	 * 
	 * @return int[] Cordinates of the object.
	 */
	private int[] change() {
		return new int[] { xChange, yChange };
	}
}
