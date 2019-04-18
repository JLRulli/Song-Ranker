import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

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
	  
	  InputStream serviceAccount = new FileInputStream("C:\\Users\\jacob\\git\\Song-Ranker\\SongRanker\\key.json");
	  GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
	  FirebaseOptions options = new FirebaseOptions.Builder()
	      .setCredentials(credentials)
	      .build();
	  FirebaseApp.initializeApp(options);

	  Firestore db = FirestoreClient.getFirestore();

	  String aname = request.getParameter("artistName");
	  
	// Create a reference to the cities collection
	  CollectionReference cities = db.collection("artists");
	  // Create a query against the collection.
	  Query query = cities.whereEqualTo("name", aname);
	  // retrieve  query results asynchronously using query.get()
	  ApiFuture<QuerySnapshot> querySnapshot = query.get();

	  try {
		for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
		    System.out.println(document.getId());
		  }
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    FirebaseApp.getInstance().delete();
    
    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().print(request.getParameter("artistName") + "\r\n");

  }
}