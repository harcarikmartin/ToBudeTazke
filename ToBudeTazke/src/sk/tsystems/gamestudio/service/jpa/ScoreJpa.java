package sk.tsystems.gamestudio.service.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import sk.tsystems.gamestudio.entity.jpa.Game;
import sk.tsystems.gamestudio.entity.jpa.Score;
import testJpa.jpa.JpaHelper;

public class ScoreJpa {
	
	public void addScore(Score score) {
		JpaHelper.beginTransaction();
		JpaHelper.getEntityManager().persist(score);
		JpaHelper.commitTransaction();
	}
	
	public List<Score> findTenBestScoresForGame(Game game) {
		List<Score> scores = new ArrayList<>();
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("Select s from Score s where s.game = :game order by s.score desc");
		query.setParameter("game", game).setMaxResults(10);
		scores = query.getResultList();
		return scores;
	}
	
	public int getScoresCount() {
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("select count(*) from Score s");
		if(query.getResultList().isEmpty()) {
			em.close();
			return 0;
		} else {
			return Math.toIntExact((long) query.getResultList().get(0));
		}
	}

	public List<Score> findScores() {
		List<Score> scores = new ArrayList<>();
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("Select s from Score s");
		scores = query.getResultList();
		return scores;
	}
}
