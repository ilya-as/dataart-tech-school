package responce;

import java.io.*;

public class ResponseWriter {

    public void write(OutputStream outputStream, Response response) throws IOException {
        InputStream resource = response.getResource();
        PrintStream answer = new PrintStream(outputStream, true, "UTF-8");
        answer.print(response.buildHeader());
        if (resource != null) {
            int count;
            byte[] buffer = new byte[1024];
            while ((count = resource.read(buffer)) != -1) {
                outputStream.write(buffer, 0, count);
            }
            resource.close();
        }
    }
}
