package sk.tsystems.gamestudio.game.stones.core;

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
@WebServlet("/stones")
public class StonesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int numberOfMoves = 0;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");

		HttpSession session = request.getSession();
		Field fieldStones = (Field) session.getAttribute("field");
		if (fieldStones == null) {
			fieldStones = new Field(4, 4);
			
			session.setAttribute("field", fieldStones);
		}
		
		try {
			String newGame = (request.getParameter("newGame").toString());
			if(newGame != null) {
				fieldStones = new Field(4, 4);
				session.setAttribute("field", fieldStones);
			}
		} catch (Exception e){	
			System.out.println(e.getMessage());
		}
		
		try {
			int value = Integer.parseInt(request.getParameter("value"));
			fieldStones.move(value);
			numberOfMoves++;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		String command = request.getParameter("command");
		if (command != null) {
			switch (command) {
			case "up":
				fieldStones.moveUp();
				numberOfMoves++;
				break;
			case "down":
				fieldStones.moveDown();
				numberOfMoves++;
				break;
			case "left":
				fieldStones.moveLeft();
				numberOfMoves++;
				break;
			case "right":
				fieldStones.moveRight();
				numberOfMoves++;
				break;
			}
		}
		
		out.println("<hr>");
		out.println("<table>");
		for (int row = 0; row < fieldStones.getRowCount(); row++) {
			out.println("<tr>");
			out.println("<td class='side'></td>");
			for (int column = 0; column < fieldStones.getColumnCount(); column++) {
				out.println("<td class='stones'>");
				int value = fieldStones.getValueAt(row, column);
				if (value == Field.EMPTY_CELL) {
					out.printf("  ");
				} else {
					out.printf("<a href='?action=play&game=stones&value=%d'>%2d</a>", value, value);
				}
				out.println("</td>");
				
				
			}
			out.println("<td class='side'></td>");
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("<div><form><input type='hidden' name='action' value='play' /><input type='hidden' name='game' value='stones' /><input type='hidden' name='newGame' value='newgame' /><input type='submit' value='New Game' /></form></div>");
		

		if (fieldStones.isSolved()) {
			out.println("<h1>You win!</h1>");
			int score = 10 * fieldStones.getColumnCount()*fieldStones.getRowCount() - numberOfMoves;
			if(score < 0) {
				score = 0;
			}
			new ScoreJpa().addScore(new Score(score, (Player) session.getAttribute("player"), new GameJpa().setPresentGame("stones")));
			out.printf("<p>Your final score is %5d.</p>", score);
			fieldStones = new Field(4, 4);
			session.setAttribute("field", fieldStones);
			numberOfMoves = 0;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
