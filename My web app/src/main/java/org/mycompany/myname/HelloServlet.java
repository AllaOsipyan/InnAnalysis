package org.mycompany.myname;



import org.mycompany.myname.Parsers.*;

import javax.jws.WebService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/check")
public class HelloServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String inn = req.getParameter("inn");
        if(inn.length()==10)
            Info.inn = inn;
        Info.createDriver();
        Parser tfs = new ArbitrScr();

        try {
            OutputInfo finalInfo =tfs.getPage();

            req.setAttribute("files",finalInfo );

            getServletContext().getRequestDispatcher("/answer.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}