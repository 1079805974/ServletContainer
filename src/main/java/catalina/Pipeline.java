package catalina;

import server.RequestFacade;
import server.ResponseFacade;

import javax.servlet.ServletException;
import java.io.IOException;


public interface Pipeline extends Contained {

    public void invoke(RequestFacade request, ResponseFacade response)
            throws IOException, ServletException;
    public void removeValve(Valve valve);
    public Valve getBasic();
    public void setBasic(Valve valve);
    public void addValve(Valve valve);
    public Valve[] getValves();
    public Valve getFirst();
}
