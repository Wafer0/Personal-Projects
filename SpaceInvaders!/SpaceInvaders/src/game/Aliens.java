package game;

import java.awt.Graphics;

/**
 * Descrubes a charecter that is called alien that is the eneny of the game.
 * Simular too ship this class has an inner class of EnemyBullets that alien can
 * use. In addition this class has multple methods too change its movement that
 * are later called inside the game class.
 * 
 * @author cmsc132_student
 *
 */
public class Aliens extends Charecter {
	/**
	 * 
	 * Default constructor that calls the superclass and creates the object
	 */
	public Aliens() {
		super(new Point[] { new Point(45, 0), new Point(55, 0), 
				new Point(70, 40), new Point(30, 40) }, 0);
	}

	public class EnemyBullets extends Charecter {
		/**
		 * 
		 * Default constructor that calls the superclass and creates the object
		 */
		public EnemyBullets() {
			super(new Point[] { new Point(200, 200), new Point(210, 200), 
					new Point(210, 220), new Point(200, 220) },
					0.0);
		}

		/**
		 * 
		 * Moves the bullet too the coordinates of its alien object and adjust 
		 * so it looks like the bullet is leaving the alien.
		 */
		public void shoot(Graphics brush) {
			if (this.yChange > 800) {
				this.xChange = change()[0] + 22;
				this.yChange = change()[1] + 22;
			}
		}

		/**
		 * 
		 * Removes bullet from screen by moving it thousands of pixels under 
		 * the screen.
		 */
		public void removeBulletFromScreen() {
			this.xChange = 20000;
			this.yChange = 20000;
		}

		/**
		 * 
		 * Moves object down by 1 pixel
		 */
		public void moveDown() {
			this.yChange += 2;
		}
	}

	/**
	 * 
	 * Moves object to the right by 1 pixel
	 */
	public void moveRight() {
		this.xChange += 1;
	}

	/**
	 * 
	 * Moves object up by 1 pixel
	 */
	public void moveUp() {
		this.yChange -= 1;
	}

	/**
	 * 
	 * Moves object to the left by 1 pixel
	 */
	public void moveLeft() {
		this.xChange -= 1;
	}

	/**
	 * 
	 * Moves object down by 1 pixel
	 */
	public void moveDown() {
		this.yChange += 1;
	}

	/**
	 * 
	 * Returns an array of integers that holds the coordinates of object
	 * 
	 * @return int[] Cordinates.
	 */
	private int[] change() {
		return new int[] { xChange, yChange };
	}

}
