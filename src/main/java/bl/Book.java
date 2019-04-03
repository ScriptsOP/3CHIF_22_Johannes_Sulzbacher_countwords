package bl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class Book {
    private String inputfilename;
    private String text;

    public Book(String inputfilename, String text) {
        this.inputfilename = inputfilename;
        this.text = text;
    }
    
    public HashMap<String, Integer> countWords() throws FileNotFoundException, IOException {
        HashMap<String, Integer> map = new HashMap<>();
        text = text.replaceAll("[^a-zA-Z]", " ");
        String[] words = text.split(" ");

        for (String word : words) {
            if (map.containsKey(word) && !word.equals(" ")) {
                map.replace(word, map.get(word) + 1);
            } else if(!word.equals(" ")){
                map.put(word, 1);
            }
        }

        for (Object object : map.keySet().toArray()) {
            if(map.get((String) object) < 2){
                map.remove((String)object);
            }
        }

        return map;
    }

    public String getInputfilename() {
        return inputfilename;
    }

    public void setInputfilename(String inputfilename) {
        this.inputfilename = inputfilename;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}