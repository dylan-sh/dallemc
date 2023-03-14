package sh.dylan.dallemc;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigParser {
    private Map<String, String> config = new HashMap<>();

    public ConfigParser(String direct, String filename) {
        System.out.println("Config Parser attempting to parse...");
        //checks if file directory exists and makes it if it doesn't
        File directory = new File(direct);
        if(!directory.exists()){
            System.out.println("\033[31m" + "[ERROR] Directory not detected. Creating directory..." + "\033[0m");
            directory.mkdirs();
        }
        filename = direct + filename;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
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
                System.out.println("[DEV] parsing: " + parts[0] + " " + parts[1]);
                System.out.println("Parse successful! (I think)");
            }
        } catch (FileNotFoundException e){
            System.out.println("\033[31m" + "DalleMC config not detected. Generating one for you now (restart required)..." + "\033[0m");
            try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))){
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

