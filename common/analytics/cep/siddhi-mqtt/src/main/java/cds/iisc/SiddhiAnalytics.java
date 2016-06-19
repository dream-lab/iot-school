/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package cds.iisc;

import org.wso2.siddhi.core.ExecutionPlanRuntime;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.query.output.callback.QueryCallback;
import org.wso2.siddhi.core.stream.input.InputHandler;
import org.wso2.siddhi.core.util.EventPrinter;

public class SiddhiAnalytics {
	public static void main(String[] args) {

		// Creating Siddhi Manager
		SiddhiManager siddhiManager = new SiddhiManager();
		String planType="pattern";
		String sub_topic="demo/temperature";
		String pub_topic="demo/siddhi/temperature";
		
		if(args.length >=1)
			planType = args[0];
		if(args.length >=2)
			sub_topic = args[1];
		if(args.length >=3)
			pub_topic = args[2];
		
		String executionPlan = null;

		if(planType.equals("filter")) {
			executionPlan = "" +
	                "define stream cseEventStream (temperature float); " +
	                "" +
	                "@info(name = 'query1') " +
	                "from cseEventStream[temperature < 20.0 ] " +
	                "select temperature " +
	                "insert into outputStream ;";
		}
		else if (planType.equals("agg_len_sli")) {
			executionPlan = "" +
    				"define stream cseEventStream (temperature float); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.length(3) " + 
                    "select avg(temperature) as avgTemp " + 
                    "insert into outputStream ;";	
		}
		else if (planType.equals("agg_len_bat")) {
			executionPlan = "" +
    				"define stream cseEventStream (temperature float); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.lengthBatch(3) " + 
                    "select avg(temperature) as avgTemp " + 
                    "insert into outputStream ;";
			
		}
		else if (planType.equals("agg_time_sli")) {
			executionPlan = "" +
    				"define stream cseEventStream (temperature float); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.time(10 sec) " + 
                    "select avg(temperature) as avgTemp " + 
                    "insert into outputStream ;";
		}
		else if (planType.equals("agg_time_bat")) {
			executionPlan = "" +
    				"define stream cseEventStream (temperature float); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from cseEventStream #window.timeBatch(10 sec) " + 
                    "select avg(temperature) as avgTemp " + 
                    "insert into outputStream ;";
		}
		else if (planType.equals("sequence")) { 
			executionPlan = "" +
    				"define stream cseEventStream (temperature float); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from every e1 =  cseEventStream, "+
                    "e2 = cseEventStream[e1.temperature == e2.temperature], "+
                    "e3 =  cseEventStream[e3.temperature == e2.temperature] " +
                    "select e1.temperature as temperature1, e2.temperature as temperature2, e3.temperature as temperature3 " + 
                    "insert into outputStream ;";
		}
		else if (planType.equals("pattern")) {
			executionPlan = "" +
    				"define stream cseEventStream (temperature float); " +
                    "" +
                    "@info(name = 'query1') " +
                    "from every e1 =  cseEventStream " +
                    "-> e2 = cseEventStream[e1.temperature == e2.temperature]" +
                    "-> e3 = cseEventStream[e2.temperature == e3.temperature] "+
                    "select e1.temperature as temperature1, e2.temperature as temperature2, e3.temperature as temperature3  " + 
                    "insert into outputStream ;";	
		}
		else
		{
			System.out.println("INVALID QUERY PARAMETER!! :(");
		}
		
		
		// Generating runtime
		ExecutionPlanRuntime executionPlanRuntime = siddhiManager.createExecutionPlanRuntime(executionPlan);

		// Initialize MQTT publisher
		final Pub_Siddhi_Temperature pubfil = new Pub_Siddhi_Temperature(pub_topic);

		// Adding callback to retrieve output events from query
		executionPlanRuntime.addCallback("query1", new QueryCallback() {
			@Override
			public void receive(long timeStamp, Event[] inEvents, Event[] removeEvents) {
//				EventPrinter.print(timeStamp, inEvents, removeEvents);
				
				Object obj[];
				for(int i=0;i < inEvents.length; i++) 
				{
					obj = inEvents[i].getData();					
					String str=Long.toString(inEvents[i].getTimestamp());
					for (Object o : obj) {
						str += "," + o.toString();
					}
					System.out.println(str);
					pubfil.deliver(str);
				}

			}
		});

		// Retrieving InputHandler to push events into Siddhi
		InputHandler inputHandler = executionPlanRuntime.getInputHandler("cseEventStream");

		// Starting event processing
		executionPlanRuntime.start();

		// Sending events to Siddhi
		Sub_Temperature sub = new Sub_Temperature(inputHandler, sub_topic);

		// Shutting down the runtime
		// executionPlanRuntime.shutdown();

		// Shutting down Siddhi
		// siddhiManager.shutdown();

	}
}
