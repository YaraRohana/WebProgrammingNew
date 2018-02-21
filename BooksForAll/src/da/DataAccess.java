package da;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.derby.tools.sysinfo;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import allClasses.Admin;
import allClasses.Ebook;
import allClasses.Like;
import allClasses.Purchase;
import allClasses.Review;
import allClasses.User;
import sql.AllStatements;

public class DataAccess {
	private static Connection conn;

	public DataAccess() throws NamingException, SQLException {
		Logger logger = Logger.getLogger(DataAccess.class.getName());
		logger.log(Level.INFO, "DataAccess c'tor: attempting connection...");

		Context context = new InitialContext();
		BasicDataSource ds = (BasicDataSource) context
				.lookup("java:comp/env/jdbc/ExampleDatasource" + AllStatements.OPEN);
		conn = ds.getConnection();

	}

	public enum creditCardInfoResult {
		creditCardNumberException, CvvException, expiredCardException, correctInfo
	}
	
	public enum userInfoResult {
		usernameAlreadyExists,streetNameException, apartmentNumberException, userNameException, cityException, nicknameException, phoneNumException, passwordException, postalCodeException, emailException, correctInfo
	}
	
	public enum userLogInResult {
		userAlreadyLoggedIn,usernameDoesNotExist,wrongPassword,loginSuccess
	}
	
	public enum adminLogInResult{
		adminAlreadyLoggedIn,wrongPassword,loginSuccess
	}

	public boolean checkIfUserExists(String username) throws SQLException {
		PreparedStatement stm = conn.prepareStatement(AllStatements.selectUserByName);
		stm.setString(1, username);
		ResultSet res = stm.executeQuery();
		return res.next();
	}

