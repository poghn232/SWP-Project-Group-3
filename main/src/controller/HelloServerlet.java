

import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;

public class HelloServerlet extends HttpServlet {
    protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException ,java.io.IOException {
        System.out.println("Do get actions");
        PrintWriter writer = resp.getWriter();
        writer.println("worked");
        writer.close();
    }
}