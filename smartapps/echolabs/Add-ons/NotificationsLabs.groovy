/* 
 * Profile - EchoSistant Add-on 
 *
 *		12/31/2016		Release 4.1.1	New features: status updates, custom commands, weather alerts, message reminders 
 *										Improvements: streamlined UI and processing
 *
 *  Copyright 2016 Jason Headley & Bobby Dobrescu
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
/**********************************************************************************************************************************************/
definition(
	name			: "NotificationProfile",
    namespace		: "EchoLabs",
    author			: "JH/BD",
	description		: "EchoSistant Add-on",
	category		: "My Apps",
    parent			: "EchoLabs:EchoSistantLabs",
	iconUrl			: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/app-Echosistant.png",
	iconX2Url		: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/app-Echosistant@2x.png",
	iconX3Url		: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/app-Echosistant@2x.png")
/**********************************************************************************************************************************************/


preferences {

    page name: "mainProfilePage"
    		page name: "pNotifyScene"          
        	page name: "pNotifications"
        	page name: "pRestrict"
            page name: "pNotifyConfig"
            page name: "severeWeatherAlertsPage"
            page name: "customSounds"
}

//dynamic page methods
page name: "mainProfilePage"
    def mainProfilePage() {
        dynamicPage (name: "mainProfilePage", install: true, uninstall: true) {
        section ("Activate and Deactivate Notifications"){
            href "pNotifyConfig", title: "Activate and Deactivate Notifications", description: none,
            image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Rest.png"
        	}
        if (!allNotifications) {
        section("") {
        paragraph ("All Notifications have been disabled.  Activate 'All Notifications' to configure this section")
        	}
        }
        if (allNotifications) {
        if (customSounds) {
        section ("Custom Sounds") {
        	href "customSounds", title: "Play these custom sounds as Notifications....", submitOnChange: true, description: "", state: complete 
            	}
            }
        section ("Switches and Dimmers", hideWhenEmpty: true) {
            if (TheSwitch || audioTextOn || audioTextOff || speech1 || push1 || notify1 || music1) paragraph "Configured with Settings"
            if (switchesAndDimmers) {
            input "ShowSwitches", "bool", title: "Switches and Dimmers", default: false, submitOnChange: true
            if (ShowSwitches) {        
                input "TheSwitch", "capability.switch", title: "Choose Switches...", required: false, multiple: true, submitOnChange: true
				input "audioTextOn", "audioTextOn", title: "Play this message", description: "...when the switch turns on", required: false, capitalization: "sentences"
                input "audioTextOff", "audioTextOff", title: "Play this message", description: "...when the switch turns off", required: false, capitalization: "sentences"
	//            href "customSounds", title: "Or, play these custom sounds....", submitOnChange: true, description: "", state: complete 
  //              if (!actionType) {
                input "speech1", "capability.speechSynthesis", title: "Message Player", required: false, multiple: true, submitOnChange: true
                input "music1", "capability.musicPlayer", title: "On this Sonos Type Devices", required: false, multiple: true, submitOnChange: true
                if (music1) {
                    input "volume1", "number", title: "Temporarily change volume", description: "0-100%", required: false
                    input "resumePlaying1", "bool", title: "Resume currently playing music after notification", required: false, defaultValue: false
                	}
                input "sendMsg1", "bool", title: "Send Push and/or Notifications", default: false, submitOnChange: true
                	if (sendMsg1) {
                	input "push1", "bool", title: "Send Push Notification (optional)", required: false, defaultValue: false, submitOnChange: true
            		input "notify1", "bool", title: "Send message to Mobile App Notifications Tab (optional)", required: false, defaultValue: false, submitOnChange: true
            			}
                    }
            	}             
        	}
		}        
        section("Doors and Windows", hideWhenEmpty: true) {
            if (TheContact || audioTextOpen || audioTextClosed || speech2 || push2 || notify2 || music2) paragraph "Configured with Settings"
            if (doorsAndWindows) {
            input "ShowContacts", "bool", title: "Doors and Windows", default: false, multiple: false, submitOnChange: true
            if (ShowContacts) {
                input "TheContact", "capability.contactSensor", title: "Choose Doors and Windows..", required: false, multiple: true, submitOnChange: true
                input "audioTextOpen", "textOpen", title: "Play this message", description: "Message to play when the door opens", required: false, capitalization: "sentences"
                input "audioTextClosed", "textClosed", title: "Play this message", description: "Message to play when the door closes", required: false, capitalization: "sentences"
                input "speech2", "capability.speechSynthesis", title: "Message Player", required: false, multiple: true, submitOnChange: true
            	input "music2", "capability.musicPlayer", title: "On this Sonos Type Devices", required: false, multiple: true, submitOnChange: true
                if (music2) {
                    input "volume2", "number", title: "Temporarily change volume", description: "0-100%", required: false
                    input "resumePlaying2", "bool", title: "Resume currently playing music after notification", required: false, defaultValue: false
                	}
                input "sendMsg2", "bool", title: "Send Push and/or Notifications", default: false, submitOnChange: true
                	if (sendMsg2) {
                	input "push2", "bool", title: "Send Push Notification (optional)", required: false, defaultValue: false, submitOnChange: true
            		input "notify2", "bool", title: "Send message to Mobile App Notifications Tab (optional)", required: false, defaultValue: false, submitOnChange: true
            		}
            	}
        	}
		}     
        section("Locks", hideWhenEmpty: true) {
            if (TheLock || audioTextLocked || audioTextUnlocked || speech3 || push3 || notify3 || music3) paragraph "Configured with Settings"
            if (Locks) {
            input "ShowLocks", "bool", title: "Locks", default: false, submitOnChange: true
            if (ShowLocks) {
                input "TheLock", "capability.lock", title: "Choose Locks...", required: false, multiple: true, submitOnChange: true
                input "audioTextLocked", "textLocked", title: "Play this message", description: "Message to play when the lock locks", required: false, capitalization: "sentences"
                input "audioTextUnlocked", "textUnlocked", title: "Play this message", description: "Message to play when the lock unlocks", required: false, capitalization: "sentences"
                input "speech3", "capability.speechSynthesis", title: "Message Player", required: false, multiple: true, submitOnChange: true
            	input "music3", "capability.musicPlayer", title: "On this Sonos Type Devices", required: false, multiple: true, submitOnChange: true
                if (music3) {
                    input "volume3", "number", title: "Temporarily change volume", description: "0-100%", required: false
                    input "resumePlaying3", "bool", title: "Resume currently playing music after notification", required: false, defaultValue: false
                	}
                input "sendMsg3", "bool", title: "Send Push and/or Notifications", default: false, submitOnChange: true
                	if (sendMsg3) {
                	input "push3", "bool", title: "Send Push Notification (optional)", required: false, defaultValue: false, submitOnChange: true
            		input "notify3", "bool", title: "Send message to Mobile App Notifications Tab (optional)", required: false, defaultValue: false, submitOnChange: true
                	}
            	}
        	}
		}        
        section("Motion Sensors", hideWhenEmpty: true) {
            if (TheMotion || audioTextActive || audioTextInactive || speech4 || push4 || notify4 || music4) paragraph "Configured with Settings"
            if (Motion) {
            input "ShowMotion", "bool", title: "Motion Sensors", default: false,  submitOnChange: true
            if (ShowMotion) {
                input "TheMotion", "capability.motionSensor", title: "Choose Motion Sensors...", required: false, multiple: true, submitOnChange: true
                input "audioTextActive", "textActive", title: "Play this message", description: "Message to play when motion is detected", required: false, capitalization: "sentences"
                input "audioTextInactive", "textInactive", title: "Play this message", description: "Message to play when motion stops", required: false, capitalization: "sentences"
                input "speech4", "capability.speechSynthesis", title: "Message Player", required: false, multiple: true, submitOnChange: true
            	input "music4", "capability.musicPlayer", title: "On this Sonos Type Devices", required: false, multiple: true, submitOnChange: true
                if (music4) {
                    input "volume4", "number", title: "Temporarily change volume", description: "0-100%", required: false
                    input "resumePlaying4", "bool", title: "Resume currently playing music after notification", required: false, defaultValue: false
                	}
                input "sendMsg4", "bool", title: "Send Push and/or Notifications", default: false, submitOnChange: true
                	if (sendMsg4) {
                	input "push4", "bool", title: "Send Push Notification (optional)", required: false, defaultValue: false, submitOnChange: true
            		input "notify4", "bool", title: "Send message to Mobile App Notifications Tab (optional)", required: false, defaultValue: false, submitOnChange: true
                	}	
            	}
        	}
		}        
        section("Presence Sensors", hideWhenEmpty: true) {
        	if (ThePresence || audioTextPresent || audioTextNotPresent || speech5 || push5 || notify5 || music5) paragraph "Configured with Settings"
            if (Presence) {
            input "ShowPresence", "bool", title: "Presence Sensors", default: false, submitOnChange: true
            if (ShowPresence) {
                input "ThePresence", "capability.presenceSensor", title: "Choose Presence Sensors...", required: false, multiple: true, submitOnChange: true
                input "audioTextPresent", "textPresent", title: "Play this message", description: "Message to play when the Sensor arrives", required: false, capitalization: "sentences"
                input "audioTextNotPresent", "textNotPresent", title: "Play this message", description: "Message to play when the Sensor Departs", required: false, capitalization: "sentences"
                input "speech5", "capability.speechSynthesis", title: "Message Player", required: false, multiple: true, submitOnChange: true
                input "music5", "capability.musicPlayer", title: "On this Sonos Type Devices", required: false, multiple: true, submitOnChange: true
                if (music5) {
                    input "volume5", "number", title: "Temporarily change volume", description: "0-100%", required: false
                    input "resumePlaying5", "bool", title: "Resume currently playing music after notification", required: false, defaultValue: false
                	}
                input "sendMsg5", "bool", title: "Send Push and/or Notifications", default: false, submitOnChange: true
                	if (sendMsg5) {
                	input "push5", "bool", title: "Send Push Notification (optional)", required: false, defaultValue: false, submitOnChange: true
            		input "notify5", "bool", title: "Send message to Mobile App Notifications Tab (optional)", required: false, defaultValue: false, submitOnChange: true
            		}
				}
			}
		}         
        section("Thermostats", hideWhenEmpty: true) {
        	if (TheThermostat || audioTextHeating || audioTextCooling || speech8 || push8 || notify8 || music8) paragraph "Configured with Settings"
            if (TStats) {
            input "ShowTstat", "bool", title: "Thermostats", default: false, submitOnChange: true
            if (ShowTstat) {
                input "TheThermostat", "capability.thermostat", title: "Choose Thermostats...", required: false, multiple: true, submitOnChange: true
                input "audioTextHeating", "textHeating", title: "Play this message", description: "Message to play when the Heating Set Point Changes", required: false, capitalization: "sentences"
                input "audioTextCooling", "textCooling", title: "Play this message", description: "Message to play when the Cooling Set Point Changes", required: false, capitalization: "sentences" 
                input "speech8", "capability.speechSynthesis", title: "Message Player", required: false, multiple: true, submitOnChange: true
                input "music8", "capability.musicPlayer", title: "On this Sonos Type Devices", required: false, multiple: true, submitOnChange: true
                if (music8) {
                    input "volume8", "number", title: "Temporarily change volume", description: "0-100%", required: false
                    input "resumePlaying8", "bool", title: "Resume currently playing music after notification", required: false, defaultValue: false
                	}
                input "sendMsg8", "bool", title: "Send Push and/or Notifications", default: false, submitOnChange: true
                	if (sendMsg8) {
                	input "push8", "bool", title: "Send Push Notification (optional)", required: false, defaultValue: false, submitOnChange: true
            		input "notify8", "bool", title: "Send message to Mobile App Notifications Tab (optional)", required: false, defaultValue: false, submitOnChange: true
                    }
                }
            }
		}                
        section("Weather", hideWhenEmpty: true) {
        	if (audioTextWeather || speech9 || push9 || notify9 || music9) paragraph "Configured with Settings"
            if (Weather) {
            	input "showWeather", "bool", title: "Weather Alerts", default: false, submitOnChange: true
                if (showWeather) {
                	input "cWeather", "enum", title: "Choose Weather Alerts...", required: false, multiple: true, submitOnChange: true,
                    options: [
                    "TOR":	"Tornado Warning",
                    "TOW":	"Tornado Watch",
                    "WRN":	"Severe Thunderstorm Warning",
                    "SEW":	"Severe Thunderstorm Watch",
                    "WIN":	"Winter Weather Advisory",
                    "FLO":	"Flood Warning",
                    "WND":	"High Wind Advisoryt",
                    "HEA":	"Heat Advisory",
                    "FOG":	"Dense Fog Advisory",
                    "FIR":	"Fire Weather Advisory",
                    "VOL":	"Volcanic Activity Statement",
                    "HWW":	"Hurricane Wind Warning"
					]
				}
                if (cWeather) {
                    input "audioTextWeather", "text", title: "Play this message", description: "Message to play when a Weather Alert is Issued", required: false, capitalization: "sentences"
                    input "actionType", "enum", title: "OR play one of these sounds", required: false, defaultValue: "Bell 1", options: [
                    "Bell 1",
                    "Bell 2",
                    "Dogs Barking",
                    "Fire Alarm",
                    "Piano",
                    "Lightsaber"]
                    input "speech9", "capability.speechSynthesis", title: "Message Player", required: false, multiple: true, submitOnChange: true
                    input "music9", "capability.musicPlayer", title: "On this Sonos Type Devices", required: false, multiple: true, submitOnChange: true
                    if (music9) {
                        input "volume9", "number", title: "Temporarily change volume", description: "0-100%", required: false
                        input "resumePlaying9", "bool", title: "Resume currently playing music after notification", required: false, defaultValue: false
                        }
                    input "sendMsg9", "bool", title: "Send Push and/or Notifications", default: false, submitOnChange: true
                        if (sendMsg9) {
                        input "push9", "bool", title: "Send Push Notification (optional)", required: false, defaultValue: false, submitOnChange: true
						}
                    }
				}
			}
            section ("Using these Restrictions") {
                href "pRestrict", title: "Use these restrictions... ", 
   				image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Media.png"   			
				}
            section ("Name and/or Remove this Profile") {
 		   	label title:"              Rename Profile ", required:false, defaultValue: "Notification Profile"  
        		
			}
		} 
	}
 
page name: "customSounds"
	def customSounds() {
    	dynamicPage(name: "customSounds", title: "Play custom sounds", uninstall: false) {
        section ("Choose a Device") {
        	input "mySwitch", "capability.switch", title: "Choose Switches...", required: false, multiple: true, submitOnChange: true
            input "myContact", "capability.contactSensor", title: "Choose Doors and Windows..", required: false, multiple: true, submitOnChange: true
            input "myLocks", "capability.lock", title: "Choose Locks..", required: false, multiple: true, submitOnChange: true
            input "myMotion", "capability.motionSensor", title: "Choose Motion Sensors..", required: false, multiple: true, submitOnChange: true
            input "myPresence", "capability.presenceSensor", title: "Choose Presence Sensors...", required: false, multiple: true, submitOnChange: true
            input "myThermostat", "capability.thermostat", title: "Choose Thermostats...", required: false, multiple: true, submitOnChange: true
			
            }
        section ("") {
        input "actionType", "enum", title: "Action?", required: false, defaultValue: "Bell 1", options: [
		//		"Custom Message",
				"Bell 1",
				"Bell 2",
				"Dogs Barking",
				"Fire Alarm",
				"The mail has arrived",
				"A door opened",
				"There is motion",
				"Smartthings detected a flood",
				"Smartthings detected smoke",
				"Someone is arriving",
				"Piano",
				"Lightsaber"]
	//		input "message","text",title:"Play this message", required:false, multiple: false
		}
		section {
        if (actionType) {
			input "sonos", "capability.musicPlayer", title: "On this Sonos type music player", required: false
			input "speechSynth1", "capability.speechSynthesis", title: "Speech Synthesis Device (may not work)", required: false, multiple: true, submitOnChange: true
        		}
//            }
        }
	}
}

page name: "pNotifyConfig"    
    def pNotifyConfig(){
        dynamicPage(name: "pNotifyConfig", title: "Choose from the available Notifications",install: false, uninstall: false) {
            section ("Activate/DeActivate Notifications", hideWhenEmpty: true){
            paragraph "To mute a notification, disable its toggle \n" +
            "To mute all notifications, disable the All Notifications toggle"
            input "allNotifications", "bool", title: "Turn on to Activate the Notifications Section", default: false, submitOnChange: true
            input "customSounds", "bool", title: "Turn on Custom Sounds Notifications", default: false, submitOnChange: true
            input "switchesAndDimmers", "bool", title: "Switches and Dimmers", default: false, submitOnChange: true
            input "doorsAndWindows", "bool", title: "Doors and Windows", default: false, submitOnChange: true
            input "Locks", "bool", title: "Locks", default: false, submitOnChange: true
            input "Motion", "bool", title: "Motion Sensors", default: false, submitOnChange: true
            input "Presence", "bool", title: "Presence Sensors", default: false, submitOnChange: true
            input "TStats", "bool", title: "Thermostats", default: false, submitOnChange: true
            input "Weather", "bool", title: "Weather Alerts", default: false, submitOnChange: true
		}
    }
}    
page name: "pRestrict"
    def pRestrict(){
        dynamicPage(name: "pRestrict", title: "", uninstall: false) {
			section ("Mode Restrictions") {
                input "modes", "mode", title: "Only when mode is", multiple: true, required: false, submitOnChange: true,
                image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Extra.png"
            }        
            section ("Days - Audio only on these days"){	
                input "days", title: "Only on certain days of the week", multiple: true, required: false, submitOnChange: true,
                    "enum", options: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"],
                    image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Extra.png"
            }
            section ("Time - Audio only during these times"){
                href "certainTime", title: "Only during a certain time", description: timeIntervalLabel ?: "Tap to set", state: timeIntervalLabel ? "complete" : null,
                image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Extra.png"
            }   
	    }
	}
page name: "certainTime"
    def certainTime() {
        dynamicPage(name:"certainTime",title: "Only during a certain time", uninstall: false) {
            section("Beginning at....") {
                input "startingX", "enum", title: "Starting at...", options: ["A specific time", "Sunrise", "Sunset"], required: false , submitOnChange: true
                if(startingX in [null, "A specific time"]) input "starting", "time", title: "Start time", required: false, submitOnChange: true
                else {
                    if(startingX == "Sunrise") input "startSunriseOffset", "number", range: "*..*", title: "Offset in minutes (+/-)", required: false, submitOnChange: true
                    else if(startingX == "Sunset") input "startSunsetOffset", "number", range: "*..*", title: "Offset in minutes (+/-)", required: false, submitOnChange: true
                }
            }
            section("Ending at....") {
                input "endingX", "enum", title: "Ending at...", options: ["A specific time", "Sunrise", "Sunset"], required: false, submitOnChange: true
                if(endingX in [null, "A specific time"]) input "ending", "time", title: "End time", required: false, submitOnChange: true
                else {
                    if(endingX == "Sunrise") input "endSunriseOffset", "number", range: "*..*", title: "Offset in minutes (+/-)", required: false, submitOnChange: true
                    else if(endingX == "Sunset") input "endSunsetOffset", "number", range: "*..*", title: "Offset in minutes (+/-)", required: false, submitOnChange: true
                }
            }
        }
    }
/************************************************************************************************************
		
************************************************************************************************************/
def installed() {
	log.debug "Installed with settings: ${settings}"
}
def updated() { 
	log.debug "Updated with settings: ${settings}"
    unsubscribe()
    initialize()
}
def initialize() {
    	subscribeToEvents()
    	subscribe(location, locationHandler)
}    
/************************************************************************************************************
		Subscriptions
************************************************************************************************************/
def subscribeToEvents() {
	if (runModes) {
		subscribe(runMode, location.currentMode, modeChangeHandler)
	}
    if (runDay) {
   		subscribe(runDay, location.day, location.currentDay)
	}
    if (TheSwitch || mySwitch) {
    	if (actionType) {subscribe(mySwitch, "switch.on", alertsHandler)}
        if (actionType) {subscribe(mySwitch, "switch.off", alertsHandler)}
        if (audioTextOn) {subscribe(TheSwitch, "switch.on", alertsHandler)}
        if (audioTextOff) {subscribe(TheSwitch, "switch.off", alertsHandler)}
       }
    if (TheContact || myContact) {
    	if (actionType) {subscribe(myContact, "contact.open", alertsHandler)}
        if (audioTextOpen) {subscribe(TheContact, "contact.open", alertsHandler)}
        else if (audioTextClosed) {subscribe(TheContact, "contact.closed", alertsHandler)}
        }
    if (TheLock || myLocks) {
    	if (actionType) {subscribe(myLocks, "lock.locked", alertsHandler)}
        if (actionType) {subscribe(myLocks, "lock.unlocked", alertsHandler)}
  		if (audioTextLocked) {subscribe(TheLock, "lock.locked", alertsHandler)}
        else if (audioTextUnlocked) {subscribe(TheLock, "lock.unlocked", alertsHandler)}
        }
    if (TheMotion || myMotion) {
    	if (actionType) {subscribe(myMotion, "motion.active", alertsHandler)}
        if (audioTextActive) {subscribe(TheMotion, "motion.active", alertsHandler)}
        else if (audioTextInactive) {subscribe(TheMotion, "motion.inactive", alertsHandler)}
        }
    if (ThePresence || myPresence) {
    	if (actionType) {subscribe(myPresence, "presenceSensor", alertsHandler)}
        if (audioTextPresent || audioTextNotPresent ) {subscribe(ThePresence, "presenceSensor", alertsHandler)}
        }
	if (TheThermostat || myThermostat) {    
        if (actionType) {subscribe(mythermostat, "heatingSetpoint", alertsHandler)}
        if (actionType) {subscribe(mythermostat, "coolingSetpoint", alertsHandler)}
        if (audioTextHeating) {subscribe(TheThermostat, "heatingSetpoint", alertsHandler)}
        else if (audioTextCooling) {subscribe(TheThermostat, "coolingSetpoint", alertsHandler)}
        }
//    if (cWeather) {
//    	subscribe(audioTextWeather, "WeatherAlerts", WeatherAlerts)} 
	loadText()
} 
/***********************************************************************************************************************
    CUSTOM SOUNDS HANDLER
***********************************************************************************************************************/

private takeAction(evt) {

	log.trace "takeAction()"

	if (TheSwitch) {
    	if (speechSynth1) speechSynth1?.playSoundAndTrack(state.sound.uri, state.sound.duration, state.selectedSong)
    	}
        else {
		sonos?.playSoundAndTrack(attention + state.sound.uri, state.sound.duration, state.selectedSong, volume)
	}
	if (resumePlaying){
		sonos.playTrackAndResume(state.sound.uri, state.sound.duration, volume)
	}
	else {
		sonos?.playTrackAndRestore(state.sound.uri, state.sound.duration, volume)
	}

	if (frequency || oncePerDay) {
		state[frequencyKey(evt)] = now()
		}
	
    log.trace "Exiting takeAction()"
}
private loadText() {
	switch ( actionType) {
		case "Bell 1":
			state.sound = [uri: "http://s3.amazonaws.com/smartapp-media/sonos/bell1.mp3", duration: "10"]
			break;
		case "Bell 2":
			state.sound = [uri: "http://s3.amazonaws.com/smartapp-media/sonos/bell2.mp3", duration: "10"]
			break;
		case "Dogs Barking":
			state.sound = [uri: "http://s3.amazonaws.com/smartapp-media/sonos/dogs.mp3", duration: "10"]
			break;
		case "Fire Alarm":
			state.sound = [uri: "http://s3.amazonaws.com/smartapp-media/sonos/alarm.mp3", duration: "17"]
			break;
		case "The mail has arrived":
			state.sound = [uri: "http://s3.amazonaws.com/smartapp-media/sonos/the+mail+has+arrived.mp3", duration: "1"]
			break;
		case "A door opened":
			state.sound = [uri: "http://s3.amazonaws.com/smartapp-media/sonos/a+door+opened.mp3", duration: "1"]
			break;
		case "There is motion":
			state.sound = [uri: "http://s3.amazonaws.com/smartapp-media/sonos/there+is+motion.mp3", duration: "1"]
			break;
		case "Smartthings detected a flood":
			state.sound = [uri: "http://s3.amazonaws.com/smartapp-media/sonos/smartthings+detected+a+flood.mp3", duration: "2"]
			break;
		case "Smartthings detected smoke":
			state.sound = [uri: "http://s3.amazonaws.com/smartapp-media/sonos/smartthings+detected+smoke.mp3", duration: "1"]
			break;
		case "Someone is arriving":
			state.sound = [uri: "http://s3.amazonaws.com/smartapp-media/sonos/someone+is+arriving.mp3", duration: "1"]
			break;
		case "Piano":
			state.sound = [uri: "http://s3.amazonaws.com/smartapp-media/sonos/piano2.mp3", duration: "10"]
			break;
		case "Lightsaber":
			state.sound = [uri: "http://s3.amazonaws.com/smartapp-media/sonos/lightsaber.mp3", duration: "10"]
			break;
		case "Custom Message":
			if (message) {
				state.sound = textToSpeech(message instanceof List ? message[0] : message) // not sure why this is (sometimes) needed)
			}
			else {
				state.sound = textToSpeech("You selected the custom message option but did not enter a message in the $app.label Smart App")
			}
			break;
		default:
			state.sound = [uri: "http://s3.amazonaws.com/smartapp-media/sonos/bell1.mp3", duration: "10"]
			break;
	}
}
/***********************************************************************************************************************
    RESTRICTIONS HANDLER
***********************************************************************************************************************/
private getAllOk() {
	modeOk && daysOk && timeOk
}
private getModeOk() {
    def result = !modes || modes?.contains(location.mode)
	log.debug "modeOk = $result"
    result
} 
private getDayOk() {
    def result = true
if (days) {
		def df = new java.text.SimpleDateFormat("EEEE")
		if (location.timeZone) {
			df.setTimeZone(location.timeZone)
		}
		else {
			df.setTimeZone(TimeZone.getTimeZone("America/New_York"))
		}
		def day = df.format(new Date())
		result = days.contains(day)
	}
	log.debug "daysOk = $result"
	result
}
private getTimeOk() {
	def result = true
	if ((starting && ending) ||
	(starting && endingX in ["Sunrise", "Sunset"]) ||
	(startingX in ["Sunrise", "Sunset"] && ending) ||
	(startingX in ["Sunrise", "Sunset"] && endingX in ["Sunrise", "Sunset"])) {
		def currTime = now()
		def start = null
		def stop = null
		def s = getSunriseAndSunset(zipCode: zipCode, sunriseOffset: startSunriseOffset, sunsetOffset: startSunsetOffset)
		if(startingX == "Sunrise") start = s.sunrise.time
		else if(startingX == "Sunset") start = s.sunset.time
		else if(starting) start = timeToday(starting,location.timeZone).time
		s = getSunriseAndSunset(zipCode: zipCode, sunriseOffset: endSunriseOffset, sunsetOffset: endSunsetOffset)
		if(endingX == "Sunrise") stop = s.sunrise.time
		else if(endingX == "Sunset") stop = s.sunset.time
		else if(ending) stop = timeToday(ending,location.timeZone).time
		result = start < stop ? currTime >= start && currTime <= stop : currTime <= stop || currTime >= start
	if (parent.debug) log.trace "getTimeOk = $result."
    }
    log.debug "timeOk = $result"
    return result
}
private hhmm(time, fmt = "h:mm a") {
	def t = timeToday(time, location.timeZone)
	def f = new java.text.SimpleDateFormat(fmt)
	f.setTimeZone(location.timeZone ?: timeZone(time))
	f.format(t)
}
private offset(value) {
	def result = value ? ((value > 0 ? "+" : "") + value + " min") : ""
}
private timeIntervalLabel() {
	def result = ""
	if      (startingX == "Sunrise" && endingX == "Sunrise") result = "Sunrise" + offset(startSunriseOffset) + " to Sunrise" + offset(endSunriseOffset)
	else if (startingX == "Sunrise" && endingX == "Sunset") result = "Sunrise" + offset(startSunriseOffset) + " to Sunset" + offset(endSunsetOffset)
	else if (startingX == "Sunset" && endingX == "Sunrise") result = "Sunset" + offset(startSunsetOffset) + " to Sunrise" + offset(endSunriseOffset)
	else if (startingX == "Sunset" && endingX == "Sunset") result = "Sunset" + offset(startSunsetOffset) + " to Sunset" + offset(endSunsetOffset)
	else if (startingX == "Sunrise" && ending) result = "Sunrise" + offset(startSunriseOffset) + " to " + hhmm(ending, "h:mm a z")
	else if (startingX == "Sunset" && ending) result = "Sunset" + offset(startSunsetOffset) + " to " + hhmm(ending, "h:mm a z")
	else if (starting && endingX == "Sunrise") result = hhmm(starting) + " to Sunrise" + offset(endSunriseOffset)
	else if (starting && endingX == "Sunset") result = hhmm(starting) + " to Sunset" + offset(endSunsetOffset)
	else if (starting && ending) result = hhmm(starting) + " to " + hhmm(ending, "h:mm a z")
}
/***********************************************************************************************************************
    SMS HANDLER
***********************************************************************************************************************/
private void sendtxt(message) {
    log.debug "Request to send sms received with message: '${message}'"
    if (sendContactText) { 
        sendNotificationToContacts(message, recipients)
            if (parent.debug) log.debug "Sending sms to selected reipients"
    } 
    else {
    	if (push) { 
    		sendPush message
            	log.debug "Sending push message to selected reipients"
        }
    } 
    if (notify) {
        sendNotificationEvent(message)
             	log.debug "Sending notification to mobile app"

    }
    if (sms) {
        sendText(sms, message)
        log.debug "Processing message for selected phones"
	}
}
private void sendText(number, message) {
    if (sms) {
        def phones = sms.split("\\;")
        for (phone in phones) {
            sendSms(phone, message)
            log.debug "Sending sms to selected phones"
        }
    }
}
private txtFormat (message, eDev, eVal) {
    def eTxt = " " 
        if(message) {
        	message = message.replace('$device', "${eDev}")
        	message = message.replace('$action', "${eVal}")
	  	    eTxt = message
        }  	
            log.debug "Processed Alert: ${eTxt} "
    		
            return eTxt
}
/************************************************************************************************************
   Play Sonos Alert
************************************************************************************************************/
def playAlert(message, speaker) {
    state.sound = textToSpeech(message instanceof List ? message[0] : message)
    speaker.playTrackAndResume(state.sound.uri, state.sound.duration, volume)
    log.debug "Sending message: ${message} to speaker: ${speaker}"

}
/************************************************************************************************************
   Alerts Handler
************************************************************************************************************/
def alertsHandler(evt) {
	def eVal = evt.value
    def eName = evt.name
    def eDev = evt.device
    def eTxt = " "
    log.debug "Received event name ${evt.name} with value:  ${evt.value}, from: ${evt.device}"

	if (getDayOk()==true && getModeOk()==true && getTimeOk()==true) {
        if (eVal == "on") {
        		if (audioTextOn) {   
                	eTxt = txtFormat(audioTextOn, eDev, eVal)
                    if (parent.debug) log.debug "Received event: on, playing message:  ${eTxt}"
                    if(speech1) speech1.speak(eTxt)
                    if (music1) {
                        playAlert(eTxt, music1)
                    	}
                    if (push1) sendPush eTxt
					if (notify1) sendNotificationEvent (eTxt)
                }
                else if (actionType) {
                	takeAction(evt)
                    }
        }
        if (eVal == "off") {       
                if (audioTextOff) {
                eTxt = txtFormat(audioTextOff, eDev, eVal)
                log.debug "Received event: off, playing message:  ${eTxt}"
                speech1?.speak(eTxt)
                    if (music1) {
                        playAlert(eTxt, music1)
                    }
                }
                    if (push1) sendPush eTxt
					if (notify1) sendNotificationEvent (eTxt)
             }
        }
        if (eVal == "open") {     
            	if (audioTextOpen) {
               eTxt = txtFormat(audioTextOpen, eDev, eVal)
               log.debug "Received event:open, playing message:  ${eTxt}"
            speech2?.speak(eTxt)
                if (music2) {
                playAlert(eTxt, music2)
				}
                    if (push2) sendPush eTxt
					if (notify2) sendNotificationEvent (eTxt)
					} 
 	           }
            if (eVal == "closed") {           
                	if (audioTextClosed) {
                eTxt = txtFormat(audioTextClosed, eDev, eVal)
                log.debug "Received event closed, playing message:  ${eTxt}"
                speech2?.speak(eTxt)
                if (music2) {
                    playAlert(eTxt, music2)
                    if (push2) sendPush eTxt
					if (notify2) sendNotificationEvent (eTxt)
}
					}
                }
        if 	(eVal == "locked") {         
				if (audioTextLocked) {
                eTxt = txtFormat(audioTextLocked, eDev, eVal)
            speech3?.speak(eTxt)
                    if (music3) {
                        playAlert(eTxt, music3)
 
					} 
                	if (push3) sendPush eTxt
					if (notify3) sendNotificationEvent (eTxt)                    
                }
            }
            if (eVal == "unlocked") {        
                    if (audioTextUnlocked) {
                        eTxt = txtFormat(audioTextUnlocked, eDev, eVal)
                        speech3?.speak(eTxt)
                            if (music3) {
                                playAlert(eTxt, music3)
                                }
                   	if (push3) sendPush eTxt
					if (notify3) sendNotificationEvent (eTxt)                            
                            
                    }
               }
        if (eVal == "active") {         
				log.debug "Received Motion Event but Motion Alerts are turned off"
            		if (audioTextActive) { 
            			eTxt = txtFormat(audioTextActive, eDev, eVal)
            			log.debug "Received event Active, playing message:  ${eTxt}"
            			speech4?.speak(eTxt)
                    		if (music4) {
                        		playAlert(eTxt, music4)
                    		} 
                   	if (push4) sendPush eTxt
					if (notify4) sendNotificationEvent (eTxt)                            
                	}
            }
            if (eVal == "inactive")  {         
				log.debug "Received Motion Event but Motion Alerts are turned off"
					if (audioTextInactive) {
                		eTxt = txtFormat(audioTextInactive, eDev, eVal)
                		log.debug "Received event Inactive, playing message:  ${eTxt}"
                			speech4?.speak(eTxt)
                    		if (music4) {
                        		playAlert(eTxt, music4)
                    		} 
                   	if (push4) sendPush eTxt
					if (notify4) sendNotificationEvent (eTxt)                     	
                        }
                }
        if (eVal == "present") {          
				log.debug "Received Presence Event but Presence Alerts are turned off"
				if (audioTextPresent) {
                eTxt = txtFormat(audioTextPresent, eDev, eVal)        
            log.debug "Received event Present, playing message:  ${eTxt}"
            speech5?.speak(eTxt)
                    if (music5) {
                        playAlert(eTxt, music5)
                    } 
                   	if (push5) sendPush eTxt
					if (notify5) sendNotificationEvent (eTxt) 
}
            }
        if (eVal == "not present")  {        
				log.debug "Received Presence Event but Presence Alerts are turned off"
				if (audioTextNotPresent) {
                eTxt = txtFormat(audioTextNotPresent, eDev, eVal)            
                log.debug "Received event Not Present, playing message:  ${eTxt}"
                speech5?.speak(eTxt)
                    if (music5) {
                        playAlert(eTxt, music5)
                    } 
                   	if (push5) sendPush eTxt
					if (notify5) sendNotificationEvent (eTxt) 
}
                }
        if (eName == "heatingSetpoint")  {                    
            if (audioTextHeating) {
            	def eTemp = eVal
                eTemp = eTemp.replace('.0', " ")
            	eTxt = txtFormat(audioTextHeating, eDev, eTemp)            
            	log.debug "Received event heatingSetpoint, playing message:  ${eTxt}"
                speech8?.speak(eTxt)
                    if (music8) {
                        playAlert(eTxt, music8)
                    } 
                    
                     if (push8) sendPush eTxt
					if (notify8) sendNotificationEvent (eTxt) 
                }
            }

            if (eName == "coolingSetpoint") {
				if (audioTextCooling) {
            		def eTemp = eVal
                	eTemp = eTemp.replace('.0', " ")
                	eTxt = txtFormat(audioTextCooling, eDev, eTemp)            
                	log.debug "Received event coolingSetpoint, playing message:  ${eTxt} "
                	speech8?.speak(eTxt)
                	if (music8) {
                        playAlert(eTxt, music8)
                    }
                     if (push8) sendPush eTxt
					if (notify8) sendNotificationEvent (eTxt) 
                }
            }  
        } 
/************************************************************************************************************
   Severe Weather Alerts Handler
************************************************************************************************************/
def WeatherAlerts(){
	def result ="Weather Alerts Active"
	def WeatherAlerts =  getWeatherFeature("alerts", settings.wZipCode)
		result = ""
        }
page name: "severeWeatherAlertsPage"    
    def severeWeatherAlertsPage(){
        dynamicPage(name: "severeWeatherAlertsPage", title: "Severe Weather Alerts",install: false, uninstall: false) {
		section ("Weather Alerts") {
        	input "WeatherAlertsToggle", "bool", title: "Activate severe weather alerts notifications", required: false, default: false, submitOnChange: true
            if (WeatherAlertsToggle) {
        	(WeatherAlerts())
            	}
        	}        
        section ("Choose which Alerts to Activate and Receive") {
		}
	}
}
