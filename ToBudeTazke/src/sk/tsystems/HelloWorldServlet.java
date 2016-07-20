package sk.tsystems;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloWorldServlet
 */
@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		response.setContentType("text/html");
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title>To je tazke</title>");
		out.println("</head>");
		out.println("<body>");
		out.append("Served at: ").append(request.getContextPath());
		out.printf("<h1>Today is %s</h1>", new Date());
		out.println("<br>");
		
		if(request.getParameter("meno") != null) {
			out.printf("<h2>Ahoj %s</h2>", request.getParameter("meno"));
		}
		
		out.println("<form>");
		out.println("Meno: <input type = 'text' name = 'meno'><br>");
		out.println("<input type = 'submit'><br>");
		out.println("<form>");
		
		out.println("</body>");
		out.println("</html>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
