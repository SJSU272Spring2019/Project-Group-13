import csv
import pandas as pd  
import datefinder
import datetime
from dateutil.parser import parse

event_types = ["Birthday", "Meeting", "Seminar", "Party", "Anniversary","Marriage"]
event_dates = ["Tomorrow", "Today", "Day After Tomorrow", "Next Month", "Next Week"]
#texts = [[word.lower() for word in text.split()] for text in data]

#with [line.strip() for line in open("CSV_NAME.csv", 'r')] as csvFile]:

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

csvFile = pd.read_csv('CSV_NAME.csv') 
messages = csvFile['Message_body']
senders = csvFile['Sender']
subjects = csvFile['Subject']
print(messages)
empevents = []
empdates = []
event_list = []
# for index,row in messages.iteritems():
# 	str1 = ''.join(row)
# 	for event_type in event_types:
# 		if event_type.lower() in str1.lower():
# 			print("Matched = ", event_type)
# 			empevents.append(event_type)
# 			matches = datefinder.find_dates(str1)
# 			print(type(matches))
# 			for match in matches:
# 				empdates.append(match)
# print(senders.iloc[0])
msg_counter = 0
for index,row in messages.iteritems():
	i = 0
	# previous_date = ""
	# next_date = ""
	event_dict = {}
	str1 = ''.join(row)
	for event_type in event_types:
		if event_type.lower() in str1.lower():
			flag = False
			for event_date in event_dates:
				if event_date.lower() in str1.lower():
					convertedDate = getDate(event_date.lower())
					print(type(convertedDate.date()))
					event_dict["event_date"] = convertedDate.date()
					matches = datefinder.find_dates(str1)
					for match in matches:
						print('Inside Match = ',i)
						if match.time():
							if i == 0:
								event_dict["event_startTime"] = match.time()
							elif i == 1:
								event_dict["event_endTime"] = match.time()
						i+=1
						# else:
						# 	if i == 0:
						# 		event_dict["event_startTime"] = "00:00:00"
						# 		event_dict["event_endTime"] = "00:00:00"
						# 	elif i == 1:
						# 		event_dict["event_endTime"] = "00:00:00"
						# i+=1

					# convertedDate = parse(event_date.lower(), fuzzy=True)
					empdates.append(convertedDate)
					flag = True
					break
			if not flag :
					matches = datefinder.find_dates(str1)

					# print(type(matches))
					# print(list(matches))
					# a = list(matches)[:]
					# print(len(a))
					# print(matches)
					for match in matches:
						print("Match =", match.date())
						if i == 0:
							event_dict["event_date"] = match.date()
							event_dict["event_startTime"] = match.time()
						elif i == 1:
							event_dict["event_endTime"] = match.time()
						i+=1
						# print(type(match))
					# if i == 0:
					# 	event_dict["event_startTime"] = "00:00:00"
					# 	event_dict["event_endTime"] = "00:00:00"
					# elif i == 1:
					# 	event_dict["event_endTime"] = "00:00:00"
						empdates.append(match)
			event_dict["event_type"] = event_type
			# empevents.append(event_type)
			event_dict["sender"] = senders.iloc[msg_counter]
			event_dict["subject"] = subjects.iloc[msg_counter]
			if i == 0:
				event_dict["event_startTime"] = "00:00:00"
				event_dict["event_endTime"] = "00:00:00"
			elif i == 1:
				event_dict["event_endTime"] = "00:00:00"
			event_list.append(event_dict)
			break
	msg_counter+=1
				

# print(empevents)
for empdate in empdates:
	print(empdate.date())
for event in event_list:
	print(event)

	# print(empdate)
	#print(empdate.date())
# print(empdates)
# with open("CSV_NAME.csv", 'r') as csvFile:
# 	reader = csv.reader(csvFile)
# 	for row in reader:
# 		print("Type =",type(row))
# 		for event_type in data:
# 			print("Type for 2nd =",type(event_type))
# 			if event_type.lower() in row.lower():
# 				print("Matched = ", event_type)
# 				break;