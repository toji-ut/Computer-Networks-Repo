import java.util.Random;

public class Consumer implements Runnable {
    private Drop drop;
    private int id;

    public Consumer(Drop drop, int id) {
        this.drop = drop;
        this.id = id;
    }

    public void run() {
        Random random = new Random();
        for (String message = drop.take();
             ! message.equals("DONE");
             message = drop.take()) {
            System.out.format("Consumer ID: %d; MESSAGE RECEIVED: %s%n", id, message);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {}
        }
    }
}