package catalina;



import server.RequestFacade;
import server.ResponseFacade;

import javax.servlet.ServletException;
import java.io.IOException;

public interface Container {
    public void invoke(RequestFacade requestFacade, ResponseFacade responseFacade)
            throws IOException, ServletException;
    public void addChild(Container child);
    public void removeChild(Container child);
    public Container findChild(String name);
    public Container[] findChildren();
    public Loader getLoader();
    public void setLoader(Loader loader);
}
