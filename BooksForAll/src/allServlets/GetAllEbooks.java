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

import allClasses.Ebook;
import da.DataAccess;

/**
 * Servlet implementation class GetAllEbooks
 */
@WebServlet("/GetAllEbooks")
public class GetAllEbooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllEbooks() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		DataAccess da=null;
		PrintWriter writer=response.getWriter();
		try {
			da=new DataAccess();
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("no connection");
			writer.println(false+" no connection");
		}
		ArrayList<Ebook> books = null;
			try {
				books=da.getAllEbooks();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("unable to retrieve books");
				writer.println(false+" unable to retrieve books from data base");
			}
			Gson gson=new Gson();
			String jsonBooks=gson.toJson(books);
			response.addHeader("Content-Type", "application/json");
			writer.println(jsonBooks);
			writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
