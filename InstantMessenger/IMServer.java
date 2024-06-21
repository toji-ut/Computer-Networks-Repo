import java.io.*;
import java.net.*;

public class IMServer {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java IMServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber); // initialize and listen to a specific port for a connection to the server
                Socket clientSocket = serverSocket.accept(); // accept the connection request
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            IMProtocol imProtocol = new IMProtocol();

            String inputLine, outputLine;

            // Initiate conversation with client
            outputLine = imProtocol.processInput(null); // set and send the initial prompt to the client
            out.println(outputLine);

            System.out.println("Client: Type a message (say Bye to end)");

            while ((inputLine = in.readLine()) != null && !inputLine.equalsIgnoreCase("Bye")) { // receive the message from the client and make sure it is not null or Bye
                System.out.println("Client: " + inputLine); // print what the client's response was
                System.out.print("Server: "); // prepare the turn for the server to enter a response
                inputLine = stdIn.readLine(); // read the input from the server
                outputLine = imProtocol.processInput(inputLine); // process what the server enters as a response
                if (outputLine != null) { // make sure that the input from the server is not a null
                    out.println(outputLine); // send the response to the client from the server
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
