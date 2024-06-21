import java.io.*;
import java.net.*;

public class IMClient {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java IMClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket imSocket = new Socket(hostName, portNumber); // connect to the server host
                PrintWriter out = new PrintWriter(imSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(imSocket.getInputStream()))
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            IMProtocol imProtocol = new IMProtocol();

            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null && !inputLine.equalsIgnoreCase("Bye")) { // check that the message received is not null or Bye
                System.out.println("Server: " + inputLine); // print what the server types
                System.out.print("Client: "); // prepare the turn for the client to enter a response
                inputLine = stdIn.readLine(); // read the response sentence
                outputLine = imProtocol.processInput(inputLine); // process what the client types as a response
                if (outputLine != null) { // make sure that the input from the client is not a null
                    out.println(outputLine); // send the response to the server from the client
                } else {
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
