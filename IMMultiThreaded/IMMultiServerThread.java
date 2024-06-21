import java.net.*;
import java.io.*;

public class IMMultiServerThread extends Thread {
    private Socket socket = null;

    public IMMultiServerThread(Socket socket) {
        super("KKMultiServerThread");
        this.socket = socket;
    }

    public void run() {

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            IMProtocol imProtocol = new IMProtocol();

            String inputLine, outputLine;

            // Initiate conversation with client
            outputLine = imProtocol.processInput(null); // set and send the initial prompt to the client
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null && !inputLine.contains("Bye")) { // receive the message from the client and make sure it is not null or Bye
                System.out.println(inputLine); // print what the client's response was
                System.out.print("Server: "); // prepare the turn for the server to enter a response
                inputLine = stdIn.readLine(); // read the input from the server
                outputLine = imProtocol.processInput(inputLine); // process what the server enters as a response
                if (outputLine != null) { // make sure that the input from the server is not a null
                    out.println("Server: " + outputLine); // send the response to the client from the server
                } else {
                    break;
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}