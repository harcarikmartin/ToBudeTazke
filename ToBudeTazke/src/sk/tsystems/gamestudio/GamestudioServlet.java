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

import sk.tsystems.gamestudio.entity.jpa.Game;
import sk.tsystems.gamestudio.entity.jpa.Player;
import sk.tsystems.gamestudio.service.jpa.CommentJpa;
import sk.tsystems.gamestudio.service.jpa.GameJpa;
import sk.tsystems.gamestudio.service.jpa.PlayerJpa;
import sk.tsystems.gamestudio.service.jpa.RatingJpa;

@WebServlet("/Gamestudio")
public class GamestudioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    List<Game> games = new GameJpa().getGames();
    List<Double> avgRatings = new ArrayList<>();
    List<Integer> ratingsCounts = new ArrayList<>();
    
    
	@Override
    public void init() throws ServletException {
//       new RatingJpa().addRating(new Rating(3, new PlayerJpa().setPresentPlayer("m"), new GameJpa().setPresentGame("minesweeper")));
//       new RatingJpa().addRating(new Rating(4, new PlayerJpa().setPresentPlayer("m"), new GameJpa().setPresentGame("gtn")));
//       new RatingJpa().addRating(new Rating(2, new PlayerJpa().setPresentPlayer("m"), new GameJpa().setPresentGame("stones")));
//       new RatingJpa().addRating(new Rating(5, new PlayerJpa().setPresentPlayer("Janko"), new GameJpa().setPresentGame("minesweeper")));
//       new RatingJpa().addRating(new Rating(1, new PlayerJpa().setPresentPlayer("Janko"), new GameJpa().setPresentGame("gtn")));
//       new RatingJpa().addRating(new Rating(4, new PlayerJpa().setPresentPlayer("Janko"), new GameJpa().setPresentGame("stones")));
		for (int i = 0; i < games.size(); i++) {
			avgRatings.add(i, new RatingJpa().findAverageRatingForGame(new GameJpa().setPresentGame(games.get(i).getGameName())));
		}
		for (int i = 0; i < games.size(); i++) {
			ratingsCounts.add(i,new RatingJpa().findRatingsCountForGame(new GameJpa().setPresentGame(games.get(i).getGameName())));
		}
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
			Player player = new PlayerJpa().setPresentPlayer(request.getParameter("user"));
			request.setAttribute("logged", player.getPlayerName());
			
			request.getRequestDispatcher("/WEB-INF/jsp/gamestudioLogged.jsp").forward(request, response);
		} else if("play".equals(action) && request.getParameter("game") != null){
			request.setAttribute("games", games);
			String gameString = request.getParameter("game");
			request.setAttribute("gamePlay", gameString);
			request.setAttribute("avgRatings", avgRatings);
			request.setAttribute("ratingsCounts", ratingsCounts);
			request.setAttribute("comments", new CommentJpa().findCommentsForGame(new GameJpa().setPresentGame(gameString)));
			request.getRequestDispatcher("/WEB-INF/jsp/gamestudioLogged.jsp").include(request, response);
		} else {
            forwardToList(request, response);
		}
		
//		if ("view".equals(action) && request.getParameter("id") != null) {
//        	Game game = games.get(Integer.parseInt(request.getParameter("id")));
//            request.setAttribute("game", game);
//            request.getRequestDispatcher("/WEB-INF/jsp/view_student.jsp").forward(request, response);
//        } else if ("add".equals(action)) {
//            	request.getRequestDispatcher("/WEB-INF/jsp/add_student.jsp").forward(request, response);
//        } else if ("edit".equals(action) && request.getParameter("id") != null) {
//        	Student student = students.get(Integer.parseInt(request.getParameter("id")));
//            request.setAttribute("student", student);
//        	request.getRequestDispatcher("/WEB-INF/jsp/edit_student.jsp").forward(request, response);
//        } else if ("insert".equals(action)) {
//        	int id = students.size() + 1;
//        	Student student = new Student(id, request.getParameter("firstName"), request.getParameter("lastName"));
//        	students.put(id, student);
//        	forwardToList(request, response);
//        } else if ("save".equals(action)) {
//        	Student student = students.get(Integer.parseInt(request.getParameter("id")));
//            student.setFirstName(request.getParameter("firstName"));
//            student.setLastName(request.getParameter("lastName"));
//            forwardToList(request, response);
         
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void forwardToList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("games", games);
		request.setAttribute("avgRatings", avgRatings);
		request.setAttribute("ratingsCounts", ratingsCounts);
		request.getRequestDispatcher("/WEB-INF/jsp/gamestudioLogged.jsp").forward(request, response);
	}
}
