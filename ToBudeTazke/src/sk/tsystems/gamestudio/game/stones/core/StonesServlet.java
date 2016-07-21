package sk.tsystems.gamestudio.game.stones.core;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class StonesServlet
 */
@WebServlet("/stones")
public class StonesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
			String newGame = (request.getParameter("newGame").toString());
			if(newGame != null) {
				field = new Field(4,4);
				session.setAttribute("field", field);
			}
		} catch (Exception e){	
		}
		
		try {
			int value = Integer.parseInt(request.getParameter("value"));
			field.move(value);
		} catch (Exception e) {
		}

		String command = request.getParameter("command");
		if (command != null) {
			switch (command) {
			case "up":
				field.moveUp();
				break;
			case "down":
				field.moveDown();
				break;
			case "left":
				field.moveLeft();
				break;
			case "right":
				field.moveRight();
				break;
			}
		}
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel='stylesheet' href='stylesheet.css' type='text/css'>");
		out.println("</head>");
		out.println("<body>");
		
		out.println("<h1>Stones</h1><br>");
		out.println("<table border='1'>");

		out.println("<tr><td colspan=6><a href='?command=up'>^</a></td></tr>");
		
		for (int row = 0; row < field.getRowCount(); row++) {
			out.println("<tr>");
			if(row == 0) {
				out.println("<td rowspan=4><a href='?command=left'><</a></td>");
			}
			for (int column = 0; column < field.getColumnCount(); column++) {
				out.println("<td>");
				int value = field.getValueAt(row, column);
				if (value == Field.EMPTY_CELL) {
					out.printf("  ");
				} else {
					out.printf("<a href='?value=%d'>%2d</a>", value, value);
				}
			}
			if(row == 0) {
				out.println("<td rowspan=4><a href='?command=right'>></a></td>");
			}
		}
		out.println("<tr><td colspan=6><a href='?command=down'>v</a></td></tr>");
		out.println("</table>");

//		out.println("<form method='get'>");
//		out.println("Value:<input type='text' name='value'><br>");
//		out.println("<input type='submit'><br>");
//		out.println("</form>");
		
		out.println("<form method='get'>");
		out.println("<input type='submit' name='newGame' value='New Game'><br>");
		out.println("</form><br>");
		
		if (field.isSolved()) {
			out.println("<h1>Vyhral si</h1>");
			// field = new Field(field.getRowCount() + 1, field.getColumnCount()
			// + 1);
			field = new Field(4, 4);
			session.setAttribute("field", field);
		}
		
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
