package server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpInputStream extends ServletInputStream {

    InputStream in;
    public static final Logger logger = LoggerFactory.getLogger(HttpInputStream.class);
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

    private BufferedReader bufferedReader;

    HttpInputStream(InputStream in) {
        this.in = in;
        this.bufferedReader = new BufferedReader(new InputStreamReader(in));
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
            String protocol = lengthLimitRead(HttpRequestLine.MAX_PROTOCOL_SIZE, LF);
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
        return new String(buffer, 0, length - 1).trim();
    }

    public HttpHeader readHeader() throws IOException {
        String headerLine = bufferedReader.readLine().toLowerCase();
        if (isHeadersEnd(headerLine))
            return null;
        int pos = headerLine.indexOf(':');
        if(pos==-1){
            throw new IOException("header is wrong : {"+headerLine+"}");
        }
        return new HttpHeader(headerLine.substring(0,pos).trim(), headerLine.substring(pos+1).trim());
    }

    private boolean isHeadersEnd(String line){
        return line.trim().isEmpty();
    }

    private boolean isNewLineChar(int b) {
        return b == CR || b == LF;
    }
}
