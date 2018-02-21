package allServlets;

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

/**
 * Servlet implementation class GetUserProfileByNickname
 */
@WebServlet("/GetUserProfileByNickname")
public class GetUserProfileByNickname extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserProfileByNickname() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String nickname=request.getParameter("nickname");
		if(nickname!=null) {
			PrintWriter writer=response.getWriter();
			DataAccess da=null;
			try {
				da=new DataAccess();
			} catch (NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("no connection");
				writer.println(false+" no connection");
			}
			User u=null;
			try {
				 u=da.getUserByNickname(nickname);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				writer.println("unable to retrieve "+nickname+"'s profile");
			}
			Gson gson=new Gson();
			String userJson=gson.toJson(u);
			System.out.println(userJson);
			writer.println(userJson);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
