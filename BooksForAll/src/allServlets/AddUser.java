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

import com.google.gson.Gson;

import allClasses.User;
import da.DataAccess;
import da.DataAccess.userInfoResult;

/**
 * Servlet implementation class AddUser
 */
@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("yaraaaaa");
		StringBuilder jsonFileContent = new StringBuilder();
		String line = null;
		boolean res = false;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
			jsonFileContent.append(line);
		System.out.println("jsonFileContent " + jsonFileContent);
		/*
		 * JSONObject tmp = new JSONObject(jsonFileContent); String username =
		 * tmp.getString("username"); String nickname = tmp.getString("nickname");
		 * String email = tmp.getString("email"); String telPrefix =
		 * tmp.getString("telStart"); String phoneNum = tmp.getString("telNum"); String
		 * password = tmp.getString("pass"); String streetName =
		 * tmp.getString("streetName"); String apartmentNum =
		 * tmp.getString("apartmentNum"); String city = tmp.getString("city"); String
		 * postalCode = tmp.getString("postalCode"); String description =
		 * tmp.getString("description"); String image = tmp.getString("imageURL");
		 */
		String newUser = jsonFileContent.toString();
		System.out.println("THE NEW USER IS:" + newUser);
		Gson gson = new Gson();
		User user = gson.fromJson(newUser, User.class);
		System.out.println("the new user as USER class is " + user.toString());
		if (user != null) {
			PrintWriter writer=response.getWriter();
			DataAccess da = null;
			try {
				da = new DataAccess();
			} catch (NamingException | SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			System.out.println("finished connecting to DB");
			userInfoResult result = null;
			try {
				result = da.validateInput(user.getUsername(), user.getNickname(), user.getEmail(),
						user.getPhoneNumber(), user.getPassword(), user.getStreetName(), user.getApartmentNumber(),
						user.getCityName(), user.getPostalCode());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (result.equals(userInfoResult.usernameAlreadyExists)) {
				writer.println("Username already exists");
			}
			if (result.equals(userInfoResult.userNameException)) {
				writer.println("Username is a required field and must be 1-10 characters long");
			} else if (result.equals(userInfoResult.nicknameException)) {
				writer.println("Nickname is a required field and must be 1-20 characters");
			} else if (result.equals(userInfoResult.apartmentNumberException)) {
				writer.println("Apartment number must be at least 1");
			} else if (result.equals(userInfoResult.cityException)) {
				writer.println(
						"City name must be at least 3 characters long and must consist of letters and spaces only");
			} else if (result.equals(userInfoResult.passwordException)) {
				writer.println("Password is a required field and must be 1-8 characters long");
			} else if (result.equals(userInfoResult.phoneNumException)) {
				writer.println("Phone Number is a required field and must either a Israeli land line or a mobile");
			} else if (result.equals(userInfoResult.streetNameException)) {
				writer.println(
						"Street name must be at least 3 characters long and must consist of letters and spaces only");
			} else if (result.equals(userInfoResult.postalCodeException)) {
				writer.println("Postal code must be 7 digits long");
			} else if (result.equals(userInfoResult.correctInfo)) {
				try {
					user.setConnected(true);
					res = da.addUser(user);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					writer.println("Unable to add User");
				}
				if (res) {
					writer.println(true);
				} else {
					writer.println(false + " Unable to add user");
				}

			}
		}

	}

}
