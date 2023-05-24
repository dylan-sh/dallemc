package sh.dylan.dallemc;

import org.bukkit.Bukkit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ImageGeneration {

    private String apiKey;
    private String suggestion;

    /** This class generates the image using the api. it also checks if the prompt has already been generated and returns that (because i'm cheap and we're using an api)
     * It will check for errors in return (nsfw error for example) and react accordingly
     * generateURL() calls the api and generates the url. it does so by using a python script located in the dallemc folder generated by ConfigParser
     * I guess I could've made some of these methods private since they're only accessed within this class but I didn't. Maybe I'll make them private after typing this. If I do I'm not deleting this line so enjoy.
     * getImageAndSaveToPixelator() checks for the error, takes the url, and saves it to pixelator, where it then returns a file name so that another class can call it and
     * execute the command in game.
     * yes this code is kind of sloppy i'm sorry
     **/

    public ImageGeneration(String apiKey, String suggestion){
        this.apiKey = apiKey;
        this.suggestion = suggestion;

    }

    //generates the URL of the image and returns error strings if not there.
    public String generateURL() throws IOException {

        if(checksIfAlreadyExists()){
            return "alreadyExists";
        }
        //dunno if actually calling script correctly, modify script to save it's output to a txt to be sure
        String pythonScript = "plugins/dallemc/generate_dalle2.py"; //modify if you want to use another name or another api
        System.out.println("[DEV]:" + pythonScript + apiKey + suggestion);
        ProcessBuilder pb = new ProcessBuilder("C:\\Users\\dylan\\AppData\\Local\\Programs\\Python\\Python311\\python.exe", pythonScript, apiKey, suggestion); //you will have to change this unless i get around to fixing it
        Process process = pb.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        System.out.println("[DEV] I got here.");
        if((line = in.readLine()) != null) { //this doesn't need to be a while loop since only the first line matters, but it's here anyway
            System.out.println(line);
            if(line.substring(0,3).equals("400")){
                System.out.println("\"\\033[31m\" + \"[API ERROR] Safety system denied (NSFW error)\" + \"\\033[0m\"");
                Bukkit.broadcastMessage("Unsafe contented detected. Creation aborted (don't suggest nsfw content lol)");
                return "nsfwError";
            } else if(line.substring(0,3).equals("401")){
                System.out.println("\"\\033[31m\" + \"[API ERROR] Invalid authentication\" + \"\\033[0m\"");
                Bukkit.broadcastMessage("System error. Contact admin.");
                return "apiError";
            } else if(line.substring(0,3).equals("429")){
                System.out.println("\"\\033[31m\" + \"[API ERROR] Rate limit or quota reached or OpenAI servers overloaded\" + \"\\033[0m\"");
                Bukkit.broadcastMessage("System error. Contact admin.");
                return "networkError";
            } else if(line.substring(0,3).equals("500")){
                System.out.println("\"\\033[31m\" + \"[API ERROR] OpenAI server issues. Check status.openai.com\" + \"\\033[0m\"");
                Bukkit.broadcastMessage("System error. Contact admin.");
                return "openAInetworkError";
            }else{
                return line;
            }
        }
        System.out.println("\"\\033[31m\" + \"[IO ERROR] If this is reached then there's an error reading the line and it's null.\" + \"\\033[0m\"");
        System.out.println("The python script should ideally always return something but if it doesn't then oh boy figure that out.");
        Bukkit.broadcastMessage("System error. Contact admin.");
        return "IOError";

    }

    public boolean checksIfAlreadyExists(){
        File file = new File("plugins/dallemc/" + suggestion + ".png");
        return file.exists();
    }

    //pulls image and returns location
    public String getImageAndSaveToPixelator() throws IOException {
        String urlString = generateURL();
        if(urlString.substring(urlString.length() -5).equals("Error")){
            System.out.println("\"\\033[31m\" + \"[API ERROR]\" + \"\\033[0m\"");
            return "Error";
        }
        try {
            URL url = new URL(urlString);
            BufferedImage image = ImageIO.read(url);
            File outputFile = new File("plugins/Pixel8or/input" + suggestion + ".png");
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suggestion + ".png"; //this is a really dumb way of doing this but i've come this far so
    }



}
