package sk.tsystems.gamestudio.entity.jpa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Coment")
public class Comment {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "coment")
	private String comment;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Player player;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Game game;

	public Comment() {
	}
	
	public Comment(String comment, Player player, Game game) {
		this.comment = comment;
		this.player = player;
		this.game = game;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
		return "Comment [id=" + id + ", comment=" + comment + ", player=" + player + ", game=" + game + "]";
	}
	
	
}
