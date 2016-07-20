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

		if (field.isSolved()) {
			out.println("<h1>Vyhral si</h1>");
			// field = new Field(field.getRowCount() + 1, field.getColumnCount()
			// + 1);
			field = new Field(4, 4);
			session.setAttribute("field", field);
		}

		out.println("<table border='1'>");

		for (int row = 0; row < field.getRowCount(); row++) {
			out.println("<tr>");
			for (int column = 0; column < field.getColumnCount(); column++) {
				out.println("<td>");
				int value = field.getValueAt(row, column);
				if (value == Field.EMPTY_CELL) {
					out.printf("  ");
				} else {
					out.printf("<a href='?value=%d'>%2d</a>", value, value);
				}
			}
		}

		out.println("</table>");

		out.println("<form method='get'>");
		out.println("Value:<input type='text' name='value'><br>");
		out.println("<input type='submit'><br>");
		out.println("</form>");

		out.println("<a href='?command=up'>Up</a><br>");
		out.println("<a href='?command=down'>Down</a><br>");
		out.println("<a href='?command=left'>Left</a><br>");
		out.println("<a href='?command=right'>Right</a><br>");
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
