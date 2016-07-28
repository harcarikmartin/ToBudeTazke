package sk.tsystems.gamestudio.game.guessthenumber.core;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sk.tsystems.gamestudio.entity.jpa.Player;
import sk.tsystems.gamestudio.entity.jpa.Score;
import sk.tsystems.gamestudio.service.jpa.GameJpa;
import sk.tsystems.gamestudio.service.jpa.ScoreJpa;

@WebServlet("/gtn")
public class GtnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int numberOfTries = 0;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		
		GuessTheNumber gtn = (GuessTheNumber) session.getAttribute("gtn");
		if (gtn == null) {
			gtn = new GuessTheNumber(100);
			session.setAttribute("gtn", gtn);
		}
		
		try {
			String newGame = (request.getParameter("newGame").toString());
			if(newGame != null) {
				gtn = new GuessTheNumber(100);
				session.setAttribute("gtn", gtn);
			}
		} catch (Exception e){	
			System.out.println(e.getMessage());
		}
		
		out.println("<form method='get'>");
		out.println("<table  class='game'>");
		int mod = gtn.getInterval()%10;
		for(int i = 0; i < gtn.getInterval()/10; i++) {
			out.println("<tr>");
			for(int j = 1; j <= 10; j++) {
				out.printf("<td><a href='?action=play&game=gtn&gtn=%d'>%3d</a></td>", (i * 10 + j), (i * 10 + j));
				}
			out.println("</tr>");
		}
		if(mod != 0) {
			out.println("<tr>");
			for(int i = 1; i <= mod; i++) {
				out.printf("<td><a href='?action=play&game=gtn&gtn=%d'>%3d</a></td>", (gtn.getInterval() - mod + i), (gtn.getInterval() - mod + i));
			}
			out.println("</tr>");
		}
		out.println("</table>");
		
		out.println("</form>");
		out.println("<br>");
		
		try {
			int guess = Integer.parseInt(request.getParameter("gtn"));
			if(guess > gtn.getNumberToGuess()) {
				out.println("<p>" + guess + " is too high.</p>");
				numberOfTries++;
			}
			if(guess < gtn.getNumberToGuess()) {
				out.println("<p>" + guess + " is too low.</p>");
				numberOfTries++;
			}
			if(guess == gtn.getNumberToGuess()) {
				out.println("<h1>" + guess + " is right. You win!</h1>");
				int score = 5 * gtn.getInterval() - numberOfTries;
				if(session.getAttribute("player") != null) {
					new ScoreJpa().addScore(new Score(score, (Player) session.getAttribute("player"), new GameJpa().setPresentGame("gtn")));
				}
				out.printf("<p>Your final score is %5d.</p>", score);
				gtn = new GuessTheNumber(100);
				session.setAttribute("gtn", gtn);
				numberOfTries = 0;
			}
		} catch (Exception e){	
			System.out.println(e.getMessage());
		}
		out.println("<div class='newGameButton'><form><input type='hidden' name='action' value='play' /><input type='hidden' name='game' value='gtn' /><input type='hidden' name='newGame' value='newgame' /><input type='submit' value='New Game' /></form></div>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
