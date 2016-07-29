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
		
		if ("logMe".equals(action) && "user" != null && "password" != null) {
			if(new PlayerJpa().getId(request.getParameter("user")) == 0 ) {
				request.setAttribute("showLogin", 1);
				request.setAttribute("register", 1);
				request.setAttribute("error", 4);
			} else if(new PlayerJpa().isPasswordCorrect(request.getParameter("user"), request.getParameter("password"))){
				player = new PlayerJpa().setPresentPlayer(request.getParameter("user"), request.getParameter("password"));
				session = request.getSession();
				session.setAttribute("player", player);
			} else if(!new PlayerJpa().isPasswordCorrect(request.getParameter("user"), request.getParameter("password"))){
				request.setAttribute("showLogin", 1);
				request.setAttribute("login", 1);
				request.setAttribute("error", 5);
			}
		} else if("registerMe".equals(action) && "user" != null && "password" != null && "passwordR" != null) {
			if(! (request.getParameter("password")).equals(request.getParameter("passwordR"))) {
				request.setAttribute("showLogin", 1);
				request.setAttribute("register", 1);
				request.setAttribute("error", 1);
			} else if (request.getParameter("password").length() < 6) {
				request.setAttribute("showLogin", 1);
				request.setAttribute("register", 1);
				request.setAttribute("error", 2);
			} else if (new PlayerJpa().getId(request.getParameter("user")) != 0) {
				request.setAttribute("showLogin", 1);
				request.setAttribute("register", 1);
				request.setAttribute("error", 3);
			} else {
				player = new PlayerJpa().setPresentPlayer(request.getParameter("user"), request.getParameter("password"));
				session = request.getSession();
				session.setAttribute("player", player);
			}
		} else if("login".equals(action)) {
			request.setAttribute("showLogin", 1);
			request.setAttribute("login", 1);
		} else if("register".equals(action)) {
			request.setAttribute("register", 1);
		} else if("logout".equals(action)) {
			session.setAttribute("player", null);
			request.setAttribute("defaultLog", 1);
		} else if("play".equals(action) && request.getParameter("game") != null){
			if(request.getParameter("player") == null) {
				request.setAttribute("defaultLog", 1);
			}
			request.setAttribute("comments", new CommentJpa().findCommentsForGame(new GameJpa().setPresentGame(request.getParameter("game"))));
			request.setAttribute("scores", new ScoreJpa().findTenBestScoresForGame(new GameJpa().setPresentGame(request.getParameter("game"))));
		} else if("insertRating".equals(action) && request.getParameter("rating") != null) {
			request.setAttribute("name", player.getPlayerName());
			Game game1 = new GameJpa().setPresentGame(request.getParameter("game"));
			new RatingJpa().addRating(new Rating(Integer.parseInt(request.getParameter("rating")), player, game1));
			request.setAttribute("comments", new CommentJpa().findCommentsForGame(new GameJpa().setPresentGame(request.getParameter("game"))));
			request.setAttribute("scores", new ScoreJpa().findTenBestScoresForGame(new GameJpa().setPresentGame(request.getParameter("game"))));
		} else if("insertComment".equals(action) && !request.getParameter("comment").isEmpty()) {
			request.setAttribute("name", player.getPlayerName());
			Game game1 = new GameJpa().setPresentGame(request.getParameter("game"));
			new CommentJpa().addComment(new Comment(request.getParameter("comment").trim(), player, game1));
			request.setAttribute("comments", new CommentJpa().findCommentsForGame(new GameJpa().setPresentGame(request.getParameter("game"))));
			request.setAttribute("scores", new ScoreJpa().findTenBestScoresForGame(new GameJpa().setPresentGame(request.getParameter("game"))));
		} else {
			request.setAttribute("defaultLog", 1);
		}
		forwardToList(request, response);
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
//		new GameJpa().setPresentGame("flipit");
//		new GameJpa().setPresentGame("gtn");
//		new GameJpa().setPresentGame("stones");
//		new PlayerJpa().setPresentPlayer("root", "root");
//		new RatingJpa().addRating(new Rating(1, new PlayerJpa().setPresentPlayer("root", "root"), new GameJpa().setPresentGame("flipit")));
//		new RatingJpa().addRating(new Rating(1, new PlayerJpa().setPresentPlayer("root", "root"), new GameJpa().setPresentGame("gtn")));
//		new RatingJpa().addRating(new Rating(1, new PlayerJpa().setPresentPlayer("root", "root"), new GameJpa().setPresentGame("stones")));
		serviceUpdate(request);
		request.getRequestDispatcher("/WEB-INF/jsp/gamestudio.jsp").forward(request, response);
	}

	private void updateRatings() {
		try {
			avgRatings.clear();
			ratingsCounts.clear();
			for (int i = 0; i < games.size(); i++) {
				
					avgRatings.add(i, (int) new RatingJpa().findAverageRatingForGame(new GameJpa().setPresentGame(games.get(i).getGameName())));
				
			}
			for (int i = 0; i < games.size(); i++) {
				ratingsCounts.add(i,new RatingJpa().findRatingsCountForGame(new GameJpa().setPresentGame(games.get(i).getGameName())));
			}
		} catch (Exception e) {
			System.err.println("No ratings found." + e.getMessage());
		}
	}
}
