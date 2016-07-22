package sk.tsystems.gamestudio.entity.jpa;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Rating {
	
	@Id
	@GeneratedValue
	private int id;
	
	private int rating;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Player player;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Game game;

	public Rating() {
	}

	public Rating(int rating, Player player, Game game) {
		this.rating = rating;
		this.player = player;
		this.game = game;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
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
		return "Rating [id=" + id + ", rating=" + rating + ", player=" + player + ", game=" + game + "]";
	}
	
	
}
