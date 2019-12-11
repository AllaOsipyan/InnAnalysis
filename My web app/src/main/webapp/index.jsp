
<%@ page language="java" import="org.mycompany.myname.*" %>
<%

    OutputInfo files = ( OutputInfo) request.getAttribute("finalInfo");
%>
<!DOCTYPE html>
<html>
    <body>
        <h2>Hello World!</h2>
         <form method="post" action="check">
               <div class="form-group">
                   <textarea name="inn" class="form-control" style="width:500px; height:25px" placeholder="Enter INN"></textarea>
               </div>
               <div class="form-group">
                   <input type="submit" class="btn btn-primary" style="margin:50px 0;" value="Check">
               </div>
         </form>


    </body>
</html>
