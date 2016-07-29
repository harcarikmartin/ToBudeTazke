package sk.tsystems.gamestudio.service.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import sk.tsystems.gamestudio.entity.jpa.Game;
import sk.tsystems.gamestudio.entity.jpa.Rating;
import testJpa.jpa.JpaHelper;

public class RatingJpa {
	
	public void addRating(Rating rating) {
		if(isRatingPresent(rating)) {
			deleteRating(rating);
		}
		JpaHelper.beginTransaction();
		JpaHelper.getEntityManager().persist(rating);
		JpaHelper.commitTransaction();
	}
	
	public int findRatingsCountForGame(Game game) {
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("Select count(r) from Rating r where r.game = :game");
		query.setParameter("game", game);
		if(query.getResultList().isEmpty()) {
			return 0;
		} else {
			return Math.toIntExact((long)query.getResultList().get(0));
		}
	}
	
	public double findAverageRatingForGame(Game game) {
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("Select avg(r.rating) from Rating r where r.game = :game");
		query.setParameter("game", game);
		if(query.getResultList().isEmpty()) {
			return 0;
		} else {
			return (double) query.getResultList().get(0);
		}
	}
	
	public double getAverageRatings() {
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("Select avg(r.rating) from Rating r");
		if(query.getResultList().isEmpty()) {
			return 0;
		} else {
			return (double) query.getResultList().get(0);
		}
	}
	
	private void deleteRating(Rating rating) {
		JpaHelper.beginTransaction();
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("delete from Rating r where r.game = :game and r.player = :player");
		query.setParameter("game", rating.getGame());
		query.setParameter("player", rating.getPlayer());
		query.executeUpdate();
		JpaHelper.commitTransaction();
	}
	
	private boolean isRatingPresent(Rating rating) {
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("select count(r.game) from Rating r where r.game = :game and r.player = :player");
		query.setParameter("game", rating.getGame());
		query.setParameter("player", rating.getPlayer());
		Object result = query.getSingleResult();
		if(Integer.parseInt(result.toString()) > 0) {
			return true;
		} else {
			return false;
		}
	} 
	
	public int getRatingsCount() {
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("select count(*) from Rating r");
		if(query.getResultList().isEmpty()) {
			em.close();
			return 0;
		} else {
			return Math.toIntExact((long) query.getResultList().get(0));
		}
	}
}
