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
    List<Double> avgRatings = new ArrayList<>();
    List<Integer> ratingsCounts = new ArrayList<>();
    Player player;
    HttpSession session = null;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if ("login".equals(action)) {
			session = request.getSession();
			player = (Player) session.getAttribute("player");
			if(player == null) {
				if(request.getParameter("user").trim().isEmpty()) {
					player = new PlayerJpa().setPresentPlayer("default");
				}else {
					player = new PlayerJpa().setPresentPlayer(request.getParameter("user"));
				}
				session.setAttribute("player", player);
			}
			request.setAttribute("games", games);
			String gameString = request.getParameter("game");
			request.setAttribute("gamePlay", gameString);
			request.setAttribute("avgRatings", avgRatings);
			request.setAttribute("ratingsCounts", ratingsCounts);
			request.getRequestDispatcher("/WEB-INF/jsp/gamestudioLogged.jsp").forward(request, response);
		} else if("play".equals(action) && request.getParameter("game") != null){
			request.setAttribute("games", games);
			String gameString = request.getParameter("game");
			request.setAttribute("gamePlay", gameString);
			request.setAttribute("avgRatings", avgRatings);
			request.setAttribute("ratingsCounts", ratingsCounts);
			request.setAttribute("comments", new CommentJpa().findCommentsForGame(new GameJpa().setPresentGame(gameString)));
			request.setAttribute("scores", new ScoreJpa().findTenBestScoresForGame(new GameJpa().setPresentGame(gameString)));
			request.getRequestDispatcher("/WEB-INF/jsp/gamestudioLogged.jsp").include(request, response);
		} else if("insert".equals(action) && request.getParameter("rating") != null) {
			request.setAttribute("name", player.getPlayerName());
			Game game1 = new GameJpa().setPresentGame(request.getParameter("game"));
			new CommentJpa().addComment(new Comment(request.getParameter("comment").trim(), player, game1));
			new RatingJpa().addRating(new Rating(Integer.parseInt(request.getParameter("rating")), player, game1));
			request.setAttribute("games", games);
			String gameString = request.getParameter("game");
			request.setAttribute("gamePlay", gameString);
			request.setAttribute("avgRatings", avgRatings);
			request.setAttribute("ratingsCounts", ratingsCounts);
			request.getRequestDispatcher("/WEB-INF/jsp/gamestudioLogged.jsp").forward(request, response);
		} else {
            forwardToList(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void forwardToList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		avgRatings.clear();
		ratingsCounts.clear();
		for (int i = 0; i < games.size(); i++) {
			avgRatings.add(i, new RatingJpa().findAverageRatingForGame(new GameJpa().setPresentGame(games.get(i).getGameName())));
		}
		for (int i = 0; i < games.size(); i++) {
			ratingsCounts.add(i,new RatingJpa().findRatingsCountForGame(new GameJpa().setPresentGame(games.get(i).getGameName())));
		}
		request.setAttribute("games", games);
		request.setAttribute("avgRatings", avgRatings);
		request.setAttribute("ratingsCounts", ratingsCounts);
		request.getRequestDispatcher("/WEB-INF/jsp/gamestudioIntro.jsp").forward(request, response);
		
		
	}
}
