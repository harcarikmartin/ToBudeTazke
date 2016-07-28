package sk.tsystems.gamestudio.game.minesweeper.core;

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
import sk.tsystems.gamestudio.game.minesweeper.core.Tile.State;
import sk.tsystems.gamestudio.service.jpa.GameJpa;
import sk.tsystems.gamestudio.service.jpa.ScoreJpa;

@WebServlet("/minesweeper")
public class MinesweeperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	long startMillis = System.currentTimeMillis();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final int ROWS = 9;
		final int COLS = 9;
		final int MINES = 11;
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		HttpSession session = request.getSession();
		Field field = (Field) session.getAttribute("minesfield");
		if (field == null) {
			field = new Field(ROWS, COLS, MINES);
			session.setAttribute("minesfield", field);
			startMillis = System.currentTimeMillis();
		}
		
		try {
			String newGame = (request.getParameter("newGame").toString());
			if(newGame != null) {
				if(request.getParameter("level").toString().equals("easy")) {
					field = new Field(6, 6, 5);
				} else {
					field = new Field(ROWS, COLS, MINES);
				}
				session.setAttribute("minesfield", field);
				startMillis = System.currentTimeMillis();
			}
		} catch (Exception e){	
			System.out.println(e.getMessage());
		}
		
		try {
			int valueX = Integer.parseInt(request.getParameter("valuex"));
			int valueY = Integer.parseInt(request.getParameter("valuey"));
			int mark = Integer.parseInt(request.getParameter("mark"));
			
			if(mark  == 1) {
				field.markTile(valueX, valueY);
			} else {
				field.openTile(valueX, valueY);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		if (field.getState().equals(GameState.SOLVED)) {
			int time = (int)((System.currentTimeMillis() - startMillis) / 1000);
			out.println("<h1 class='finished'>You Win!</h1>");
			if(session.getAttribute("player") != null) {
				new ScoreJpa().addScore(new Score(600 - time, (Player) session.getAttribute("player"), new GameJpa().setPresentGame("minesweeper")));
			}
			out.println("<p>It took you " + time + " seconds.</p>");
		} 
		if (field.getState().equals(GameState.FAILED)) {
			out.println("<h1 class='finished'>You Lost! Let's try again.</h1>");
		} 
		out.println("<table class='game'>");

		for (int x = 0; x < field.getRowCount(); x++) {
			out.println("<tr>");
			for (int y = 0; y < field.getColumnCount(); y++) {
				out.println("<td>");
				if (field.getTile(x, y) instanceof Mine && field.getTile(x, y).getState().equals(State.OPEN)) {
					out.print("<img alt='mark' src='images/mine.jpg'>");
				} else if (field.getTile(x, y) instanceof Clue && field.getTile(x, y).getState().equals(State.OPEN)) {
					switch (((Clue) field.getTile(x, y)).getValue()) {
					case 0:
						out.print("<img alt='open' src='images/0.png'>");
						break;
					case 1:
						out.print("<img alt='open' src='images/1.png'>");
						break;
					case 2:
						out.print("<img alt='open' src='images/2.png'>");
						break;
					case 3:
						out.print("<img alt='open' src='images/3.png'>");
						break;
					case 4:
						out.print("<img alt='open' src='images/4.png'>");
						break;
					case 5:
						out.print("<img alt='open' src='images/5.png'>");
						break;

					default:
						out.print("&nbsp;" + ((Clue) field.getTile(x, y)).getValue());
						break;
					}
					
				} else if (field.getTile(x, y).getState().equals(State.MARKED)) {
					out.printf("<a href='?action=play&game=minesweeper&valuex=%d&valuey=%d&mark=%d'><img alt='marked' src='images/mark.jpg'></a>", x, y, 1);
				} else if (field.getTile(x, y).getState().equals(State.CLOSED)) {
					out.println("<div>");
					out.printf("<a href='?action=play&game=minesweeper&valuex=%d&valuey=%d&mark=%d'><img alt='open' src='images/openCommand.png'></a>", x, y, 0);
					out.println("</div>");
					out.println("<div>");
					out.printf("<a href='?action=play&game=minesweeper&valuex=%d&valuey=%d&mark=%d'><img alt='mark' src='images/markCommand.png'></a>", x, y, 1);
					out.println("</div>");
				}
				out.println("</td>");
				
			}
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("<br>");
		
		out.println("<p>Remaining mines: " + field.getRemainingMineCount() + "</p>");
		out.println("<br>");
		out.println("<form class='newGameButton'>"
					+ "<input type='hidden' name='action' value='play' />"
					+ "<input type='hidden' name='game' value='minesweeper' />"
					+ "<input type='hidden' name='newGame' value='newgame' />"
					+ "<input type='submit' value='New Game' />"
					+ "&nbsp;Easy <input type='radio' name='level' value='easy' checked='checked'/>"
					+ "&nbsp;Medium <input type='radio' name='level' value='medium' />"
				+ "</form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
