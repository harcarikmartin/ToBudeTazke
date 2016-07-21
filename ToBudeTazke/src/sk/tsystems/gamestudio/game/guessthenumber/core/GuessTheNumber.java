package sk.tsystems.gamestudio.game.guessthenumber.core;

import java.util.Random;

public class GuessTheNumber {
	private int interval;
	private Random rn = new Random();
	private int numberToGuess;
	
	
	public GuessTheNumber(int interval) {
		this.interval = interval;
		numberToGuess = rn.nextInt(interval);
	}

	public int getNumberToGuess() {
		return numberToGuess;
	}

	public int getInterval() {
		return interval;
	}
	
}
