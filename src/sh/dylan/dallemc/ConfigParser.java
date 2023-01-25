package sh.dylan.dallemc;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigParser {
    private Map<String, String> config = new HashMap<>();

    public ConfigParser(String filepath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
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
        } catch (FileNotFoundException e){
            System.out.println("DalleMC config not detected. Generating one for you now (restart required)...");
            try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filepath)))){
                writer.println("API_OR_LOCAL=API"); //defaults to API cuz i def haven't coded local yet
                writer.println("API_KEY=KEY GOES HERE");
            }catch(IOException e2){
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSpecificValue(String key) {
        return config.get(key);
    }

    public String getAPIKey(){
        return config.get("API_KEY");
    }

    public String getAOL(){
        return config.get("API_OR_LOCAL");
    }
}

