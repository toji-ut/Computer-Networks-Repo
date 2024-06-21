import java.io.*;
import java.net.*;
import java.util.*;

public final class WebServer {
    public static void main(String[] argv) throws Exception {
        if (argv.length != 1) {
            System.err.println("Usage: java WebServer <port number>");
            System.exit(1);
        }

        // Set the port number.
        int port = Integer.parseInt(argv[0]);

        boolean listening = true;

        // Establish the listen socket.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            while (listening) {
                // Listen for a TCP connection request.
                Socket connectionSocket = listenSocket.accept();

                // https://mkyong.com/java/how-to-get-the-current-working-directory-in-java/#:~:text=In%20Java%2C%20we%20can%20use,where%20your%20program%20was%20launched.
                String dirName = System.getProperty("user.dir");

                // Construct an object to process the HTTP request message
                HttpRequest request = new HttpRequest(connectionSocket, dirName);

                // Create a new thread to process the request
                Thread thread = new Thread(request);

                // Start the thread
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            System.exit(-1);
        }

    }
}
