package catalina;

import server.RequestFacade;
import server.ResponseFacade;

import javax.servlet.ServletException;
import java.io.IOException;


public interface ValveContext {

    public String getInfo();

    public void invokeNext(RequestFacade request, ResponseFacade response)
            throws IOException, ServletException;


}
