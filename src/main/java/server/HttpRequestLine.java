package server;

final class HttpRequestLine {
    public static final int INITIAL_METHOD_SIZE = 8;
    public static final int INITIAL_URI_SIZE = 64;
    public static final int INITIAL_PROTOCOL_SIZE = 8;
    public static final int MAX_METHOD_SIZE = 1024;
    public static final int MAX_URI_SIZE = 32768;
    public static final int MAX_PROTOCOL_SIZE = 1024;
    private String method;
    private String uri;
    private String protocol;
    private String queryString;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    private String parseUri(String originUri) {
        String uri = "";
        int pos;
        pos = originUri.indexOf('?');
        if (pos > 0) {
            originUri = originUri.substring(0, pos);
        }
        if (originUri.startsWith("/"))
            uri = originUri;
        else {
            pos = originUri.indexOf("://");
            if (pos != -1) {
                pos = originUri.indexOf('/', pos + 3);
            } else {
                pos = originUri.indexOf('/');
            }
            this.uri = originUri.substring(pos);
        }
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        if(queryString != null)
            return method + " " + uri + "?" + queryString + " " + protocol;
        return method + " " + uri  + " " + protocol;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void parseOriginUri(String originUri) {
        setUri(parseUri(originUri));
        setQueryString(parseQueryString(originUri));
    }

    private String parseQueryString(String originUri) {
        int pos = originUri.indexOf('?');
        if (pos == -1) {
            return null;
        }
        return originUri.substring(pos + 1);
    }
}
