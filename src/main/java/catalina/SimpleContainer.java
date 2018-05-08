package catalina;


import config.ServletConfig;
import config.ServletConfigContext;
import server.RequestFacade;
import server.ResponseFacade;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.util.ArrayList;

import static server.Constants.SERVLET_ROOT;

public class SimpleContainer implements Container {

    public void invoke(RequestFacade request, ResponseFacade response)
            throws IOException, ServletException {
        String uri = ((HttpServletRequest) request).getRequestURI();
        String mapping = uri;

        URLClassLoader loader = null;
        try {
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File servletRoot = new File(SERVLET_ROOT);
            String repository = (new URL("file", null, servletRoot.getCanonicalPath() + File.separator)).toString();
            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Class myClass = null;
        String servletName = null;
        ArrayList<ServletConfig> servletConfigArrayList = ServletConfigContext.getServletConfigArrayList();
        for (ServletConfig sc : servletConfigArrayList) {
            if (mapping.equals(sc.getMappingUri()))
                servletName = sc.getClassName();
        }
        if (servletName != null) {
            try {
                myClass = loader.loadClass(servletName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            response.sendError(404);
            return;
        }
        Servlet servlet = null;

        try {
            servlet = (Servlet) myClass.newInstance();
            servlet.init(null);
            servlet.service((HttpServletRequest) request, (HttpServletResponse) response);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addChild(Container child) {

    }

    @Override
    public void removeChild(Container child) {

    }

    @Override
    public Container findChild(String name) {
        return null;
    }

    @Override
    public Container[] findChildren() {
        return new Container[0];
    }

    @Override
    public Loader getLoader() {
        return null;
    }

    @Override
    public void setLoader(Loader loader) {

    }
}