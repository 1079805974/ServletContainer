package startup;

import catalina.*;
import catalina.SimpleWrapper;
import catalina.Valves.HeaderLoggerValve;
import catalina.Wrapper;
import server.HttpConnector;

public class Bootstrap2 {
    public static void main(String[] args) {

        HttpConnector connector = new HttpConnector();
        Wrapper wrapper = new SimpleWrapper();
        wrapper.setServletClass("ModernServlet");
        SimpleContainer container = new SimpleContainer();
        container.addChild(wrapper);
        Loader loader = new SimpleLoader();
        Valve valve1 = new HeaderLoggerValve();

        wrapper.setLoader(loader);
        ((Pipeline) wrapper).addValve(valve1);

        connector.setContainer(wrapper);

        try {
            connector.initialize();
            connector.start();

            // make the application wait until we press a key.
            System.in.read();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
