import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;
    private int id;

    public Producer(Drop drop, int id) {
        this.drop = drop;
        this.id = id;
    }

    public void run() {
        String importantInfo[] = {
                "Mares eat oats",
                "Does eat oats",
                "Little lambs eat ivy",
                "A kid will eat ivy too"
        };
        Random random = new Random();

        for (int i = 0; i < importantInfo.length; i++) {
            drop.put(importantInfo[i] + "; Producer ID: " + id);
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {}
        }
        drop.put("DONE");
    }
}