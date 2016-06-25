# web 
import urllib2
import sys
import json
class thingspeak_interface:
  
  def __init__(self, base_url, read_api_key, write_api_key, proxy = None):
    self.base_url = base_url
    self.read_api_key = read_api_key
    self.write_api_key = write_api_key
    if proxy:
      proxy = urllib2.ProxyHandler({'https': 'proxy.iisc.ernet.in:3128'})
      opener = urllib2.build_opener(proxy)
      urllib2.install_opener(opener)
 
  def write_to_thingspeak(self, content):
    temp = self.base_url + '/update?api_key='+self.write_api_key+"&field1=%s&field2=%s" % (content[0], content[1]) 
    try:
      f = urllib2.urlopen(temp)
      f.read()
      f.close()
    except:
      print sys.exc_info()
      
  def read_from_thingspeak(self, channel_id):
    conn = urllib2.urlopen(self.base_url+"/channels/%s/feeds/last.json?api_key=%s" % (channel_id, self.read_api_key))
    try:
      response = conn.read()
      print "http status code=%s" % (conn.getcode())
      data=json.loads(response)
      print data['field1'],data['created_at']
      conn.close()
    except:
      print sys.exc_info()


