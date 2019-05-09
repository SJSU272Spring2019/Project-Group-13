# Project-Group-13

<p float="left">
  <img src="![1](https://user-images.githubusercontent.com/11595332/57488080-e6726c80-7266-11e9-8057-21e3fc56ac34.jpeg)
" width="100" />
  <img src="![WhatsApp Image 2019-05-09 at 2 27 48 PM](https://user-images.githubusercontent.com/11595332/57488104-f722e280-7266-11e9-9c69-b9a81a722ba7.jpeg)" width="100" /> 
  <img src="
![3](https://user-images.githubusercontent.com/11595332/57488121-06099500-7267-11e9-8960-275a7f2a1e90.jpeg)
" width="100" />
</p>

## Calendared App & Eventify NLP

Calendered App is an Android app developed to help students keep up with the latest information about all the events happening on the San Jose State University campus. It can also be used to track personal events. The app relies on the Eventify NLP backend module for getting the data about events. After the user signs into the app and gives consent to read emails, they are presented with a list of event types to choose from. The Eventify module securely scans all the user’s emails, categorizes them into event types and prepares a list of events for the user. This list is displayed in the app and the user can add the events to the phone’s calendar app.





__Steps to run the project__.

Clone the project 

Create an ubuntu instance in EC-2

Now go to com.calendered>Common utilities and change the parameter of Server_URL_auth to your ec2 instance URl

Go to your EC2 instance and copy gmailec2.py and requirement.txt 

change the permission to admistrator usign '''sudo su'''

now start the server using:
'''export FLASK_APP=gmailec2.py'''

'''python3 -m flask run --host=0.0.0.0''''

__You are ready to go!!__.
