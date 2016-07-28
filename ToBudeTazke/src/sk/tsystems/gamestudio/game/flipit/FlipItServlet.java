package sk.tsystems.gamestudio.game.flipit;

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

/**
 * Servlet implementation class StonesServlet
 */
@WebServlet("/flipit")
public class FlipItServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int numberOfMoves = 0;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		HttpSession session = request.getSession();
		Field fieldFlip = (Field) session.getAttribute("flipitfield");
		if (fieldFlip == null) {
			fieldFlip = new Field(5, 5);
			
			session.setAttribute("flipitfield", fieldFlip);
		}
		
		try {
			String newGame = (request.getParameter("newGame").toString());
			if(newGame != null) {
				if(request.getParameter("level").toString().equals("easy")) {
					fieldFlip = new Field(4, 4);
				} else {
					fieldFlip = new Field(5, 5);
				}
				session.setAttribute("flipitfield", fieldFlip);
				numberOfMoves = 0;
			}
		} catch (Exception e){	
			System.out.println(e.getMessage());
		}
		
		try {
//			int value = Integer.parseInt(request.getParameter("value").toString());
			int row = Integer.parseInt(request.getParameter("row").toString());
			int column = Integer.parseInt(request.getParameter("column").toString());
			fieldFlip.flip(row, column);
			numberOfMoves++;
		} catch (Exception e){	
			System.out.println(e.getMessage());
		}
		
		out.println("<table>");
		for (int row = 0; row < fieldFlip.getRowCount(); row++) {
			out.println("<tr>");
			for (int column = 0; column < fieldFlip.getColumnCount(); column++) {
				out.println("<td class='flipit'>");
				if (fieldFlip.getValueAt(row, column) == false) {
					out.printf("<a href='?action=play&amp;game=flipit&amp;row=%d&amp;column=%d'><img alt='blackdot' src='images/black.png'></a>", row, column);
				} else {
					out.printf("<a href='?action=play&amp;game=flipit&amp;row=%d&amp;column=%d'><img alt='whitedot' src='images/white.png'></a>", row, column);
				}
				out.println("</td>");	
			}
		}
		out.println("</table>");
		out.println("<br>");
		out.println("<div class='newGameButton'>"
				+ "<form action=''><input type='hidden' name='action' value='play' />"
					+ "<input type='hidden' name='game' value='flipit' />"
					+ "<input type='hidden' name='newGame' value='newgame' />"
					+ "<input type='submit' value='New Game' />"
					+ "&nbsp;Easy <input type='radio' name='level' value='easy' checked='checked'/>"
					+ "&nbsp;Medium <input type='radio' name='level' value='medium' />"
				+ "</form>"
				+ "</div>");
		

		if (fieldFlip.isSolved()) {
			out.println("<h1>You win!</h1>");
			int score = 10 * fieldFlip.getColumnCount()*fieldFlip.getRowCount() - numberOfMoves;
			if(score < 0) {
				score = 0;
			}
			if(session.getAttribute("player") != null) {
				new ScoreJpa().addScore(new Score(score, (Player) session.getAttribute("player"), new GameJpa().setPresentGame("flipit")));
			}
			out.printf("<p>Your final score is %5d.</p>", score);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
