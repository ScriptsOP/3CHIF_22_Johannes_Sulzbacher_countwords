package producer;

import bl.Book;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import queue.MyQueue;

public class BookProducer implements Runnable {
    
    private File folder = new File("./textfiles");
    private MyQueue queue;

    public BookProducer(MyQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            File[] files = folder.listFiles();
            String fileText;
            String fileLine;

            for (File file : files) {
                fileText = "";
                fileLine = "";
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BookProducer.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    while ((fileLine = br.readLine()) != null) {
                        fileText += fileLine;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(BookProducer.class.getName()).log(Level.SEVERE, null, ex);
                }

                synchronized (queue) {
                    if (queue.getQueue().size() == 3) {
                        try {
                            queue.wait();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(BookProducer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    queue.add(new Book(file.getName(), fileText));
                    queue.notifyAll();
                }
            }
        }
    }

}