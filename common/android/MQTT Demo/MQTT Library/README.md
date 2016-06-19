# Common docs, scripts and code samples for android master, chat app.

Setup Android Studio using the links below


## Android Studio Download link:
https://developer.android.com/studio/index.html

## Android Studio Installation Guide
https://developer.android.com/studio/install.html

## Geny Motion (Android Emulator)
https://www.genymotion.com/

## MQTT Client Library - dependency
1. Add this in your root (Project) level build.gradle, at the end of repositories
	```
	allprojects {
	    repositories {
	        ...
	        maven { url "https://jitpack.io" }
	    }
	}

	```

2. Add this in your app (Module) level build.gradle. at the end of dependencies
	```
	dependencies {
    ...
    compile 'com.github.abhilash1in:SmartCampusMQTT:0.2.0'
	}

	```

3. Sync Gradle files

