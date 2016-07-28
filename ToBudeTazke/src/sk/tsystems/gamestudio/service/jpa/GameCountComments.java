package sk.tsystems.gamestudio.service.jpa;

public class GameCountComments {
	private String game;
	
	private Long countComments;

	
	public GameCountComments(String game, Long countComments) {
		this.game = game;
		this.countComments = countComments;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public Long getCountComments() {
		return countComments;
	}

	public void setCountComments(Long countComments) {
		this.countComments = countComments;
	}
}
