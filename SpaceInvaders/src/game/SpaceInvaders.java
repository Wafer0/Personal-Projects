package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

import game.Aliens.EnemyBullets;

/**
 * Main class where all the calss for movement painting and creating objects are
 * made for the game.
 * 
 * @author cmsc132_student
 *
 */
class SpaceInvaders extends Game {
	private static final long serialVersionUID = 1L;

	// Objects
	private Ship ship;
	private Aliens[] alien;
	private Ship.Bullets bul;
	private Object[] enBul;
	private Aliens superA;
	private Aliens.EnemyBullets superB;
	private Bonus mileStone;

	// Alive Variables
	private boolean[] alive;
	private boolean bosAlive;
	private int[] arrayX = { 150, 250, 350, 450, 550, 150, 250, 350, 450, 550, 
			150, 250, 350, 450, 550 };
	private int[] arrayY = { 50, 50, 50, 50, 50, 150, 150, 150, 150, 150, 250, 
			250, 250, 250, 250 };

	// Counters
	private int counter = 0;
	private int counter2 = 0;
	private int counter3 = 0;
	private int calls = 1; 

	// Game Statistics
	private int score = 0;
	private int level = 1; 

	// Completion Variables
	private boolean gameOver;
	private int bossDefeated = 0; 

	/**
	 * Sets up the screen and creates all the obejcts of the game. It also 
	 * prepares the alive variables for th enemies. In addition it makes
	 * the lamda expresion fro the bonus interface and sets up the 
	 * keyListener.
	 */
	public SpaceInvaders() {
		// Setting up the screen
		super("SpaceInvaders!", 800, 600);
		this.setFocusable(true);
		this.requestFocus();

		// Create objects
		ship = new Ship();
		arrayEnemies();
		createBoss();

		// create bullets
		bul = ship.new Bullets();
		bul.removeBulletFromScreen();

		gameOver = false;

		// Set alive variables
		bosAlive = false;
		alive = new boolean[15];
		for (int i = 0; i < alive.length; i++) {
			alive[i] = true;
		}

		// set up keyListener Interface
		this.addKeyListener(ship);

		// Lambda Expression
		mileStone = x -> x * 10;
	}

	/**
	 * Main Game driver that is called every 10 ms. This method takes in a brush
	 * and paints all the bullets that were shop, all the enamies that are  
	 * alive, the ship object, counter, score, level, and checks for collisions 
	 * every time it runs.
	 * 
	 * @param brush Needed too paint on screen.
	 */
	public void paint(Graphics brush) {
		// If game isn't over paint everything needed too play
		if (gameOver == false && level < 6) {

			// set up screen
			brush.setColor(Color.black);
			brush.fillRect(0, 0, width, height);

			// sample code for printing message for debugging
			// counter is incremented and this message printed
			// each time the canvas is repainted
			counter++;
			brush.setColor(Color.white);
			brush.drawString("Counter is " + counter, 10, 10);
			brush.drawString("Score is:  " + score, 700, 10);
			brush.drawString("Level is:" + level, 740, 30);

			// set up ship
			brush.setColor(Color.RED);
			ship.paint(brush);
			brush.setColor(Color.white);
			brush.drawString("Earth Defender", ship.xChange - 20, 
					ship.yChange + 30);
			brush.setColor(Color.BLUE);
			arrayPaintAndMove(brush);

			// Bullet movement
			brush.setColor(Color.GRAY);
			bul.paint(brush);
			if (ship.shootOn) {
				if (bul.shoot(brush)) {
					ship.shootOn = false;
				}
			}
			bul.moveUp();

			// Enemy bullet movement
			brush.setColor(Color.GREEN);
			for (int i = 0; i < 15; i++) {
				Aliens.EnemyBullets temp = (EnemyBullets) enBul[i];
				temp.paint(brush);
				temp.moveDown();
			}
			enShoot(brush, level);

			// Next level
			if (Arrays.toString(alive).indexOf("true") == -1 && bossDefeated == 
					level) {
				level++;
				score = mileStone.extraPoints(score);
				resetEnemies();
			}

			// Spawn boss
			if (Arrays.toString(alive).indexOf("true") == -1) {
				if (calls == level) {
					resetBoss();
					bosAlive = true;
					calls++;
				}
			}

			// boss bullet movement
			if (bosAlive) {
				brush.setColor(Color.GREEN);
				superB.shoot(brush);
				superB.paint(brush);
				superB.moveDown();
			}

			// boss movement
			arrayPaintAndMoveBoss(brush);

			// collision method
			detection();

		} else if (level == 6) {
			// End of game win screen
			brush.setColor(Color.WHITE);
			brush.fillRect(0, 0, width, height);
			brush.setColor(Color.BLACK);
			brush.drawString("You Win!", 380, 300);
		} else {
			// End of game lose screen
			brush.setColor(Color.WHITE);
			brush.fillRect(0, 0, width, height);
			brush.setColor(Color.BLACK);
			brush.drawString("You Lose", 380, 300);
		}
	}