	public boolean addUser(User user) throws SQLException {
		boolean res = checkIfUserExists(user.getUsername());
		if (res) {// cannot add an already existing user
			return false;
		}
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.addUser);
		stm.setString(1, user.getUsername());
		stm.setString(2, user.getEmail());
		stm.setString(3, user.getStreetName());
		stm.setInt(4, user.getApartmentNumber());
		stm.setString(5, user.getCityName());
		stm.setString(6, user.getPostalCode());
		stm.setString(7, user.getPhoneNumber());
		stm.setString(8, user.getPassword());
		stm.setString(9, user.getNickname());
		stm.setString(10, user.getDescription());
		stm.setString(11, user.getImageURL());
		stm.setBoolean(12, user.isConnected());
		stm.executeUpdate();
		return true;
	}

	public void removeUser(String username) throws SQLException {
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.deleteUser);
		stm.setString(1, username);
		stm.executeUpdate();
	}

	public User getUserByName(String username) throws SQLException {
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.selectUserByName);
		stm.setString(1, username);
		ResultSet res = stm.executeQuery();
		User u = null;
		while (res.next()) {
			u = new User(res.getString("username"), res.getString("email"), res.getString("city"),
					res.getString("streetName"), res.getInt("apartmentNumber"), res.getString("postalCode"),
					res.getString("phoneNumber"), res.getString("password"), res.getString("nickname"),
					res.getString("description"), res.getString("imageURL"), res.getBoolean("isConnected"));
		}
		return u;
	}

	public User getUserByNickname(String nickname) throws SQLException {
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.selectUserByNickname);
		stm.setString(1, nickname);
		ResultSet res = stm.executeQuery();
		User u = null;
		while (res.next()) {
			u = new User(res.getString("username"), res.getString("email"), res.getString("city"),
					res.getString("streetName"), res.getInt("apartmentNumber"), res.getString("postalCode"),
					res.getString("phoneNumber"), res.getString("password"), nickname, res.getString("description"),
					res.getString("imageURL"), res.getBoolean("isConnected"));
		}
		return u;
	}

	public ArrayList<User> getAllUsers() throws SQLException {
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.getAllUsers);
		ResultSet res = stm.executeQuery();
		ArrayList<User> users = new ArrayList<User>();
		while (res.next()) {
			users.add(new User(res.getString("username"), res.getString("email"), res.getString("city"),
					res.getString("streetName"), res.getInt("apartmentNumber"), res.getString("postalCode"),
					res.getString("phoneNumber"), res.getString("password"), res.getString("nickname"),
					res.getString("description"), res.getString("imageURL"), res.getBoolean("isConnected")));
		}
		return users;
	}

	boolean validatePhoneNumber(String phoneNumber) {
		if (phoneNumber.startsWith("03") || phoneNumber.startsWith("02") || phoneNumber.startsWith("04")
				|| phoneNumber.startsWith("08") || phoneNumber.startsWith("09")) {
			return phoneNumber.length() == 9;
		} else if (phoneNumber.startsWith("050") || phoneNumber.startsWith("051") || phoneNumber.startsWith("052")
				|| phoneNumber.startsWith("053") || phoneNumber.startsWith("054") || phoneNumber.startsWith("055")
				|| phoneNumber.startsWith("056") || phoneNumber.startsWith("058") || phoneNumber.startsWith("059")) {
			return phoneNumber.length() == 10;
		}
		return false;
	}

	boolean validatePostalCode(String postalCode) {
		return postalCode.length() == 7;
	}

	boolean validateString(String str) {
		String regex = "^[a-zA-Z\\s]+$";
		return str.matches(regex);
	}

	boolean validateEmailAddress(String email) {
		String emailRegex = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$";
		return email.matches(emailRegex);
	}

	boolean validateStreetAndCity(String str) {
		return str.length() >= 3 && validateString(str);
	}

	boolean validatePasswordLength(String pass) {
		return pass.length() <= 8;
	}



	public userInfoResult validateInput(String username, String nickname, String email, String phoneNumber,
			String password, String streetName, int apartment, String city, String postalCode) throws SQLException {
		System.out.println("we're in the validate");
		boolean result=checkIfUserExists(username);
		if(result==true) {
			System.out.println("here1");
			return userInfoResult.usernameAlreadyExists;
		}
		 result = username.length() >= 1 && username.length() <= 10;
		if (result == false) {
			System.out.println("here2");
			return userInfoResult.userNameException;
		}
		result = nickname.length() >= 1 && nickname.length() <= 20;
		if (result == false) {
			System.out.println("here3");
			return userInfoResult.nicknameException;
		}
		result = validateStreetAndCity(city);
		if (result == false) {
			System.out.println("here4");
			return userInfoResult.cityException;
		}
		result = validateStreetAndCity(streetName);
		if (result == false) {
			System.out.println("here5");
			return userInfoResult.streetNameException;
		}
		result = apartment > 1;
		if (result == false) {
			System.out.println("here6");
			return userInfoResult.apartmentNumberException;
		}
		result = validatePostalCode(postalCode);
		if (result == false) {
			System.out.println("here7");
			return userInfoResult.postalCodeException;
		}
		result = validateEmailAddress(email);
		if (result == false) {
			System.out.println("here8");
			return userInfoResult.emailException;
		}
		result = validatePhoneNumber(phoneNumber);
		if (result == false) {
			System.out.println("here9");
			return userInfoResult.phoneNumException;
		}
		result = password.length() >= 1 && password.length() <= 8;
		if (result == false) {
			System.out.println("here10");
			return userInfoResult.passwordException;
		}
		System.out.println("here11");
		return userInfoResult.correctInfo;

	}

	public ArrayList<Purchase> getPurchasesByUsername(String username) throws SQLException {
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.getPurchasesByUsername);
		stm.setString(1, username);
		ResultSet res = stm.executeQuery();
		ArrayList<Purchase> purchases = new ArrayList<Purchase>();
		while (res.next()) {
			purchases.add(new Purchase(username, res.getString("bookName"), res.getString("creditCardNumber"),
					res.getString("creditCardCompany"), res.getString("creditCardExpiry"), res.getString("CVV"),
					res.getString("fullName")));
		}
		return purchases;
	}

	public userLogInResult validateLoginUser(String username, String password) throws SQLException {
		User u=getUserByName(username);
		if(u==null) {
			return userLogInResult.usernameDoesNotExist;
		}
		if(u.isConnected()==true) {
			return userLogInResult.userAlreadyLoggedIn;
		}
		if(password.equals(u.getPassword())==false) {
			return userLogInResult.wrongPassword;
		}
		PreparedStatement stm=conn.prepareStatement(sql.AllStatements.signInUser);
		stm.setString(1, username);
		stm.executeUpdate();
		return userLogInResult.loginSuccess;
	}

	public Admin getAdmin() throws SQLException {
		PreparedStatement stm=conn.prepareStatement(sql.AllStatements.getAdmin);
		ResultSet res=stm.executeQuery();
		Admin a=null;
		while(res.next()) {
			a=new Admin(res.getString("name"), res.getString("password"), res.getBoolean("isConnected"));
		}
		return a;
	}
	
	public adminLogInResult validateLoginAdmin(String password) throws SQLException {
		Admin admin=getAdmin();
		if(admin.isConnected()==true) {
			return adminLogInResult.adminAlreadyLoggedIn;
		}
		if(password.equals(admin.getPassword())==false) {
			return adminLogInResult.wrongPassword;
		}
		PreparedStatement stm=conn.prepareStatement(sql.AllStatements.signInAdmin);
		stm.executeUpdate();
		return adminLogInResult.loginSuccess;
	}
	
	public boolean checkIfBookPurchasedByUsername(String username, String bookName) throws SQLException {
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.checkIfPurchasedByUsername);
		stm.setString(1, username);
		stm.setString(2, bookName);
		ResultSet res = stm.executeQuery();
		return res.next();
	}

	public void addReview(Review review) throws SQLException {
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.addReview);
		stm.setString(1, review.getBookName());
		stm.setString(2, review.getUsername());
		stm.setString(3, review.getDescription());
		stm.executeUpdate();

	}

	public void addLike(Like like) throws SQLException {
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.addLike);
		stm.setString(1, like.getBookName());
		stm.setString(2, like.getNickname());
		stm.setString(3, like.getUsername());
		stm.executeUpdate();
		stm = conn.prepareStatement(sql.AllStatements.getBookByName);
		stm.setString(1, like.getBookName());
		ResultSet res = stm.executeQuery();
		int oldLikesNum = 0;
		while (res.next()) {
			oldLikesNum = res.getInt("likes");
		}
		stm = conn.prepareStatement(sql.AllStatements.updateLikesByBookname);
		stm.setInt(1, oldLikesNum + 1);
		stm.setString(2, like.getBookName());
		stm.executeUpdate();

	}

	public void unlikeBook(String username, String bookName) throws SQLException {
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.deleteLike);
		stm.setString(1, username);
		stm.setString(2, bookName);
		stm.executeUpdate();
		stm = conn.prepareStatement(sql.AllStatements.getBookByName);
		stm.setString(1, bookName);
		ResultSet res = stm.executeQuery();
		int oldLikesNum = 0;
		while (res.next()) {
			oldLikesNum = res.getInt("likes");
		}
		stm = conn.prepareStatement(sql.AllStatements.updateLikesByBookname);
		stm.setInt(1, oldLikesNum - 1);
		stm.setString(2, bookName);
		stm.executeUpdate();

	}

	public int getNumOfLikesByBookname(String bookName) throws SQLException {
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.getBookByName);
		stm.setString(1, bookName);
		ResultSet res = stm.executeQuery();
		int likes = 0;
		while (res.next()) {
			likes = res.getInt("likes");
		}
		return likes;
	}

	public int getNumOfReviewsByBookname(String bookName) throws SQLException {
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.getAllApprovedReviewsOfBook);
		stm.setString(1, bookName);
		ResultSet res = stm.executeQuery();
		int counter = 0;
		while (res.next()) {
			counter++;
		}
		return counter;
	}

	public ArrayList<String> getLikersNicknamesByBookname(String bookName) throws SQLException {
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.getLikesByBookName);
		stm.setString(1, bookName);
		ResultSet res = stm.executeQuery();
		ArrayList<String> nicknames = new ArrayList<String>();
		while (res.next()) {
			nicknames.add(res.getString("nickname"));
		}
		return nicknames;
	}

	public ArrayList<Review> getAllApprovedReviewsByBookname(String bookName) throws SQLException {
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.getAllApprovedReviewsOfBook);
		stm.setString(1, bookName);
		ResultSet res = stm.executeQuery();
		ArrayList<Review> reviews = new ArrayList<Review>();
		while (res.next()) {
			if (res.getBoolean("approved") == true) {
				reviews.add(new Review(res.getString("bookName"), res.getString("username"),
						res.getString("description"), true));
			}
		}
		return reviews;
	}

	public ArrayList<Review> getAllUnapprovedReviews() throws SQLException {
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.getAllUnapporovedReviews);
		ResultSet res = stm.executeQuery();
		ArrayList<Review> reviews = new ArrayList<Review>();
		while (res.next()) {
			if (res.getBoolean("approved") == false) {
				reviews.add(new Review(res.getString("bookName"), res.getString("username"),
						res.getString("description"), false));
			}
		}
		return reviews;
	}

	public ArrayList<Ebook> getAllEbooks() throws SQLException {
		PreparedStatement stm = conn.prepareStatement(AllStatements.getAllEbooks);
		ResultSet res = stm.executeQuery();
		ArrayList<Ebook> allBooks = new ArrayList<Ebook>();
		while (res.next()) {
			allBooks.add(new Ebook(res.getString("bookName"), res.getString("imageURL"), res.getDouble("price"),
					res.getString("description"), res.getInt("likes")));
		}
		return allBooks;
	}
	
	public ArrayList<Ebook> getAllEbooksByUsername(String username) throws SQLException {
		ArrayList<Purchase> purchases=getPurchasesByUsername(username);
		ArrayList<Ebook> books=new ArrayList<Ebook>();
		PreparedStatement stm;
		ResultSet res;
		for (Purchase purchase : purchases) {//TODO: add 'reading' booking and not only purchased
			stm=conn.prepareStatement(sql.AllStatements.getBookByName);
			stm.setString(1, purchase.getBookName());
			res=stm.executeQuery();
			while(res.next()) {
				books.add(new Ebook(res.getString("bookName"), res.getString("imageURL"), res.getDouble("price"),
						res.getString("description"), res.getInt("likes")));
			}
		}
	return books;
	}

	public void approveReview(String username, String bookName, String description) throws SQLException {
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.approveReviewByAllInfo);
		stm.setString(1, bookName);
		stm.setString(2, username);
		stm.setString(3, description);
		stm.executeUpdate();
	}

	public boolean validateCreditCardNumber(String creditCardNum, String creditCardCompany) {
		String amexRegex = "^3[47][0-9]{13}$";
		String visaRegex = "^4[0-9]{12}(?:[0-9]{3}){0,2}$";
		String masterCardRegex = "^(?:5[1-5]|2(?!2([01]|20)|7(2[1-9]|3))[2-7])\\d{14}$";

		switch (creditCardCompany) {
		case "amex":
			return creditCardNum.matches(amexRegex);
		case "visa":
			return creditCardNum.matches(visaRegex);
		case "masterCard":
			return creditCardNum.matches(masterCardRegex);
		}
		return false;
	}

	public boolean validateCvv(String creditCardCompany, String cvv) {
		switch (creditCardCompany) {
		case "amex":
			return cvv.matches("[0-9]+") && cvv.length() == 4;
		case "visa":
			return cvv.matches("[0-9]+") && cvv.length() == 3;
		case "masterCard":
			return cvv.matches("[0-9]+") && cvv.length() == 3;
		}
		return false;
	}

	/**
	 * boolean returning whether the expiration date of the credit card has expired
	 * or not
	 * 
	 * @param expirationDate
	 *            in MM/YY format
	 * @return boolean result indicating whether the card's expired
	 * @throws ParseException
	 */
	public boolean validateExpirationDate(String expirationDate) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
		simpleDateFormat.setLenient(false);
		Date expiry = simpleDateFormat.parse(expirationDate);
		return expiry.before(new Date());
	}

	public creditCardInfoResult validateAllCreditCardInfo(String creditCardCompany, String creditCardNumber, String cvv,
			String expirationDate) throws ParseException {
		boolean result = validateCreditCardNumber(creditCardNumber, creditCardCompany);
		if (result == false) {
			return creditCardInfoResult.creditCardNumberException;
		}
		result = validateCvv(creditCardCompany, cvv);
		if (result == false) {
			return creditCardInfoResult.CvvException;
		}
		result = validateExpirationDate(expirationDate);
		if (result == true) {
			return creditCardInfoResult.expiredCardException;
		}
		return creditCardInfoResult.correctInfo;
	}

	public void purchaseBook(Purchase purchase) throws SQLException {
		PreparedStatement stm = conn.prepareStatement(sql.AllStatements.addPurchase);
		stm.setString(1, purchase.getUsername());
		stm.setString(2, purchase.getBookName());
		stm.setString(3, purchase.getCreditCardNumber());
		stm.setString(4, purchase.getCreditCardCompany());
		stm.setString(5, purchase.getCreditCardExpiry());
		stm.setString(6, purchase.getCVV());
		stm.setString(7, purchase.getFullName());
		stm.executeUpdate();
	}
	
	
	public void updateDescriptionByUsername(String username,String description) throws SQLException {
		PreparedStatement stm=conn.prepareStatement(sql.AllStatements.updateDescriptionForUser);
		stm.setString(1, description);
		stm.setString(2, username);
		stm.executeUpdate();
	}
	
	
	public void updateImageByUsername(String username,String imageURL) throws SQLException {
		PreparedStatement stm=conn.prepareStatement(sql.AllStatements.updateImageForUser);
		stm.setString(1, imageURL);
		stm.setString(2, username);
		stm.executeUpdate();
	}
	
	public ArrayList<Purchase> getAllPurchases() throws SQLException {
		PreparedStatement stm=conn.prepareStatement(sql.AllStatements.getAllPurchases);
		ResultSet res=stm.executeQuery();
		ArrayList<Purchase> allPurchases=new ArrayList<Purchase>();
		while(res.next()) {
			allPurchases.add(new Purchase(res.getString("username"), res.getString("bookName"), res.getString("creditCardNumber"),
					res.getString("creditCardCompany"), res.getString("creditCardExpiry"), res.getString("CVV"),
					res.getString("fullName")));
		}
		return allPurchases;
	}
	
	public void updateDefaultImage(String username) throws SQLException {
		PreparedStatement stm=conn.prepareStatement(sql.AllStatements.setDefaultImage);
		stm.setString(1, username);
		stm.executeUpdate();
	}
}
