package allServlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import allClasses.Review;
import da.DataAccess;

/**
 * Servlet implementation class GetAllUnapprovedReviews
 */
@WebServlet("/GetAllUnapprovedReviews")
public class GetAllUnapprovedReviews extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllUnapprovedReviews() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		DataAccess da = null;
		PrintWriter writer = response.getWriter();
		try {
			da = new DataAccess();
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("no connection");
			writer.println(false + " no connection");
		}
		ArrayList<Review> reviews = new ArrayList<Review>();
		try {
			reviews = da.getAllUnapprovedReviews();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			writer.println("unable to retrieve unapproved reviews");
		}
		Gson gson = new Gson();
		String jsonReviews = gson.toJson(reviews);
		response.addHeader("Content-Type", "application/json");
		writer.println(jsonReviews);
		writer.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
