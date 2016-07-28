package sk.tsystems.gamestudio.service.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import sk.tsystems.gamestudio.entity.jpa.Game;
import testJpa.jpa.JpaHelper;

public class GameJpa {
	public void addGame(Game game) {
		JpaHelper.beginTransaction();
		JpaHelper.getEntityManager().persist(game);
		JpaHelper.commitTransaction();
	}
	
	public Game setPresentGame(String gameName) {
		int id = getId(gameName);
		if(id > 0) {
			EntityManager em = JpaHelper.getEntityManager();
			return em.find(Game.class, id);
		} else {
			addGame(new Game(gameName));
			return setPresentGame(gameName);
		}
	}
	
	public List<Game> getGames() {
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("select g from Game g");
		List<Game> games = new ArrayList<>();
		games = query.getResultList();
		return games;
	
	}

	private int getId(String gameName) {
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("select id from Game g where g.gameName = :gameName");
		query.setParameter("gameName", gameName);
		if(query.getResultList().isEmpty()) {
			return 0;
		} else {
			return (int) query.getResultList().get(0);
		}
	}
	
	public int getGamesCount() {
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("select count(*) from Game g");
		if(query.getResultList().isEmpty()) {
			em.close();
			return 0;
		} else {
			return Math.toIntExact((long) query.getResultList().get(0));
		}
	}
}
