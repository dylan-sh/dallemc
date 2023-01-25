package sh.dylan.dallemc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigParser {
    private Map<String, String> config = new HashMap<>();

    public ConfigParser(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //split the line by the delimiter '='
                String[] parts = line.split("=");
                //if line doesn't contain '=' or is empty, skip it
                if (parts.length != 2 || line.isEmpty()) {
                    continue;
                }
                //trim the parts and put them in the map
                config.put(parts[0].trim(), parts[1].trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
            //not at all done yet lol
            System.out.println("DalleMC config not detected. Generating one for you now (restart required)...");

        }
    }

    public String getValue(String key) {
        return config.get(key);
    }
}

