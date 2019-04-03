package consumer;

import bl.Book;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import queue.MyQueue;

public class BookConsumer extends Thread {
    private MyQueue<Book> queue;

    public BookConsumer(MyQueue<Book> queue) {
        this.queue = queue;
    }
    
    public void createBook(HashMap<String, Integer> map, String inputfilename) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("./output-files/" + inputfilename.replace(".txt", "") + "-output")));
        for (String word : map.keySet()) {
            bw.write(word+": "+map.get(word)+"\n");
        }
        bw.flush();
        bw.close();
    }

    @Override
    public void run() {
        while (true) {
            HashMap<String, Integer> map = null;
            synchronized (queue) {
                if (queue.getQueue().isEmpty()) {
                    queue.notify();
                    try {
                        queue.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    for (Book book : queue.getQueue()) {
                        try {
                            map = book.countWords();
                            System.out.println(book.getInputfilename().toUpperCase());
                            for (String word : map.keySet()) {
                                System.out.println(word + ": " + map.get(word));
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        queue.remove(book);
                        this.createBook(map, book.getInputfilename());
                        queue.notifyAll();
                    }
                } catch (Exception e) {
                }

            }
        }
    }
}