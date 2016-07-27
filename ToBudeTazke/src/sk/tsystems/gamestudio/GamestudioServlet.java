package sk.tsystems.gamestudio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sk.tsystems.gamestudio.entity.jpa.Comment;
import sk.tsystems.gamestudio.entity.jpa.Game;
import sk.tsystems.gamestudio.entity.jpa.Player;
import sk.tsystems.gamestudio.entity.jpa.Rating;
import sk.tsystems.gamestudio.service.jpa.CommentJpa;
import sk.tsystems.gamestudio.service.jpa.GameJpa;
import sk.tsystems.gamestudio.service.jpa.PlayerJpa;
import sk.tsystems.gamestudio.service.jpa.RatingJpa;
import sk.tsystems.gamestudio.service.jpa.ScoreJpa;

@WebServlet("/Gamestudio")
public class GamestudioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    List<Game> games = new GameJpa().getGames();
    List<Integer> avgRatings = new ArrayList<>();
    List<Integer> ratingsCounts = new ArrayList<>();
    Player player;
    HttpSession session;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if ("logMe".equals(action)) {
			session = request.getSession();
			player = (Player) session.getAttribute("player");
			if(player == null) {
				player = new PlayerJpa().setPresentPlayer(request.getParameter("user").trim());
			}
			session.setAttribute("player", player);
			serviceUpdate(request);
			request.getRequestDispatcher("/WEB-INF/jsp/gamestudio.jsp").forward(request, response);
		} else if("login".equals(action)) {
			request.getRequestDispatcher("/WEB-INF/jsp/gamestudioIntro.jsp").forward(request, response);
		} else if("play".equals(action) && request.getParameter("game") != null){
			serviceUpdate(request);
			request.setAttribute("comments", new CommentJpa().findCommentsForGame(new GameJpa().setPresentGame(request.getParameter("game"))));
			request.setAttribute("scores", new ScoreJpa().findTenBestScoresForGame(new GameJpa().setPresentGame(request.getParameter("game"))));
			request.getRequestDispatcher("/WEB-INF/jsp/gamestudio.jsp").include(request, response);
		} else if("insertRating".equals(action) && request.getParameter("rating") != null) {
			request.setAttribute("name", player.getPlayerName());
			Game game1 = new GameJpa().setPresentGame(request.getParameter("game"));
			new RatingJpa().addRating(new Rating(Integer.parseInt(request.getParameter("rating")), player, game1));
			serviceUpdate(request);
			request.getRequestDispatcher("/WEB-INF/jsp/gamestudio.jsp").forward(request, response);
		} else if("insertComment".equals(action) && !request.getParameter("comment").isEmpty()) {
			request.setAttribute("name", player.getPlayerName());
			Game game1 = new GameJpa().setPresentGame(request.getParameter("game"));
			new CommentJpa().addComment(new Comment(request.getParameter("comment").trim(), player, game1));
			serviceUpdate(request);
			request.getRequestDispatcher("/WEB-INF/jsp/gamestudio.jsp").forward(request, response);
		} else {
            forwardToList(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void serviceUpdate(HttpServletRequest request) {
		request.setAttribute("games", games);
		request.setAttribute("gamePlay", request.getParameter("game"));
		updateRatings();
		request.setAttribute("avgRatings", avgRatings);
		request.setAttribute("ratingsCounts", ratingsCounts);
	}
	
	private void forwardToList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		serviceUpdate(request);
		request.getRequestDispatcher("/WEB-INF/jsp/gamestudio.jsp").forward(request, response);
	}

	private void updateRatings() {
		avgRatings.clear();
		ratingsCounts.clear();
		for (int i = 0; i < games.size(); i++) {
			avgRatings.add(i, (int) new RatingJpa().findAverageRatingForGame(new GameJpa().setPresentGame(games.get(i).getGameName())));
		}
		for (int i = 0; i < games.size(); i++) {
			ratingsCounts.add(i,new RatingJpa().findRatingsCountForGame(new GameJpa().setPresentGame(games.get(i).getGameName())));
		}
	}
}
