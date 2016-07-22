package sk.tsystems.gamestudio.entity.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Player {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String playerName;
	
	
	public Player() {
	
	}

	public Player(String playerName) {
		this.playerName = playerName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", playerName=" + playerName + "]";
	}
	
	
}
