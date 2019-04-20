import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
	  
	  CollectionReference cities = db.collection("artists");
	  Query query = cities.whereEqualTo("name", aname);
	  ApiFuture<QuerySnapshot> querySnapshot = query.get();

	  String id = null;
	  
	  try {
		for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
		    if (document.getId() != null) {
		    	id = document.getId();
		    } 
		}
	  } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  } catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
    
    if (id != null) {
    	
    	List<List<String>> queries = new ArrayList<List<String>>();
    	
    	ApiFuture<QuerySnapshot> future =
    		    db.collection("artists").document(id).collection("entries").get();
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
    		
    	request.setAttribute("entries", queries);
    	request.setAttribute("id", id);
    	request.setAttribute("artistName", aname);
    	RequestDispatcher rd = request.getRequestDispatcher("/listArtist.jsp");
    	
    	FirebaseApp.getInstance().delete();
    	rd.forward(request,response);
    	
    } else {
    	
    	// Create a Map to store the data we want to set
  	  Map<String, Object> docData = new HashMap<>();
  	  docData.put("name", aname);
  	  
  	  // Add a new document (asynchronously) in collection "cities" with id "LA"
  	  ApiFuture<WriteResult> future = db.collection("artists").document(aname).set(docData);
  	  
  	request.setAttribute("artistName", aname);
  	//request.setAttribute("db", db);
	RequestDispatcher rd = request.getRequestDispatcher("/listArtist.jsp");
	
	FirebaseApp.getInstance().delete();
	rd.forward(request,response);
    
    }

  }
}