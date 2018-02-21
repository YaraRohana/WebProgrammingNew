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

import allClasses.Like;
import da.DataAccess;

/**
 * Servlet implementation class AddLike
 */
@WebServlet("/AddLike")
public class AddLike extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddLike() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		StringBuilder jsonFileContent = new StringBuilder();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
		     jsonFileContent.append(line);
		JSONObject jsonInput=null;
		try {
			jsonInput = new JSONObject(jsonFileContent.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String bookName=null,userName=null;
		try {
			bookName = jsonInput.getString("bookName");
			 userName=jsonInput.getString("username");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(bookName!=null && userName!=null) {
			boolean result=false;
			PrintWriter out=response.getWriter();
			DataAccess da=null;
			try {
				 da=new DataAccess();
			} catch (NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("no connection");
				out.println(false+" no connection");
			}
			try {
				result=da.checkIfBookPurchasedByUsername(userName, bookName);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(result) {
				String nickname=null;
				try {
					nickname = da.getUserByName(userName).getNickname();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Like like=new Like(bookName, userName, nickname);
				try {
					da.addLike(like);
					out.println(true+" like added successfully");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				out.println(false+" book is not purchased by user and therefore cannot be liked");
			}
			
		}
	}

}
