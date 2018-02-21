package example.listeners;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import allClasses.Ebook;
import allClasses.Like;
import allClasses.Purchase;
import allClasses.Review;
import allClasses.User;
import sql.AllStatements;

/**
 * An example listener that reads the customer json file and populates the data
 * into a Derby database
 */
@WebListener
public class ManageDb implements ServletContextListener {

	/**
	 * Default constructor.
	 */
	public ManageDb() {
		// TODO Auto-generated constructor stub
		System.out.println("listener");
	}

	// utility that checks whether the customer tables already exists
	private boolean tableAlreadyExists(SQLException e) {
		boolean exists;
		if (e.getSQLState().equals("X0Y32")) {
			exists = true;
		} else {
			exists = false;
		}
		return exists;
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		ServletContext cntx = event.getServletContext();

		try {

			// obtain CustomerDB data source from Tomcat's context
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context
					.lookup(cntx.getInitParameter(AllStatements.DB_DATASOURCE) + AllStatements.OPEN);
			Connection conn = ds.getConnection();

			boolean created = false;
			try {
				// create Customers table
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(AllStatements.CREATE_ADMIN_TABLE);
				// commit update
				stmt.executeUpdate(AllStatements.CREATE_EBOOKS_TABLE);
				conn.commit();
				stmt.executeUpdate(AllStatements.CREATE_USERS_TABLE);
				conn.commit();
				stmt.executeUpdate(AllStatements.CREATE_LIKES_TABLE);
				conn.commit();
				stmt.executeUpdate(AllStatements.CREATE_PURCHASES_TABLE);
				conn.commit();
				stmt.executeUpdate(AllStatements.CREATE_REVIEWS_TABLE);
				conn.commit();
				stmt.close();

			} catch (SQLException e) {
				// check if exception thrown since table was already created (so
				// we created the database already
				// in the past

				System.out.println("we're in the SQL exception");
				created = tableAlreadyExists(e);
				System.out.println(created);
				System.out.println("printing all content of all tables");
				PreparedStatement stm = conn.prepareStatement(AllStatements.getAllUsers);
				ResultSet res = stm.executeQuery();
				System.out.println("USERS TABLE");
				while (res.next()) {
					System.out.println(new User(res.getString("username"), res.getString("email"),
							res.getString("city"), res.getString("streetName"), res.getInt("apartmentNumber"),
							res.getString("postalCode"), res.getString("phoneNumber"), res.getString("password"),
							res.getString("nickname"), res.getString("description"), res.getString("imageURL"), false)
									.toString());
				}
				stm = conn.prepareStatement("select * from ADMIN");
				res = stm.executeQuery();
				System.out.println("admin table");
				while (res.next()) {
					System.out.println(res.getString("name") + " " + res.getString("password") + " "
							+ res.getBoolean("isConnected"));
				}
				stm = conn.prepareStatement(AllStatements.getAllEbooks);
				res = stm.executeQuery();
				System.out.println("EBOOKS TABLE");
				while (res.next()) {
					System.out.println(new Ebook(res.getString("bookName"), res.getString("imageURL"),
							res.getDouble("price"), res.getString("description"), res.getInt("likes")).toString());
				}
				stm = conn.prepareStatement(AllStatements.getAllLikes);
				res = stm.executeQuery();
				System.out.println("LIKES TABLE");
				while (res.next()) {
					System.out.println(
							new Like(res.getString("bookName"), res.getString("username"), res.getString("nickname"))
									.toString());
				}
				stm = conn.prepareStatement(AllStatements.getAllPurchases);
				res = stm.executeQuery();
				System.out.println("PURCHASES TABLE");
				while (res.next()) {
					System.out.println(new Purchase(res.getString("username"), res.getString("bookName"),
							res.getString("creditCardNumber"), res.getString("creditCardCompany"),
							res.getString("creditCardExpiry"), res.getString("CVV"), res.getString("fullName"))
									.toString());
				}
				stm = conn.prepareStatement(AllStatements.getAllReviews);
				res = stm.executeQuery();
				System.out.println("REVIEWS TABLE");
				while (res.next()) {
					System.out.println(new Review(res.getString("bookName"), res.getString("username"),
							res.getString("description"), res.getBoolean("approved")).toString());
				}

				if (!created) {
					throw e;// re-throw the exception so it will be caught in
							// the
					// external try..catch and recorded as error in the log
				}
			}

			// if no database exist in the past - further populate its records
			// in the table
			if (!created) {
				System.out.println("not created");
				System.out.println(
						"we're okay, this is the first time we're creating the tables,inserting data into tables");
				/** admin insertion,only one admin account **/
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ADMIN VALUES('admin','Passw0rd',false)");
				pstmt.executeUpdate();

				/* Users insertion */
				ArrayList<User> users = loadUsers(
						cntx.getResourceAsStream(File.separator + sql.AllStatements.USERS_JSON_FILE));
				pstmt = conn.prepareStatement(sql.AllStatements.addUser);
				for (User u : users) {
					System.out.println(u.toString());
					pstmt.setString(1, u.getUsername());
					pstmt.setString(2, u.getEmail());
					pstmt.setString(3, u.getStreetName());
					pstmt.setInt(4, u.getApartmentNumber());
					pstmt.setString(5, u.getCityName());
					pstmt.setString(6, u.getPostalCode());
					pstmt.setString(7, u.getPhoneNumber());
					pstmt.setString(8, u.getPassword());
					pstmt.setString(9, u.getNickname());
					pstmt.setString(10, u.getDescription());
					pstmt.setString(11, u.getImageURL());
					pstmt.setBoolean(12, u.isConnected());
					pstmt.executeUpdate();
				}

				/* ebooks insertion */
				System.out.println("books json "
						+ cntx.getResourceAsStream(File.separator + sql.AllStatements.BOOKS_JSON_FILE).toString());

				Collection<Ebook> ebooks = loadBooks(
						cntx.getResourceAsStream(File.separator + sql.AllStatements.BOOKS_JSON_FILE));
				pstmt = conn.prepareStatement(sql.AllStatements.addBook);
				for (Ebook ebook : ebooks) {
					System.out.println(ebook.toString());
					pstmt.setString(1, ebook.getName());
					pstmt.setDouble(2, ebook.getPrice());
					pstmt.setString(3, ebook.getDescription());
					pstmt.setInt(4, ebook.getLikes());
					pstmt.setString(5, ebook.getImageURL());
					pstmt.executeUpdate();
				}
				/** likes insertion **/
				Collection<Like> likes = loadLikes(
						cntx.getResourceAsStream(File.separator + sql.AllStatements.LIKES_JSON_FILE));
				pstmt = conn.prepareStatement(sql.AllStatements.addLike);
				for (Like like : likes) {
					System.out.println(like.toString());
					pstmt.setString(1, like.getBookName());
					pstmt.setString(2, like.getNickname());
					pstmt.setString(3, like.getUsername());
					pstmt.executeUpdate();
				}
				Collection<Review> reviews = loadReviews(
						cntx.getResourceAsStream(File.separator + sql.AllStatements.REVIEWS_JSON_FILE));
				pstmt = conn.prepareStatement(sql.AllStatements.addReview);
				for (Review review : reviews) {
					System.out.println(review.toString());
					pstmt.setString(1, review.getBookName());
					pstmt.setString(2, review.getUsername());
					pstmt.setString(3, review.getDescription());
					pstmt.setBoolean(4, review.isApproved());
					pstmt.executeUpdate();
				}
				Collection<Purchase> purchases = loadPurchases(
						cntx.getResourceAsStream(File.separator + sql.AllStatements.PURCHASES_JSON_FILE));
				pstmt = conn.prepareStatement(sql.AllStatements.addPurchase);
				for (Purchase purchase : purchases) {
					System.out.println(purchase.toString());
					pstmt.setString(1, purchase.getUsername());
					pstmt.setString(2, purchase.getBookName());
					pstmt.setString(3, purchase.getCreditCardNumber());
					pstmt.setString(4, purchase.getCreditCardCompany());
					pstmt.setString(5, purchase.getCreditCardExpiry());
					pstmt.setString(6, purchase.getCVV());
					pstmt.setString(7, purchase.getFullName());
					pstmt.executeUpdate();
				}
			}
			
			
			// close connection
			conn.close();

		} catch (SQLException | NamingException | IOException | JSONException e) {
			// log error
			cntx.log("Error during database initialization", e);
		}
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		ServletContext cntx = event.getServletContext();

		// shut down database
		try {
			// obtain CustomerDB data source from Tomcat's context and shutdown
			Context context = new InitialContext();
			BasicDataSource ds = (BasicDataSource) context
					.lookup(cntx.getInitParameter(AllStatements.DB_DATASOURCE) + AllStatements.SHUTDOWN);
			ds.getConnection();
			ds = null;
		} catch (SQLException | NamingException e) {
			cntx.log("Error shutting down database", e);
		}

	}

