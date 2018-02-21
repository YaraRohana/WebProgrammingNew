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

import allClasses.Purchase;
import da.DataAccess;

/**
 * Servlet implementation class GetAllPurchasesByUsername
 */
@WebServlet("/GetAllPurchasesByUsername")
public class GetAllPurchasesByUsername extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllPurchasesByUsername() {
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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String username = request.getParameter("username");
		if (username != null) {
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

			try {
				ArrayList<Purchase> purchases = da.getPurchasesByUsername(username);
				for (Purchase purchase : purchases) {
					System.out.println(purchase.toString());
				}
				Gson gson = new Gson();
				String jsonPurchases = gson.toJson(purchases);
				response.addHeader("Content-Type", "application/json");// TODO:SEE THIS
				writer.println(jsonPurchases);
				writer.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				writer.println("unable to retrieve purchases");
			}
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
