package server;

import jdk.incubator.http.internal.common.HttpHeadersImpl;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpInputStream extends ServletInputStream {

    InputStream in;

    /**
     * CR.
     */
    private static final byte CR = (byte) '\r';


    /**
     * LF.
     */
    private static final byte LF = (byte) '\n';


    /**
     * SP.
     */
    private static final byte SP = (byte) ' ';


    /**
     * HT.
     */
    private static final byte HT = (byte) '\t';


    /**
     * COLON.
     */
    private static final byte COLON = (byte) ':';

    /**
     * COLON.
     */
    private static final byte QUESTION = (byte) ':';

    HttpInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }

    public HttpRequestLine readRequestLine() {
        HttpRequestLine requestLine = new HttpRequestLine();
        int b = 0;
        try {
            //skip empty line
            while (isNewLineChar(b)) {
                b = read();
            }
            String method = lengthLimitRead(HttpRequestLine.MAX_METHOD_SIZE, SP);
            String originUri = lengthLimitRead(HttpRequestLine.MAX_URI_SIZE, SP);
            String protocol = lengthLimitRead(HttpRequestLine.MAX_PROTOCOL_SIZE, CR, LF);
            requestLine.setMethod(method);
            requestLine.setProtocol(protocol);
            requestLine.parseOriginUri(originUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestLine;
    }

    private String lengthLimitRead(int limit, byte breakChar) throws IOException {
        int b = -1, length = 0;
        char buffer[] = new char[limit];
        while ((char) b != breakChar) {
            b = read();
            buffer[length] = (char) b;
            length++;
            if (length > limit)
                throw new IOException("too long~");
        }
        return new String(buffer, 0, length - 1);
    }
    private String lengthLimitRead(int limit, byte breakChar1, byte breakChar2) throws IOException {
        int b = -1, length = 0;
        char buffer[] = new char[limit];
        while ((char) b != breakChar1 && (char) b != breakChar2) {
            b = read();
            buffer[length] = (char) b;
            length++;
            if (length > limit)
                throw new IOException("too long~");
        }
        return new String(buffer, 0, length - 1);
    }

    public HttpHeader readHeader(){
        try {
            String headerName = lengthLimitRead(HttpRequestLine.MAX_PROTOCOL_SIZE, COLON);
            String headerValue = lengthLimitRead(HttpRequestLine.9, CR, LF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isNewLineChar(int b) {
        return b == CR || b == LF;
    }
}
