import rawday2date
import pandas as pd
import json
import datetime
import datefinder

def extractevent():

	event_types = ["coffee night","job fairs","career fairs","career fair","tech talk", "alumni connection","lecture", "Birthday", "Meeting", "Seminar", "Party", "Anniversary","Marriage", "Appointment", "Meet", "sports", "career fair", "Workshop"]
	event_dates = ["Tomorrow", "Today", "Day After Tomorrow", "Next Month", "Next Week"]

	csvFile = pd.read_csv('CSV_NAME.csv') 
	messages = csvFile['Message_body']
	senders = csvFile['Sender']
	subjects = csvFile['Subject']
	empevents = []
	empdates = []
	event_list = []

	msg_counter = 0
	for index,row in messages.iteritems():
		i = 0
		event_dict = {}
		str1 = ''.join(row)
		str1 = str1.replace("-"," ")
		str1 = str1.replace("|"," ")
		for event_type in event_types:
			if event_type.lower() in str1.lower():
				flag = False
				for event_date in event_dates:
					if event_date.lower() in str1.lower():
						convertedDate = rawday2date.getDate(event_date.lower())
						json_event_date = convertedDate.date()
						matches = datefinder.find_dates(str1)
						for match in matches:
							if match.time():
								if i == 0:
									json_event_startTime = match.time()
								elif i == 1:
									json_event_endTime = match.time()
							i+=1
						flag = True
						break
				if not flag :
					matches = datefinder.find_dates(str1)

					for match in matches:
						if i == 0:
							json_event_date = match.date()
							json_event_startTime = match.time()
						elif i == 1:
							json_event_endTime = match.time()
						i+=1

				json_event_type = event_type
				json_event_sender = senders.iloc[msg_counter]
				json_event_subject = subjects.iloc[msg_counter]
				if i == 0:
					json_event_startTime = "00:00:00"
					json_event_endTime = "00:00:00"
				elif i == 1:
					json_event_endTime = "00:00:00"
				event_dict["type"] = json_event_type
				event_dict["date"] = str(json_event_date)
				event_dict["stime"] = str(json_event_startTime)
				event_dict["etime"] = str(json_event_endTime)
				event_dict["title"] = json_event_sender
				event_dict["desc"] = json_event_subject

				event_list.append(event_dict)
				break
		msg_counter+=1

	for empdate in empdates:
		print(empdate.date())
	for event in event_list:
		print(event)
	return event_list