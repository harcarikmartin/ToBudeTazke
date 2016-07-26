package sk.tsystems.gamestudio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
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
    
    
	@Override
    public void init() throws ServletException {
		
       }
	
	@SessionScoped
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		Game game = (Game) session.getAttribute("Gamestudio");
		if (game == null) {
			game = new Game();
			session.setAttribute("Gamestudio", game);
		}
		
		if ("login".equals(action) && !(request.getParameter("user").trim().isEmpty())) {
			player = new PlayerJpa().setPresentPlayer(request.getParameter("user"));
			request.setAttribute("logged", player.getPlayerName());
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
		} else if("insert".equals(action) && request.getParameter("userName") != null && request.getParameter("rating") != null) {
			Player player = new PlayerJpa().setPresentPlayer(request.getParameter("userName"));
			Game game1 = new GameJpa().setPresentGame(request.getParameter("game"));
			new CommentJpa().addComment(new Comment(request.getParameter("comment"), player, game1));
			new RatingJpa().addRating(new Rating(Integer.parseInt(request.getParameter("rating")), player, game1));
			forwardToList(request, response);
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
		request.getRequestDispatcher("/WEB-INF/jsp/gamestudioLogged.jsp").forward(request, response);
	}
}
