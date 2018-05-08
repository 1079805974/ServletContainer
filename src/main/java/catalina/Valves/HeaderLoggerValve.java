package catalina.Valves;

import catalina.*;
import server.Request;
import server.RequestFacade;
import server.ResponseFacade;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;



public class HeaderLoggerValve implements Valve, Contained {

    protected Container container;

    public void invoke(RequestFacade request, ResponseFacade response, ValveContext valveContext)
            throws IOException, ServletException {

        // Pass this request on to the next valve in our pipeline
        valveContext.invokeNext(request, response);

        System.out.println("Header Logger Valve");
        ServletRequest sreq = request;
        if (sreq instanceof HttpServletRequest) {
            HttpServletRequest hreq = (HttpServletRequest) sreq;
            Enumeration headerNames = hreq.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement().toString();
                String headerValue = hreq.getHeader(headerName);
                System.out.println(headerName + ":" + headerValue);
            }

        }
        else
            System.out.println("Not an HTTP Request");

        System.out.println("------------------------------------");
    }

    public String getInfo() {
        return null;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}