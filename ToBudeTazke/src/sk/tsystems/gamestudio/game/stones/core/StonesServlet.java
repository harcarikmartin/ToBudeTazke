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
		Field field = (Field) session.getAttribute("field");
		if (field == null) {
			field = new Field(4, 4);
			
			session.setAttribute("field", field);
		}
		
		try {
			int value = Integer.parseInt(request.getParameter("value"));
			field.move(value);
			numberOfMoves++;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		String command = request.getParameter("command");
		if (command != null) {
			switch (command) {
			case "up":
				field.moveUp();
				numberOfMoves++;
				break;
			case "down":
				field.moveDown();
				numberOfMoves++;
				break;
			case "left":
				field.moveLeft();
				numberOfMoves++;
				break;
			case "right":
				field.moveRight();
				numberOfMoves++;
				break;
			}
		}
		out.println("<table class='stones'>");

		out.println("<tr><td colspan=6 class='stones'><a href='?action=play&game=stones&command=up'>^</a></td></tr>");
		
		for (int row = 0; row < field.getRowCount(); row++) {
			out.println("<tr>");
			if(row == 0) {
				out.println("<td rowspan=4 class='stones'><a href='?action=play&game=stones&command=left'>&lt;</a></td>");
			}
			for (int column = 0; column < field.getColumnCount(); column++) {
				out.println("<td class='stones'>");
				int value = field.getValueAt(row, column);
				if (value == Field.EMPTY_CELL) {
					out.printf("  ");
				} else {
					out.printf("<a href='?action=play&game=stones&value=%d'>%2d</a>", value, value);
				}
			}
			if(row == 0) {
				out.println("<td rowspan=4 class='stones'><a href='?action=play&game=stones&command=right'>></a></td>");
			}
		}
		out.println("<tr><td colspan=6 class='stones'><a href='?action=play&game=stones&command=down'>v</a></td></tr>");
		out.println("</table>");

		if (field.isSolved()) {
			out.println("<h1>You win!</h1>");
			int score = 5 * field.getColumnCount()*field.getRowCount() - numberOfMoves;
			if(score < 0) {
				score = 0;
			}
			new ScoreJpa().addScore(new Score(score, (Player) session.getAttribute("player"), new GameJpa().setPresentGame("stones")));
			out.printf("<p>Your final score is %5d.</p>", score);
			field = new Field(4, 4);
			session.setAttribute("field", field);
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
