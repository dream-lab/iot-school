__[IISc MSR IoT Summer School](http://research.microsoft.com/en-us/events/msri_ss_2016/)__

# Tutorial Schedule [Tentative]

The tutorials will happen in two tracks: (1) Sensing & Communication, and (2) Software & Analytics, with students partitioned evenly between the two. Some sessions will be jointly held for both the tracks. Joint sessions will be in Room CDS 102. 

Following the tutorials, teams of 4 will be formed with 2 students from each track, and these teams can participate in IoT Hackaton Projects for the duration of the school. Several project topics will be suggested based on available hardware resources, and teams can also come up with their own ideas. The projects will be evaluated on Sat Jun 26.

## Sensing & Communication Track [Room CDS202]
### Day 1, Mon 20 June from 3-630PM
- Joint session of both tracks: Introduction to the Tutorials *(Yogesh, CDS)* [15mins]
- Practical Sensing *(Ashish, RBCCPS)* [60mins]
  * Types of sensors, properties, PCB design, UART
-  Acquiring sensor data *(Akshay and Anand, ECE)* [40 mins]
  * Getting data from Sensor to Pi, and Sensor to Contiki to Pi
  * Hands on: Read data from sensor thru Laptop/Pi and Contiki
- Android and BLE *(Vasant, RBCCPS)* [20mins]
- JouleJotter Power Metering *(Abhirami & Yashaswini, ESE)* [15mins]
- jPLug Power Metering and Nebula Sensor kits *(Tanuja, Data Glen)* [30mins]
- Joint session for tracks: Team formation, project idea discussion *(Yogesh, CDS)* [30mins]

### Day 2, Tue 21 June from 330-630PM
- Joint session of both tracks: Using Pi & Azure VM by teams *(Yogesh, CDS)* [30mins]
- Wireless Networking and Communication standards *(Akshay and Anand, ECE)* [150mins]
  * Power, standards, motes/WSN, 6lowpan, mesh NW
  * IPv6, routing, RPL, CoAP, MQTT
  * Hands on: Transmitting data through sensor network to border router (Pi)


## Software & Analytics Track [Room CDS102]
### Day 1, Mon 20 June from 3-630PM
- Joint session of both tracks: Introduction to the Tutorials *(Yogesh, CDS)* [15mins]
- Connecting to Azure VM & accessing GitHub *(Anshu, CDS)* [30mins]
- IoT Software Fabric & Event-based Architecture *(Vyshak, CDS)* [75mins]
  * COAP, Publish-Subcribe, MQTT & SenML
  * Hands on: Run MQTT Pub-Sub samples from personal laptop or Azure VM
- Complex Event Processing *(Rajrup, CDS)* [60mins]
  * Hands on: Run Siddhi CEP engine samples from personal laptop or Azure VM
- Joint session for tracks: Team formation, project idea discussion *(Yogesh, CDS)* [30mins]

### Day 2, Tue 21 June from 330-630PM
- Joint session of both tracks: Using Pi & Azure VM by teams *(Yogesh, CDS)* [30mins]
- Stream processing using Apache Storm *(Anshu, CDS)* [60 mins]
  * Hands on: Run Apache Storm IoT topology from Azure VM
- Pub-Sub on Android using IISc Notification Platform *(Abhilash, CDS)* [30mins]
  * Hands on: Run chat app from personal Android phone
- Analytics & Visualization Tools *(Rajrup, CDS)* [30mins]
  * R Analytics, Weka, Rickshaw
- Data Glen IoT Platform *(Tanuja, Data Glen)* [30mins]


# Tasks for Participants
- **Before arriving/At start of Day 1**
  - Subscribe to Mailing list: (https://groups.google.com/forum/#!forum/iot_summer_school_2016)
  - Get connected to guest WiFi at IISc (will be announced at opening session/watch the white board outside CDS 102)
  - Have your Laptop with following software installed
    * Git or GitHub client
    * SSH Client supporting proxy (e.g PuttY for Windows, Corkscrew for Linux)
    * SW&A track
      - Java IDE such as Eclipse, IntelliJ
      - Java JDK 7 or later
      - Apache Maven
      - Python
      - R Studio
      - Android Studio (optional)
    * S&C Track
      - Ubuntu OS prefered
      - ...?
  - GitHub
    - Request commit access to summer school GitHub site: https://github.com/dream-lab/iot-school
    - Clone the GitHub common folder to your laptop, https://github.com/dream-lab/iot-school/tree/master/common

- **By start of Day 2**
  * Pick a team (2 from Sensing/Comm, 2 from SW/Analytics) and a team name. 
  * Create a folder with your team name on GitHub under https://github.com/dream-lab/iot-school/tree/master/teams. Create a file "members.txt" in your team folder with the name, affiliation and email address of your team members.
  * Pick up Raspberry Pi and Azure VM account information for your team

- **By end of Day 2**
  * Pick a project topic to work on. Pick up sensors required for your project. Identify mentor for your project.

