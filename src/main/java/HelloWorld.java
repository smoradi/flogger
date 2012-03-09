import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

public class HelloWorld extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().print("Hello from Java!\n");
        try {
        	FileReader _input= new FileReader("data/startMyBlog.xml");
        	for (int i=0; i < 2085; i++)
        		resp.getWriter().print((char) _input.read());
        	try {
        		_input.close();
        	} catch (IOException e) {
        		resp.getWriter().print("IOE: " + e.getMessage());
        	}
        } catch (FileNotFoundException e) {
        	resp.getWriter().print("FNFE: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception{
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new HelloWorld()),"/*");
        server.start();
        server.join();   
    }
}
