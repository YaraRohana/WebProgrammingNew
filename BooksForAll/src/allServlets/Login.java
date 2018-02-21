package allServlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;


import da.DataAccess;
import da.DataAccess.adminLogInResult;
import da.DataAccess.userLogInResult;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		StringBuilder jsonFileContent = new StringBuilder();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
			jsonFileContent.append(line);

		JSONObject loginInfoObj = new JSONObject(jsonFileContent);
		String username = null, password = null;
		try {
			username = loginInfoObj.getString("username");
			password = loginInfoObj.getString("password");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DataAccess da = null;
		try {
			da = new DataAccess();
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter writer = response.getWriter();
		if (username.equals("admin")) {//Admin trying to sign in 
			adminLogInResult result = null;
			try {
				result = da.validateLoginAdmin(password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (result.equals(adminLogInResult.adminAlreadyLoggedIn)) {
				writer.println(false);
				writer.println("User already logged in");
				return;
			} else if (result.equals(adminLogInResult.wrongPassword)) {
				writer.println(false);
				writer.println("Wrong password");
				return;
			} else if (result.equals(adminLogInResult.loginSuccess)) {
				writer.println(true);
				
				writer.println("Admin signed in successfully");
				return;
			}
		} else {//User trying to log in is not an Admin
			userLogInResult userResult = null;
			try {
				userResult = da.validateLoginUser(username, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (userResult.equals(userLogInResult.usernameDoesNotExist)) {
				writer.println(false);
				writer.println("Username does not exist");
				return;
			} else if (userResult.equals(userLogInResult.userAlreadyLoggedIn)) {
				writer.println(false);
				writer.println("User already logged in");
				return;
			} else if (userResult.equals(userLogInResult.wrongPassword)) {
				writer.println(false);
				writer.println("Wrong password");
				return;
			} else if (userResult.equals(userLogInResult.loginSuccess)) {
				writer.println(true);
				writer.println("User logged in successfully");
				writer.flush();
				writer.close();
				return;
			}
		}
	}

}
