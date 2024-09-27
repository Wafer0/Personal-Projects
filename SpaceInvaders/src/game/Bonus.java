package game;

/**
 * Simple interface that will allow us later on to descirbe how bonus work at
 * the end of levels.
 * 
 * @author cmsc132_student
 *
 */
public interface Bonus {
	/**
	 * Simple method used later for lambda expression. It implements a bonus 
	 * system for the score variable.
	 * 
	 * @param x Argument.
	 * @return int Output.
	 */
	public int extraPoints(int x);
}
