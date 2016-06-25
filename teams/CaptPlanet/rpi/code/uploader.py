from flask import Flask, render_template, request
from werkzeug import secure_filename
import os
app = Flask(__name__)

@app.route('/uploader', methods = ['GET', 'POST'])
def upload_file():
	print request.__dict__
	print "HEHEHEHEHEHEH"
	print request.data
	if request.method == 'POST':
		print "Here"
		f = request.files['file']
		print "SJHDGJAHGSJHAGJHG"
		print f.filename
		f.save(os.path.join("../images",f.filename)))
		#	return request.__dict__
		return 'file uploaded successfully'
    	else:
    		return "HELLO"

		
if __name__ == '__main__':
   app.run(debug=True)
