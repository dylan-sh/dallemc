import os
import requests
import json

PROMPT = input("Enter Prompt (should be automated): ")
API_KEY = input("Enter API key (should be automated): ")
input_dir = os.path.normpath(os.path.join(os.path.dirname(os.path.realpath(__file__)), "..", "Pixel8or", "input"))


headers = {
	'Content-Type': 'application/json',
	'Authorization': f'Bearer {API_KEY}'
}

data = {
	'prompt': PROMPT,
	'n': 1,
	'size': '256x256'
}

try:
	response = requests.post('https://api.openai.com/v1/images/generations', headers=headers, data=json.dumps(data))
	response.raise_for_status()
	# parsing the response assuming it's a json containing url of generated images
	jsonResponse = response.json()
	for idx, image_info in enumerate(jsonResponse["data"]):
		image_url = image_info["url"]
		filename = os.path.normpath(os.path.join(input_dir, PROMPT.replace(' ', '_') + f"_{idx}.png"))
		r = requests.get(image_url)
		with open(filename, 'wb') as f:
			f.write(r.content)
	print(f"Image saved as {filename}")

except Exception as e:
	print(e)
	with open('generate_dalle2_output.txt', 'w') as output_txt:
		output_txt.write(str(e))


		#this is still so broken
