import datetime

def getDate(str):
		now = datetime.datetime.now()
		datetime.datetime.now() + datetime.timedelta(days=1)
		switcher = { 
	        "today" : now , 
	        "tomorrow": now + datetime.timedelta(days=1), 
	        "day after tommorow": now + datetime.timedelta(days=2), 
			"next week": now + datetime.timedelta(days=7),
			"next month": now + datetime.timedelta(days=30)
	    }
		return switcher.get(str, "Not a valid date")