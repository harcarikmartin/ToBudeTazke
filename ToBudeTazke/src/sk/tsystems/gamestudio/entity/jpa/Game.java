package sk.tsystems.gamestudio.entity.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Game {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String gameName;

	public Game() {
	
	}

	public Game(String gameName) {
		this.gameName = gameName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", gameName=" + gameName + "]";
	}
	
	
}
