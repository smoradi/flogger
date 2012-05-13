import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.*;

@SuppressWarnings("serial")
public class HelloWorld extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().print("Hello from Java!\n");
        String defaultPost= (req.getParameter("name") != null ? req.getParameter("name") : "startMyBlog.xml");
        try {
        	FileReader _input= new FileReader("data/" + defaultPost);
        	int c;
        	while(-1 != (c = _input.read()))
        		resp.getWriter().print((char) c);
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
        context.setContextPath("/app");
        context.addServlet(new ServletHolder(new HelloWorld()),"/file/*");
        
        ResourceHandler resourceHandler= new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase(".");
        
        HandlerList handlers= new HandlerList();
        handlers.setHandlers(new Handler[] {context, resourceHandler, new DefaultHandler()});
        server.setHandler(handlers);
        
        server.start();
        server.join();   
    }
}
