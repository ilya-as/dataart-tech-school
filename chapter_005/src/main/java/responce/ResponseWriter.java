package responce;

import java.io.*;

public class ResponseWriter {

    private final int BUFFER_SZE = 1024;

    public void write(OutputStream outputStream, Response response) throws IOException {
        InputStream resource = response.getResource();
        PrintStream answer = new PrintStream(outputStream, true, "UTF-8");
        answer.print(response.buildHeader());
        if (resource != null) {
            int count;
            byte[] buffer = new byte[BUFFER_SZE];
            while ((count = resource.read(buffer)) != -1) {
                outputStream.write(buffer, 0, count);
            }
        }
    }
}
