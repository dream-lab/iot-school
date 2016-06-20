1. SETUP PROXY:
	a. Put 10proxy file in '/etc/apt/apt.conf.d/' folder. If file is not present create one.
	
	Content: 
		Acquire::http::Proxy "http://proxy.iisc.ernet.in:3128";
		Acquire::https::Proxy "https://proxy.iisc.ernet.in:3128";
		Acquire::ftp::Proxy "ftp://proxy.iisc.ernet.in:3128";
		
	b. 	Change your '.bashrc' file to export proxy. Sample '.bashrc' file has been attached
	
	Append at the end 3 lines:
		export http_proxy="http://proxy.iisc.ernet.in:3128"
		export https_proxy="https://proxy.iisc.ernet.in:3128"
		export ftp_proxy="http://proxy.iisc.ernet.in:3128"
		
2. Setup wifi:
	a. Use the Static IP written in Raspberry Pi Box in the kit provided.
	b. Check the default network settings in '/etc/network/interfaces' file. Sample 'interfaces' file has been attached 
	   Make changes at appropriate places. It should look like this:
		
		# interfaces(5) file used by ifup(8) and ifdown(8)

		# Please note that this file is written to be used with dhcpcd
		# For static IP, consult /etc/dhcpcd.conf and 'man dhcpcd.conf'

		# Include files from /etc/network/interfaces.d:
		source-directory /etc/network/interfaces.d

		auto lo
		iface lo inet loopback

		iface eth0 inet manual

		allow-hotplug wlan0
		iface wlan0 inet manual
			wpa-conf /etc/wpa_supplicant/wpa_supplicant.conf

		allow-hotplug wlan1
		iface wlan1 inet manual
			wpa-conf /etc/wpa_supplicant/wpa_supplicant.conf
		
	b. Append these lines at the end of '/etc/dhcpcd.conf' file. Sample 'dhcpcd.conf' file has been attached
		
		interface wlan0
		inform 10.16.241.200/23				#Replace 10.16.241.200 with the Static IP provided
		static routers=10.16.240.1
		static domain_name_servers=10.16.25.15

		interface eth0
		inform 192.168.1.1/24
		
	c. To setup wifi ssid and password: 
	   Append these lines at the end of '/etc/wpa_supplicant/wpa_supplicant.conf' file. Sample 'wpa_supplicant.conf' file has been attached.

		network={
		ssid="iiscwlan"
		scan_ssid=1
		key_mgmt=WPA-EAP
		eap=PEAP
		identity="cdsiot"
		password="iotss@2016"
		phase1="peapver=0"
		phase2="auth=MSCHAPV2"
	}
	
3. $ ssh pi@<Static IP>
	 password: raspberry
	 
	 or
	 
   $ ssh pi@192.168.1.1     (After connecting Raspberry Pi with lan cable provided)
   	 password: raspberry


	 

		
	
