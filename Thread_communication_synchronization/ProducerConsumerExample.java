public class ProducerConsumerExample {
    public static void main(String[] args) {
        Drop drop = new Drop();
        for (int i = 0; i < 3; i++) {
            (new Thread(new Producer(drop, i + 1))).start();
            (new Thread(new Consumer(drop, i + 1))).start();
        }
    }
}