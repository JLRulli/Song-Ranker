<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    <title>Add Entry</title>
  </head>

  <body>
  
    <h1>Add you top songs for ${artistName}</h1>
    
    <h2>Search for an Artist</h2>
    
    <%
		String name=request.getParameter("artistName");
	%>

    <form action="/updateEntries" method="get" target="_blank">
    	<input type="hidden" name="artistName" value="<%=name%>">
    	Your Name <input type="text" name="userName"><br>
        #1 <input type="text" name="song1"><br>
        #2 <input type="text" name="song2"><br>
        #3 <input type="text" name="song3"><br>
        #4 <input type="text" name="song4"><br>
        #5 <input type="text" name="song5"><br>
        <input type="submit" value="Submit">
    </form>
    
  </body>
</html>
