import requests
r = requests.post('http://127.0.0.1:5000/uploader', files={'file': open('../images/2.jpg', 'rb')})