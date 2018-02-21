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
 * Servlet implementation class GetAllReviewsByBookname
 */
@WebServlet("/GetAllReviewsByBookname")
public class GetAllReviewsByBookname extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllReviewsByBookname() {
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
		String bookName = request.getParameter("bookName");
		if (bookName != null) {
			PrintWriter writer = response.getWriter();
			DataAccess da = null;
			try {
				da = new DataAccess();
			} catch (NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("no connection");
				writer.println(false + " no connection");
			}
			int reviews = 0;
			try {
				reviews = da.getNumOfReviewsByBookname(bookName);
				response.addHeader("Content-Type", "application/json");
				Gson gson = new Gson();
				String jsonReviewsNum = gson.toJson(reviews);
				writer.println(jsonReviewsNum);
				ArrayList<Review> allReviews = da.getAllApprovedReviewsByBookname(bookName);
				String jsonReviews = gson.toJson(allReviews);
				writer.println(jsonReviews);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				writer.println("unable to retrieve reviews for book " + bookName);
			}
			writer.close();
		}
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
