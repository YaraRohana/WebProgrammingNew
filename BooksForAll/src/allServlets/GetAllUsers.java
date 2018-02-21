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

import allClasses.User;
import da.DataAccess;

/**
 * Servlet implementation class GetAllUsers
 */
@WebServlet("/GetAllUsers")
public class GetAllUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllUsers() {
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
	/*	PrintWriter writer = response.getWriter();
		DataAccess da = null;
		try {
			da = new DataAccess();
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("no connection");
			writer.println(false + " no connection");
		}

		ArrayList<User> users = new ArrayList<User>();
		try {
			users = da.getAllUsers();
			for (User user : users) {
				System.out.println(user.toString());
			}
			Gson gson = new Gson();
			String jsonUsers = gson.toJson(users);
			response.addHeader("Content-Type", "application/json");// TODO:SEE THIS
			writer.println(jsonUsers);
			writer.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			writer.println(false+" unable to retrieve users");
		}*/
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		System.out.println("we are in the post");
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

		ArrayList<User> users = new ArrayList<User>();
		try {
			users = da.getAllUsers();
			for (User user : users) {
				System.out.println(user.toString());
			}
			Gson gson = new Gson();
			String jsonUsers = gson.toJson(users);
			response.addHeader("Content-Type", "application/json");// TODO:SEE THIS
			System.out.println("*******************"+jsonUsers);
			writer.println(jsonUsers);
			writer.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			writer.println(false+" unable to retrieve users");
		}

	}

}
