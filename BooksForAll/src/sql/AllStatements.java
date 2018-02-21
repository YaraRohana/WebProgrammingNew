package sql;

/**
 * A simple place to hold global application constants
 */
public interface AllStatements {

	public final String NAME = "name";
	// derby constants
	public final String DB_NAME = "DB_NAME";
	public final String DB_DATASOURCE = "DB_DATASOURCE";
	public final String PROTOCOL = "jdbc:derby:";
	public final String OPEN = "Open";
	public final String SHUTDOWN = "Shutdown";
	//public final Type CUSTOMER_COLLECTION = new TypeToken<Collection<User>>() {}.getType();
	// sql statements
	public final String USERS_JSON_FILE="USERS.json";
	public final String BOOKS_JSON_FILE="EBOOKS.json";
	public final String LIKES_JSON_FILE="LIKES.json";
	public final String REVIEWS_JSON_FILE="REVIEWS.json";
	public final String PURCHASES_JSON_FILE="PURCHASES.json";
	/************** Tables creation *********************/
	public final static String CREATE_USERS_TABLE = "CREATE TABLE USERS(username varchar(10) PRIMARY KEY,"
			+ "email varchar(50) NOT NULL," + "streetName varchar(15) NOT NULL," + "apartmentNumber INTEGER NOT NULL,"
			+ "city varchar(20) NOT NULL," + "postalCode varchar(7) NOT NULL," + "phoneNumber varchar(10) NOT NULL,"
			+ "password varchar(8) NOT NULL," + "nickname varchar(20) NOT NULL," + "description varchar(100),"
			+ "imageURL varchar(100),isConnected BOOLEAN)";

	public final static String CREATE_EBOOKS_TABLE = "CREATE TABLE EBOOKS(bookName varchar(50) PRIMARY KEY,"
			+ "price double NOT NULL,description varchar(100),likes INTEGER,ImageURL varchar(100) NOT NULL)";

	public final static String CREATE_REVIEWS_TABLE = "CREATE TABLE REVIEWS(bookName varchar(50) NOT NULL,"
			+ "username varchar(10) NOT NULL," + "description varchar(100) NOT NULL," + " approved BOOLEAN,"
			+ "FOREIGN KEY(bookName) REFERENCES EBOOKS(bookName) ON DELETE CASCADE,"
			+ "FOREIGN KEY(username) REFERENCES USERS(username) ON DELETE CASCADE)";// TODO: maybe should add
																					// nickname?(ask sami)

	public final static String CREATE_LIKES_TABLE = "CREATE TABLE LIKES(bookName varchar(50)," + "nickname varchar(20),"
			+ "username varchar(10)," + "FOREIGN KEY(bookName) REFERENCES EBOOKS(bookName) ON DELETE CASCADE,"
			+ "FOREIGN KEY(username) REFERENCES USERS(username) ON DELETE CASCADE)";

	public final static String CREATE_ADMIN_TABLE = "CREATE TABLE ADMIN(name varchar(5) PRIMARY KEY,"
			+ "password varchar(8),isConnected BOOLEAN)";

	public final static String CREATE_PURCHASES_TABLE = "CREATE TABLE PURCHASES(username varchar(10) NOT NULL,bookName varchar(50) NOT NULL,"
			+ "creditCardNumber varchar(20) NOT NULL," + "creditCardCompany varchar(10) NOT NULL,"
			+ "creditCardExpiry varchar(7) NOT NULL," + "CVV varchar(4) NOT NULL," + "fullName varchar(20) NOT NULL,"
			+ "FOREIGN KEY(bookName) REFERENCES EBOOKS(bookName) ON DELETE CASCADE,"
			+ "FOREIGN KEY(username) REFERENCES USERS(username) ON DELETE CASCADE)";

	/******************** User queries ********************/
	public final static String addUser = "INSERT INTO USERS VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
	public final static String getAllUsers = "SELECT * from USERS";
	public final static String deleteUser = "DELETE from USERS where username=?";
	public final static String selectUserByName = "SELECT * from USERS where username=?";
	public final static String selectUserByNickname = "SELECT * from USERS where nickname=?";
	public final static String validateLogin = "SELECT * FROM USERS WHERE username=? and password=? and isConnected=false";
	public final static String updateDescriptionForUser = "UPDATE USERS SET description=? where username=?";
	public final static String updateImageForUser = "UPDATE USERS SET imageURL=? where username=?";
	public final static String signOutUser = "UPDATE USERS SET icConnected=false where username=?";
	public final static String signInUser = "UPDATE USERS SET icConnected=true where username=?";
	public final static String setDefaultImage="UPDATE USERS SET imageURL='..\\Avatar.png' where username=?";
	/**************** Ebook queries ***********************/
	public final static String addBook="INSERT INTO EBOOKS VALUES(?,?,?,?,?)";
	public final static String getAllEbooks = "SELECT * FROM EBOOKS";
	public final static String getBookByName = "SELECT * FROM EBOOKS WHERE bookName=?";
	public final static String updateLikesByBookname = "UPDATE EBOOKS SET likes=? where bookName=?";
	public final static String deleteLike = "DELETE FROM LIKES where username=? and bookName=?";
	/**************** Likes queries ***********************/
	public final static String getAllLikes = "SELECT * FROM LIKES";
	public final static String addLike = "INSERT INTO LIKES VALUES(?,?,?)";
	public final static String getLikesByBookName = "SELECT * FROM LIKES where bookName=?";
	/****************** Reviews queries ********************/
	public final static String getAllReviews = "SELECT * FROM REVIEWS";
	public final static String addReview = "INSERT INTO REVIEWS VALUES(?,?,?,?)";
	public final static String getAllApprovedReviewsOfBook = "SELECT * FROM REVIEWS WHERE bookName=? and approved=true";
	public final static String getAllUnapporovedReviews = "Select * from REVIEWS where approved=false";
	public final static String approveReviewByAllInfo = "UPDATE REVIEWS SET approved=true WHERE bookName=? and username=? and description=?";
	public final static String getReviewByAllInfo = "select * from REVIEWS where bookName=? and username=? and description=?";
	/******************** Purchases queries *******************/
	public final static String getAllPurchases = "SELECT * FROM PURCHASES";
	public final static String addPurchase = "INSERT INTO PURCHASES VALUES(?,?,?,?,?,?,?)";
	public final static String getPurchasesByUsername = "SELECT * FROM PURCHASES WHERE username=?";
	public final static String checkIfPurchasedByUsername = "SELECT * FROM PURCHASES WHERE username=? and bookName=?";
	/*********************** Admin queries ********************/
	public final static String getAdmin = "SELECT * FROM ADMIN WHERE name='admin'";
	public final static String signInAdmin = "UPDATE ADMIN SET isConnected=true where name='admin'";
}