	/**
	 * Creates the superA object and superB object which is superA's bullet 
	 * which is the boss stage of the game that appears at the end of each 
	 * level. It sets superA to its proper starting position and removes its 
	 * bullet from the screen for later use.
	 */
	public void createBoss() {
		superA = new Aliens() {
			public void moveRight() {
				this.xChange += 3;
			}

			public void moveUp() {
				this.yChange -= 3;
			}

			public void moveLeft() {
				this.xChange -= 3;
			}

			public void moveDown() {
				this.yChange += 3;
			}
		};
		superA.xChange = 0;
		superA.xChange = 50;
		superA.yChange = 0;
		superA.yChange = 50;
		superB = superA.new EnemyBullets();
		superB.removeBulletFromScreen();
	}

	/**
	 * 
	 * Uses the contains method in the character class too check if any objects 
	 * are colliding. If a ship bullet hits an alien it will be declared dead. 
	 * If an alien bullet hits the ship it will be declared game over. If a ship
	 *  collides with an alien it is game over. If a ship collides with superA 
	 *  orsuperB which is superA's bullets then the game is over.
	 */
	public void detection() {
		for (int i = 0; i < 15; i++) {
			if (alive[i] == true) {
				if (alien[i].contains(ship.shape, ship)) {
					gameOver = true;
				}
				if (alien[i].contains(bul.shape, bul) && alive[i] == true) {
					score += 50;
					alive[i] = false;
					bul.removeBulletFromScreen();
				}
			}
		}
		for (int i = 0; i < 15; i++) {
			Aliens.EnemyBullets temp = (EnemyBullets) enBul[i];
			if (temp.contains(ship.shape, ship)) {
				gameOver = true;
			}
		}
		if (bosAlive == true && superA != null) {
			if (superA.contains(ship.shape, ship)) {
				gameOver = true;
			}
			if (superA.contains(bul.shape, bul)) {
				bosAlive = false;
				bossDefeated++;
			}
			if (superB.contains(ship.shape, ship)) {
				gameOver = true;
			}
		}
	}

	/**
	 * Creates all the alien objects and their bullets. Sets them too their 
	 * starting position and removes the bullets from the screen for later use.
	 */
	public void arrayEnemies() {
		alien = new Aliens[15];
		enBul = new Object[15];
		for (int i = 0; i < 15; i++) {
			alien[i] = new Aliens();
			alien[i].xChange = arrayX[i];
			alien[i].yChange = arrayY[i];
			enBul[i] = alien[i].new EnemyBullets();
			Aliens.EnemyBullets temp = (EnemyBullets) enBul[i];
			temp.removeBulletFromScreen();
		}
	}

	/**
	 * Resets the Enemies alive status too true and then resets their position 
	 * too where they are supposed too start and resets their movement counter 
	 * (counter2) and rotation so they can move in the right direction.
	 */
	public void resetEnemies() {
		for (int i = 0; i < 15; i++) {
			alien[i].yChange = arrayY[i];
			alien[i].xChange = arrayX[i];
			alien[i].setRotation(0);
		}
		for (int i = 0; i < alive.length; i++) {
			alive[i] = true;
		}
		counter2 = 0;
	}

