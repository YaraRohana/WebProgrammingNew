package allServlets;

import java.io.BufferedReader;
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

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import da.DataAccess;

/**
 * Servlet implementation class GetAllLikesByBookname
 */
@WebServlet("/GetAllLikesByBookname")
public class GetAllLikesByBookname extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllLikesByBookname() {
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
		BufferedReader reader = request.getReader();
		String bookname=reader.readLine();
		JSONObject jsonUsername=null;
		try {
			 jsonUsername=new JSONObject(bookname);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			 bookname=jsonUsername.getString("bookName");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(bookname!=null) {
			PrintWriter out=response.getWriter();
			DataAccess da=null;
			try {
				 da=new DataAccess();
			} catch (NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(da!=null) {
				int likes=0;
				try {
					likes=da.getNumOfLikesByBookname(bookname);
					Gson gson=new Gson();
					String jsonLikes=gson.toJson(likes);
					out.println(jsonLikes);
					ArrayList<String> nicknames=da.getLikersNicknamesByBookname(bookname);
					String jsonNicknames=gson.toJson(nicknames);
					out.println(jsonNicknames);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				System.out.println("no connection");
				out.println(false+" no connection");
			}
		}
	}

}