	private ArrayList<User> loadUsers(InputStream is) throws IOException, JSONException {
		// wrap input stream with a buffered reader to allow reading the file line by
		// line
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonFileContent = new StringBuilder();
		// read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null) {
			jsonFileContent.append(nextLine);
		}
		Gson gson = new Gson();
		// this is a require type definition by the Gson utility so Gson will
		// understand what kind of object representation should the json file match
		// JSONObject jsonUsers = new JSONObject(jsonFileContent);
		Type type = new TypeToken<Collection<User>>() {
		}.getType();
		ArrayList<User> users = gson.fromJson(jsonFileContent.toString(), type);
		// close
		br.close();
		return users;

	}

	private Collection<Ebook> loadBooks(InputStream is) throws IOException {
		// wrap input stream with a buffered reader to allow reading the file line by
		// line
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonFileContent = new StringBuilder();
		// read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null) {
			jsonFileContent.append(nextLine);
		}
		Gson gson = new Gson();
		// this is a require type definition by the Gson utility so Gson will
		// understand what kind of object representation should the json file match
		Type type = new TypeToken<Collection<Ebook>>() {
		}.getType();
		Collection<Ebook> books = gson.fromJson(jsonFileContent.toString(), type);
		// close
		br.close();
		return books;

	}

	private Collection<Like> loadLikes(InputStream is) throws IOException {
		// wrap input stream with a buffered reader to allow reading the file line by
		// line
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonFileContent = new StringBuilder();
		// read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null) {
			jsonFileContent.append(nextLine);
		}
		Gson gson = new Gson();
		// this is a require type definition by the Gson utility so Gson will
		// understand what kind of object representation should the json file match
		Type type = new TypeToken<Collection<Like>>() {
		}.getType();
		Collection<Like> likes = gson.fromJson(jsonFileContent.toString(), type);
		// close
		br.close();
		return likes;

	}

	private Collection<Review> loadReviews(InputStream is) throws IOException {
		// wrap input stream with a buffered reader to allow reading the file line by
		// line
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonFileContent = new StringBuilder();
		// read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null) {
			jsonFileContent.append(nextLine);
		}
		Gson gson = new Gson();
		// this is a require type definition by the Gson utility so Gson will
		// understand what kind of object representation should the json file match
		Type type = new TypeToken<Collection<Review>>() {
		}.getType();
		Collection<Review> reviews = gson.fromJson(jsonFileContent.toString(), type);
		// close
		br.close();
		return reviews;
	}
	
	
	private Collection<Purchase> loadPurchases(InputStream is) throws IOException {
		// wrap input stream with a buffered reader to allow reading the file line by
		// line
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonFileContent = new StringBuilder();
		// read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null) {
			jsonFileContent.append(nextLine);
		}
		Gson gson = new Gson();
		// this is a require type definition by the Gson utility so Gson will
		// understand what kind of object representation should the json file match
		Type type = new TypeToken<Collection<Purchase>>() {
		}.getType();
		Collection<Purchase> purchases = gson.fromJson(jsonFileContent.toString(), type);
		// close
		br.close();
		return purchases;
	}
}
