package ex05.connector.http;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseStream extends OutputStream {
    private OutputStream output;
    public ResponseStream(OutputStream outputStream){
        this.output=outputStream;
    }
    @Override
    public void write(int b) throws IOException {
        output.write(b);
    }
}
