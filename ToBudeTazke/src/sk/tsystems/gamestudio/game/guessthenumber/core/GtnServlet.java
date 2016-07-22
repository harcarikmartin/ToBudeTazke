package sk.tsystems.gamestudio.game.guessthenumber.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/gtn")
public class GtnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		HttpSession session = request.getSession();
		GuessTheNumber gtn = (GuessTheNumber) session.getAttribute("gtn");
		if (gtn == null) {
			gtn = new GuessTheNumber(208);
			session.setAttribute("gtn", gtn);
		}
		
		try {
			String newGame = (request.getParameter("newGame").toString());
			if(newGame != null) {
				gtn = new GuessTheNumber(208);
				session.setAttribute("gtn", gtn);
			}
		} catch (Exception e){	
			System.out.println(e.getMessage());
		}
		
		out.println("<!DOCTYPE HTML>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Guess the Number</title>");
		out.println("<link rel='stylesheet' href='stylesheet.css' type='text/css'>");
		out.println("</head>");
		out.println("<body>");
		
		out.println("<h1>Guess The Number</h1>");
		
		out.println("<form method='get'>");
		out.println("<table>");
		int mod = gtn.getInterval()%15;
		for(int i = 0; i < gtn.getInterval()/15; i++) {
			out.println("<tr>");
			for(int j = 1; j <= 15; j++) {
				out.println("<td><input type='submit' name='gtn' value='"+ (i * 15 + j) +"'></td>");
			}
			out.println("</tr>");
		}
		if(mod != 0) {
			out.println("<tr>");
			for(int i = 1; i <= mod; i++) {
				out.println("<td><input type='submit' name='gtn' value='"+ (gtn.getInterval() - mod + i) +"'></td>");
			}
			out.println("</tr>");
		}
		out.println("</table>");
		out.println("</form>");
		
		try {
			int guess = Integer.parseInt(request.getParameter("gtn"));
			if(guess > gtn.getNumberToGuess()) {
				out.println("<p>" + guess + " is too high.</p><br>");
			}
			if(guess < gtn.getNumberToGuess()) {
				out.println("<p>" + guess + " is too low.</p><br>");
			}
			if(guess == gtn.getNumberToGuess()) {
				out.println("<p>" + guess + " is right. You win!</p><br>");
			}
		} catch (Exception e){	
			System.out.println(e.getMessage());
		}
		
		out.println("<form method='get'>");
		out.println("<input type='submit' name='newGame' value='New Game'><br>");
		out.println("</form><br>");
		
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
