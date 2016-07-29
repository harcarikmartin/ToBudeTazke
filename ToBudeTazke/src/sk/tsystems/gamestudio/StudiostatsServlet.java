package sk.tsystems.gamestudio;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sk.tsystems.gamestudio.service.jpa.CommentJpa;
import sk.tsystems.gamestudio.service.jpa.GameCountComments;
import sk.tsystems.gamestudio.service.jpa.GameCountRatings;
import sk.tsystems.gamestudio.service.jpa.GameCountScores;
import sk.tsystems.gamestudio.service.jpa.GameJpa;
import sk.tsystems.gamestudio.service.jpa.PlayerJpa;
import sk.tsystems.gamestudio.service.jpa.RatingJpa;
import sk.tsystems.gamestudio.service.jpa.ScoreJpa;
import testJpa.jpa.JpaHelper;

/**
 * Servlet implementation class Studiostats
 */
@WebServlet("/Studiostats")
public class StudiostatsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

//	List<GameCountScores> gameCountScores = 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("playersCount", new PlayerJpa().getPlayersCount());
		request.setAttribute("gamesCount", new GameJpa().getGamesCount());
		request.setAttribute("ratingsCount", new RatingJpa().getRatingsCount());
		request.setAttribute("averageRating", new RatingJpa().getAverageRatings());
		request.setAttribute("commentsCount", new CommentJpa().getCommentsCount());
		request.setAttribute("scoresCount", new ScoreJpa().getScoresCount());
		request.setAttribute("gameCountScores", JpaHelper.getEntityManager().createQuery("Select new sk.tsystems.gamestudio.service.jpa.GameCountScores(s.game.gameName, count(s.game.gameName)) from "
				+ "Score s group by s.game.gameName order by count(s.game.gameName) desc", GameCountScores.class).getResultList());
		request.setAttribute("gameCountComments", JpaHelper.getEntityManager().createQuery("Select new sk.tsystems.gamestudio.service.jpa.GameCountComments(c.game.gameName, count(c.game.gameName)) from "
				+ "Comment c group by c.game.gameName order by count(c.game.gameName) desc", GameCountComments.class).getResultList());
		request.setAttribute("gameCountRatings", JpaHelper.getEntityManager().createQuery("Select new sk.tsystems.gamestudio.service.jpa.GameCountRatings(r.game.gameName, count(r.game.gameName)) from "
				+ "Rating r group by r.game.gameName order by count(r.game.gameName) desc", GameCountRatings.class).getResultList());
		request.setAttribute("mostActivePlayer",JpaHelper.getEntityManager().createQuery("Select new sk.tsystems.gamestudio.service.jpa.GameCountScores(s.player.playerName, count(s.player.playerName)) from "
				+ "Score s group by s.player.playerName order by count(s.player.playerName) desc", GameCountScores.class).getResultList());
		request.getRequestDispatcher("/WEB-INF/jsp/studioStats.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
}
