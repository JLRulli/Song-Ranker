import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
    name = "SearchArtist",
    urlPatterns = {"/searchArtist"}
)
public class SearchArtist extends HttpServlet {
	
/*
 * 
 * Search database for artists name "artistName"
 * if exists, go to artistListing.jsp with attribute artistName 
 * else go to addArtist.jsp with attribute artistName
 * 
 */

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException, ServletException {

    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");

    String name = request.getParameter("artistName");
    
    if (name == "no") {
    	request.getRequestDispatcher("").forward(request, response);;
    }
    response.getWriter().print(request.getParameter("artistName") + "\r\n");

  }
}