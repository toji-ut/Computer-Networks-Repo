import java.io.*;
import java.net.*;

public class IMClient {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Usage: java IMClient <host name> <port number> <client name>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String name = args[2];

        try (
                Socket imSocket = new Socket(hostName, portNumber); // connect to the server host
//                PrintWriter out = new PrintWriter(imSocket.getOutputStream(), true);
//                BufferedReader in = new BufferedReader(new InputStreamReader(imSocket.getInputStream()))
                ObjectOutputStream out = new ObjectOutputStream(imSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(imSocket.getInputStream())
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            IMProtocol imProtocol = new IMProtocol();

            Message inputMessage, outputMessage;

            while ((inputMessage = (Message) in.readObject()) != null && !inputMessage.getMessage().equalsIgnoreCase("Bye")) {
                System.out.println(inputMessage.getName() + ": " + inputMessage.getMessage());
                System.out.print(name + ": ");
                String clientInput = stdIn.readLine();
                outputMessage = new Message(name, imProtocol.processInput(clientInput));
                out.writeObject(outputMessage);
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.exit(1);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
