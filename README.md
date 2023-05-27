# DalleMC

About ten months ago I was watching an episode of *Techlinked*, a couple times a week news show on YouTube, in which the host made a joke about having an AI create images using blocks in Minecraft. It was with this that I was inspired to create my own plugin and server to host it, where it would take in suggestions and use AI to generate pixel art of the image. The purpose of DalleMC is to do exactly this.

**How it Works:**

[![Youtube Demonstration](https://img.youtube.com/vi/IKPfUOyyxWk/0.jpg)](https://www.youtube.com/watch?v=IKPfUOyyxWk)

*Click the image for a YouTube demonstration*

DalleMC is the plugin to manage it, and I currently host it on my personal server (dallemc.dylan.sh - probably online right now!). DalleMC collects suggestions, allows you to vote on them, and after a certain amount of time a winner is picked. The winning suggestion is then generated in the form of minecraft blocks below.

There are three commands, /suggest &lt;prompt&gt; which makes a suggestion, /listsuggestions which shows the suggestions and how many votes it has, and /vote &lt;prompt&gt; which allows you to vote on the suggestion you want to see.

The DalleMC server currently generates the image in a 256x256 grid below the players.

**Development:**

![UML Diagram](https://github.com/dylan-sh/dallemc/assets/50163127/4ade713c-14e3-4a75-9501-6446d1b36968)

*UML Class Diagram*

Because Minecraft is Java and all plugins are exclusively Java, this was written almost entirely in Java. At the time I started development, I was really interested in [DALL-E Mini](https://github.com/borisdayma/dalle-mini), a model very similar to the DALLE·2 model, but at a fraction of the size. I was initially going to use this to handle AI image generation, but unfortunately it made things complicated as my current hardware isn't optimized for GPU intensive workloads. Luckily I received API access for DALLE·2, and decided to use that instead.

DalleMC consists of eight classes and one Python script. Essentially, there's a main class that actually runs the plugin, and it calls the other ones for various tasks. 

When the voting cycle starts, the 'Main' class uses 'SuggestionDB' and 'VoteRecords' to create two databases for who has voted/suggested, and what they've voted/suggested. The timing and voting cycle is all handled via a scheduled repeating task, with adjustibility if needed. 'SuggestionDB' and 'VoteRecords' are essentially just objects with maps and getters/setters for access and manipulation.

In Spigot, plugins have separate classes for commands, so 'CommandVote' and 'CommandSuggest' are used to make a suggestion and to vote on the desired suggestion. These two classes can be called by players, and they utilize 'SuggestionDB' and 'VoteRecords' to make sure that a player cannot submit multiple suggestions/votes, and to track who voted. There is an additional class, 'CommandListSuggestions', that serves the purpose of listing suggestions and how many votes it has, utilizing SuggestionDB to do so.

When a suggestion is determined to be the winner, it will call the 'ImageGeneration' class to generate the image. This class passes in parameters to a Python script that calls the OpenAI API, receives the URL to the generated 256x256 image, and downloads it to the relevant folder. Because this project relies on a modified version of Pixelator (.png to Minecraft blocks converter), the Python script saves it to the input folder for that plugin. In the event that the suggestion breaks OpenAI's content rules, or it has an error generating, 'ImageGeneration' will broadcast this and describe the error in the console.

'ImageGeneration' relies on an API key, and because I would be a fool to hardcode that in, I have included the 'ConfigParser' class as a way to take in the OpenAI API key from a 'config.txt' file located in the "DalleMC" folder adjacent to the plugin jar file. 'ConfigParser' reads this after being called in the main class, and is not included in the repeating task.

After generating the image, the 'Main' class broadcasts the winner with the number of votes and who suggested it. It then calls Pixelator to generate the image, and Pixelator does so. Because [Pixelator](https://github.com/TheBizii/Pixelator) was created to be executed by a player and generated directly at the players location, I had to modify it for my use case. A simple [fork](https://github.com/dylan-sh/Pixelator-Location) and command modification to take in x, y, z coordinates, as well as making it executable by console, was all I needed. 'ImageGeneration' sends the command to the console with the coordinates,and out it goes.

**Limitations:**

DalleMC was created for my personal use, and while anyone could use it, I doubt anyone will. Because of this, a lot of aspects have been hardcoded in. I could have used the 'ConfigParser' class to take in a bunch of different aspects, but in the interest of time and scope, I have not. The API was something that I felt most certainly could not be hardcoded in, however.

**Server Setup:**

Spawn is protected by WorldGuard, and the map was created with WorldEdit. There was considerable effort put in as far as creating the activity area, and ensuring it was protected from unauthorized commands and block placement/destruction.


**Project Challenges:**

Because my setup was development on my Mac and hosting on a Windows desktop downstairs, I quickly realized how difficult it was managing my development environment vs the production environment. For example, my python script that called the OpenAI API worked perfectly fine on my Mac, but did not work at all on my PC. I had to make a change to the script to use Python’s ‘requests’ instead of ‘curl’ with ‘os.system’. This solved the issue, but cost me an hour and a half of my time. 

Another issue I had was that, because I was running everything through a VPN (ISP provided router is difficult to port forward, and I need to make sure there aren’t any trackers when downloading my collection of Linux ISO’s) the actual host name resolution was a hurdle at times. While I could just directly connect, I preferred to route it through the “dallemc.dylan.sh” domain. The dynamic IP address the VPN uses means that I very often have to run a Powershell command to update my DNS records, which results in me having to wait for the DNS records to update on my Mac that I’m using to launch Minecraft and connect to the server with. If I was on Linux, I could just make it a CRON job, but unfortunately for the time being I’m still on Windows. Apparently, the subdomain i'm using might only work locally without the specified port, so I have to figure out if my SRV records are correct too.

While getting the plugin to execute the Python script, I spent over an hour trying to figure out why my Java code was incorrect. The script executed perfectly manually, but didn’t work when trying to execute it with the server. What I didn’t realize, was that I had coded the script to accept *user* input, not Java ProcessBuilder arguments, so all that time I had spent staring at my Java code could have been saved by focusing on my Python code.

## Installation:

This code itself would have to be modified a bit to fit your use case. You'd have to clone the repo and edit aspects of it and rebuild it (and the artifacts), then follow the instructions below:

1. Build the .jar and place it in your plugins folder.
2. Run your server once.
3. Configure the config.txt file found in 'plugins/dallemc'.
4. Enjoy!
