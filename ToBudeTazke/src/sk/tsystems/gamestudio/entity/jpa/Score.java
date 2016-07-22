package sk.tsystems.gamestudio.entity.jpa;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Score {
	
	@Id
	@GeneratedValue
	private int id;
	
	private int score;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Player player;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Game game;
	
	public Score() {
	}

	public Score(int score, Player player, Game game) {
		this.score = score;
		this.player = player;
		this.game = game;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public String toString() {
		return "Score [id=" + id + ", score=" + score + ", player=" + player + ", game=" + game + "]";
	}
	
	
}
