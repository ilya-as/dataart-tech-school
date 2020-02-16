package responce;

import java.io.*;

public class ResponseWriter {

    private final int BUFFER_SZE = 1024;

    public void write(OutputStream outputStream, Response response) throws IOException {
        InputStream resource = response.getResource();
        BufferedOutputStream bs = new BufferedOutputStream(outputStream, BUFFER_SZE);
        bs.write(response.buildHeader().getBytes());
        if (resource != null) {
            int count;
            byte[] buffer = new byte[BUFFER_SZE];
            while ((count = resource.read(buffer)) != -1) {
                outputStream.write(buffer, 0, count);
            }
            resource.close();
        }
    }
}
