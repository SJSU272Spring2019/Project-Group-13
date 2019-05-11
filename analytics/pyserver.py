import code2token
import rawday2date
import readgmail
import eventextractor
from pymongo import MongoClient
from flask import request
from flask import Flask
from flask import jsonify
app = Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def index():    
		try:
		    client = MongoClient('mongodb://10.0.1.143:27017')
		    db = client.events
		    data = request.get_json()
		    print(data)
		    authorization_code = data['authcode']
		    access_token = code2token.code2token(authorization_code)
		    print(access_token)
		    readgmail.readmail(access_token)
		    event_list = eventextractor.extractevent()
		    db.eventstore.insert(event_list)
		    return jsonify(results= event_list)
		except Exception as e:
			print(e)
			return e    

if __name__ == "__main__":
	app.run(debug = True)

