import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@WebServlet(
    name = "AddEntry",
    urlPatterns = {"/addEntry"}
)
public class AddEntry extends HttpServlet {
	
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

	  
	  	String aname = request.getParameter("artistName");
	  	
	  
	  	request.setAttribute("artistName", aname);
  		RequestDispatcher rd = request.getRequestDispatcher("/addEntry.jsp");
  	
  		rd.forward(request,response);

  }
}