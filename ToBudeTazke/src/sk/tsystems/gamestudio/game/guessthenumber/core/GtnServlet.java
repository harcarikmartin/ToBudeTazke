package sk.tsystems.gamestudio.game.guessthenumber.core;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sk.tsystems.gamestudio.entity.jpa.Score;
import sk.tsystems.gamestudio.service.jpa.GameJpa;
import sk.tsystems.gamestudio.service.jpa.PlayerJpa;
import sk.tsystems.gamestudio.service.jpa.ScoreJpa;

@WebServlet("/gtn")
public class GtnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		int numberOfTries = 0;
		GuessTheNumber gtn = (GuessTheNumber) session.getAttribute("gtn");
		if (gtn == null) {
			gtn = new GuessTheNumber(208);
			session.setAttribute("gtn", gtn);
		}
		
		out.println("<form method='get'>");
		out.println("<table class='gtn'>");
		int mod = gtn.getInterval()%15;
		for(int i = 0; i < gtn.getInterval()/15; i++) {
			out.println("<tr>");
			for(int j = 1; j <= 15; j++) {
				out.printf("<td><a href='?action=play&game=gtn&gtn=%d'>%3d</a></td>", (i * 15 + j), (i * 15 + j));
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
		
		try {
			int guess = Integer.parseInt(request.getParameter("gtn"));
			if(guess > gtn.getNumberToGuess()) {
				out.println("<p>" + guess + " is too high.</p><br>");
				numberOfTries++;
			}
			if(guess < gtn.getNumberToGuess()) {
				out.println("<p>" + guess + " is too low.</p><br>");
				numberOfTries++;
			}
			if(guess == gtn.getNumberToGuess()) {
				out.println("<p>" + guess + " is right. You win!</p><br>");
				int score = 5 * gtn.getInterval() - numberOfTries;
				new ScoreJpa().addScore(new Score(score, new PlayerJpa().setPresentPlayer("ja"), new GameJpa().setPresentGame("gtn")));
				out.printf("<p>Your final score is %5d.</p>", score);
				gtn = new GuessTheNumber(208);
				session.setAttribute("gtn", gtn);
				numberOfTries = 0;
			}
		} catch (Exception e){	
			System.out.println(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
