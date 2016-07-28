package sk.tsystems.gamestudio.service.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import sk.tsystems.gamestudio.entity.jpa.Comment;
import sk.tsystems.gamestudio.entity.jpa.Game;
import testJpa.jpa.JpaHelper;

public class CommentJpa {
	
	public void addComment(Comment comment) {
		JpaHelper.beginTransaction();
		JpaHelper.getEntityManager().persist(comment);
		JpaHelper.commitTransaction();
	}
	
	public List<Comment> findCommentsForGame(Game game) {
		List<Comment> comments = new ArrayList<>();
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("Select c from Comment c where c.game = :game and c.comment is not null order by c.id desc");
		query.setParameter("game", game).setMaxResults(10);
		comments = query.getResultList();
		return comments;
	}
	
	public int getCommentsCount() {
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("select count(*) from Comment c");
		if(query.getResultList().isEmpty()) {
			em.close();
			return 0;
		} else {
			return Math.toIntExact((long) query.getResultList().get(0));
		}
	}
}
