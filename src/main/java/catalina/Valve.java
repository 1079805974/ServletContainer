package catalina;

import server.RequestFacade;
import server.ResponseFacade;

import javax.servlet.ServletException;
import java.io.IOException;

public interface Valve {
    public String getInfo();
    public void invoke(RequestFacade request, ResponseFacade response,
                       ValveContext context)
            throws IOException, ServletException;


}
