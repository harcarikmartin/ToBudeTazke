package sk.tsystems.gamestudio.service.jpa;

public class GameCountRatings {
	private String game;
	
	private Long countRatings;

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public Long getCountRatings() {
		return countRatings;
	}

	public void setCountRatings(Long countRatings) {
		this.countRatings = countRatings;
	}

	public GameCountRatings(String game, Long countRatings) {
		this.game = game;
		this.countRatings = countRatings;
	}
	
	
}
