package queue;

import bl.Book;
import java.util.ArrayList;

public class MyQueue<T> {
    private ArrayList<Book> queue = new ArrayList<>();
    
    public void add(Book book){
        queue.add(book);
    }
    
    public void remove(Book book){
        queue.remove(book);
    }

    public ArrayList<Book> getQueue() {
        return queue;
    }
}