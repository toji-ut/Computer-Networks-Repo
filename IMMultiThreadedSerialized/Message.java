import java.io.Serializable;

public class Message implements Serializable {
    private final String name;
    private final String message;

    // constructor for the message class
    public Message(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return this.name;
    } // getter for the name of the sender

    public String getMessage() {
        return this.message;
    } // getter for the response of the sender
}

