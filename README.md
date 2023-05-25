# DalleMC

*updated readme incoming with information on project development, challenges, limitations, etc.*
It's dalle... in Minecraft!


DalleMC is a plugin for my own personal server (dallemc.dylan.sh). It's main goal is to implement Dalle-mini (https://github.com/borisdayma/dalle-mini)
in Minecraft so that images will be generated in the form of blocks.

DalleMC is designed to do these majors tasks:

1. Establishes rules and voting for what should be generated.
2. Calling an API (or modified to generate locally) that will generate an image using replicate.com
3. Putting that into the game using Pixelator (https://github.com/TheBizii/Pixelator)

DalleMC is not currently very configurable. It is designed simply as a side project for me, and I doubt anyone will use it enough for me to care to continue
work on it after it's completed. Feel free to branch the repo and modify it yourself.

## Installation:

1. Simply download the dallemc.jar (currently located in 'dallemc/out/artifacts/dallemc_jar') and place it in your plugins folder.
2. Run your server once.
3. Configure the config.txt file found in 'plugins/dallemc'.
4. Enjoy!
