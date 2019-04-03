package bl;

import consumer.BookConsumer;
import producer.BookProducer;
import queue.MyQueue;

public class Main {
    public static void main(String[] args) {
        MyQueue queue = new MyQueue();
        
        BookProducer producer = new BookProducer(queue);
        BookConsumer consumer1 = new BookConsumer(queue);
        BookConsumer consumer2 = new BookConsumer(queue);
        BookConsumer consumer3 = new BookConsumer(queue);
        BookConsumer consumer4 = new BookConsumer(queue);
    
        new Thread(producer, "BookProducer 1").start();
        new Thread(consumer1, "BookConsumer 1").start();
        new Thread(consumer2, "BookConsumer 2").start();
        new Thread(consumer3, "BookConsumer 3").start();
        new Thread(consumer4, "BookConsumer 4").start();
    }
}