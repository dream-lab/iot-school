
# coding: utf-8

# In[1]:

import requests
import numpy as np


# In[10]:

pathToFileInDisk = '../images/2.jpg'
with open( pathToFileInDisk, 'rb' ) as f:
    data = f.read()


# In[13]:

from IPython.display import Image


# In[78]:

requests.post(url='http://10.16.240.106:5000/uploader', files = {'file': open('../images/2.jpg', 'rb')})


# In[53]:

http_proxy  = "http://proxy.iisc.ernet.in:3128"
https_proxy = "https://proxy.iisc.ernet.in:1080"
ftp_proxy   = "ftp://proxy.iisc.ernet.in:3128"

proxyDict = { 
              "http"  : http_proxy, 
              "https" : https_proxy, 
              "ftp"   : ftp_proxy
            }


# In[74]:

r = requests.post('http://127.0.0.1:5000/uploader', files={'file': open('../images/2.jpg', 'rb')}, proxies=proxyDict)


# In[80]:

r = requests.post('http://127.0.0.1:5000/uploader', files={'file': open('../images/2.jpg', 'rb')})
HTML(r.content)


# In[56]:

r = requests.post("http://127.0.0.1:5000/uploader", data={'number': 12524, 'type': 'issue', 'action': 'show'}, proxies=proxyDict)


# In[52]:

r = requests.post("http://httpbin.org/post", data={'number': 12524, 'type': 'issue', 'action': 'show'})


# In[55]:


from IPython.display import HTML
HTML(r.content)


# In[7]:

def processRequest( json, data, headers, params ):

    """
    Helper function to process the request to Project Oxford

    Parameters:
    json: Used when processing images from its URL. See API Documentation
    data: Used when processing image read from disk. See API Documentation
    headers: Used to pass the key information and the data type request
    """

    retries = 0
    result = None

    while True:

        response = requests.request( 'post', _url, json = json, data = data, headers = headers, params = params )

        if response.status_code == 429: 

            print( "Message: %s" % ( response.json()['error']['message'] ) )

            if retries <= _maxNumRetries: 
                time.sleep(1) 
                retries += 1
                continue
            else: 
                print( 'Error: failed after retrying!' )
                break

        elif response.status_code == 200 or response.status_code == 201:

            if 'content-length' in response.headers and int(response.headers['content-length']) == 0: 
                result = None 
            elif 'content-type' in response.headers and isinstance(response.headers['content-type'], str): 
                if 'application/json' in response.headers['content-type'].lower(): 
                    result = response.json() if response.content else None 
                elif 'image' in response.headers['content-type'].lower(): 
                    result = response.content
        else:
            print( "Error code: %d" % ( response.status_code ) )
            print( "Message: %s" % ( response.json()['error']['message'] ) )

        break
        
    return result


# In[9]:

# URL direction to image
urlImage = 'https://oxfordportal.blob.core.windows.net/vision/Analysis/3.jpg'

# Computer Vision parameters
params = { 'visualFeatures' : 'All'} 

headers = dict()
headers['Ocp-Apim-Subscription-Key'] = _key
headers['Content-Type'] = 'application/json' 

json = { 'url': urlImage } 
data = None

result = processRequest( json, data, headers, params )


# In[10]:

result


# In[15]:

import cv2


# In[14]:

if result is not None:
    # Load the original image, fetched from the URL
    arr = np.asarray( bytearray( requests.get( urlImage ).content ), dtype=np.uint8 )
    img = cv2.cvtColor( cv2.imdecode( arr, -1 ), cv2.COLOR_BGR2RGB )

    renderResultOnImage( result, img )

    ig, ax = plt.subplots(figsize=(15, 20))
    ax.imshow( img )


# In[16]:

# Load raw image file into memory
pathToFileInDisk = r'/Users/puneet2895/Downloads/banana-tomato-fruit-free-bananas-tomatoes.jpg'
with open( pathToFileInDisk, 'rb' ) as f:
    data = f.read()
    
# Computer Vision parameters
params = { 'visualFeatures' : 'Color,Categories,Tags'} 

headers = dict()
headers['Ocp-Apim-Subscription-Key'] = _key
headers['Content-Type'] = 'application/octet-stream'

json = None

result = processRequest( json, data, headers, params )


# In[17]:

result


# In[16]:

from werkzeug import secure_filename


# In[ ]:



