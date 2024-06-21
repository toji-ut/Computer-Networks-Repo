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
//                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            IMProtocol imProtocol = new IMProtocol();

            Message inputMessage, outputMessage;

            // Initiate conversation with client
            outputMessage = new Message("Server", imProtocol.processInput(null));
            out.writeObject(outputMessage);

            while ((inputMessage = (Message) in.readObject()) != null && !inputMessage.getMessage().equalsIgnoreCase("Bye")) {
                System.out.println(inputMessage.getName() + ": " + inputMessage.getMessage());
                System.out.print("Server: ");
                String serverInput = stdIn.readLine();
                outputMessage = new Message("Server", imProtocol.processInput(serverInput));
                out.writeObject(outputMessage);
            }
            socket.close();
        } catch (IOException e) {
            System.exit(1);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}