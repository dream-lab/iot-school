#!/usr/bin/python
import sys
from azure.servicebus import ServiceBusService, Message, Queue


class azure_interface:
  def __init__(self, name_space, key_name, key_value, proxy = None):
    self.sbs = ServiceBusService(service_namespace=name_space,shared_access_key_name=key_name, shared_access_key_value=key_value)
    if proxy:
      self.sbs.set_proxy('proxy.iisc.ernet.in',3128)

  def event_send(self, hubname ,msg):
    try:
      hubStatus = self.sbs.send_event(hubname, str(msg))
      print "Send Status:", repr(hubStatus)
    except:
      print sys.exc_info()               

