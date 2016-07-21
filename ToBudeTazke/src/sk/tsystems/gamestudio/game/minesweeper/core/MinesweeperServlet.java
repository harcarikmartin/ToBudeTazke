package sk.tsystems.gamestudio.game.minesweeper.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sk.tsystems.gamestudio.game.minesweeper.core.Tile.State;

@WebServlet("/minesweeper")
public class MinesweeperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final int ROWS = 8;
		final int COLS = 8;
		final int MINES = 10;
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		HttpSession session = request.getSession();
		Field field = (Field) session.getAttribute("field");
		if (field == null) {
			field = new Field(ROWS, COLS, MINES);
			session.setAttribute("field", field);
		}
		
		try {
			String newGame = (request.getParameter("newGame").toString());
			if(newGame != null) {
				field = new Field(ROWS, COLS, MINES);
				session.setAttribute("field", field);
			}
		} catch (Exception e){	
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
		}
		
		out.println("<h1>Minesweeper</h1><br>");
		out.println("<table border='1'>");

		for (int x = 0; x < field.getRowCount(); x++) {
			out.println("<tr>");
			for (int y = 0; y < field.getColumnCount(); y++) {
				out.println("<td>");
				if (field.getTile(x, y) instanceof Mine && field.getTile(x, y).getState().equals(State.OPEN)) {
					out.print("&nbsp;X");
				} else if (field.getTile(x, y) instanceof Clue && field.getTile(x, y).getState().equals(State.OPEN)) {
					out.print("&nbsp;" + ((Clue) field.getTile(x, y)).getValue());
				} else if (field.getTile(x, y).getState().equals(State.MARKED)) {
					out.printf("<a href='?valuex=%d&valuey=%d&mark=%d'>&nbsp;*</a>", x, y, 1);
				} else if (field.getTile(x, y).getState().equals(State.CLOSED)) {
					out.printf("<a href='?valuex=%d&valuey=%d&mark=%d'>O</a>", x, y, 0);
					out.printf("<a href='?valuex=%d&valuey=%d&mark=%d'>*</a>", x, y, 1);
				}
				out.println("</td>");
				
			}
			out.println("</tr>");
		}
		out.println("</table><br>");
		
		out.println("<form method='get'>");
		out.println("<input type='submit' name='newGame' value='New Game'><br>");
		out.println("</form><br>");
		
		if (field.getState().equals(GameState.SOLVED)) {
			out.println("<h1 class='finished'>Vyhral si</h1>");
		} 
		if (field.getState().equals(GameState.FAILED)) {
			out.println("<h1 class='finished'>Prehral si</h1>");
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
