package server;


/**
 * HTTP header enum type.
 *
 * @author Remy Maucherat
 * @version $Revision: 1.4 $ $Date: 2002/03/18 07:15:40 $
 *
 */

final class HttpHeader {

    public static final int INITIAL_NAME_SIZE = 32;
    public static final int INITIAL_VALUE_SIZE = 64;
    public static final int MAX_NAME_SIZE = 128;
    public static final int MAX_VALUE_SIZE = 4096;

    private String name;
    private String value;

    public HttpHeader(String name, String value) {
        this.name = name.toLowerCase();
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name+":"+value;
    }
}

