import json
import urllib.request
import httplib2
import requests

def code2token(auth_code):
	params={}
	params['client_id']="515619392132-eik3ukaie7ok9ohh44s2bfb4439u2phm.apps.googleusercontent.com"
	params['client_secret'] = "BtrC-F0zOTEp58LPztrvK_nW"
	params['grant_type'] = "authorization_code"
	params['redirect_uri'] = "urn:ietf:wg:oauth:2.0:oob"
	params['code']=auth_code
	headers = {'content-type': 'application/x-www-form-urlencoded'}
	r=requests.post("https://oauth2.googleapis.com/token", data=params, headers=headers)
	new_credentials = json.loads(r.text)
	access_token = new_credentials['access_token']
	print(access_token)
	return  access_token
