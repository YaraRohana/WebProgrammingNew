package allServlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import allClasses.Purchase;
import da.DataAccess;
import da.DataAccess.creditCardInfoResult;

/**
 * Servlet implementation class PurchaseBook
 */
@WebServlet("/PurchaseBook")
public class PurchaseBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PurchaseBook() {
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
		// System.out.println("hereee in get");
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuilder jsonFileContent = new StringBuilder();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
			jsonFileContent.append(line);
		String newPurchase = jsonFileContent.toString();
		System.out.println("new Purchase " + newPurchase);
		Gson gson = new Gson();
		Purchase purchase = gson.fromJson(newPurchase, Purchase.class);
		System.out.println("Purchase :" + purchase.toString());
		if (purchase != null) {
			PrintWriter writer = response.getWriter();

			DataAccess da = null;
			try {
				da = new DataAccess();
			} catch (NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				writer.println("no connection to data base");
			}
			creditCardInfoResult res = null;
			try {
				res = da.validateAllCreditCardInfo(purchase.getCreditCardCompany(), purchase.getCreditCardNumber(),
						purchase.getCVV(), purchase.getCreditCardExpiry());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				writer.println("Unable to validate credit card info");
			}
			if (res.equals(creditCardInfoResult.creditCardNumberException)) {
				writer.println("Credit card number is incorrect");
			} else if (res.equals(creditCardInfoResult.CvvException)) {
				writer.println("CVV number is incorrect");
			} else if (res.equals(creditCardInfoResult.expiredCardException)) {
				writer.println("Credit card expired");
			} else if (res.equals(creditCardInfoResult.correctInfo)) {
				/*
				 * Purchase p = new Purchase(username, bookName, creditCardNumber,
				 * creditCardCompany, expirationDate, cvv, fullName);
				 */
				try {
					da.purchaseBook(purchase);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					writer.println("Unable to purchase book");
				}
				writer.println(true + " Book purchased sucessfully");
			}
		}
	}

}
