package sk.tsystems.gamestudio.service.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import sk.testJpa.jpa.JpaHelper;
import sk.tsystems.gamestudio.entity.jpa.Comment;
import sk.tsystems.gamestudio.entity.jpa.Game;

public class CommentJpa {
	
	public void addComment(Comment comment) {
		JpaHelper.beginTransaction();
		JpaHelper.getEntityManager().persist(comment);
		JpaHelper.commitTransaction();
	}
	
	public List<Comment> findCommentsForGame(Game game) {
		List<Comment> comments = new ArrayList<>();
		EntityManager em = JpaHelper.getEntityManager();
		Query query = em.createQuery("Select c from Comment c where c.game = :game order by c.id desc");
		query.setParameter("game", game);
		comments = query.getResultList();
		return comments;
	}
}
