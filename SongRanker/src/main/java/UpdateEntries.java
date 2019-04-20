import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@WebServlet(
    name = "UpdateEntries",
    urlPatterns = {"/updateEntries"}
)
public class UpdateEntries extends HttpServlet {
	
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
	  	System.out.println(aname);
	  	String userName = request.getParameter("userName");
	  	System.out.println(userName);
	  	String song1 = request.getParameter("song1");
	  	System.out.println(song1);
	  	String song2 = request.getParameter("song2");
	  	System.out.println(song2);
	  	String song3 = request.getParameter("song3");
	  	System.out.println(song3);
	  	String song4 = request.getParameter("song4");
	  	System.out.println(song4);
	  	String song5 = request.getParameter("song5");
	  	System.out.println(song5);
	  	
	  	InputStream serviceAccount = new FileInputStream("C:\\Users\\jacob\\git\\Song-Ranker\\SongRanker\\key.json");
		  GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
		  FirebaseOptions options = new FirebaseOptions.Builder()
		      .setCredentials(credentials)
		      .build();
		  FirebaseApp.initializeApp(options);

		  Firestore db = FirestoreClient.getFirestore();
	  	
		  List<List<String>> queries = new ArrayList<List<String>>();
	    	
	    	ApiFuture<QuerySnapshot> future =
	    		    db.collection("artists").document(aname).collection("entries").get();
	    		List<QueryDocumentSnapshot> documents = null;
				try {
					documents = future.get().getDocuments();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		for (DocumentSnapshot document : documents) {
	    			List<String> entry = new ArrayList<String>();
	    			entry.add(document.getId());
	    			List<String> songs = (List<String>) document.get("songs");
	    			for (int i = 0; i < songs.size(); i++) {
	    				entry.add(songs.get(i));
	    			}
	    			queries.add(entry);
	    		}
	    		
	    		System.out.println(queries);
	    		
	    		Map<String, Object> docData = new HashMap<>();
	    	  	  docData.put("name", "testing");
	    	  	  
	    	  	  // Add a new document (asynchronously) in collection "cities" with id "LA"
	    	  	  ApiFuture<WriteResult> future2 = db.collection("artists").document(aname).set(docData);
	  	 
	  
	  	request.setAttribute("artistName", aname);
		RequestDispatcher rd = request.getRequestDispatcher("/listArtist.jsp");
		
		FirebaseApp.getInstance().delete();
		rd.forward(request,response);

  }
}