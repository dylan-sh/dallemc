import argparse
import os
import requests
import json

# Set up argument parser
parser = argparse.ArgumentParser()
parser.add_argument("prompt")
parser.add_argument("api_key")
args = parser.parse_args()

PROMPT = args.prompt
API_KEY = args.api_key
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
		base_filename = PROMPT.replace(' ', '_') + f"_{idx}"
		extension = ".png"
		filename = os.path.normpath(os.path.join(input_dir, base_filename + extension))

		# If the file already exists, add a suffix to the filename to avoid overwriting
		suffix = 1
		while os.path.exists(filename):
			filename = os.path.normpath(os.path.join(input_dir, base_filename + f"_{suffix}" + extension))
			suffix += 1

		r = requests.get(image_url)
		with open(filename, 'wb') as f:
			f.write(r.content)
	print(f"filename:{filename}")

except Exception as e:
	print(e)
	with open('generate_dalle2_output.txt', 'w') as output_txt:
		output_txt.write(str(e))
