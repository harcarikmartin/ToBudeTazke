package sk.tsystems.gamestudio.service.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import sk.tsystems.gamestudio.entity.jpa.Player;
import testJpa.jpa.JpaHelper;

public class PlayerJpa {
	
	public void addPlayer(Player player) {
		JpaHelper.beginTransaction();
		JpaHelper.getEntityManager().persist(player);
		JpaHelper.commitTransaction();
	}
	
	public Player setPresentPlayer(String playerName, String password) {
		int id = getId(playerName);
		if(id > 0) {
			EntityManager em = JpaHelper.getEntityManager();
			return em.find(Player.class, id);
		} else {
			addPlayer(new Player(playerName, password));
			return setPresentPlayer(playerName, password);
		}
	}

	public int getId(String playerName) {
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("select id from Player p where p.playerName = :playerName");
		query.setParameter("playerName", playerName);
		if(query.getResultList().isEmpty()) {
			em.close();
			return 0;
		} else {
			System.out.println((int) query.getResultList().get(0));
			return (int) query.getResultList().get(0);
		}
	}
	
	public boolean isPasswordCorrect(String playerName, String password) {
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("select id from Player p where p.playerName = :playerName and "
				+ "p.password = :password");
		query.setParameter("playerName", playerName);
		query.setParameter("password", password);
		if(query.getResultList().isEmpty()) {
			em.close();
			return false;
		} else {
			return true;
		}
	}
}
