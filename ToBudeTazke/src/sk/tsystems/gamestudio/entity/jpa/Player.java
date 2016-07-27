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
	private String password;
	
	
	public Player() {
	
	}
	

	public Player(String playerName, String password) {
		this.playerName = playerName;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "Player [id=" + id + ", playerName=" + playerName + ", password=" + password + "]";
	}
	
	
}
