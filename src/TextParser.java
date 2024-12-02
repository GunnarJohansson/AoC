package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextParser {
    public String readFromFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String content;
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while(line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            content = sb.toString();
        } finally {
            br.close();
        }
        return content;
    }
}
