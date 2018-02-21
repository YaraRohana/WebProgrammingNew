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

/**
 * Servlet implementation class ApproveReview
 */
@WebServlet("/ApproveReview")
public class ApproveReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApproveReview() {
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
		String bookName=null,username=null,description=null;
		try {
			 bookName=jsonInput.getString("bookName");
			 username=jsonInput.getString("username");
			 description=jsonInput.getString("description");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(bookName!=null && username!=null && description!=null) {
			System.out.println("we're in");
			DataAccess da=null;
			PrintWriter writer=response.getWriter();
			try {
				 da=new DataAccess();
			} catch (NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("no connection");
				writer.println(false + " no connection");
			}
			try {
				da.approveReview(username, bookName, description);
				writer.println("review approved");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				writer.println("unable to approve review");
			}
			
		}
		
		
	}

}
