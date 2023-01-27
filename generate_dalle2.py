# generate_dalle2.py

import os

import openai

PROMPT = input("Enter Prompt (should be automated")

openai.api_key = input("Enter API key (should be automated)")
try:
	response = openai.Image.create(
    		prompt=PROMPT,
		n=1,
    		size="256x256",
	)
	print(response["data"][0]["url"])
except openai.error.OpenAIError as e:
	print(e.http_status)
	print(e.error)
