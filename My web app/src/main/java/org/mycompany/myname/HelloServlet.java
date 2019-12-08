package org.mycompany.myname;



import org.mycompany.myname.Parsers.*;

import javax.jws.WebService;
import javax.servlet.http.*;
import java.io.IOException;
@WebService
public class HelloServlet extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        Info.inn = "7729717254";
        Info.createDriver();
        Parser tfs = new ArbitrScr();
        try {
            tfs.getPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpServletResponse.getWriter().print("Hello from servlet");
    }
}