	/**
	 * Sets the bossAlive status too true and changes his coordinates too his
	 * starting position. IT also resets his move counter (counter3) so he can 
	 * move int the right direction.
	 */
	public void resetBoss() {
		bosAlive = true;
		superA.xChange = 50;
		superA.yChange = 50;
		counter3 = 0;

	}
	/**
	 * The following method moves the superA object which is the boss element 
	 * in a square path (300 by 300) arround the screen and it rotates it in 
	 * order for it too face the direction it is going. 
	 * @param brush Needed too paint onto the screen 
	 */
	public void arrayPaintAndMoveBoss(Graphics brush) {
		counter3++;
		if (bosAlive) {
			superA.paint(brush);
			brush.setColor(Color.PINK);
			superA.paint(brush);
		}
		if (counter3 < 100) {
			if (bosAlive) {
				superA.setRotation(180);
				superA.moveDown();
				brush.setColor(Color.PINK);
				superA.paint(brush);
			}
		} else if (counter3 < 200) {
			if (bosAlive) {
				superA.setRotation(90);
				superA.moveRight();
				brush.setColor(Color.PINK);
				superA.paint(brush);
			}
		} else if (counter3 < 300) {
			if (bosAlive) {
				superA.setRotation(0);
				superA.moveUp();
				brush.setColor(Color.PINK);
				superA.paint(brush);
			}
		} else if (counter3 < 400) {
			if (bosAlive) {
				superA.setRotation(-90);
				superA.moveLeft();
				brush.setColor(Color.PINK);
				superA.paint(brush);
			}
		} else {
			counter3 = 0;
		}
	}

	/**
	 * Takes in a brush and level. Based on the level the chances of a bullet 
	 * being painted is a lot higher which changes the difficulty of the game. 
	 * The algorithm uses prime numbers and the counter for the game frames too 
	 * decide when enemies shoot. on low levels the prime number is more rare 
	 * while on high levels it much more common. When the counter aligns with 
	 * the prime number the method uses a random variable that returns an int 
	 * form 0-14 that is used too pick a random alien too shoot the bullet.
	 * 
	 * @param brush Needed too paint on screen.
	 * @param level Used too change the frequeuncy of bullet firing.
	 */
	public void enShoot(Graphics brush, int level) {
		Random rand = new Random();
		int dif = 0;
		if (level == 1) {
			dif = 41;
		} else if (level == 2) {
			dif = 25;
		} else if (level == 3) {
			dif = 15;
		} else if (level == 4) {
			dif = 7;
		} else {
			dif = 2;
		}
		if (counter % dif == 0) {
			int index = rand.nextInt(15);
			if (alive[index]) {
				Aliens.EnemyBullets temp = (EnemyBullets) enBul[index];
				temp.shoot(brush);
			}
		}
	}

	/**
	 * takes in the brush and paints each of the alien objects in the alien 
	 * array if they are alive. If they are not the objects aren't painted. It 
	 * uses a counter too change the position of the object and move them 100 
	 * pixels down, then right, then up then left. Allowing them too move 
	 * together in a circle. When an object moves a certain direction its 
	 * rotation also changes too match where it is headed.
	 * 
	 * @param brush Needed too paint on screen.
	 */
	public void arrayPaintAndMove(Graphics brush) {
		counter2++;
		for (int i = 0; i < 15; i++) {
			if (alive[i]) {
				brush.setColor(Color.BLUE);
				alien[i].paint(brush);
			}
		}
		if (counter2 < 100) {
			for (int i = 0; i < 15; i++) {
				if (alive[i]) {
					alien[i].setRotation(180);
					alien[i].moveDown();
					brush.setColor(Color.BLUE);
					alien[i].paint(brush);
				}
			}
		} else if (counter2 < 200) {
			for (int i = 0; i < 15; i++) {
				if (alive[i]) {
					alien[i].setRotation(90);
					alien[i].moveRight();
					brush.setColor(Color.BLUE);
					alien[i].paint(brush);
				}
			}
		} else if (counter2 < 300) {
			for (int i = 0; i < 15; i++) {
				if (alive[i]) {
					alien[i].setRotation(0);
					alien[i].moveUp();
					brush.setColor(Color.BLUE);
					alien[i].paint(brush);
				}
			}
		} else if (counter2 < 400) {
			for (int i = 0; i < 15; i++) {
				if (alive[i]) {
					alien[i].setRotation(-90);
					alien[i].moveLeft();
					brush.setColor(Color.BLUE);
					alien[i].paint(brush);
				}
			}
		} else {
			counter2 = 0;
		}
	}
	/**
	 * Used too run the game and call paint every 10ms.
	 * 
	 * @param args Functionality of main method.
	 */
	public static void main(String[] args) {
		SpaceInvaders a = new SpaceInvaders();
		a.repaint();
	}
}