package Client;

public class HttpClientImp extends SimpleHttpClient {

    public HttpClientImp(int port) {
        setPort(port);
    }
    @Override
    public boolean isResponseBodyOK() {
        return getResponseBody().contains("test - data");
    }
}
