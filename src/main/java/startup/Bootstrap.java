package startup;


import catalina.Container;
import catalina.SimpleContainer2;
import config.ServletConfigContext;
import server.HttpConnector;
import server.Processor;

public class Bootstrap {
    public static void main(String[] args) {
        Container container = new SimpleContainer2();
        HttpConnector httpConnector = new HttpConnector();
        ServletConfigContext.init();
        Processor.init();
        httpConnector.setContainer(container);
        httpConnector.initialize();
        try {
            httpConnector.start();
            // make the application wait until we press a key.
            System.in.read();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
