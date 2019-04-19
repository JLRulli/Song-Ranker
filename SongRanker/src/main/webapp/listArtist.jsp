<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    <title>Index</title>
  </head>

  <body>
  
    <h1>${artistName} / ${id}</h1>
    
    <%
		String name=request.getParameter("artistName");
	%>
    
    <form action="/addEntry" method="get" target="_blank">
    	<input type="hidden" name="artistName" value="<%=name%>">
        <input type="submit" value="Add Entry">
    </form>
    
    <p>
    	<c:forEach items="${entries}" var="item" >
    		<c:forEach items="${item}" var="item2" >
    			${item2}<br>
			</c:forEach>
			<br>
		</c:forEach>
    </p>

    
  </body>
</html>