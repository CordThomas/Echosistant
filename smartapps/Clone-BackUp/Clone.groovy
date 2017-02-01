/* 
 * EchoSistant - The Ultimate Voice and Text Messaging Assistant Using Your Alexa Enabled Device.
 *
 *		1/25/2017		Version:4.0 R.4.2.14		Bug fixes: new Pin Validation process, more bug fixes, removed feedback page
 *		1/20/2017		Version:4.0 R.4.2.13		Bug fixes: minor fixes
 *		1/20/2017		Version:4.0 R.4.2.11		Feature: completed Phase 2 - Basic Device Feedback
 *		1/14/2017		Version:4.0 R.4.2.10		Feature: added garage door relay control 
 *		1/14/2017		Version:4.0 R.4.2.08		Bug fixes: for try again module; added PIN cycle for relays 
 *		1/13/2017		Version:4.0 R.4.2.07		Feature Added: Alexa Feedback and device status 
 *		1/13/2017		Version:4.0 R.4.2.05		Feature: First Alexa Thinks
 *		1/12/2017		Version:4.0 R.4.2.04		Bug fixes: scheduling process
 *		1/12/2017		Version:4.0 R.4.2.03		Bug fixes: pin requests
 *		1/12/2017		Version:4.0 R.4.2.02		Bug fixes: fans, pin,  and non dimmer switches 
 *		1/11/2017		Version:4.0 R.4.2.01		New features: HVAC filters reminders 
 *												Improvements: Alexa responses
 *		12/31/2016		Version:4.0 R.4.1.1		New features: status updates, custom commands, weather alerts, message reminders 
 *												Improvements: streamlined UI and processing 
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
 * Phase 1 - Device Control Module - status COMPLETED
 * Phase 2 - Feedback Module (same app) - status COMPLETED
 * Phase 3 - Text to Speech Module (new add-on app Profile) - in PROGRESS
 * Phase 4 - Scenes Labs (new add-on app Scenes) - not STARTED
 * Phase 5 - Security Module - Parent App - not STARTED
 * Phase 6 - Adding UI (review and test final product)
 *
 *
/**********************************************************************************************************************************************/
definition(
	name			: "EchoSistantLabs",
    namespace		: "EchoLabs",
    author			: "JH/BD",
	description		: "The Ultimate Voice Controlled Assistant Using Alexa Enabled Devices.",
	category		: "My Apps",
    singleInstance	: true,
	iconUrl			: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/app-Echosistant.png",
	iconX2Url		: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/app-Echosistant@2x.png",
	iconX3Url		: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/app-Echosistant@2x.png")
/**********************************************************************************************************************************************/
preferences {   
    page name: "mainParentPage"
    		page name: "mIntent"
            	page name: "mSHMSec"
                	page name: "mSecuritySuite"
				page name: "mDevices"
                page name: "mDefaults"            
    		page name: "mRooms"
            	page name: "mNotifyProfile"
            	page name: "mControlProfile"
            page name: "mSupport"
            page name: "mSettings"
           		page name: "mSkill"
            		page name: "mProfileDetails"
            		page name: "mDeviceDetails" 
                page name: "mTokens"
                    page name: "mConfirmation"            
                    	page name: "mTokenReset"
            page name: "mBonus"
            	page name: "mDashboard"
                	page name: "mDashConfig"
                    page name: "pageTwo"
                    page name: "mWeatherConfig"
                    page name: "severeWeatherAlertsPage" 
}            
//dynamic page methods
page name: "mainParentPage"
    def mainParentPage() {	
       dynamicPage(name: "mainParentPage", title:"", install: true, uninstall:false) {
       		section ("") {
                href "mIntent", title: "Main Home Control",
                	image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/app-Echosistant.png"    
				href "mRooms", title: "Configure Rooms",
                	image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_msg.png"
				href "mSettings", title: "General Settings",
                	image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Config.png"
				href "mSupport", title: "Install and Support",// description: mSupportSettings(), state: mSupportComplete(),
					image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Config.png"                               
			if (activateDashboard) {
				href "mDashboard", title: "Dashboard",// description: dashboardSettings(), state: dashboardComplete(),
					image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Config.png"
				}
				href "mBonus", title: "                    The Current Mode is \n" +
				"					                        ${location.currentMode} \n" +
                     
					"       Smart Home Monitor Status is: ${location.currentState("alarmSystemStatus")?.value}", description: ""
			}
		}
	}
page name: "mIntent"
    def mIntent() {
    	dynamicPage (name: "mIntent", title: "", install: false, uninstall: false) {
			section("Devices that are controlled by EchoSistant") {
	            href "mDevices", title: "Select Devices",
                image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_devices.png"    
			}               
            section ("System and Device Control Defaults") {
                href "mDefaults", title: "Change Defaults",
                image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Extra.png"
			}
            section ("Manage Home Security") {
            	href "mSHMSec", title: "Home Security control options...",//, description: mSHMSecSettings(), state: mSHMSecComplete(),
                image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Extra.png"
            }
    	}            
	}
page name: "mRooms"    
	def mRooms() {	
    	dynamicPage(name: "mRooms", title:"I want to create a...", install: true, uninstall: false) {
        	if (!controlOn && !notifyOn && !feedbackOn && !securityOn) {
        	section ("Where are my Rooms???") {
        	paragraph "You have not installed and/or activated any of the plug in modules! \n" +
        	"Please navigate to the 'Install and Support' section on the main page to activate modules"
        	}
        }
        if (controlOn) {
        	section ("Control and Messaging Profile") {
				href "mControlProfile", title: "View and Create Control and Messaging Profiles...",// description: pCreateSettings(), state: pCreateComplete(),
            	image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_devices.png"            			
            	}
            }
     	if (notifyOn) {
        	section ("Notifications Profile") {
  				href "mNotifyProfile", title: "View and Create Notification Profiles...",// description: pCreateSettings(), state: pCreateComplete(),
            	image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_devices.png"            			
				}
			}
        if (coreOn) {   
            section ("CoRE Pistons") {
            	href "mCoRE", title: "Manage your CoRE Pistons",
               	image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_devices.png"            			
				}
            }
//            section ("Button Controller Plus") {
//            	href "mButton", title: "Program your Button Controllers",
//               	image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_devices.png"            			
//			}
    	}   
	}
page name: "mSupport"  
 def mSupport(){
        dynamicPage(name: "mSupport", uninstall: false) {
        	section ("EchoSistant Modules") {
            	paragraph "For the notifications and room feedback to be operational, they must be installed in the ST IDE and the toggles below must be activated"
            	input "controlOn", "bool", title: "Is the Control & Messaging Module Installed?", required: true, defaultValue: false
                input "notifyOn", "bool", title: "Is the Notifications Module Installed? ", required: true, defaultValue: false
 				input "securityOn", "bool", title: "Is the Security Suite Module Installed?", required: true, defaultValue: false
                input "coreOn", "bool", title: "Is the CoRE Piston Management Module Installed?", required: true, defaultValue: false
                }
                section ("Amazon AWS Skill Details") {
					href "SkillDetails", title: "Tap to view setup data for the AWS Main Intent Skill...", description: "",
                	image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/echosistant_About.png"
            		}
                section ("Directions, How-to's, and Troubleshooting") { 
 					href url:"http://thingsthataresmart.wiki/index.php?title=EchoSistant", title: "Tap to go to the EchoSistant Wiki", description: none,
                	image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/app-Echosistant.png"
                	}   
            	section ("AWS Lambda website") {
            		href url:"https://aws.amazon.com/lambda/", title: "Tap to go to the AWS Lambda Website", description: none,
                	image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_aws.png"
                	}
            	section ("Amazon Developer website") {    
   					href url:"https://developer.amazon.com/", title: "Tap to go to Amazon Developer website", description: none,
                	image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Skills.png"
					}
                section ("Developers", hideWhenEmpty: true){  
            		paragraph ("You can reach out to the Echosistant Developers with the following information: \n" + 
                	"Jason Headley \n"+
                	"Forum user name @bamarayne \n" +
                	"Bobby Dobrescu \n"+
                	"Forum user name @SBDobrescu")
                	}
                }	            	
            }    
page name: "mSHMSec"    
    def mSHMSec(){
        dynamicPage(name: "mSHMSec", title: "Home Security Information via Alexa",install: false, uninstall: false) {
        section ("Configure Security Options for Alexa") {
        	input "cSec", "capability.relaySwitch", title: "Select switch(s) for Alexa SHM control...", multiple: true, required: false, submitOnChange: true
			input "cPIN", "password", title: "Set PIN for ALL Alexa Controlled Controls", default: false, required: false
        	}
        if (securityOn) {    
		section ("Access Security Suite") {
			href "mSecuritySuite", title: "Tap to configure your Home Security Suite module", description: "",
            image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Extra.png"
        	}    
        }        	
		section ("Smart Home Monitor Status Change Feedback", hideable: true, hidden: true) {
			input "fSecFeed", "bool", title: "Activate SHM status change announcements.", default: false, submitOnChange: true
			if (fSecFeed) {    
				input "shmSynthDevice", "capability.speechSynthesis", title: "On this Speech Synthesis Type Devices", multiple: true, required: false,
				image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Media.png"
				input "shmSonosDevice", "capability.musicPlayer", title: "On this Sonos Type Devices", required: false, multiple: true, submitOnChange: true,    
				image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Media.png"
				}
            if (fSecFeed) {
				input "volume", "number", title: "Temporarily change volume", description: "0-100%", required: false
				input "resumePlaying", "bool", title: "Resume currently playing music after notification", required: false, defaultValue: false
				}
			}
        }
    } 
/*page name: "mButton"    
    def mButton() {
        dynamicPage (name: "mButton", title: "", install: true, uninstall: false) {
        	if (childApps.size()) { 
            	section(childApps.size()==1 ? "One Button Controller configured" : childApps.size() + " Room configured" )
            }
            if (childApps.size()) {  
            	section("Rooms",  uninstall: false){
                	app(name: "Buttons", appName: "Button Controller+", namespace: "EchoLabs", title: "Program a new Button Controller", multiple: true,  uninstall: false)
            	}
            }
            else {
            	section("Rooms",  uninstall: false){
            		paragraph "NOTE: Looks like you haven't created any Rooms yet.\n \nPlease make sure you have installed the Rooms Smart App Add-on before creating a new Room!"
            		app(name: "Buttons", appName: "Button Controller+", namespace: "EchoLabs", title: "Program a new Button Controller", multiple: true,  uninstall: false)
        		}
            }
       }
} */
page name: "mCoRE"    
    def mCoRE() {
        dynamicPage (name: "mCoRE", title: "", install: true, uninstall: false) {
        	if (childApps.size()) { 
            	section(childApps.size()==1 ? "One Room configured" : childApps.size() + " Room configured" )
            }
            if (childApps.size()) {  
            	section("Rooms",  uninstall: false){
                	app(name: "Pistons", appName: "CoRE", namespace: "EchoLabs", title: "Create a new piston", multiple: true,  uninstall: false)
            	}
            }
            else {
            	section("Rooms",  uninstall: false){
            		paragraph "NOTE: Looks like you haven't created any Rooms yet.\n \nPlease make sure you have installed the Rooms Smart App Add-on before creating a new Room!"
            		app(name: "Pistons", appName: "CoRE", namespace: "EchoLabs", title: "Create a new piston", multiple: true,  uninstall: false)
        		}
            }
       }
}       
page name: "mControlProfile"    
    def mControlProfile() {
        dynamicPage (name: "mControlProfile", title: "", install: true, uninstall: false) {
        	if (childApps.size()) { 
            	section(childApps.size()==1 ? "One Room configured" : childApps.size() + " Room configured" )
            }
            if (childApps.size()) {  
            	section("Rooms",  uninstall: false){
                	app(name: "rooms", appName: "MessagingAndControl", namespace: "EchoLabs", title: "Create a new Room", multiple: true,  uninstall: false)
            	}
            }
            else {
            	section("Rooms",  uninstall: false){
            		paragraph "NOTE: Looks like you haven't created any Rooms yet.\n \nPlease make sure you have installed the Rooms Smart App Add-on before creating a new Room!"
            		app(name: "room", appName: "MessagingAndControl", namespace: "EchoLabs", title: "Create a new Room", multiple: true,  uninstall: false)
        		}
            }
       }
}   
page name: "mNotifyProfile"    
    def mNotifyProfile() {
        dynamicPage (name: "mNotifyProfile", title: "", install: true, uninstall: false) {
        	if (childApps.size()) { 
            	section(childApps.size()==1 ? "One Room configured" : childApps.size() + " Room configured" )
            }
            if (childApps.size()) {  
            	section("Notification Profile",  uninstall: false){
                	app(name: "profile", appName: "NotificationProfile", namespace: "EchoLabs", title: "Create a new Notifications Profile", multiple: true,  uninstall: false)
            	}
            }
            else {
            	section("Profiles",  uninstall: false){
            		paragraph "NOTE: Looks like you haven't created any Profiles yet.\n \nPlease make sure you have installed the Rooms Smart App Add-on before creating a new Room!"
            		app(name: "profile", appName: "NotificationProfile", namespace: "EchoLabs", title: "Create a new Notifications Profile", multiple: true,  uninstall: false)
        		}
            }
       }
	}
page name: "mSecuritySuite"    
    def mSecuritySuite() {
        dynamicPage (name: "mSecuritySuite", title: "", install: true, uninstall: false) {
        	if (childApps.size()) { 
            	section(childApps.size()==1 ? "Security configured" : childApps.size() + " Room configured" )
            }
            if (childApps.size()) {  
            	section("Security Suite",  uninstall: false){
                	app(name: "profile", appName: "SecuritySuite", namespace: "EchoLabs", title: "Configure Security Suite", multiple: false,  uninstall: false)
            	}
            }
            else {
            	section("Profiles",  uninstall: false){
            		paragraph "NOTE: Looks like you haven't created any Profiles yet.\n \nPlease make sure you have installed the Rooms Smart App Add-on before creating a new Room!"
            		app(name: "profile", appName: "SecuritySuite", namespace: "EchoLabs", title: "Configure Security Suite", multiple: false,  uninstall: false)
        		}
            }
       }
	}    

page name: "mDevices"    
        def mDevices(){
            dynamicPage(name: "mDevices", title: "",install: false, uninstall: false) {
                // DEVICE categories: cSwitch,cVent,cFan,cTstat,cDoor,cRelay,cContactRelay,cLock,cMotion,cContact,cWater,cPresence,cSpeaker,cSynth,cMedia,cBattery 
                section ("Select devices", hideWhenEmpty: true){ }
                section ("Lights and Switches", hideWhenEmpty: true){  
                    input "cSwitch", "capability.switch", title: "Allow These Switch(es)...", multiple: true, required: false, submitOnChange: true
                    input "cVent", "capability.switchLevel", title: "Allow These Smart Vent(s)...", multiple: true, required: false
                    input "cFan", "capability.switchLevel", title: "Allow These Fan(s)...", multiple: true, required: false
                }
                section ("PIN Protected Devices (Voice Activated Setting)" , hideWhenEmpty: true) {
                    input "cTstat", "capability.thermostat", title: "Allow These Thermostat(s)...", multiple: true, required: false, submitOnChange: true
                    if (cTstat) {input "uPIN_T", "bool", title: "Use PIN to control Thermostats?", default: false}
                    input "cDoor", "capability.garageDoorControl", title: "Allow These Garage Door(s)...", multiple: true, required: false, submitOnChange: true
                    input "cRelay", "capability.switch", title: "Allow These Garage Door Relay(s)...", multiple: false, required: false, submitOnChange: true
                    if (cRelay) input "cContactRelay", "capability.contactSensor", title: "Allow This Contact Sensor to Monitor the Garage Door Relay(s)...", multiple: false, required: false
                    if (cDoor || cRelay) {input "uPIN_D", "bool", title: "Use PIN to control Doors?", default: false}  
                    input "cLock", "capability.lock", title: "Allow These Lock(s)...", multiple: true, required: false, submitOnChange: true
                    if (cLock) {input "uPIN_L", "bool", title: "Use PIN to control Locks?", default: false}
                	input "cMiscDev", "capability.switch", title: "Allow these Misc. Switches...", multiple: true, required: false, submitOnChange: true
                    if (cMiscDev) paragraph "All devices chosen here require PIN for all (on/off) operations. Commands are 'Activate' & 'Deactivate'"
                } 
                section ("Sensors", hideWhenEmpty: true) {
                 	input "cMotion", "capability.motionSensor", title: "Allow These Motion Sensor(s)...", multiple: true, required: false
                    input "cContact", "capability.contactSensor", title: "Allow These Contact Sensor(s)...", multiple: true, required: false      
            		input "cWater", "capability.waterSensor", title: "Allow These Water Sensor(s)...", multiple: true, required: false                       
                    input "cPresence", "capability.presenceSensor", title: "Allow These Presence Sensors(s)...", multiple: true, required: false
                }
                section ("Media" , hideWhenEmpty: true){
                    input "cSpeaker", "capability.musicPlayer", title: "Allow These Media Player Type Device(s)...", required: false, multiple: true
                    input "cSynth", "capability.speechSynthesis", title: "Allow These Speech Synthesis Capable Device(s)", multiple: true, required: false
                    input "cMedia", "capability.mediaController", title: "Allow These Media Controller(s)", multiple: true, required: false
                } 
                section ("Batteries", hideWhenEmpty: true ){
                    input "cBattery", "capability.battery", title: "Allow These Device(s) with Batteries...", required: false, multiple: true
                } 
				if (showAdvanced) {	
                    href "mCustomDevices", title: "Create custom devices and commands",// description: devicesControlCustomDescr() , state: completeDevicesControlCustom(),
                    image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Plus.png"                    
            		}
				}
    		} 
page name: "mCustomDevices"    
    def mCustomDevices(){
        dynamicPage(name: "mCustomDevices", title: "Create Devices That Alexa Can Control Directly",install: false, uninstall: false) {
            section ("Create a Device", hideWhenEmpty: true){
                input "custSwitch1", "capability.switch", title: "Select Device...", multiple: false, required: false, submitOnChange: true
                input "custName1", "text", title: "Name Device...", multiple: false, required: false
				if(custSwitch1) {                           
                        def availableCommands = custSwitch1.capabilities                      
                    	availableCommands.sort()
                        if (debug) log.debug "availableCommands = $availableCommands"
                        paragraph "Add any of these commands to your LIST_OF_COMMANDS custom slot: $availableCommands"
    			}
            }
            if (custSwitch1) {
                section ("+ Create another Device", hideWhenEmpty: true){
                    input "custSwitch2", "capability.switch", title: "Select Device...", multiple: false, required: false, submitOnChange: true
                    input "custName2", "text", title: "Name Device...", multiple: false, required: false
                    if(custSwitch2) {
						def availableCommands = custSwitch2.supportedCommands                       
                    	availableCommands.sort()
                        if (debug) log.debug "availableCommands = $availableCommands"
                        paragraph "Add any of these commands to your LIST_OF_COMMANDS custom slot: $availableCommands"
    				} 
                }
            }
            if (custSwitch2) {
                section ("+ Create another Device", hideWhenEmpty: true){
                    input "custSwitch3", "capability.switch", title: "Select Device...", multiple: false, required: false, submitOnChange: true
                    input "custName3", "text", title: "Name Device...", multiple: false, required: false
                    if(custSwitch3) {
						def availableCommands = custSwitch2.supportedCommands                       
                    	availableCommands.sort()
                        if (debug) log.debug "availableCommands = $availableCommands"
                        paragraph "Add any of these commands to your LIST_OF_COMMANDS custom slot: $availableCommands"
    				}
                }
            }
            if (custSwitch3) {
                section ("+ Create another Device", hideWhenEmpty: true){
                    input "custSwitch4", "capability.switch", title: "Select Device...", multiple: false, required: false, submitOnChange: true
                    input "custName4", "text", title: "Name Device...", multiple: false, required: false
                    if(custSwitch4) {
						def availableCommands = custSwitch2.supportedCommands                       
                    	availableCommands.sort()
                        if (debug) log.debug "availableCommands = $availableCommands"
                        paragraph "Add any of these commands to your LIST_OF_COMMANDS custom slot: $availableCommands"
    				}
                }
            }
            if (custSwitch4) {            
                section ("+ Create another Device", hideWhenEmpty: true){
                    input "custSwitch5", "capability.switch", title: "Select Device...", multiple: false, required: false, submitOnChange: true
                    input "custName5", "text", title: "Name Device...", multiple: false, required: false
                    if(custSwitch5) {
						def availableCommands = custSwitch2.supportedCommands                       
                    	availableCommands.sort()
                        if (debug) log.debug "availableCommands = $availableCommands"
                        paragraph "Add any of these commands to your LIST_OF_COMMANDS custom slot: $availableCommands"
    				}
                }
            }
        }
    }     
page name: "mDefaults"
    def mDefaults(){
		dynamicPage(name: "mDefaults", title: "", uninstall: false){
	section ("General Control") {            
		input "cLevel", "number", title: "Alexa Adjusts Light Levels by using a scale of 1-10 (default is +/-3)", defaultValue: 3, required: false
		input "cVolLevel", "number", title: "Alexa Adjusts the Volume Level by using a scale of 1-10 (default is +/-2)", defaultValue: 2, required: false
		input "cTemperature", "number", title: "Alexa Automatically Adjusts temperature by using a scale of 1-10 (default is +/-1)", defaultValue: 1, required: false
		input "cFilterReplacement", "number", title: "Alexa Automatically Schedules HVAC Filter Replacement in this number of days (default is 90 days)", defaultValue: 90, required: false                    
		}
	section ("Fan Control") {            
		input "cHigh", "number", title: "Alexa Adjusts High Level to 99% by default", defaultValue: 99, required: false
		input "cMedium", "number", title: "Alexa Adjusts Medium Level to 66% by default", defaultValue: 66, required: false
		input "cLow", "number", title: "Alexa Adjusts Low Level to 33% by default", defaultValue: 33, required: false
		input "cFanLevel", "number", title: "Alexa Automatically Adjusts Ceiling Fans by using a scale of 1-100 (default is +/-33%)", defaultValue: 33, required: false
		}
	section ("Activity Defaults") {            
		input "cLowBattery", "number", title: "Alexa Provides Low Battery Feddback when the Bettery Level falls below (default is 25%)", defaultValue: 25, required: false
		input "cInactiveDev", "number", title: "Alexa Provides Inactive Device Feddback when No Activity was Detected for (default is 24 hours) ", defaultValue: 24, required: false
		}
	}
}
page name: "mSettings"  
	def mSettings(){
        dynamicPage(name: "mSettings", uninstall: true) {
                section("Debugging") {
                    input "debug", "bool", title: "Enable Debug Logging", default: false, submitOnChange: true 
                    }
                section ("Apache License"){
                    input "ShowLicense", "bool", title: "Show License", default: false, submitOnChange: true
//                    def msg = textLicense()
//                        if (ShowLicense) paragraph "${msg}"
                    }
                section ("Show Security Tokens") {
                	paragraph ("Log into the IDE on your computer and navigate to the Live Logs tab. Leave that window open, come back here, and open this section")
                    input "ShowTokens", "bool", title: "Show Security Tokens", default: false, submitOnChange: true
                    if (ShowTokens) paragraph "The Security Tokens are now displayed in the Live Logs section of the IDE"
    				if (ShowTokens) log.trace "STappID = '${app.id}' , STtoken = '${state.accessToken}'"
                    if (ShowTokens) paragraph 	"Access token:\n"+
                                                "${state.accessToken}\n"+
                                                "Application ID:\n"+
                                                "${app.id}"
                    }
               section ("Revoke/Renew Access Token & Application ID"){
                    href "tokens", title: "Revoke/Reset Security Access Token", description: none
                    def msg = state.accessToken != null ? state.accessToken : "Could not create Access Token. OAuth may not be enabled. "+
                    "Go to the SmartApp IDE settings to enable OAuth."	
					}
                section("Tap below to remove the ${textAppName()} application.  This will remove ALL Profiles and the App from the SmartThings mobile App."){
                }	
			}             
		}
page name: "SkillDetails"
    def SkillDetails(){
            dynamicPage(name: "SkillDetails", uninstall: false) {
 			section ("List of Profiles") { 
				href "ProfileDetails", title: "View your List of Profiles for copy & paste to the AWS Skill...", description: "", state: "complete", 
                image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/echosistant_About.png"
            }
            section ("List of Devices") {
				href "DeviceDetails", title: "View your List of Devices for copy & paste to the AWS Skill...", description: "", state: "complete", 
                image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/echosistant_About.png"            	
				}
            }
        }         
page name: "ProfileDetails"
    def ProfileDetails(){
            dynamicPage(name: "ProfileDetails", uninstall: false) {
 			section ("List of Profiles") { 
                def ProfileList = getProfileDetails()   
                    paragraph ("${ProfileList}")
                      	log.info "${ProfileList} "
                        }
					}
				} 
page name: "DeviceDetails"
    def DeviceDetails(){
            dynamicPage(name: "DeviceDetails", uninstall: false) {
 			section ("List of Devices") { 
                def DeviceList = getDeviceDetails()   
                    paragraph ("${DeviceList}")
                      	log.info "${DeviceList} "
                        }
					}
                }    
page name: "tokens"
	def tokens(){
            dynamicPage(name: "tokens", title: "Security Tokens", uninstall: false){
                section(""){
                    paragraph "Tap below to Reset/Renew the Security Token. You must log in to the IDE and open the Live Logs tab before tapping here. "+
                    "Copy and paste the displayed tokens into your Amazon Lambda Code."
                    if (!state.accessToken) {
                        OAuthToken()
                        paragraph "You must enable OAuth via the IDE to setup this app"
                        }
                    }
                        def msg = state.accessToken != null ? state.accessToken : "Could not create Access Token. "+
                        "OAuth may not be enabled. Go to the SmartApp IDE settings to enable OAuth." 
                        log.trace "STappID = '${app.id}' , STtoken = '${state.accessToken}'"
                section ("Reset Access Token / Application ID"){
                    href "pageConfirmation", title: "Reset Access Token and Application ID", description: none
                    }
                }
            } 
page name: "pageConfirmation"
    def pageConfirmation(){
            dynamicPage(name: "pageConfirmation", title: "Reset/Renew Access Token Confirmation", uninstall: false){
                section {
                    href "pageReset", title: "Reset/Renew Access Token", description: "Tap here to confirm action - READ WARNING BELOW"
                    paragraph "PLEASE CONFIRM! By resetting the access token you will disable the ability to interface this SmartApp with your Amazon Echo."+
                    "You will need to copy the new access token to your Amazon Lambda code to re-enable access." +
                    "Tap below to go back to the main menu with out resetting the token. You may also tap Done above."
                    }
                section(" "){
                    href "mainParentPage", title: "Cancel And Go Back To Main Menu", description: none 
                    }
                }
            }
page name: "pageReset"
    def pageReset(){
            dynamicPage(name: "pageReset", title: "Access Token Reset", uninstall: false){
                section{
                    revokeAccessToken()
                    state.accessToken = null
                    OAuthToken()
                    def msg = state.accessToken != null ? "New access token:\n${state.accessToken}\n\n" : "Could not reset Access Token."+
                    "OAuth may not be enabled. Go to the SmartApp IDE settings to enable OAuth."
                    paragraph "${msg}"
                    paragraph "The new access token and app ID are now displayed in the Live Logs tab of the IDE."
                    log.info "STappID = '${app.id}' , STtoken = '${state.accessToken}'"
                }
                section(" "){ 
                    href "mainParentPage", title: "Tap Here To Go Back To Main Menu", description: none 
                    }
                }
            }
page name: "mBonus"    
    def mBonus(){
        dynamicPage(name: "mBonus", title: "EchoSistant Bonus Features",install: false, uninstall: false) {
        section ("Home Status Dashboard") {
        	input "activateDashboard", "bool", title: "Activate the DashBoard on the Home Page", required: false, default: false, submitOnChange: true
        	}
        if (activateDashboard) {
		section ("Configure the DashBoard") {
        	href "mDashConfig", title: "Tap here to configure Dashboard", description: "", state: complete,
            image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Routines.png"
			}
        }
	}
}        
page name: "mDashboard"
	def mDashboard(){
        dynamicPage(name: "mDashboard", uninstall: false) {
        if (ActivateLocalWeather) {
        section("Today's Weather"){
        	paragraph (mGetWeather())
    		}
		}
        section ("ThermoStats and Temperature") {
        	def tStat1 = ThermoStat1
            def temp1 = (tStat1?.currentValue("temperature"))
            def setPC1 = (tStat1?.currentValue("coolingSetpoint"))
            def setPH1 = (tStat1?.currentValue("heatingSetpoint"))
            def mode1 = (tStat1?.currentValue("thermostatMode"))
            def oper1 = (tStat1?.currentValue("thermostatOperatingState"))
            def tStat2 = ThermoStat2
            def temp2 = (tStat2?.currentValue("temperature"))
            def setPC2 = (tStat2?.currentValue("coolingSetpoint"))
            def setPH2 = (tStat2?.currentValue("heatingSetpoint"))
            def mode2 = (tStat2?.currentValue("thermostatMode"))
            def oper2 = (tStat2?.currentValue("thermostatOperatingState"))
		if ("${mode1}" == "auto") 
        	paragraph "The ${tStat1} is ${temp1}°. The thermostat is in ${mode1} mode, the heat is set to ${setPH1}°, the cooling is set to ${setPC1}°, and it is currently ${oper1}."
        if ("${mode1}" == "cool")
            paragraph "The ${tStat1} is ${temp1}°. The thermostat is set to ${setPC1}°, is in ${mode1} mode and is currently ${oper1}."
        if ("${mode1}" == "heat")
            paragraph "The ${tStat1} is ${temp1}°. The thermostat is set to ${setPH1}°, is in ${mode1} mode and is currently ${oper1}."
        if ("${mode1}" == "off")
        	paragraph "The ${tStat1} thermostat is currently ${mode1}" 
		if ("${mode2}" == "auto") 
        	paragraph "The ${tStat2} is ${temp2}°. The thermostat is in ${mode2} mode, the heat is set to ${setPH2}°, the cooling is set to ${setPC2}°, and it is currently ${oper2}."
        if ("${mode2}" == "cool")
            paragraph "The ${tStat2} is ${temp2}°. The thermostat is set to ${setPC2}°, is in ${mode2} mode and is currently ${oper2}."
        if ("${mode2}" == "heat")
            paragraph "The ${tStat2} is ${temp2}°. The thermostat is set to ${setPH2}°, is in ${mode2} mode and is currently ${oper2}."
        if ("${mode2}" == "off")
        	paragraph "The ${tStat2} thermostat is currently ${mode2}" 
		}
		section ("Temperature Sensors") {
        	def Sens1temp = (tempSens1?.currentValue("temperature"))
            def Sens2temp = (tempSens2?.currentValue("temperature"))
            def Sens3temp = (tempSens3?.currentValue("temperature"))
            def Sens4temp = (tempSens4?.currentValue("temperature"))
            def Sens5temp = (tempSens5?.currentValue("temperature"))
            if (tempSens1)
            	paragraph "The temperature of the ${tempSens1} is ${Sens1temp}°."
            if (tempSens2)
            	paragraph "The temperature of the ${tempSens2} is ${Sens2temp}°."
            if (tempSens3)
            	paragraph "The temperature of the ${tempSens3} is ${Sens3temp}°."
            if (tempSens4)
            	paragraph "The temperature of the ${tempSens4} is ${Sens4temp}°."
            if (tempSens5)
            	paragraph "The temperature of the ${tempSens5} is ${Sens5temp}°."
			}
		} 
	} 
page name: "mDashConfig"
	def mDashConfig(){
        dynamicPage(name: "mDashConfig", uninstall: false) {
        section ("Local Weather") {
        	input "ActivateLocalWeather", "bool", title: "Display local weather conditions on Dashboard", required: false, default: false, submitOnChange: true
            }
        if (ActivateLocalWeather) {
		section ("Local Weather Information") {
            href "mWeatherConfig", title: "Tap here to configure Weather information on Dashboard", description: "", state: complete,
            image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Routines.png"
			}	
        }            
		section ("Thermoststats") {
        	input "ThermoStat1", "capability.temperatureMeasurement", title: "First ThermoStat", required: false, default: false, submitOnChange: true 
        	input "ThermoStat2", "capability.temperatureMeasurement", title: "Second ThermoStat", required: false, default: false, submitOnChange: true 
        	//input "ThermoStat3", "capability.temperatureMeasurement", title: "Other Thermostats (not displayed on Dashboard)", required: false, multiple: true 
            }
        section ("Temperature Sensors") {
        	input "tempSens1", "capability.temperatureMeasurement", title: "First Temperature Sensor", required: false, default: false, submitOnChange: true 
            input "tempSens2", "capability.temperatureMeasurement", title: "Second Temperature Sensor", required: false, default: false, submitOnChange: true 
            input "tempSens3", "capability.temperatureMeasurement", title: "Third Temperature Sensor", required: false, default: false, submitOnChange: true 
            input "tempSens4", "capability.temperatureMeasurement", title: "Fourth Temperature Sensor", required: false, default: false, submitOnChange: true 
            input "tempSens5", "capability.temperatureMeasurement", title: "Fifth Temperature Sensor", required: false, default: false, submitOnChange: true 
            //input "tempSens6", "capability.temperatureMeasurement", title: "Other Temperature Sensors (not displayed on Dashboard)", required: false, multiple: true 
        }
    }
}
def mWeatherConfig() {
	dynamicPage(name: "mWeatherConfig", title: "Weather Settings") {
		section {
    		input "wImperial", "bool", title: "Report Weather In Imperial Units\n(°F / MPH)", defaultValue: "true", required: "false"
            input "wZipCode", "text", title: "Zip Code (If Location Not Set)", required: "false"
            paragraph("Currently forecast is automatically pulled from getWeatherFeature your location must be set in your SmartThings app for this to work.")
		}
	}
}
def mGetWeather(){
	def result ="Today's weather is unavailable"
	try {
    	def weather = getWeatherFeature("forecast", settings.wZipCode)
        if(settings.wImperial){
			result = "Today's forecast is " + weather.forecast.txt_forecast.forecastday[0].fcttext  + " Tonight it will be " + weather.forecast.txt_forecast.forecastday[1].fcttext 
            }else
        	{
    		result = "Today's forecast is " + weather.forecast.txt_forecast.forecastday[0].fcttext_metric + " Tonight it will be " + weather.forecast.txt_forecast.forecastday[1].fcttext_metric
	    }
		return result
	}
	catch (Throwable t) {
		log.error t
        return result
	}
}
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
/*************************************************************************************************************
   CREATE INITIAL TOKEN
************************************************************************************************************/
def OAuthToken(){
	try {
		createAccessToken()
		log.debug "Creating new Access Token"
	} catch (e) {
		log.error "Access Token not defined. OAuth may not be enabled. Go to the SmartApp IDE settings to enable OAuth."
	}
}
/*************************************************************************************************************
   LAMBDA DATA MAPPING
************************************************************************************************************/
mappings {
	path("/b") { action: [GET: "processBegin"] }
	path("/c") { action: [GET: "controlDevices"] }
	path("/f") { action: [GET: "feedbackHandler"] }
    path("/p") { action: [GET: "controlProfiles"] }
    path("/r") { action: [GET: "incomingResponse"] }    
	path("/t") { action: [GET: "processTts"] }
}
/************************************************************************************************************
		Base Process
************************************************************************************************************/
def installed() {
	if (debug) log.debug "Installed with settings: ${settings}"
}
def updated() { 
	if (debug) log.debug "Updated with settings: ${settings}"
    unsubscribe()
    initialize()
}
def initialize() {
	// Other stuff
    	 	log.debug "there are ${childApps.size()} child smartapps"
    		childApps.each {child ->
        	log.debug "child app: ${child.label}"
    		}
        	sendLocationEvent(name: "echoSistant", value: "refresh", data: [profiles: getProfileList()] , isStateChange: true, descriptionText: "echoSistant Profile list refresh")
        	def children = getChildApps()
    		if (debug) log.debug "Refreshing Profiles for CoRE, ${getChildApps()*.label}"
			if (!state.accessToken) {
        	if (debug) log.error "Access token not defined. Attempting to refresh. Ensure OAuth is enabled in the SmartThings IDE."
                OAuthToken()
			}            
    //SHM status change and keypad initialize
    		subscribe(location, locationHandler)
    		subscribe(location, "alarmSystemStatus",alarmStatusHandler)//used for ES speaker feedback
			def event = [name:"alarmSystemStatus", value: location.currentState("alarmSystemStatus").value, 
			displayed: true, description: "System Status is ${shmState}"]
     //State Vriables            
            state.lastMessage = null
            state.lastIntent  = null
            state.lastTime  = null
            state.lambdaReleaseTxt = "Not Set"
            state.lambdaReleaseDt = "Not Set" 
            state.lambdatextVersion = "Not Set"
            state.weatherAlert = "There are no weather alerts for your area"
        //Alexa Responses
			state.pTryAgain = false
        	state.pContCmds = true
            state.pMuteAlexa = false
			state.pContCmdsR = "init"       
        //PIN Settings
            state.usePIN_T = settings.uPIN_T
            state.usePIN_L = settings.uPIN_L
            state.usePIN_D = settings.uPIN_D
            state.usePIN_S = false
            state.savedPINdata = null
            state.pinTry = null
        //Other Settings
			state.changedFilters
            state.scheduledHandler
            state.filterNotif
            state.lastAction = null
	}
/************************************************************************************************************
		CoRE Integration
************************************************************************************************************/
def getProfileList(){
		return getChildApps()*.label
		if (debug) log.debug "Refreshing Profiles for CoRE, ${getChildApps()*.label}"
}
def childUninstalled() {
	if (debug) log.debug "Profile has been deleted, refreshing Profiles for CoRE, ${getChildApps()*.label}"
    sendLocationEvent(name: "echoSistant", value: "refresh", data: [profiles: getProfileList()] , isStateChange: true, descriptionText: "echoSistant Profile list refresh")
} 
/************************************************************************************************************
		Begining Process - Lambda via page b
************************************************************************************************************/
def processBegin(){
    if (debug) log.debug "^^^^____Initial Commands Received from Lambda___^^^^"  
    def versionTxt  = params.versionTxt 		
    def versionDate = params.versionDate
    def releaseTxt = params.releaseTxt
    def event = params.intentResp
        state.lambdaReleaseTxt = releaseTxt
        state.lambdaReleaseDt = versionDate
        state.lambdatextVersion = versionTxt
    def versionSTtxt = textVersion() 
    def pPendingAns = false    
    def String outputTxt = (String) null 

	try {

    if (event == "noAction") {//event == "AMAZON.NoIntent" removed 1/20/17
    	state.pinTry = null
        state.savedPINdata = null
        state.pContCmdsR = null // added 1/20/2017
        state.pTryAgain = false
    }
    if (event == "AMAZON.NoIntent"){
    	if(state.pContCmdsR == "level"){
            if (state.lastAction != null) {
                def savedData = state.lastAction
                //getCustomCmd(ctCommand, ctUnit, ctGroup)
                outputTxt = controlHandler(savedData) 
                pPendingAns = "level"
            }
            else {
                state.pContCmdsR = null
                pPendingAns = null
            }
        }
        if( state.pContCmdsR == "door"){
            if (state.lastAction != null) {
                state.lastAction = null
                state.pContCmdsR = null 
                pPendingAns = null 
            }
        }
        if( state.pContCmdsR == "feedback" ||  state.pContCmdsR == "bat" || state.pContCmdsR == "act" ){
            if (state.lastAction != null) {
                state.lastAction = null
                state.pContCmdsR = null 
                pPendingAns = null 
            }
        }
        if( state.pContCmdsR == "init"){
        	state.pTryAgain = false
        }
        if( state.pContCmdsR == null){
        	state.pTryAgain = false
        }
    }
    if (event == "AMAZON.YesIntent") {
        if (state.pContCmdsR == "level") {
            state.pContCmdsR = null
            state.lastAction = null
            pPendingAns = "level"
        }
        else {
        	state.pTryAgain = false
        }
        if( state.pContCmdsR == "door"){
            if (state.lastAction != null) {
                def savedData = state.lastAction
 				//PIN VALIDATION
 				if(state.usePIN_D == true) {
     				//RUN PIN VALIDATION PROCESS
                	def pin = "undefined"
               		def command = "validation"
                	def num = 0
                	def unit = "doors"
                	outputTxt = pinHandler(pin, command, num, unit)
                    pPendingAns = "pin"
					return ["outputTxt":outputTxt, "pContinue":state.pMuteAlexa, "pPendingAns":pPendingAns, "versionSTtxt":versionSTtxt]
            	}
                else {
                outputTxt = controlHandler(savedData) 
                pPendingAns = "door"
            	}
        	}
        }
        if( state.pContCmdsR == "feedback"){
            if (state.lastAction != null) {
                def savedData = state.lastAction
                outputTxt = getMoreFeedback(savedData) 
                pPendingAns = "feedback"
				return ["outputTxt":outputTxt, "pContinue":state.pMuteAlexa, "pPendingAns":pPendingAns, "versionSTtxt":versionSTtxt]
            }
         }
         if( state.pContCmdsR == "bat" || state.pContCmdsR == "act"){
            if (state.lastAction != null) {
                def savedData = state.lastAction
                outputTxt = savedData
                pPendingAns = "feedback"
				return ["outputTxt":outputTxt, "pContinue":state.pMuteAlexa, "pPendingAns":pPendingAns, "versionSTtxt":versionSTtxt]
            }
       }
       if( state.pContCmdsR == "caps"){
            if (state.lastAction != null) {
                outputTxt = state.lastAction
                pPendingAns = "caps"
				state.pContCmdsR = null 
				state.lastAction = null
                return ["outputTxt":outputTxt, "pContinue":state.pMuteAlexa, "pPendingAns":pPendingAns, "versionSTtxt":versionSTtxt]
            }
        }        
     }   
    //state.pContCmdsR = null
    //state.lastAction = null
	  if (debug){log.debug "Initial data received: (event) = '${event}', (ver) = '${versionTxt}', (date) = '${versionDate}', (release) = '${releaseTxt}'"+ 
      "; data sent: pContinue = '${state.pContCmds}', pPendingAns = '${pPendingAns}', versionSTtxt = '${versionSTtxt}', outputTxt = '${outputTxt}' ; other data: pContCmdsR = '${state.pContCmdsR}', pinTry'=${state.pinTry}' "
	}
    return ["outputTxt":outputTxt, "pContinue":state.pMuteAlexa, "pPendingAns":pPendingAns, "versionSTtxt":versionSTtxt]

	} catch (e) {
		log.error "Oh no, something went wrong. If this happens again, please reach out for help!"
        outputTxt = "Oh no, something went wrong. If this happens again, please reach out for help!"
        state.pTryAgain = true
        return ["outputTxt":outputTxt, "pContinue":state.pMuteAlexa, "pPendingAns":pPendingAns, "versionSTtxt":versionSTtxt]
	}

}   

/************************************************************************************************************
		FEEDBACK HANDLER - from Lambda via page f
************************************************************************************************************/
def feedbackHandler() {
    def fProfile = params.fProfile
    def fDevice = params.fDevice
   	def fQuery = params.fQuery
    def fOperand = params.fOperand 
    def fCommand = params.fCommand 	
    def pPIN = false
    state.pTryAgain = false
    
    def String deviceType = (String) null
    def String outputTxt = (String) null
    def String deviceM = (String) null
	def currState
    def stateDate
    def stateTime
	def data = [:]
    fDevice = fDevice.replaceAll("[^a-zA-Z0-9 ]", "") 
    if (debug){
    	log.debug 	"Feedback data: (fProfile) = '${fProfile}', (fDevice) = '${fDevice}', "+
    				"(fQuery) = '${fQuery}', (fOperand) = '${fOperand}', (fCommand) = '${fCommand}'"
    } 
    
	try {
    
    fOperand = fOperand == "switches" ? "lights" : fOperand
    
    if (fDevice != "undefined" && fQuery != "undefined" && fOperand == "undefined" && fQuery != "about"  ) {
    	def dMatch = deviceMatchHandler(fDevice)
		if (dMatch?.deviceMatch == null) { 				
			outputTxt = "Sorry, I couldn't find any details about " + fDevice
            state.pTryAgain = true
            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]	
		}
        else {
            def dDevice = dMatch?.deviceMatch
            def dType = dMatch?.deviceType
            def dState = dMatch?.currState
            def dMainCap = dMatch?.mainCap
            def dCapCount = getCaps(dDevice,dType, dMainCap, dState)
            state.pContCmdsR = "caps"
            outputTxt = "Sorry, I couldn't quite get that, but I found a matching device that has " + dCapCount + " capabilities. Would you like to hear more about this device?"         
            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]	
		}
	}
    if (fOperand == "undefined" && fQuery == "about") {
		def deviceMatch=cTstat.find {d -> d.label.toLowerCase() == fDevice.toLowerCase()}
			if(deviceMatch)	{
                    deviceType = "cTstat"
                    def currentMode = deviceMatch.latestValue("thermostatMode")
                    def currentHSP = deviceMatch.latestValue("heatingSetpoint") 
                    def currentCSP = deviceMatch.latestValue("coolingSetpoint") 
                    def currentTMP = deviceMatch.latestValue("temperature")
                    int temp = currentTMP
                    int hSP = currentHSP
                    int cSP = currentCSP
                    stateDate = deviceMatch.currentState("temperature").date
                    stateTime = deviceMatch.currentState("temperature").date.time
                    def timeText = getTimeVariable(stateTime, deviceType)            
                    outputTxt = "The " + fDevice + " temperature is " + temp + " degrees and the current mode is " + currentMode + " , with set points of " + cSP + " for cooling and " + hSP + " for heating"
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR": state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
			}
            else {
				def rSearch = deviceMatchHandler(fDevice)
				if (debug) log.debug "Getting device details"
                    if (rSearch?.deviceMatch == null) { 
                        outputTxt = "Sorry I couldn't find any details about " + fDevice
                        state.pTryAgain = true
                        return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]	
                    }
                    else {
                        deviceM = rSearch?.deviceMatch
                        outputTxt = deviceM + " has been " + rSearch?.currState + " since " + rSearch?.tText
                        return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]	
                    }                  
                if (rSearch.deviceType == "cBattery") {
					outputTxt = "The battery level for " + deviceM + " is " + rSearch.currState + " and was last recorded " + rSearch.tText
				}
				if (rSearch.deviceType == "cMedia") {
					outputTxt = rSearch.currState + " since " + rSearch.tText
                }
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]	
    		}
    }
    else {
        if(fOperand == "temperature") {
            if(cTstat){
                cTstat.find {s -> 
                    if(s.label.toLowerCase() == fDevice.toLowerCase()){
                        deviceType = "cTstat"
                        def currentTMP = s.latestValue("temperature")
                        int temp = currentTMP
                        stateDate = s.currentState("temperature").date
                        stateTime = s.currentState("temperature").date.time
                        def timeText = getTimeVariable(stateTime, deviceType)            
                        log.warn "temperature"
                        outputTxt = "The temperature " + fDevice + " is " + temp + " degrees and was recorded " + timeText.currDate + " at " + timeText.currTime
                    }
                }
            }
            if(cMotion){
                cMotion.find {s -> 
                    if(s.label.toLowerCase() == fDevice.toLowerCase()){
                        deviceType = "cTstat"
                        def currentTMP = s.latestValue("temperature")
                        int temp = currentTMP
                        stateDate = s.currentState("temperature").date
                        stateTime = s.currentState("temperature").date.time
                        def timeText = getTimeVariable(stateTime, deviceType)            
                        outputTxt = "The temperature in " + fDevice + " is " + temp + " degrees and was recorded " + timeText.currDate + " at " + timeText.currTime
                    }
                }
            }
            if(cWater){
                cWater.find {s -> 
                    if(s.label.toLowerCase() == fDevice.toLowerCase()){
                        deviceType = "cWater"
                        def currentTMP = s.latestValue("temperature")
                        int temp = currentTMP
                        stateDate = s.currentState("temperature").date
                        stateTime = s.currentState("temperature").date.time
                        def timeText = getTimeVariable(stateTime, deviceType)            
                        outputTxt = "The temperature in the " + fDevice + " is " + temp + " degrees and was recorded " + timeText.currDate + " at " + timeText.currTime
                    }
                }
            }            
			if (outputTxt == null ) { outputTxt = "Device named " + fDevice + " doesn't have a temperature sensor" }
            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]                        
        }
        if(fOperand == "lights" && fCommand != "undefined") { 
            if(cSwitch){
				def devList = []
                if (cSwitch.latestValue("switch").contains(fCommand)) {
                    cSwitch.each { deviceName ->
                                if (deviceName.latestValue("switch")=="${fCommand}") {
                                    String device  = (String) deviceName
                                    devList += device
                                }
                    }
                }
				if (fQuery == "how" || fQuery.contains ("if") || fQuery == "undefined" || fQuery == "are there") {
                    if (devList.size() > 0) {
                    	if (devList.size() == 1) {
                    		outputTxt = "There is one switch " + fCommand + " , would you like to know which one"                           			
                    	}
                    	else {
                    		outputTxt = "There are " + devList.size() + " switches " + fCommand + " , would you like to know which switches"
                    	}
                    data.devices = devList
                    data.cmd = fCommand
                    data.deviceType = "cSwitch"
                    state.lastAction = data
                    state.pContCmdsR = "feedback"
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]

                    }
                    else {outputTxt = "There are no switches " + fCommand}
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]

				}
				else if (fQuery.contains ("what") || fQuery.contains ("which") || fQuery == "what's") {
                	def devNames = []
                    if (cSwitch.latestValue("switch").contains(fCommand)) {
                    	cSwitch.each { deviceName ->
									if (deviceName.latestValue("switch")=="${fCommand}") {
                                    	String device  = (String) deviceName
                                    	devNames += device
                                	}
                    	}
					log.warn " list: ${devNames}"
                    outputTxt = "The following switches are " + fCommand + "," + devNames.sort().unique()
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]

                	}
                    else {outputTxt = "There are no switches " + fCommand}
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
				}     
            }
        }
		if(fOperand.contains("doors")) { // && fCommand != "undefined") { removed 1/23/2017
            //if(cContact){
				def devList = []
				if (cContact.latestValue("contact").contains(fCommand) || cDoor.latestValue("door").contains(fCommand)) {
                    cContact.each { deviceName ->
                                if (deviceName.latestValue("contact")=="${fCommand}") {
                                    String device  = (String) deviceName
                                    devList += device
                                }
                    }
                    cDoor?.each { deviceName ->
								if (deviceName.latestValue("door")=="${fCommand}") {
                                   	String device  = (String) deviceName
                                   	devNames += device
                                }
                    }                    
                }
				if (fQuery == "how" || fQuery== "how many" || fQuery == "undefined" || fQuery == "are there") {
                    if (devList.size() > 0) {
                    	if (devList.size() == 1) {
                    		outputTxt = "There is one door or window " + fCommand + " , would you like to know which one"                           			
                    	}
                    	else {
                    		outputTxt = "There are " + devList.size() + " doors or windows " + fCommand + " , would you like to know which doors or windows"
                    	}
                    data.devices = devList
                    data.cmd = fCommand
                    data.deviceType = "cContact"
                    state.lastAction = data
                    state.pContCmdsR = "feedback"
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]

                    }
                    else {outputTxt = "There are no doors or windows " + fCommand}
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
				}
				else if (fQuery.contains ("what") || fQuery.contains ("which") || fQuery == "what's") {
                	def devNames = []
                    fOperand = fOperand.contains("closed") ? "closed" : fOperand.contains("open") ? "open" : fOperand 
                    fCommand = fCommand.contains("close") ? "closed" : fCommand
                    fCommand = fOperand == "closed" ? "closed" : fOperand == "open" ? "open" : fCommand                  
                    log.warn "fCommand = '${fCommand}'"
                        if (cContact.latestValue("contact").contains(fCommand) || cDoor.latestValue("door").contains(fCommand)) {
                            cContact?.each { deviceName ->
                                        if (deviceName.latestValue("contact")=="${fCommand}") {
                                            String device  = (String) deviceName
                                            devNames += device
                                        }
                            }
                            cDoor?.each { deviceName ->
                                        if (deviceName.latestValue("door")=="${fCommand}") {
                                            String device  = (String) deviceName
                                            devNames += device
                                        }
                            }
                        outputTxt = "The following doors or windows are " + fCommand + "," + devNames.sort().unique()
                        return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]

                        }
                        else {outputTxt = "There are no doors or windows " + fCommand}
                        return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
            	}
        }
		if(fOperand == "batteries") {
        def cap = "bat"
        def devList = getCapabilities(cap)
			if (fQuery == "how" || fQuery== "how many" || fQuery == "undefined" || fQuery == "are there" || fCommand == "low" || fQuery == "give" || fQuery == "get") {
                    if (devList.listSize > 0) {
                    	if (devList.listSize == 1) {
                    		outputTxt = "There is one device with low battery level , would you like to know which one"                           			
                    	}
                    	else {
                    		outputTxt = "There are " + devList.listSize + " devices with low battery levels, would you like to know which devices"
                    	}
                    def sdevices = devList?.listBat
                    def devListString = sdevices.join(",")
                    data.list = devListString
                    state.lastAction = devListString
                    state.pContCmdsR = "bat"
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                    }
                    else {outputTxt = "There are no devices with low battery levels"}
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
				}
                else if (fQuery.contains ("what") || fQuery.contains ("which")) {
                    if (devList.listSize > 0) {
                    outputTxt = "The following devices have low battery levels " + devList.listBat.sort()//.unique()
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                	}
                    else {outputTxt = "There are no devices with low battery levels "
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
					} 
            	}
		}
		if(fOperand == "inactive" || fOperand.contains("inactive") ) { //devices inactive
        def cap = "act"
        def devList = getCapabilities(cap)
			if (fQuery == "how" || fQuery== "how many" || fQuery == "undefined" || fQuery == "are there" || fQuery == "give" || fQuery == "get") {
                    if (devList.listSize > 0) {
                    	if (devList.listSize == 1) {
                    		outputTxt = "There is one inactive device, would you like to know which one?"                           			
                    	}
                    	else {
                    		outputTxt = "There are " + devList.listSize + " inactive devices, would you like to know which devices"
                    	}
                    def sdevices = devList?.listDev
                    def devListString = sdevices.join(",")
                    data.list = devListString
                    state.lastAction = devListString
                    state.pContCmdsR = "act"
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                    }
                    else {outputTxt = "There are no inactive devices"}
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
				}
                else if (fQuery.contains ("what") || fQuery.contains ("which")) {
                    if (devList.listSize > 0) {
                    outputTxt = "The following devices have been inactive for more than " + cInactiveDev + " " + devList.listDev.sort()
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                	}
                    else {outputTxt = "There are no inactive devices"
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
					} 
            	}
		}       
		if(fOperand == "settings") {
  			def pCmds = state.pContCmds == true ? "enabled" : "disabled"
            def pCmdsR = state.pContCmdsR //last continuation response
            def pMute = state.pMuteAlexa == true ? "Alexa voice is disabled" : "Alexa voice is active"
            //state.scheduledHandler
            def pin_D = state.usePIN_D == true ? "active" : "inactive"
            def pin_L = state.usePIN_L == true ? "active" : "inactive"
            def pin_T = state.usePIN_T == true ? "active" : "inactive"
			def pin_S = state.usePIN_S == true ? "active" : "inactive"
            
            def activePin = pin_D == "active" ? "doors" : null
                activePin  = pin_L == "active" ? activePin + ", locks" : activePin
            	activePin  = pin_S == "active" ? activePin + ", switches" : activePin
                activePin  = pin_T == "active" ? activePin + ", thermostats"  : activePin
            if (activePin == null) { activePin = "no groups"}                
            def inactivePin = pin_D == "inactive" ? "doors" : null
                inactivePin  = pin_L == "inactive" ? inactivePin + ", locks" : inactivePin
            	inactivePin  = pin_S == "inactive" ? inactivePin + ", switches" : inactivePin
                inactivePin  = pin_T == "inactive" ? inactivePin + ", thermostats" : inactivePin
            if (inactivePin == null) { inactivePin = "no groups"}    
            outputTxt = pMute + " and the conversational module is " + pCmds + ". The pin number is active for: " +  activePin + " and inactive for: " + inactivePin
            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
		}
        if (fQuery == "who" ) {
			if(cPresence){
                    def devListP = []
                    def devListNP = []
                    if (cPresence.latestValue("presence").contains("present")) {
                        cPresence.each { deviceName ->
                                    if (deviceName.latestValue("presence")=="present") {
                                        String device  = (String) deviceName
                                        devListP += device
                                    }
                        }
                    }
                    if (cPresence.latestValue("presence").contains("not present")) {
                        cPresence.each { deviceName ->
                                    if (deviceName.latestValue("presence")=="not present") {
                                        String device  = (String) deviceName
                                        devListNP += device
                                    }
                        }
                    }
                if (fOperand == "here" || fOperand == "at home" || fOperand == "present" || fOperand == "home" ) {
                        if (devListP.size() > 0) {
                            if (devListP.size() == 1) {
                                outputTxt = "Only" + devListP + "is at home"                         			
                            }
                            else {
                                outputTxt = "The following " + devListP.size() + " people are at home: " + devListP
                            }

                        }
                        else outputTxt = "No one is home"
                        return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                }
                else if (fOperand.contains("not")) {
                    if (devListNP.size() > 0) {
                        if (devListNP.size() == 1) {
                                outputTxt = "Only" + devListNP + "is not home"                         			
                    	}
                    	else {
                                outputTxt = "The following " + devListNP.size() + " people are not at home: " + devListNP
                    	}
					}
					else outputTxt = "Everyone is at home"
					return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                }
            }
        }
        if (fQuery.contains ("when")) {
            def deviceData = deviceMatchHandler(fDevice)
            deviceM  = deviceData.deviceMatch  
			outputTxt = deviceM + " was last " + fOperand + " " + deviceData.tText
            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]

		}
	}

	} catch (e) {
		log.error "Oh no, something went wrong. If this happens again, please reach out for help!"
        outputTxt = "Oh no, something went wrong. If this happens again, please reach out for help!"
        state.pTryAgain = true
        return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
	}

}

/******************************************************************************
	 DEVICE MATCH											
******************************************************************************/
private deviceMatchHandler(fDevice) {

    def pPIN = false
    state.pTryAgain = false
    def String deviceType = (String) null

	def currState
    def stateDate
    def stateTime
	def deviceMatch
    def result
	
		if(cTstat){
           deviceMatch = cTstat.find {d -> d.label.toLowerCase() == fDevice.toLowerCase()}
			if(deviceMatch){
				deviceType = "cTstat"
                currState = deviceMatch.currentState("thermostatOperatingState").value
                stateDate = deviceMatch.currentState("thermostatOperatingState").date
                stateTime = deviceMatch.currentState("thermostatOperatingState").date.time
                def timeText = getTimeVariable(stateTime, deviceType)            
            	return ["deviceMatch" : deviceMatch, "deviceType": deviceType, "currState": currState, "tText": timeText.tText, "mainCap": "thermostatOperatingState" ]
            }
        }
        if (cSwitch){
		deviceMatch = cSwitch.find {d -> d.label.toLowerCase() == fDevice.toLowerCase()}
			if(deviceMatch){
				deviceType = "cSwitch" 
				currState = deviceMatch.currentState("switch").value
				stateDate = deviceMatch.currentState("switch").date
				stateTime = deviceMatch.currentState("switch").date.time
				def timeText = getTimeVariable(stateTime, deviceType)
            	return ["deviceMatch" : deviceMatch, "deviceType": deviceType, "currState": currState, "tText": timeText.tText, "mainCap": "switch"]
        	}
        }
        if (cContact){
        deviceMatch =cContact.find {d -> d.label.toLowerCase() == fDevice.toLowerCase()}
            if(deviceMatch)	{
                deviceType = "cContact" 
				currState = deviceMatch.currentState("contact").value
				stateDate = deviceMatch.currentState("contact").date
				stateTime = deviceMatch.currentState("contact").date.time
				def timeText = getTimeVariable(stateTime, deviceType)
                return ["deviceMatch" : deviceMatch, "deviceType": deviceType, "currState": currState, "tText": timeText.tText, "mainCap": "contact"]
            }
        }
        if (cMotion){
        deviceMatch =cMotion.find {d -> d.label.toLowerCase() == fDevice.toLowerCase()}
            if(deviceMatch)	{
                deviceType = "cMotion" 
                currState = deviceMatch.currentState("motion").value 
                stateDate = deviceMatch.currentState("motion").date
                stateTime = deviceMatch.currentState("motion").date.time
                def timeText = getTimeVariable(stateTime, deviceType)
                return ["deviceMatch" : deviceMatch, "deviceType": deviceType, "currState": currState, "tText": timeText.tText, "mainCap": "motion"]
        	}
        } 
        if (cLock){
        deviceMatch =cLock.find {d -> d.label.toLowerCase() == fDevice.toLowerCase()}
            if(deviceMatch)	{
                deviceType = "cLock"
                currState = deviceMatch.currentState("lock").value 
                stateDate = deviceMatch.currentState("lock").date
                stateTime = deviceMatch.currentState("lock").date.time
                def timeText = getTimeVariable(stateTime, deviceType)
                return ["deviceMatch" : deviceMatch, "deviceType": deviceType, "currState": currState, "tText": timeText.tText, "mainCap": "lock"]
        	}
        }        
        if (cPresence){
        deviceMatch =cPresence.find {d -> d.label.toLowerCase() == fDevice.toLowerCase()}
            if(deviceMatch)	{
                deviceType = "cPresence"
                currState = deviceMatch.currentState("presence").value 
                stateDate = deviceMatch.currentState("presence").date
                stateTime = deviceMatch.currentState("presence").date.time
                def timeText = getTimeVariable(stateTime, deviceType)
                return ["deviceMatch" : deviceMatch, "deviceType": deviceType, "currState": currState, "tText": timeText.tText, , "mainCap": "presence"]
        	}
        }  
        if (cDoor){
        deviceMatch =cDoor.find {d -> d.label.toLowerCase() == fDevice.toLowerCase()}
            if(deviceMatch)	{
                deviceType = "cDoor"
                currState = deviceMatch.currentState("door").value 
                stateDate = deviceMatch.currentState("door").date
                stateTime = deviceMatch.currentState("door").date.time
                def timeText = getTimeVariable(stateTime, deviceType)
                return ["deviceMatch" : deviceMatch, "deviceType": deviceType, "currState": currState, "tText": timeText.tText, "mainCap": "door"]
        	}
        }  
        if (cVent){
		deviceMatch =cVent.find {d -> d.label.toLowerCase() == fDevice.toLowerCase()}
            if(deviceMatch)	{
                deviceType = "cVent"
                currState = deviceMatch.currentState("switch").value 
                currState = currState == "on" ? "open" : currState == "off" ? "closed" : "unknown"
                stateDate = deviceMatch.currentState("switch").date
                stateTime = deviceMatch.currentState("switch").date.time
                def timeText = getTimeVariable(stateTime, deviceType)
                return ["deviceMatch" : deviceMatch, "deviceType": deviceType, "currState": currState, "tText": timeText.tText, , "mainCap": "switch"]
        	}
        }
        if (cWater){
		deviceMatch =cWater.find {d -> d.label.toLowerCase() == fDevice.toLowerCase()}
            if(deviceMatch)	{
                deviceType = "cWater"
                currState = deviceMatch.currentState("water").value 
                stateDate = deviceMatch.currentState("water").date
                stateTime = deviceMatch.currentState("water").date.time
                def timeText = getTimeVariable(stateTime, deviceType)
                return ["deviceMatch" : deviceMatch, "deviceType": deviceType, "currState": currState, "tText": timeText.tText,  "mainCap": "water"]
        	}
        }        
        if (cMedia){
		deviceMatch =cMedia.find {d -> d.label.toLowerCase() == fDevice.toLowerCase()}
            if(deviceMatch)	{
                deviceType = "cMedia"
                currState = deviceMatch.currentState("currentActivity").value 
                currState = currState == "--" ? " all activities are off " : " The " + currState + " has been on"
                stateDate = deviceMatch.currentState("currentActivity").date
                stateTime = deviceMatch.currentState("currentActivity").date.time
                def timeText = getTimeVariable(stateTime, deviceType)
                return ["deviceMatch" : deviceMatch, "deviceType": deviceType, "currState": currState, "tText": timeText.tText,  "mainCap": "currentActivity"]
            }
        }        
        if (cFan){
		deviceMatch =cFan.find {d -> d.label.toLowerCase() == fDevice.toLowerCase()}
            if(deviceMatch)	{
                deviceType = "cFan"
                currState = deviceMatch.currentState("switch").value 
                stateDate = deviceMatch.currentState("switch").date
                stateTime = deviceMatch.currentState("switch").date.time
                def timeText = getTimeVariable(stateTime, deviceType)
                return ["deviceMatch" : deviceMatch, "deviceType": deviceType, "currState": currState, "tText": timeText.tText, "mainCap": "switch"] 
            }
        }         
        if (cRelay){
		deviceMatch =cRelay.find {d -> d.label.toLowerCase() == fDevice.toLowerCase()}
            if(deviceMatch)	{
				deviceType == "cRelay"
                if (cContactRelay) {
                currState = cContactRelay.currentState("contact").value 
                stateDate = cContactRelay.currentState("contact").date
                stateTime = cContactRelay.currentState("contact").date.time
                def timeText = getTimeVariable(stateTime, deviceType)
                return ["deviceMatch" : deviceMatch, "deviceType": deviceType, "currState": currState, "tText": timeText.tText,  "mainCap": "contact"] 
                }
			}
        }
        if (cBattery){
        deviceMatch =cBattery.find {d -> d.label.toLowerCase() == fDevice.toLowerCase()}
            if(deviceMatch)	{
                deviceType = "cBattery"
                currState = cBattery.currentState("battery").value
				stateTime = cBattery.currentState("battery").date.time
                def timeText = getTimeVariable(stateTime, deviceType)            
                return ["deviceMatch" : deviceMatch, "deviceType": deviceType, "currState": "", "tText": timeText.tText,  "mainCap": "battery"]
            } 
     	}       
}
/******************************************************************************
	 ADDITIONAL FEEDBACK											
******************************************************************************/
def getMoreFeedback(data) {
def devices = data.devices
def deviceType = data.deviceType
def command = data.cmd
def result 

	if ( deviceType == "cSwitch") {
    	result = "The following switches are " + command + "," + devices.sort().unique()
    }
	if ( deviceType == "cContact") {
    	result = "The following doors or windows are " + command + "," + devices.sort().unique()
    }
    state.pContCmdsR = null 
	state.lastAction = null
	
    return result
}

/******************************************************************************
	 DEVICE CAPABILITIES											
******************************************************************************/
private getCaps(capDevice,capType, capMainCap, capState) {

def deviceName = capDevice
def deviceType = capType
def deviceCap = capMainCap
def deviceState = capState
state.pContCmdsR = "caps"

def result
def attr = [:]

log.warn " deviceName: ${deviceName} deviceName: ${deviceName} , deviceType: ${deviceType}, deviceCap: ${deviceCap}, deviceState: ${deviceState}"
def supportedCaps = deviceName.capabilities
	supportedCaps.each { c ->
		def capName = c.name
            c.attributes.each {a ->
        		def attrName = a.name
                 def attrValue = deviceName.latestValue(attrName)
                 
                 if (a.name != null && a.name !=checkInterval && a.name !=polling  && a.name !=refresh && attrValue != null ) {
                    if (a.name == "temperature") 		{ result = "The " + attrName + " is " + attrValue + " degrees, " }
                    if (a.name == "motion") 			{ result = result + attrName + " is " + attrValue +", " }
                    if (a.name == "humidity") 			{ result = result + attrName + " is " + attrValue + ", " }
                    if (a.name == "illuminance") 		{ result = result + "lux level is " + attrValue + ", " }
                    if (a.name == "heatingSetpoint") 	{ result = result + "Heating Set Point is " + attrValue + " degrees, " }
                    if (a.name == "coolingSetpoint") 	{ result = result + "Cooling Set Point is" + attrValue + " degrees, " }
                    if (a.name == "thermostatMode") 	{ result = result + "The thermostat Mode is " + attrValue + ", " }
                    if (a.name == "thermostatFanMode") 	{ result = result + "The Fan Mode is " + attrValue + ", " }
					if (a.name == "battery") 			{ result = result + attrName + " level is " + attrValue + " percent, " }
                    	attr << ["${attrName}": attrValue]
            	}
           }
     }
	state.lastAction = result
	state.pContCmdsR = "caps"
    log.debug "${deviceName} has ${attr.size()} capabilities:  ${attr}  with the following attributes: ${result}" 
    result = attr.size()
    return result
}
/******************************************************************************
	 CAPABILITIES GROUP											
****************************************************************************/
private getCapabilities(cap) {
// DEVICE categories: cSwitch,cVent,cFan,cTstat,cDoor,cRelay,cContactRelay,cLock,cMotion,cContact,cWater,cPresence,cSpeaker,cSynth,cMedia,cBattery 
def DeviceDetails = [] 
def batDetails = [] 
def result = [:] 
	try {

//batteries
	if (cap == "bat") {
        cMotion?.each 	{ d ->
        def attrValue = d.latestValue("battery") 
        	if (attrValue < cLowBattery) {
        		batDetails << d.displayName         
             }     
         }
        cContact?.each 	{ d ->
        def attrValue = d.latestValue("battery") 
        	if (attrValue < cLowBattery) {
        		batDetails << d.displayName         
             }
         }
         
        cPresence?.each 	{ d ->
        def attrValue = d.latestValue("battery") 
        	if (attrValue < cLowBattery) {
        		batDetails << d.displayName         
             }
         }
        cWater?.each 	{ d ->
        def attrValue = d.latestValue("battery") 
        	if (attrValue < cLowBattery) {
        		batDetails << d.displayName         
             }
         }
        cVent?.each 	{ d ->
        def attrValue = d.latestValue("battery") 
        	if (attrValue < cLowBattery) {
        		batDetails << d.displayName        
             }
         }
        cLock.each 	{ d ->
        def attrValue = d.latestValue("battery") 
        	if (attrValue < cLowBattery) {
        		batDetails << d.displayName         
             }
         }    
        cBattery?.each 	{ d ->
        def attrValue = d.latestValue("battery") 
        	if (attrValue < cLowBattery) {
        		batDetails << d.displayName     
             }
         }
        def dUniqueList = batDetails.unique (false)
        dUniqueList = dUniqueList.sort()       
        def listSize = dUniqueList.size()
        def listBat = dUniqueList
        result = [listSize: listSize, listBat: listBat]
        return result //dUniqueListString
	}
//activity	
    if (cap == "act") {
        cMotion?.each 	{ d ->
        	def stateTime = d.currentState("motion").date.time
			def endTime = now() + location.timeZone.rawOffset
    		def startTimeAdj = new Date(stateTime + location.timeZone.rawOffset)
    			startTimeAdj = startTimeAdj.getTime()
    		int hours = (int)((endTime - startTimeAdj) / (1000 * 60 * 60) )
    		//int minutes = (int)((endTime - startTime) / ( 60 * 1000))
                if ( hours > cInactiveDev ) {
                    DeviceDetails << d.displayName 
                }
        }
        cContact?.each 	{ d ->
        def attrValue = d.latestValue("contact") 
        	def stateTime = d.currentState("contact").date.time
			def endTime = now() + location.timeZone.rawOffset
    		def startTimeAdj = new Date(stateTime + location.timeZone.rawOffset)
    			startTimeAdj = startTimeAdj.getTime()
    		int hours = (int)((endTime - startTimeAdj) / (1000 * 60 * 60) )
    		//int minutes = (int)((endTime - startTime) / ( 60 * 1000))
                if ( hours > cInactiveDev ) {
                    DeviceDetails << d.displayName 
                }
        }
        cPresence?.each 	{ d ->
        def attrValue = d.latestValue("presence") 
        	def stateTime = d.currentState("presence").date.time
			def endTime = now() + location.timeZone.rawOffset
    		def startTimeAdj = new Date(stateTime + location.timeZone.rawOffset)
    			startTimeAdj = startTimeAdj.getTime()
    		int hours = (int)((endTime - startTimeAdj) / (1000 * 60 * 60) )
    		//int minutes = (int)((endTime - startTime) / ( 60 * 1000))
                if ( hours > cInactiveDev ) {
                    DeviceDetails << d.displayName 
                }
        }
        cWater?.each 	{ d ->
        def attrValue = d.latestValue("water") 
        	def stateTime = d.currentState("water").date.time
			def endTime = now() + location.timeZone.rawOffset
    		def startTimeAdj = new Date(stateTime + location.timeZone.rawOffset)
    			startTimeAdj = startTimeAdj.getTime()
    		int hours = (int)((endTime - startTimeAdj) / (1000 * 60 * 60) )
    		//int minutes = (int)((endTime - startTime) / ( 60 * 1000))
                if ( hours > cInactiveDev ) {
                    DeviceDetails << d.displayName 
                }
        }
        cVent?.each 	{ d ->
        def attrValue = d.latestValue("switch") 
        	def stateTime = d.currentState("switch").date.time
			def endTime = now() + location.timeZone.rawOffset
    		def startTimeAdj = new Date(stateTime + location.timeZone.rawOffset)
    			startTimeAdj = startTimeAdj.getTime()
    		int hours = (int)((endTime - startTimeAdj) / (1000 * 60 * 60) )
    		//int minutes = (int)((endTime - startTime) / ( 60 * 1000))
                if ( hours > cInactiveDev ) {
                    DeviceDetails << d.displayName 
                }
        }
        cLock.each 	{ d ->
        def attrValue = d.latestValue("lock") 
        	def stateTime = d.currentState("lock").date.time
			def endTime = now() + location.timeZone.rawOffset
    		def startTimeAdj = new Date(stateTime + location.timeZone.rawOffset)
    			startTimeAdj = startTimeAdj.getTime()
    		int hours = (int)((endTime - startTimeAdj) / (1000 * 60 * 60) )
    		//int minutes = (int)((endTime - startTime) / ( 60 * 1000))
                if ( hours > cInactiveDev ) {
                    DeviceDetails << d.displayName 
                }
        }
        cSwitch.each 	{ d ->
        	def attrValue = d.latestValue("switch") 
            if (d?.currentState("switch") != null) {
            def stateTime = d?.currentState("switch").date.time
			def endTime = now() + location.timeZone.rawOffset
    		def startTimeAdj = new Date(stateTime + location.timeZone.rawOffset)
    			startTimeAdj = startTimeAdj.getTime()
    		int hours = (int)((endTime - startTimeAdj) / (1000 * 60 * 60) )
    		//int minutes = (int)((endTime - startTime) / ( 60 * 1000))
                if ( hours > cInactiveDev ) {
                    DeviceDetails << d.displayName 
                }
           	}
        }        
        cLock.each 	{ d ->
        def attrValue = d.latestValue("lock") 
        	def stateTime = d.currentState("lock").date.time
			def endTime = now() + location.timeZone.rawOffset
    		def startTimeAdj = new Date(stateTime + location.timeZone.rawOffset)
    			startTimeAdj = startTimeAdj.getTime()
    		int hours = (int)((endTime - startTimeAdj) / (1000 * 60 * 60) )
    		//int minutes = (int)((endTime - startTime) / ( 60 * 1000))
                if ( hours > cInactiveDev ) {
                    DeviceDetails << d.displayName 
                }
        }        
        def dUniqueList = DeviceDetails.unique (false)
        dUniqueList = dUniqueList.sort()       
        def listSize = dUniqueList.size()
        def listDev = dUniqueList
        result = [listSize: listSize, listDev: listDev]
        log.warn " result = ${result}"
        return result //dUniqueListString
	}

//non battery repo
/*
swi =  device.currentValue("switch")

wat = device.currentValue("water")

hum = device.currentValue("humidity")
        cMotion.each 	{DeviceDetails << it.displayName +"\n"}
        cWater.each 	{DeviceDetails << it.displayName +"\n"}

tem = device.currentValue("temperature")
        cMotion.each 	{DeviceDetails << it.displayName +"\n"}
        cContact.each 	{DeviceDetails << it.displayName +"\n"}
        cWater.each 	{DeviceDetails << it.displayName +"\n"}
        cTstat.each 	{DeviceDetails << it.displayName +"\n"}

acc = device.currentValue("acceleration")
        cContact.each 	{DeviceDetails << it.displayName +"\n"}

mot = device.currentValue("motion")
        cMotion.each 	{DeviceDetails << it.displayName +"\n"}

lux = device.currentValue("illuminance")
        cMotion.each 	{DeviceDetails << it.displayName +"\n"}

loc = device.currentValue("lock")
        cLock.each 		{DeviceDetails << it.displayName +"\n"}     

win = device.currentValue("windowShade")
lev = device.currentValue("level")
tra = device.currentValue("trackDescription") 
mut = device.currentValue("mute")
pow = device.currentValue("power")
}
*/

    	} catch (e) {
		log.error "Oh no, something went wrong. If this happens again, please reach out for help!"
        result = "Oh no, something went wrong. If this happens again, please reach out for help!"
        state.pTryAgain = true
        return result
		}
}
/******************************************************************************
	 CUSTOM FEEDBACK MESSAGE											
******************************************************************************/
private txtFormat (message, eDev, eVal) {
    def eTxt = " " 
        if(message) {
        	message = message.replace('$device', "${eDev}")
        	message = message.replace('$action', "${eVal}")
	  	    eTxt = message
        }  	
            if (debug) log.debug "Processed Alert: ${eTxt} "
    		
            return eTxt
}
/******************************************************************************
	 DATE & TIME FUNCTIONS											
******************************************************************************/
private getTimeVariable(date, type) {
	def currTime
    def currDate
    def String tText = (String) null    
    def String duration = (String) null
    def today = new Date(now()).format("EEEE, MMMM dd, yyyy", location.timeZone)
    def yesterday = new Date(today -0.1).format("EEEE, MMMM dd, yyyy", location.timeZone)
	def time = new Date(now()).format("h:mm aa", location.timeZone)

    log.debug "Today's date is: ${today} and the current time is ${time}, yesterday: ${yesterday} "
    
    currTime = new Date(date + location.timeZone.rawOffset).format("h:mm aa")                       
	currDate = new Date(date + location.timeZone.rawOffset).format("EEEE, MMMM d, yyyy")
	currDate = today == currDate ? "today" : yesterday == currDate ? "yesterday" : currDate
		def endTime = now() + location.timeZone.rawOffset
    	def startTime = new Date(date + location.timeZone.rawOffset)
    	startTime = startTime.getTime()
    int hours = (int)((endTime - startTime) / (1000 * 60 * 60) )
    int minutes = (int)((endTime - startTime) / ( 60 * 1000))
    duration = minutes < 60 ? minutes + " minutes" : hours + " hours"

    //if (type == "cSwitch" || type == "cContact" || type == "cMotion") {
    tText = currDate + " at " + currTime
    log.debug 	"Time Variables: type = ${type}, (currDate) = ${currDate}, (currTime) = ${currTime}, (tText) = ${tText},"+
    			" (hours) = ${hours}, (minutes) = ${minutes}, (duration) = ${duration}"
  	return ["currTime":currTime, "currDate":currDate, "tText":tText, "duration": duration] 
	//}
 
}
/************************************************************************************************************
		PROFILE CONTROL HANDLER - from Lambda via page p
************************************************************************************************************/
def controlProfiles() {
	def outputTxt = "Sorry, the group control module is not ready. If you believe this was not a request to control a Profile group, please open a trouble ticket. Thank you for your help, "
	def pPIN = false
    state.pTryAgain = true
    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
}
/************************************************************************************************************
   CONTROL DEVICES - from Lambda via page c
************************************************************************************************************/
def controlDevices() {
		def ctCommand = params.cCommand
        def ctNum = params.cNum
        def ctPIN = params.cPIN
        def ctDevice = params.cDevice
        def ctUnit = params.cUnit
        def ctGroup = params.cGroup       
		def pintentName = params.intentName
        def String outputTxt = (String) null 
        //this is used to activate Lambda "Try Again" response
		def pPIN = false
        def String deviceType = (String) null
        def String command = (String) null
		def String numText = (String) null
        def String result = (String) null
        def delay = false
        def data
        ctDevice = ctDevice.replaceAll("[^a-zA-Z0-9 ]", "")
    	state.pContCmdsR = "undefined"
        if (debug) log.debug "Received Lambda request to control devices with settings:" +
        					 " (ctCommand)= ${ctCommand}',(ctNum) = '${ctNum}', (ctPIN) = '${ctPIN}', "+
                             "(ctDevice) = '${ctDevice}', (ctUnit) = '${ctUnit}', (ctGroup) = '${ctGroup}', (pintentName) = '${pintentName}'"
	try {	
    
    if (pintentName == "main") {	
        if (ctNum == "undefined" || ctNum =="?") {ctNum = 0 } 
        if (ctCommand =="?") {ctCommand = "undefined"} 
			ctNum = ctNum as int
        if (ctUnit =="?" || ctUnit == "undefined") {
			def String unit =  (String) "undefined"
        }    
        else {
        	if (ctNum>0){
            	def getTxt = getUnitText(ctUnit, ctNum)     
        		numText = getTxt.text
        		ctUnit = getTxt.unit
        	}
        }   
        if (ctUnit == "flash" || ctUnit == "text" || ctUnit == "audio") {
			if (state.scheduledHandler == "filters" && state.pContCmdsR == "filters") {
            	state.filterNotif = ctUnit
                def getTxt = getUnitText(ctUnit, ctNum)  
                outputTxt = "Ok, when you need to change your filters, I will " + getTxt.text
         		return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]               
            }
        }
        if (ctNum > 0 && ctDevice != "undefined" && ctCommand == "undefined") {
            ctCommand = "set"
        }
        if (state.pinTry != null) {
        	if (ctCommand == "undefined" && ctDevice == "undefined") {
        		log.debug "Pin try is not null and it doesn't appear that this is a new request"
            	outputTxt = pinHandler(ctPIN, ctCommand, ctNum, ctUnit)
        		return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
        	}
            else {
            state.pinTry = null
            state.pTryAgain = false  
        	}
        }
        if (ctCommand != "undefined") {
        	if (ctCommand.contains ("try again") && state.lastAction != null ) {
                	def savedData = state.lastAction
                    outputTxt = controlHandler(savedData)
            }       
            else {
            	outputTxt = getCustomCmd(ctCommand, ctUnit, ctGroup)
            	if (ctCommand.contains ("try again")) {
                	state.pContCmdsR = "clear"
                    state.savedPINdata = null
                    state.pinTry = null
                    outputTxt = " I am sorry for the trouble. I am getting my act together now, so you can continue enjoing your Echosistant app"
                    return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                    }
        	}
            if (outputTxt!= null ) {
            		if (ctUnit == "pin number" || ctUnit == "pin") {
						if (ctGroup == "thermostats" || ctGroup == "locks" || ctGroup == "doors") {
                        	state.pTryAgain = false
                        }
                        else {
                            state.pTryAgain = true
                        }
            		}
                    if (outputTxt == "Pin number please") {pPIN = true}
            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
			}
            else {
        		if (debug) log.debug "Fetching command and device type"           
            	def  getCMD = getCommand(ctCommand, ctUnit) 
            	deviceType = getCMD.deviceType
            	command = getCMD.command
            	if (debug) log.debug "Received command data: deviceType= '${deviceType}', command= '${command}' _____>>>>> STARTING MAIN PROCESS <<<<<< ______"
        	}
		}
        
		log.warn "Begining searching for devices to control with : '${deviceType}', command= '${command}'"
            if (deviceType == "volume" || deviceType == "general" || deviceType == "light" || deviceType == "Security") {      
                    def deviceMatch = null
                    def dType = null
                        if (settings.cSpeaker?.size()>0) {
                            deviceMatch = cSpeaker.find {s -> s.label.toLowerCase() == ctDevice.toLowerCase()}
                        	dType = "v"
                        }
                        if (deviceMatch == null && settings.cSynth?.size()>0) {
                            deviceMatch = cSynth.find {s -> s.label.toLowerCase() == ctDevice.toLowerCase()}                 
                        	dType = "v"
                        }
                        if (deviceMatch == null && settings.cMedia?.size()>0) {
                            deviceMatch = cMedia.find {s -> s.label.toLowerCase() == ctDevice.toLowerCase()}                 
                        	dType = "v"
                        }
                       	if (deviceMatch == null && settings.cSwitch?.size()>0 && state.pinTry == null) {
                            deviceMatch = cSwitch.find {s -> s.label.toLowerCase() == ctDevice.toLowerCase()}                 
                        	dType = "s"
                        }
					if (deviceMatch && dType == "v") {
						device = deviceMatch
						delay = false
						data = [type: "cVolume", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
						outputTxt = controlHandler(data)
						return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                    }
                    if (deviceMatch && dType == "s") {
                        if (debug) log.debug "Found a device: '${deviceMatch}'"
                        device = deviceMatch
                        if (command == "disarm" || command == "disable" | command == "deactivate"|| command == "stop") {command = "off"}
                        if (command == "arm" || command == "enable" | command == "activate"|| command == "start") {command = "on"}    
                    	if (ctNum > 0 && ctUnit == "minutes") {
                        	device = device.label
                            delay = true
                            data = [type: "cSwitch", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            runIn(ctNum*60, controlHandler, [data: data])
                            if (command == "arm") {outputTxt = "Ok, arming " + ctDevice + " " + command + ", in " + numText}
                            else if (command == "on" || command == "off" ) {outputTxt = "Ok, turning " + ctDevice + " " + command + ", in " + numText}
                            else if (command == "decrease") {outputTxt = "Ok, decreasing the " + ctDevice + " level in " + numText}
                            else if (command == "increase") {outputTxt = "Ok, increasing the " + ctDevice + " level in " + numText}
                            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                    	}
                        else {
                        	delay = false
                        	data = [type: "cSwitch", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            outputTxt = controlHandler(data)
                            if (command == "arm") {outputTxt = "Ok, arming " + ctDevice}
                            if (command == "disarm") {outputTxt = "Ok, disarming " + ctDevice}
                            else if (command == "decrease" || command == "increase" ) {state.pContCmdsR = "level"}
                            if (debug) log.debug "Sending params to Lambda: pContCmds: '${state.pContCmds}', pContCmdsR: '${state.pContCmdsR}', pTryAgain: '${state.pTryAgain}' "
                            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                  		}
                    }
        }
        else if (deviceType == "temp") {
                if (settings.cTstat?.size() > 0) {           
                    if (debug) log.debug "Searching for a thermostat named '${ctDevice}'"
                    def deviceMatch = cTstat.find {t -> t.label.toLowerCase() == ctDevice.toLowerCase()}
                    if (deviceMatch) {
 						device = deviceMatch 
                        if (debug) log.debug "Found a device: '${deviceMatch}'"        	
                        if(state.usePIN_T == true) { // (THIS PIN VALIDATION HAS BEEN Deprecated as of 1/23/2017)
            				if (debug) log.debug "PIN protected device type - '${deviceType}'"
                			delay = false
                            data = ["type": "cTstat", "command": command , "device": ctDevice, "unit": ctUnit, "num": ctNum, delay: delay]
                            state.savedPINdata = data
                			outputTxt = "Pin number please"
                			pPIN = true
                            state.pinTry = 0
                            if (debug) log.debug "PIN response pending - '${state.pinTry}'"
                			return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
            			}
                        else {                       
                            if (ctNum && ctUnit == "minutes") {
                                delay = true
                                data = [type: "cTstat", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                                runIn(ctNum*60, delayHandler, [data: data])
                                if (command == "decrease") {outputTxt = "Ok, decreasing the " + ctDevice + " temperature in " + numText}
                                else if (command == "increase") {outputTxt = "Ok, increasing the " + ctDevice + " temperature in " + numText}
                                return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                            }
                            else {
                            	delay = false
                            	data = [type: "cTstat", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            	outputTxt = controlHandler(data)
                            	return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                            }
                        }
                   }
                }
         }
        else if (deviceType == "lock") {
            if (settings.cLock?.size()>0) {   
                if (debug) log.debug "Searching for a lock named '${ctDevice}'"
                def deviceMatch = cLock.find {l -> l.label.toLowerCase() == ctDevice.toLowerCase()}             
                if (deviceMatch) {
                    if (debug) log.debug "Found a device: '${deviceMatch}'"
                    device = deviceMatch
					if(state.usePIN_L == true) { // (THIS PIN VALIDATION HAS BEEN Deprecated as of 1/23/2017)
            			if (debug) log.debug "PIN protected device type - '${deviceType}'"               		
                        delay = false
	                    data = [type: "cLock", "command": command , "device": ctDevice, "unit": ctUnit, "num": ctNum, delay: delay]
                        state.savedPINdata = data
                		outputTxt = "Pin number please"
                		pPIN = true
                        state.pinTry = 0
                        if (debug) log.debug "PIN response pending - '${state.pinTry}'"
                		return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
            		}
                    else {
                        if (ctNum > 0 && ctUnit == "minutes") {
                            device = device.label
                            delay = true
                            data = [type: "cLock", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            runIn(ctNum*60, controlHandler, [data: data])
                            if (command == "lock") {outputTxt = "Ok, locking the " + ctDevice + " in " + numText}
                            else if (command == "unlock") {outputTxt = "Ok, unlocking the " + ctDevice + " in " + numText}
                            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                        }
                        else {
                            delay = false
                            data = [type: "cLock", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            outputTxt = controlHandler(data)
                            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                        }
                    }
                }
            }
        }
        else if (deviceType == "fan") {
            if (settings.cFan?.size()>0) {     
                if (debug) log.debug "Searching for a fan named '${ctDevice}'"
                def deviceMatch = cFan.find {f -> f.label.toLowerCase() == ctDevice.toLowerCase()}
                if (deviceMatch) {
                    if (debug) log.debug "Found a device: '${deviceMatch}'"
                        device = deviceMatch
                        if (ctNum && ctUnit == "minutes") {
                            delay = true
                            data = [type: "cFan", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            runIn(ctNum*60, delayHandler, [data: data])
                            if (command == "decrease") {outputTxt = "Ok, decreasing the " + ctDevice + " temperature in " + numText}
                            else if (command == "increase") {outputTxt = "Ok, increasing the " + ctDevice + " temperature in " + numText}
							return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                        }
                        else {
                            delay = false
                            data = [type: "cFan", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            outputTxt = controlHandler(data)
							return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                        }
                }
            }
        }
        else if (deviceType == "door") {
            if (settings.cDoor?.size()>0) {          
                if (debug) log.debug "Searching for a door named '${ctDevice}'"
                def deviceMatch = cDoor.find {d -> d.label.toLowerCase() == ctDevice.toLowerCase()}
                if (deviceMatch) {
                	if (debug) log.debug "Found a device: '${deviceMatch}'"
                    device = deviceMatch
                //Check Status
					def deviceR = device.label
					def cDoorStatus = device.contactState.value
					if (debug) log.debug "Garage Door Status is: '${cDoorStatus}', command is: '${command}'  "
						if (command == "open" && cDoorStatus == "open") {
						outputTxt = "The " + device + " is already open, would you like to close it instead?"
						state.pContCmdsR = "door"
						def actionData = ["type": "cDoor", "command": "close" , "device": deviceR, "unit": ctUnit, "num": ctNum, delay: delayD]
						state.lastAction = actionData
						return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                    }
					if (command == "close" && cDoorStatus =="closed") {
                        outputTxt = "The " + device + " is already closed, would you like to open it instead? "
                        state.pContCmdsR = "door"
                        def actionData = ["type": "cDoor", "command": "open" , "device": deviceR, "unit": ctUnit, "num": ctNum, delay: delayD]
                        state.lastAction = actionData
                        return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                    }
					if(state.usePIN_D == true) {
     					//PIN VALIDATION PROCESS (Deprecated code as of 1/23/2017)
                        if (debug) log.debug "PIN protected device type - '${deviceType}'"
                		delay = false
	                    data = [type: "cDoor", "command": command , "device": ctDevice, "unit": ctUnit, "num": ctNum, delay: delay]
                        state.savedPINdata = data
                		outputTxt = "Pin number please"
                		pPIN = true
                        state.pinTry = 0
                        if (debug) log.debug "PIN response pending - '${state.pinTry}'"
                		return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
            		}
                    else {                 
                        if (ctNum && ctUnit == "minutes") {
                            delay = true
                            data = [type: "cDoor", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            runIn(ctNum*60, delayHandler, [data: data])
                            if (command == "open") {outputTxt = "Ok, opening " + ctDevice + " in " + numText}
                            else if (command == "close") {outputTxt = "Ok, closing " + ctDevice + " in " + numText}
							return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                        }
                        else {
                            delay = false
                            data = [type: "cDoor", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            outputTxt = controlHandler(data)
							return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                        }
                	}
                }
            }
                else if (deviceType == "Security") {
                if (debug) log.debug "Searching for a security switch named '${ctDevice}'"
                def deviceMatch = cSec.find {l -> l.label.toLowerCase() == ctDevice.toLowerCase()}             
                if (deviceMatch) {
                    if (debug) log.debug "Found a device: '${deviceMatch}'"
                    device = deviceMatch
					if(state.usePIN_S == true && command == "off") {
            			if (debug) log.debug "PIN protected device type - '${deviceType}'"               		
                        delay = false
	                    data = [type: "Security", command: command , "device": ctDevice, "unit": ctUnit, "num": ctNum, delay: delay]
                        state.savedPINdata = data
                		outputTxt = "Pin number please"
                		pPIN = true
                        state.pinTry = 0
                        if (debug) log.debug "PIN response pending - '${state.pinTry}'"
                		return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
            			alarmKeypadHandler(event)
                        }
                    	if (ctNum > 0 && ctUnit == "minutes") {
                            device = device.label
                            delay = true
                            data = [type: "Security", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            runIn(ctNum*60, controlHandler, [data: data])
                            if (command == "on") {outputTxt = "Ok, arming the " + ctDevice + " system in " + numText}
                            else if (command == "off") {outputTxt = "Ok, disarming the " + ctDevice + " system "}
                            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                        	}
                        else if (command == "on" && ctNum == 0 || command == "off" && ctNum == 0) {
                       		delay = false
                            data = [type: "Security", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            outputTxt = controlHandler(data)
                            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                    		}
                        }
                    }
        else if (deviceType == "miscDevices") {
                if (debug) log.debug "Searching for a pin protected device named '${ctDevice}'"
                def deviceMatch = cMiscDev.find {l -> l.label.toLowerCase() == ctDevice.toLowerCase()}             
                if (deviceMatch) {
                    if (debug) log.debug "Found a device: '${deviceMatch}'"
                    device = deviceMatch
						if (debug) log.debug "PIN protected device type - '${deviceType}'"               		
                        delay = false
	                    data = [type: "cMiscDev", command: command , "device": ctDevice, "unit": ctUnit, "num": ctNum, delay: delay]
                        state.savedPINdata = data
                		outputTxt = "Pin number please"
                		pPIN = true
                        state.pinTry = 0
                        if (debug) log.debug "PIN response pending - '${state.pinTry}'"
                        if (command == "activate") {outputTxt = "Ok, activating " + ctDevice}
                        else if (command == "deactivate") {outputTxt = "Ok, deactivating" + ctDevice}
                		return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
            			}
                    	if (ctNum > 0 && ctUnit == "minutes") {
                            device = device.label
                            delay = true
                            data = [type: "cMiscDev", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            runIn(ctNum*60, controlHandler, [data: data])
                            if (command == "activate") {outputTxt = "Ok, activating the " + ctDevice + " system in " + numText}
                            else if (command == "deactivate") {outputTxt = "Ok, deactivating the " + ctDevice + " system "}
                            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                        	}
                        else if (command == "activate" && ctNum == 0 || command == "deactivate" && ctNum == 0) {
                       		delay = false
                            data = [type: "cMiscDev", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            outputTxt = controlHandler(data)
                            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                    	}   
                    }             
			if (cRelay !=null) {
            //this is needed for Garage Doors that are set up as relays
            	if (debug) log.debug "Searching for a relay named '${ctDevice}'"
                def deviceMatch = cRelay.find {s -> s.label.toLowerCase() == ctDevice.toLowerCase()}             
				if (deviceMatch) {
                	if (debug) log.debug "Found a device: '${deviceMatch}'"
                    device = deviceMatch
             		if (cContactRelay) {
                    	if (debug) log.debug "Garage Door has a contact sensor"
                        def deviceR = device.label
                         def cCRelayValue = cContactRelay.contactState.value
                            if (command == "open" && cCRelayValue == "open") {
                                outputTxt = "The " + device + " is already open, would you like to close it instead?"
                                state.pContCmdsR = "door"
                                def actionData = ["type": "cRelay", "command": "close" , "device": deviceR, "unit": unitU, "num": newLevel, delay: delayD]
                                state.lastAction = actionData
	                            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                            }
                            if (command == "close" && cCRelayValue =="closed") {
                                outputTxt = "The " + device + " is already closed, would you like to open it instead? "
                                state.pContCmdsR = "door"
                                def actionData = ["type": "cRelay", "command": "open" , "device": deviceR, "unit": ctUnit, "num": ctNum, delay: delay]
                                state.lastAction = actionData
                            	return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                             }
                         }
                    	//PIN VALIDATION PROCESS (Deprecated code as of 1/23/2017)
						if(state.usePIN_D == true) {
                        	if (debug) log.debug "PIN protected device type - '${deviceType}'"
                            delay = false
                            data = [type: "cRelay", "command": command , "device": ctDevice, "unit": ctUnit, "num": ctNum, delay: delay]
                            state.savedPINdata = data
                            outputTxt = "Pin number please"
                            pPIN = true
                            state.pinTry = 0
                            if (debug) log.debug "PIN response pending - '${state.pinTry}'"
                            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                        }
                        else {                 
                        //END PIN VALIDATION                                       
                        	if (ctNum > 0 && ctUnit == "minutes") {
                                device = device.label
                                delay = true
                                data = [type: "cRelay", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                                runIn(ctNum*60, controlHandler, [data: data])
                                if (ctCommand == "open") {outputTxt = "Ok, opening the " + ctDevice + " in " + numText}
                                else if (command == "close") {outputTxt = "Ok, closing the " + ctDevice + " in " + numText}
                                return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                            }
                            else {
                            	delay = false
                                data = [type: "cRelay", "command": command, "device": device, unit: ctUnit, num: ctNum, delay: delay]
                                outputTxt = controlHandler(data)
                                return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                           }
                     }
                }
            }
            if (settings.cVent?.size()>0) {
            //this is needed to enable open/close command for Vents group
            	if (debug) log.debug "Searching for a vent named '${ctDevice}'"
                def deviceMatch = cVent.find {s -> s.label.toLowerCase() == ctDevice.toLowerCase()}             
                if (deviceMatch) {
                	if (debug) log.debug "Found a device: '${deviceMatch}'"
                    if (command == "open") {command = "onD"}
                    if (command == "close") {command = "offD"}
                    device = deviceMatch
                    	if (ctNum > 0 && ctUnit == "minutes") {
                            device = device.label
                            delay = true
                            data = [type: "cSwitch", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            runIn(ctNum*60, controlHandler, [data: data])
                            if (ctCommand == "open") {outputTxt = "Ok, opening the " + ctDevice + " in " + numText}
                            else if (command == "close") {outputTxt = "Ok, closing the " + ctDevice + " in " + numText}
                            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                        }
                        else {
                            delay = false
                            data = [type: "cSwitch", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            controlHandler(data)
                            if (ctCommand == "open") {outputTxt = "Ok, opening the " + ctDevice}
                            else if (ctCommand == "close") {outputTxt = "Ok, closing the " + ctDevice}
                            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                        }
				}
			}
		}
		outputTxt = "I wish I could help, but EchoSistant couldn't find the device named '${ctDevice}' or the command may not be supported"
		state.pTryAgain = true
		return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
	}
    
    	} catch (e) {
		log.error "Oh no, something went wrong. If this happens again, please reach out for help!"
        outputTxt = "Oh no, something went wrong. If this happens again, please reach out for help!"
        state.pTryAgain = true
        return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
	}
}
/************************************************************************************************************
	UNIT CONVERSIONS
************************************************************************************************************/ 
private getUnitText (unit, num) {     
    def String text = (String) null
    def String nUnit = (String) null
    if (unit == "minute" || unit == "minutes" || unit.contains ("minutes") || unit.contains ("minute")) {
    	nUnit = "minutes"
        text = num == 1 ? num + " minute" : num + " minutes" 
        return ["text": text, "unit": nUnit]
    } 
	if (unit == "degrees"  || unit.contains ("degrees")) {
		nUnit = "degrees"
        int tNum = num
        text = tNum + " degrees"
        return ["text":text, "unit":nUnit]
    }             
	if (unit == "percent" || unit.contains ("percent")) {
		nUnit = "percent"
		text = num + " percent" 
        return ["text":text, "unit":nUnit]
    }
    if (unit == "flash" || unit.contains ("flash")) {text = "flash your lights"}
   	if (unit == "text" || unit.contains ("text")) {text = "send you a text"}
    if (unit == "audio" || unit.contains ("audio")){text = "play a message"}
    	return ["text":text, "unit":nUnit]
}   
/************************************************************************************************************
	CUSTOM CONTROL COMMANDS
************************************************************************************************************/ 
private getCustomCmd(command, unit, group) {

    def result
    if (command == "repeat") {
		result = getLastMessageMain()
		return result
    }
	if (command == "change" || command == "changed" || command == "replace" || command == "replaced") {
		if (unit=="filters") {
        result = scheduleHandler(unit)
      	}
		return result
    }
    if (command == "cancel" || command == "stop" || command == "disable" || command == "unschedule") {  //|| command == "deactivate"  Removed due to interferring with misc PIN switches
    	if (unit == "reminder" || unit == "reminders" || unit == "timer" || unit == "timers" || unit.contains ("reminder") || unit.contains ("timer") || unit.contains ("schedule") ) {
        	if (unit.contains ("reminder") || unit.contains ("schedule")) {
            	if (state.scheduledHandler != null) {
                	if (state.scheduledHandler == "filters") {
                    	unschedule(filtersHandler)
                        state.scheduledHandler = null
		                result = "Ok, canceling reminder for " + state.scheduledHandler
                    }
                    else {
                    result = "Sorry, I couldn't find any scheduled reminders"// for " + state.scheduledHandler
                    }
                    return result
            	}
            }
            else {
                if (unit.contains ("timer")) {
                    unschedule(controlHandler)
                    result = "Ok, canceling timer"
                    return result
                }
            }
        }
		if (unit == "conversation" || unit.contains ("conversation")) {
			state.pContCmds = false
            result = "Ok, disabling conversational features. To activate just say, start the conversation"
			return result
        }
		if (unit == "pin number" || unit == "pin") {
			if(state.usePIN_D == true || state.usePIN_T == true || state.usePIN_L == true || state.usePIN_S != null || state.usePIN_M == true) {
        		result = "Pin number please"
        		state.pinTry = 0
                state.savedPINdata = "disable" + group
        		if (debug) log.debug "PIN response pending to disable pin number - '${state.pinTry}'"
        		return result
			}
		}
         if (unit == "feedback") {
        	state.pMuteAlexa = true
            result = "Ok, disabling Alexa feedback. To activate just say, activate the feedback"
            return result
		}
         if (unit == "undefined" && group == "undefined" ) {
            result = "Ok, I am here when you need me "
            return result
		}        
    }
	if (command == "start" || command == "enable" || command == "schedule") {  //|| command == "activate" Removed due to interferring with misc PIN switches
		if (unit == "reminder" || unit == "reminders" || unit == "timer" || unit == "timers" || unit.contains ("reminder") || unit.contains ("timer") ) {
        	state.scheduledHandler = "reminder"
            result = "Ok, reminder scheduled"
           	return result
    	}
		if (unit == "conversation" || unit.contains ("conversation")) {
           state.pContCmds = true        
           result = "Ok, activating conversational features. To disable just say, stop the conversation"
            return result
        }
		if (unit == "pin number" || unit == "pin") {
			if (group == "thermostats" || group == "locks" || group == "doors" || group == "security" || group == "miscDevices") {
            	if (group == "thermostats") {state.usePIN_T = true}
            	if (group == "locks") {state.usePIN_L = true}
            	if (group == "doors") {state.usePIN_D = true}
                if (group == "security") {state.usePIN_S = true}                
                if (group == "miscDevices") {state.usePIN_M = true}
            	if (debug) log.debug "Group:'${group}' PIN: T- '${state.usePIN_T}', L-'${state.usePIN_L}', D-'${state.usePIN_D}', S-'${state.usePIN_S}', M-'${state.usePIN_M}'"
                result = "Ok, the pin has been activated for " + group + ".  To disable, just say disable the PIN for " + group
            	return result
            }
            else {
                result = "Sorry, the pin number cannot be enabled for this group "
            	return result
            }         
        }
        if (unit == "feedback") {
        	state.pMuteAlexa = false
            result = "Ok, activating Alexa feedback. To disable just say, stop the feedback"
            return result
		}
	}
} 
/************************************************************************************************************
	PIN HANDLER
************************************************************************************************************/ 
private pinHandler(pin, command, num, unit) {
	def result
        def String pinNum = (String) null
		pinNum = num
	
    if (command == "validation") {
		state.savedPINdata = state.lastAction
        state.lastAction = null
		result = "Pin number please"
		state.pinTry = 0
		if (debug) log.debug "PIN response pending - '${state.pinTry}'"  
        return result
	}    
    
    if (pin == cPIN || command == cPIN || pinNum == cPIN || unit == cPIN ) {
		//def data = state.savedPINdata 1/23/17
		def data = state.savedPINdata != null ? state.savedPINdata : lastAction
        state.pTryAgain = false
            if (data == "disablelocks" || data == "disablethermostats" || data == "disabledoors" || data == "disablesecurity" || data == "disabledevices"){ 
                if (data == "disablelocks"){state.usePIN_L = false}
                else if (data == "disablethermostats") {state.usePIN_T = false}
                else if (data == "disabledoors") {state.usePIN_D = false}
                else if (data == "disablesecurity") {state.usePIN_S = false}
                else if (date == "disabledevices") {state.usePIN_M = false}                
                result = "Ok, pin number for " + data.replace("disable", "") + " has been disabled.  To activate it again, just say enable the PIN number for " + data.replace("disable", "")   
            }
            else {
            result = controlHandler(data)
            }
            state.pinTry = null
            state.savedPINdata = null  
            return result
	}
	else {
		state.pinTry = state.pinTry + 1
			if (state.pinTry < 4){
				result = "I'm sorry, that is incorrect "
				if (debug) log.debug "PIN NOT Matched! PIN = '${cPIN}', ctPIN= '${ctPIN}', ctNum= '${num}', ctCommand ='${command}', try# ='${state.pinTry}'"
				state.pTryAgain = true
                return result
			}
			else { 
				state.pinTry = null
                state.savedPINdata = null
				result = "I'm sorry, that is incorrect. Please check your pin number and try again later"
                return result
			}
	} 
}
/************************************************************************************************************
   DEVICE CONTROL HANDLER
************************************************************************************************************/      
def controlHandler(data) {   
    def deviceType = data.type
    def deviceCommand = data.command
   	def deviceD = data.device
    def unitU = data.unit
    def numN = data.num
    def delayD = data.delay
	def result = " "
    def actionData
    if (debug) log.debug 	"Received device control handler data: " +
        					" (deviceType)= ${deviceType}',(deviceCommand) = '${deviceCommand}', (deviceD) = '${deviceD}', " +
                            "(unitU) = '${unitU}', (numN) = '${numN}', (delayD) = '${delayD}'"  

	try {
	if (deviceType == "cSwitch") {
    	if (deviceCommand == "on" || deviceCommand == "off") {
            if (delayD == true) {
                deviceD = cSwitch.find {s -> s.label.toLowerCase() == deviceD.toLowerCase()}   
            	deviceD."${deviceCommand}"()
            }
            else {
            	deviceD."${deviceCommand}"()            	
                result = "Ok, turning " + deviceD + " " + deviceCommand 
                return result
            }
        }
        else if (deviceCommand == "onD") {
        		deviceD.on()
                deviceD.setLevel(100)
                
        }
        else if (deviceCommand == "offD") {
        		deviceD.off()
        }        
        else if (deviceCommand == "increase" || deviceCommand == "decrease" || deviceCommand == "setLevel" || deviceCommand == "set") {
 			if (delayD == true || state.pContCmdsR == "level") {
                deviceD = cSwitch.find {s -> s.label.toLowerCase() == deviceD.toLowerCase()} 
                if (debug) log.debug "Matched device control (deviceD)= ${deviceD.label}"
            }
            def currLevel = deviceD.latestValue("level")
            def currState = deviceD.latestValue("switch")
            def newLevel = cLevel*10
            if (unitU == "percent") newLevel = numN      
            if (deviceCommand == "increase") {
            	if (unitU == "percent") {
                	newLevel = numN
                }   
                else {
                	if (currLevel == null){
                    deviceD.on()
                    result = "Ok, turning " + deviceD + " on"
            		return result    
                    }
                    else {
                	newLevel =  currLevel + newLevel
            		newLevel = newLevel < 0 ? 0 : newLevel >100 ? 100 : newLevel
            		}
                }
            }
            if (deviceCommand == "decrease") {
            	if (unitU == "percent") {
                	newLevel = numN
                }   
                else {
                	if (currLevel == null) {
                    deviceD.off()
                    result = "Ok, turning " + deviceD + " off"
            		return result                    
                    }
                    else {
                	newLevel =  currLevel - newLevel
            		newLevel = newLevel < 0 ? 0 : newLevel >100 ? 100 : newLevel
                    }
                }            
            }
            if (deviceCommand == "setLevel") {
            	if (unitU == "percent") {
                	newLevel = numN
                }   
                else {
                	newLevel =  numN*10
            		newLevel = newLevel < 0 ? 0 : newLevel >100 ? 100 : newLevel
                }            
            }
            if (newLevel > 0 && currState == "off") {
            	deviceD.on()
            	deviceD.setLevel(newLevel)
            }
            else {                                    
            	if (newLevel == 0 && currState == "on") {deviceD.off()}
                else {deviceD.setLevel(newLevel)}
            } 
            def device = deviceD.label
            actionData = ["type": deviceType, "command": deviceCommand , "device": device, "unit": unitU, "num": newLevel, delay: delayD]
            state.lastAction = actionData
            if (debug) log.debug "Saved last action for level switch true (2)!"  
            result = "Ok, setting  " + deviceD + " to " + newLevel + " percent"            
            if (delayD == false) { return result } 
    	}
	}
	else if (deviceType == "cTstat") {
 		if (delayD == true || state.pinTry != null) {  
                deviceD = cTstat.find {t -> t.label.toLowerCase() == deviceD.toLowerCase()} 
        }
        state.pinTry = null
    	def currentMode = deviceD.latestValue("thermostatMode")
    	def currentHSP = deviceD.latestValue("heatingSetpoint") 
        def currentCSP = deviceD.latestValue("coolingSetpoint") 
    	def currentTMP = deviceD.latestValue("temperature") 
    	def newSetPoint = currentTMP
		numN = numN < 60 ? 60 : numN >85 ? 85 : numN
        if (unitU == "degrees") {
    		newSetPoint = numN
            int cNewSetPoint = newSetPoint
    		if (debug) log.debug "Targeted set point is = '${newSetPoint}', current temperature is = '${currentTMP}'"
    		if (newSetPoint > currentTMP) {
    			if (currentMode == "cool" || currentMode == "off") {
    				deviceD?."heat"()
					if (debug) log.debug 	"Turning heat on because requested temperature of '${newSetPoint}' "+
                    						"is greater than current temperature of '${currentTMP}' " 
    			}
				deviceD?.setHeatingSetpoint(newSetPoint)
				if (debug) log.debug "Adjusting Heating Set Point to '${newSetPoint}' because requested temperature is greater than current temperature of '${currentTMP}'"
                result = "Ok, setting " + deviceD + " heating to " + cNewSetPoint 
                	if (debug) log.debug "delay = '${newSetPoint}'"
                    if (delayD == false) { 
                    	state.pinTry = null
                    	return result 
                    }
            }
 			else if (newSetPoint < currentTMP) {
				if (currentMode == "heat" || currentMode == "off") {
					deviceD?."cool"()
					if (debug) log.debug "Turning AC on because requested temperature of '${newSetPoint}' is less than current temperature of '${currentTMP}' "    
				}
				deviceTMatch?.setCoolingSetpoint(newSetPoint)                 
				if (debug) log.debug "Adjusting Cooling Set Point to '${newSetPoint}' because requested temperature is less than current temperature of '${currentTMP}'"
                result = "Ok, setting " + deviceD + " cooling to " + cNewSetPoint + " degrees "
                    if (delayD == false) { 
                    	return result 
                    }                       
            }
            else result = "Your room temperature is already " + cNewSetPoint + " degrees "
                    if (delayD == false) { 
                    	return result 
                    }
		}
		if (deviceCommand == "increase") {
			newSetPoint = currentTMP + cTemperature
			newSetPoint = newSetPoint < 60 ? 60 : newSetPoint >85 ? 85 : newSetPoint
            int cNewSetPoint = newSetPoint
			if (currentMode == "cool" || currentMode == "off") {
				deviceD?."heat"()
                deviceD?.setHeatingSetpoint(newSetPoint)
                if (debug) log.debug "Turning heat on because requested command asked for heat to be set to '${newSetPoint}'"
                result = "Ok, turning the heat mode on " + deviceD + " and setting heating to " + cNewSetPoint + " degrees "
                return result 
			}
			else {
				if  (currentHSP < newSetPoint) {
					deviceD?.setHeatingSetpoint(newSetPoint)
					thermostat?.poll()
					if (debug) log.debug "Adjusting Heating Set Point to '${newSetPoint}'"
                    result = "Ok, setting " + deviceD + " heating to " + cNewSetPoint + " degrees "
                        if (delayD == false) { 
                            return result 
                        }    
                }
                else {
                   	if (debug) log.debug "Not taking action because heating is already set to '${currentHSP}', which is higher than '${newSetPoint}'" 
                    result = "Your heating set point is already higher than  " + cNewSetPoint + " degrees "
                    if (delayD == false) { 
                    	return result 
                    }    
               	}  
            }
       	}
        if (deviceCommand == "decrease") {
        	newSetPoint = currentTMP - cTemperature
        	newSetPoint = newSetPoint < 60 ? 60 : newSetPoint >85 ? 85 : newSetPoint     
            int cNewSetPoint = newSetPoint
            if (currentMode == "heat" || currentMode == "off") {
        		deviceD?."cool"()
                deviceD?.setCoolingSetpoint(newSetPoint)
        		if (debug) log.debug "Turning AC on because requested command asked for cooling to be set to '${newSetPoint}'"
                result = "Ok, turning the AC mode on " + deviceD + " and setting cooling to " + cNewSetPoint + " degrees "
                return result                 
        	}   	
        	else {
        		if (currentCSP > newSetPoint) {
        			deviceD?.setCoolingSetpoint(newSetPoint)
        			thermostat?.poll()
        			if (debug) log.debug "Adjusting Cooling Set Point to '${newSetPoint}'"
        			result = "Ok, setting " + deviceD + " cooling to " + cNewSetPoint + " degrees "
                    if (delayD == false) { 
                    	return result 
                    }
                }
        		else {
        			if (debug) log.debug "Not taking action because cooling is already set to '${currentCSP}', which is lower than '${newSetPoint}'"  
                    result = "Your cooling set point is already lower than  " + cNewSetPoint + " degrees "
                    if (delayD == false) { 
                    	return result 
                    }
                } 
        	}  
        }
    }
    else if (deviceType == "cMiscDev") {
    	if (delayD == true || state.pinTry != null) {  
        	deviceD = cMiscDev.find {l -> l.label.toLowerCase() == deviceD.toLowerCase()} 
        }
        state.pinTry = null
   		deviceD?."${deviceCommand}"()
        if (deviceCommand == "activate") result = "Ok, turning on " + deviceD
        else if (deviceCommand == "deactivate") result = "Ok, turning off the  " + deviceD                    
        if (delayD == false) {return result}  
	}
	else if (deviceType == "cLock") {
    	if (delayD == true || state.pinTry != null) {  
        	deviceD = cLock.find {l -> l.label.toLowerCase() == deviceD.toLowerCase()} 
        }
        state.pinTry = null
   		deviceD."${deviceCommand}"()
        if (deviceCommand == "lock") result = "Ok, locking " + deviceD
        else if (deviceCommand == "unlock") result = "Ok, unlocking the  " + deviceD                    
        if (delayD == false) {return result}  
	}
	else if (deviceType == "Security") {
    	if (delayD == true || state.pinTry != null) {  
        	deviceD = cSec.find {l -> l.label.toLowerCase() == deviceD.toLowerCase()} 
        }
        state.pinTry = null
   		deviceD."${deviceCommand}"()
        if (deviceCommand == "on") result = "Ok, arming the " + deviceD + " system"
        else if (deviceCommand == "off") result = "Ok, disarming the " + deviceD + " system"                  
        if (delayD == false) {return result}  
	}   
	else if (deviceType == "cDoor" || deviceType == "cRelay" ) {
    	def cmd = deviceCommand
        if (delayD == true || state.pinTry != null || state.pContCmdsR == "door" ) {  
            def deviceR = cRelay.find {r -> r.label.toLowerCase() == deviceD.toLowerCase()}
			deviceD = cDoor.find {d -> d.label.toLowerCase() == deviceD.toLowerCase()}   
            if (deviceR) {deviceD = deviceR}
        }
        if (deviceType == "cRelay") {
		     cmd = "on"
        }
        state.pinTry = null
   		deviceD."${cmd}"()
        state.pContCmdsR = null //"reverse"
        if (deviceCommand == "open") {result = "Ok, opening the " + deviceD}
        if (deviceCommand == "close") {result = "Ok, closing the  " + deviceD}                   
        if (delayD == false || delayD == null) {return result}  
	}
    else if (deviceType == "cFan") {
		if (cHigh == null) cHigh = 99
		if (cMedium == null) cMedium = 66
        if (cLow == null) cLow = 33
        if (cFanLevel == null) cFanLevel = 33
		if (delayD == true) {  
        	deviceD = cFan.find {f -> f.label == deviceD}   
        }
		def currLevel = deviceD.latestValue("level")
		def currState = deviceD.latestValue("switch")
		def newLevel = cFanLevel     
        	if (deviceCommand == "increase") {
            	newLevel =  currLevel + newLevel
            	newLevel = newLevel < 0 ? 0 : newLevel >100 ? 100 : newLevel
                deviceD.setLevel(newLevel)
                result = "Ok, increasing  " + deviceD + " to " + newLevel + " percent"
       				if (delayD == false) { return result }
            }
            else if (deviceCommand == "decrease") {
               	newLevel =  currLevel - newLevel
            	newLevel = newLevel < 0 ? 0 : newLevel >100 ? 100 : newLevel
                deviceD.setLevel(newLevel)
                result = "Ok, decreasing  " + deviceD + " to " + newLevel + " percent"
       				if (delayD == false) { return result }        
            }
            else {
                if (deviceCommand == "high") {newLevel = cHigh}
                if (deviceCommand == "medium") {newLevel = cMedium}
                if (deviceCommand == "low") {newLevel = cLow}
                    deviceD.setLevel(newLevel)
                    result = "Ok, setting  " + deviceD + " to " + newLevel + " percent"
                    if (delayD == false) {return result} 
           }           
	}
	if (deviceType == "cVolume") {
   		if (deviceCommand == "increase" || deviceCommand == "decrease" || deviceCommand == "setLevel") {
            def currLevel = deviceD.latestValue("level")
            def currState = deviceD.latestValue("switch")
            if (cVolLevel == null) {cVolLevel = 2}
            def newLevel = cVolLevel*10
			if (unitU == "percent") newLevel = numN      
            if (deviceCommand == "increase") {
            	if (unitU == "percent") {
                	newLevel = numN
                }   
                else {
                	newLevel =  currLevel + newLevel
            		newLevel = newLevel < 0 ? 0 : newLevel >100 ? 100 : newLevel
            	}
            }
            if (deviceCommand == "decrease") {
            	if (unitU == "percent") {
                	newLevel = numN
                }   
                else {
                	newLevel =  currLevel - newLevel
            		newLevel = newLevel < 0 ? 0 : newLevel >100 ? 100 : newLevel
                }            
            }
            if (deviceCommand == "setLevel") {
            	if (unitU == "percent") {
                	newLevel = numN
                }   
                else {
                	newLevel =  numN*10
            		newLevel = newLevel < 0 ? 0 : newLevel >100 ? 100 : newLevel
                }            
            }
            if (newLevel > 0 && currState == "off") {
            	deviceD.on()
            	deviceD.setLevel(newLevel)
            }
            else {                                    
            	if (newLevel == 0 && currState == "on") {deviceD.off()}
                else {deviceD.setLevel(newLevel)}
            } 
            result = "Ok, setting  " + deviceD + " volume to " + newLevel + " percent"
            return result
    	}
    	else {
			//NEED LOGIC TO COVER HARMONY ACTIVITIES
			deviceD."${deviceCommand}"()
    		result = "Ok, adjusting the volume of your " + deviceD
            return result
       }
    }
	} catch (e) {
		log.error "Oh no, something went wrong. If this happens again, please reach out for help!"
        result = "Oh no, something went wrong. If this happens again, please reach out for help!"
        state.pTryAgain = true
        return result
	}    
}
/***********************************************************************************************************************
    LAST MESSAGE HANDLER
***********************************************************************************************************************/
def getLastMessageMain() {
	def outputTxt = "The last message sent was," + state.lastMessage + ", and it was sent to, " + state.lastIntent + ", at, " + state.lastTime
    return  outputTxt 
  	if (debug) log.debug "Sending last message to Lambda ${outputTxt} "
}
/***********************************************************************************************************
		SMART HOME MONITOR STATUS AND KEYPAD HANDLER
***********************************************************************************************************/
// ALARM STATUS CHANGE FEEDBACK TO SPEAKERS
def alarmStatusHandler(evt) {
	if (fSecFeed) {
	def curEvtValue = evt.value
	log.info "Smart Home Monitor status changed to: ${curEvtValue}"
		if (shmSynthDevice || shmSonosDevice) {
			if (evt.value == "away") {
            	sendAwayCommand
            	if(shmSynthDevice) shmSynthDevice?.speak("Attention, The alarm system has changed status to armed '${curEvtValue}'")
            	if (shmSonosDevice) 
             	shmSonosDevice?.playTextAndRestore("Attention, The alarm system has changed status to armed '${curEvtValue}'")
            	}
                else if (evt.value == "stay") {
                	if(shmSynthDevice) shmSynthDevice?.speak("Attention, The alarm system has changed status to armed '${curEvtValue}'")
            		if (shmSonosDevice) 
             		shmSonosDevice?.playTextAndRestore("Attention, The alarm system has changed status to armed '${curEvtValue}'")
            		}
                    else if(evt.value == "off") {
                    	if(shmSynthDevice) shmSynthDevice?.speak("Attention, The alarm system has changed status to disarmed")
            			if (shmSonosDevice) 
             			shmSonosDevice?.playTextAndRestore("Attention, The alarm system has changed status to disarmed")
            			}
					}
       			}
			}
def subscribeToEvents() {
	subscribe (location.onSecurityEnable("alarmSystemStatus")?.value)
    log.info "The SHM security system has changed status to '${alarmSystemStatus}'"
} 
/***********************************************************************************************************************
    SCHEDULE HANDLER
***********************************************************************************************************************/
private scheduleHandler(unit) {
    def rowDate = new Date(now())
    def cDay = rowDate.date
    def cHour= rowDate.hours
	def cMin = rowDate.minutes   
    def result
    if (debug) log.debug "Received filter replacement request, scheduler data MIN = ${cMin} , HOUR = ${cHour}, DAY = ${cDay}   "
    if (unit == "filters") {
    	if (debug) log.debug "Received filter replacement request"
        state.scheduledHandler = "filters"
        def xDays = settings.cFilterReplacement
        runOnce(new Date() + xDays , "filtersHandler")
        result = 	"Ok, I have scheduled a reminder to replace the filters in " + settings.cFilterReplacement + " days."+
        			" How would you like me to notify you when it's time to change your filters?"+
        			" You could say, text, audio or flash."
        if (debug) log.debug result
        state.pContCmdsR = "filters"
    	return result
    }
}
/***********************************************************************************************************************
    FILTERS REMINDER
***********************************************************************************************************************/
private filtersHandler() {
def text = "It's time to replace your HVAC filters" 

//NEED A SWITCH FOR THIS
if (state.filterNotif == "flash") {text = "flashing lights"}
if(state.filterNotif == "text")  {text = "sending text"}
if(state.filterNotif == "audio")  {text = "pushed audio message"}
}
/***********************************************************************************************************************
 		SKILL DETAILS
 ***********************************************************************************************************************/
private getProfileDetails() {
	def c = "" 
	def children = getChildApps()	
    	children?.each { child -> 
			c +=child.label +"\n" } 
	def ProfileDetails = "${c}" 
    	return  ProfileDetails
}
private getDeviceDetails() {
// DEVICE categories: cSwitch,cVent,cFan,cTstat,cDoor,cRelay,cContactRelay,cLock,cMotion,cContact,cWater,cPresence,cSpeaker,cSynth,cMedia,cBattery 
def DeviceDetails = [] 
        //switches
        cSwitch.each 	{DeviceDetails << it.displayName +"\n"}
        cTstat.each 	{DeviceDetails << it.displayName +"\n"}
        cLock.each 		{DeviceDetails << it.displayName +"\n"}     
        cMotion.each 	{DeviceDetails << it.displayName +"\n"}
        cContact.each 	{DeviceDetails << it.displayName +"\n"}
        cPresence.each 	{DeviceDetails << it.displayName +"\n"}
        cDoor.each 		{DeviceDetails << it.displayName +"\n"}
        cWater.each 	{DeviceDetails << it.displayName +"\n"}
        cSpeaker.each 	{DeviceDetails << it.displayName +"\n"}
        cVent.each 		{DeviceDetails << it.displayName +"\n"}
        cFan.each 		{DeviceDetails << it.displayName +"\n"}
		cVent.each		{DeviceDetails << it.displayName +"\n"}
    	cRelay.each		{DeviceDetails << it.displayName +"\n"}
        cSynth.each		{DeviceDetails << it.displayName +"\n"}
        cMedia.each		{DeviceDetails << it.displayName +"\n"}
        cBattery.each	{DeviceDetails << it.displayName +"\n"}

        def dUniqueList = DeviceDetails.unique (false)
        dUniqueList = dUniqueList.sort()
        def dUniqueListString = dUniqueList.join("")
        return dUniqueListString
}
/***********************************************************************************************************************
    COMMANDS HANDLER
***********************************************************************************************************************/
private getCommand(command, unit) {
	def deviceType = " "
		if (command) {
	//case "General Commands":
    		deviceType = "general"
        if (unit == "undefined") {
            if (command == "decrease" || command == "down") {
                command = "decrease" 
                deviceType = "general"
            }
            if (command == "increase" || command == "up") {
                command = "increase"
                deviceType = "general"
            }
            if (command == "set" || command == "set level") {
                command = "setLevel"
                deviceType = "general"
            }
        }
	//case "Temperature Commands":  
        if (command == "cooler" || command == "colder" || command =="not cold enough" || command =="too hot" || command == "too warm") {
            command = "decrease"
            deviceType = "temp"
        }     
        else if (command == "freezing" || command =="not hot enough" || command == "too chilly" || command == "too cold" || command == "warmer") {
            command = "increase"
            deviceType = "temp"
        }
        else if (unit == "degrees" || unit =="heat" || unit =="AC" || unit =="cooling" || unit =="heating") {
            if (command == "up") {
           		command = "increase"
        	}
            else if (command == "down") {
            	command = "decrease"
            }
            deviceType = "temp"
        }
        else if (unit=="degrees" || unit.contains("degrees") ||  unit.contains("heat") ||  unit.contains("AC")) {
           deviceType = "temp"    
        }       
    //case "Dimmer Commands":
        if (command == "darker" || command == "too bright" || command == "dim" || command == "dimmer") {
            command = "decrease" 
            deviceType = "light"
        }
        else if  (command == "not bright enough" || command == "brighter" || command == "too dark" || command == "brighten") {
            command = "increase" 
            deviceType = "light"     
        } 
        else if (unit == "percent") {
        	deviceType = "light"
        }
    //case "Volume Commands":
        if  (command == "mute" || command == "quiet" || command == "unmute" ) {
            deviceType = "volume" 
        }
        else if  (command == "too loud" || command == "down" ) {
            command = "decrease"
            deviceType = "volume" 
        }
        else if  (command == "not loud enough" || command == "too quiet" || command == "up") {
            command = "increase"
            deviceType = "volume"
        }
    //case "Fan Control Commands":
        if  (command == "slow down" || command == "too fast" ) {
            command = "decrease"
            deviceType = "fan" 
        }
        else if  (command == "speed up" || command == "too slow") {
            command = "increase"
            deviceType = "fan" 
        }
		else if (command == "high" || command == "medium"|| command == "low") {
			deviceType = "fan"                  
		}         
    //case "Other Commands":
    	if (command == "activate" || command == "deactivate") {
        	deviceType = "miscDevices"
            if (command == "activate") {
            	command = "on"
                }
                if (command == "deactivate") {
                	command = "off"
                    }
        		}
   		if (command == "lock" || command == "unlock") {
			deviceType = "lock"                  
		}
        else if (command == "open" || command == "close") {
			deviceType = "door"                  
		}
        if (command == "arm" || command == "disarm") {
			deviceType = "Security"
            if (command == "arm") {
            command = "on"
            }
            if (command == "disarm") {
            command = "off"
            }
        }
    }
    return ["deviceType":deviceType, "command":command ]                          
}
/************************************************************************************************************
   Custom Color Filter
************************************************************************************************************/       
private colorB() { 
	if (hueCmd == "off") { hues?.off() }
    if (hueCmd1 == "off") { hues1?.off() }
		if (debug) log.debug "color bulbs initiated"
		def hueColor = 0
        def hueColor1 = 0
        fillColorSettings()
        if (color == "White")hueColor = 48
        if (color == "Red")hueColor = 0
        if (color == "Blue")hueColor = 70//60  
        if (color == "Green")hueColor = 39//30
        
        if(color == "Yellow")hueColor = 25//16
        if(color == "Orange")hueColor = 11
        if(color == "Purple")hueColor = 75
        if(color == "Pink")hueColor = 83
        
	def colorB = [hue: hueColor, hue1: hueColor1, saturation: 100, level: (lightLevel as Integer) ?: 100]
    hues*.setColor(colorB)
	}

// call using - parent.fillColorSettings().name
def fillColorSettings() {
	return [
		[ name: "Soft White",				rgb: "#B6DA7C",		h: 83,		s: 44,		l: 67,	],
		[ name: "Warm White",				rgb: "#DAF17E",		h: 51,		s: 20,		l: 100,	],
        [ name: "Very Warm White",			rgb: "#DAF17E",		h: 51,		s: 60,		l: 51,	],
		[ name: "Daylight White",			rgb: "#CEF4FD",		h: 191,		s: 9,		l: 90,	],
		[ name: "Cool White",				rgb: "#F3F6F7",		h: 187,		s: 19,		l: 96,	],
		[ name: "White",					rgb: "#FFFFFF",		h: 0,		s: 0,		l: 100,	],
		[ name: "Alice Blue",				rgb: "#F0F8FF",		h: 208,		s: 100,		l: 97,	],
		[ name: "Antique White",			rgb: "#FAEBD7",		h: 34,		s: 78,		l: 91,	],
		[ name: "Aqua",						rgb: "#00FFFF",		h: 180,		s: 100,		l: 50,	],
		[ name: "Aquamarine",				rgb: "#7FFFD4",		h: 160,		s: 100,		l: 75,	],
		[ name: "Azure",					rgb: "#F0FFFF",		h: 180,		s: 100,		l: 97,	],
		[ name: "Beige",					rgb: "#F5F5DC",		h: 60,		s: 56,		l: 91,	],
		[ name: "Bisque",					rgb: "#FFE4C4",		h: 33,		s: 100,		l: 88,	],
		[ name: "Blanched Almond",			rgb: "#FFEBCD",		h: 36,		s: 100,		l: 90,	],
		[ name: "Blue",						rgb: "#0000FF",		h: 240,		s: 100,		l: 50,	],
		[ name: "Blue Violet",				rgb: "#8A2BE2",		h: 271,		s: 76,		l: 53,	],
		[ name: "Brown",					rgb: "#A52A2A",		h: 0,		s: 59,		l: 41,	],
		[ name: "Burly Wood",				rgb: "#DEB887",		h: 34,		s: 57,		l: 70,	],
		[ name: "Cadet Blue",				rgb: "#5F9EA0",		h: 182,		s: 25,		l: 50,	],
		[ name: "Chartreuse",				rgb: "#7FFF00",		h: 90,		s: 100,		l: 50,	],
		[ name: "Chocolate",				rgb: "#D2691E",		h: 25,		s: 75,		l: 47,	],
		[ name: "Coral",					rgb: "#FF7F50",		h: 16,		s: 100,		l: 66,	],
		[ name: "Corn Flower Blue",			rgb: "#6495ED",		h: 219,		s: 79,		l: 66,	],
		[ name: "Corn Silk",				rgb: "#FFF8DC",		h: 48,		s: 100,		l: 93,	],
		[ name: "Crimson",					rgb: "#DC143C",		h: 348,		s: 83,		l: 58,	],
		[ name: "Cyan",						rgb: "#00FFFF",		h: 180,		s: 100,		l: 50,	],
		[ name: "Dark Blue",				rgb: "#00008B",		h: 240,		s: 100,		l: 27,	],
		[ name: "Dark Cyan",				rgb: "#008B8B",		h: 180,		s: 100,		l: 27,	],
		[ name: "Dark Golden Rod",			rgb: "#B8860B",		h: 43,		s: 89,		l: 38,	],
		[ name: "Dark Gray",				rgb: "#A9A9A9",		h: 0,		s: 0,		l: 66,	],
		[ name: "Dark Green",				rgb: "#006400",		h: 120,		s: 100,		l: 20,	],
		[ name: "Dark Khaki",				rgb: "#BDB76B",		h: 56,		s: 38,		l: 58,	],
		[ name: "Dark Magenta",				rgb: "#8B008B",		h: 300,		s: 100,		l: 27,	],
		[ name: "Dark Olive Green",			rgb: "#556B2F",		h: 82,		s: 39,		l: 30,	],
		[ name: "Dark Orange",				rgb: "#FF8C00",		h: 33,		s: 100,		l: 50,	],
		[ name: "Dark Orchid",				rgb: "#9932CC",		h: 280,		s: 61,		l: 50,	],
		[ name: "Dark Red",					rgb: "#8B0000",		h: 0,		s: 100,		l: 27,	],
		[ name: "Dark Salmon",				rgb: "#E9967A",		h: 15,		s: 72,		l: 70,	],
		[ name: "Dark Sea Green",			rgb: "#8FBC8F",		h: 120,		s: 25,		l: 65,	],
		[ name: "Dark Slate Blue",			rgb: "#483D8B",		h: 248,		s: 39,		l: 39,	],
		[ name: "Dark Slate Gray",			rgb: "#2F4F4F",		h: 180,		s: 25,		l: 25,	],
		[ name: "Dark Turquoise",			rgb: "#00CED1",		h: 181,		s: 100,		l: 41,	],
		[ name: "Dark Violet",				rgb: "#9400D3",		h: 282,		s: 100,		l: 41,	],
		[ name: "Deep Pink",				rgb: "#FF1493",		h: 328,		s: 100,		l: 54,	],
		[ name: "Deep Sky Blue",			rgb: "#00BFFF",		h: 195,		s: 100,		l: 50,	],
		[ name: "Dim Gray",					rgb: "#696969",		h: 0,		s: 0,		l: 41,	],
		[ name: "Dodger Blue",				rgb: "#1E90FF",		h: 210,		s: 100,		l: 56,	],
		[ name: "Fire Brick",				rgb: "#B22222",		h: 0,		s: 68,		l: 42,	],
		[ name: "Floral White",				rgb: "#FFFAF0",		h: 40,		s: 100,		l: 97,	],
		[ name: "Forest Green",				rgb: "#228B22",		h: 120,		s: 61,		l: 34,	],
		[ name: "Fuchsia",					rgb: "#FF00FF",		h: 300,		s: 100,		l: 50,	],
		[ name: "Gainsboro",				rgb: "#DCDCDC",		h: 0,		s: 0,		l: 86,	],
		[ name: "Ghost White",				rgb: "#F8F8FF",		h: 240,		s: 100,		l: 99,	],
		[ name: "Gold",						rgb: "#FFD700",		h: 51,		s: 100,		l: 50,	],
		[ name: "Golden Rod",				rgb: "#DAA520",		h: 43,		s: 74,		l: 49,	],
		[ name: "Gray",						rgb: "#808080",		h: 0,		s: 0,		l: 50,	],
		[ name: "Green",					rgb: "#008000",		h: 120,		s: 100,		l: 25,	],
		[ name: "Green Yellow",				rgb: "#ADFF2F",		h: 84,		s: 100,		l: 59,	],
		[ name: "Honeydew",					rgb: "#F0FFF0",		h: 120,		s: 100,		l: 97,	],
		[ name: "Hot Pink",					rgb: "#FF69B4",		h: 330,		s: 100,		l: 71,	],
		[ name: "Indian Red",				rgb: "#CD5C5C",		h: 0,		s: 53,		l: 58,	],
		[ name: "Indigo",					rgb: "#4B0082",		h: 275,		s: 100,		l: 25,	],
		[ name: "Ivory",					rgb: "#FFFFF0",		h: 60,		s: 100,		l: 97,	],
		[ name: "Khaki",					rgb: "#F0E68C",		h: 54,		s: 77,		l: 75,	],
		[ name: "Lavender",					rgb: "#E6E6FA",		h: 240,		s: 67,		l: 94,	],
		[ name: "Lavender Blush",			rgb: "#FFF0F5",		h: 340,		s: 100,		l: 97,	],
		[ name: "Lawn Green",				rgb: "#7CFC00",		h: 90,		s: 100,		l: 49,	],
		[ name: "Lemon Chiffon",			rgb: "#FFFACD",		h: 54,		s: 100,		l: 90,	],
		[ name: "Light Blue",				rgb: "#ADD8E6",		h: 195,		s: 53,		l: 79,	],
		[ name: "Light Coral",				rgb: "#F08080",		h: 0,		s: 79,		l: 72,	],
		[ name: "Light Cyan",				rgb: "#E0FFFF",		h: 180,		s: 100,		l: 94,	],
		[ name: "Light Golden Rod Yellow",	rgb: "#FAFAD2",		h: 60,		s: 80,		l: 90,	],
		[ name: "Light Gray",				rgb: "#D3D3D3",		h: 0,		s: 0,		l: 83,	],
		[ name: "Light Green",				rgb: "#90EE90",		h: 120,		s: 73,		l: 75,	],
		[ name: "Light Pink",				rgb: "#FFB6C1",		h: 351,		s: 100,		l: 86,	],
		[ name: "Light Salmon",				rgb: "#FFA07A",		h: 17,		s: 100,		l: 74,	],
		[ name: "Light Sea Green",			rgb: "#20B2AA",		h: 177,		s: 70,		l: 41,	],
		[ name: "Light Sky Blue",			rgb: "#87CEFA",		h: 203,		s: 92,		l: 75,	],
		[ name: "Light Slate Gray",			rgb: "#778899",		h: 210,		s: 14,		l: 53,	],
		[ name: "Light Steel Blue",			rgb: "#B0C4DE",		h: 214,		s: 41,		l: 78,	],
		[ name: "Light Yellow",				rgb: "#FFFFE0",		h: 60,		s: 100,		l: 94,	],
		[ name: "Lime",						rgb: "#00FF00",		h: 120,		s: 100,		l: 50,	],
		[ name: "Lime Green",				rgb: "#32CD32",		h: 120,		s: 61,		l: 50,	],
		[ name: "Linen",					rgb: "#FAF0E6",		h: 30,		s: 67,		l: 94,	],
		[ name: "Maroon",					rgb: "#800000",		h: 0,		s: 100,		l: 25,	],
		[ name: "Medium Aquamarine",		rgb: "#66CDAA",		h: 160,		s: 51,		l: 60,	],
		[ name: "Medium Blue",				rgb: "#0000CD",		h: 240,		s: 100,		l: 40,	],
		[ name: "Medium Orchid",			rgb: "#BA55D3",		h: 288,		s: 59,		l: 58,	],
		[ name: "Medium Purple",			rgb: "#9370DB",		h: 260,		s: 60,		l: 65,	],
		[ name: "Medium Sea Green",			rgb: "#3CB371",		h: 147,		s: 50,		l: 47,	],
		[ name: "Medium Slate Blue",		rgb: "#7B68EE",		h: 249,		s: 80,		l: 67,	],
		[ name: "Medium Spring Green",		rgb: "#00FA9A",		h: 157,		s: 100,		l: 49,	],
		[ name: "Medium Turquoise",			rgb: "#48D1CC",		h: 178,		s: 60,		l: 55,	],
		[ name: "Medium Violet Red",		rgb: "#C71585",		h: 322,		s: 81,		l: 43,	],
		[ name: "Midnight Blue",			rgb: "#191970",		h: 240,		s: 64,		l: 27,	],
		[ name: "Mint Cream",				rgb: "#F5FFFA",		h: 150,		s: 100,		l: 98,	],
		[ name: "Misty Rose",				rgb: "#FFE4E1",		h: 6,		s: 100,		l: 94,	],
		[ name: "Moccasin",					rgb: "#FFE4B5",		h: 38,		s: 100,		l: 85,	],
		[ name: "Navajo White",				rgb: "#FFDEAD",		h: 36,		s: 100,		l: 84,	],
		[ name: "Navy",						rgb: "#000080",		h: 240,		s: 100,		l: 25,	],
		[ name: "Old Lace",					rgb: "#FDF5E6",		h: 39,		s: 85,		l: 95,	],
		[ name: "Olive",					rgb: "#808000",		h: 60,		s: 100,		l: 25,	],
		[ name: "Olive Drab",				rgb: "#6B8E23",		h: 80,		s: 60,		l: 35,	],
		[ name: "Orange",					rgb: "#FFA500",		h: 39,		s: 100,		l: 50,	],
		[ name: "Orange Red",				rgb: "#FF4500",		h: 16,		s: 100,		l: 50,	],
		[ name: "Orchid",					rgb: "#DA70D6",		h: 302,		s: 59,		l: 65,	],
		[ name: "Pale Golden Rod",			rgb: "#EEE8AA",		h: 55,		s: 67,		l: 80,	],
		[ name: "Pale Green",				rgb: "#98FB98",		h: 120,		s: 93,		l: 79,	],
		[ name: "Pale Turquoise",			rgb: "#AFEEEE",		h: 180,		s: 65,		l: 81,	],
		[ name: "Pale Violet Red",			rgb: "#DB7093",		h: 340,		s: 60,		l: 65,	],
		[ name: "Papaya Whip",				rgb: "#FFEFD5",		h: 37,		s: 100,		l: 92,	],
		[ name: "Peach Puff",				rgb: "#FFDAB9",		h: 28,		s: 100,		l: 86,	],
		[ name: "Peru",						rgb: "#CD853F",		h: 30,		s: 59,		l: 53,	],
		[ name: "Pink",						rgb: "#FFC0CB",		h: 350,		s: 100,		l: 88,	],
		[ name: "Plum",						rgb: "#DDA0DD",		h: 300,		s: 47,		l: 75,	],
		[ name: "Powder Blue",				rgb: "#B0E0E6",		h: 187,		s: 52,		l: 80,	],
		[ name: "Purple",					rgb: "#800080",		h: 300,		s: 100,		l: 25,	],
		[ name: "Red",						rgb: "#FF0000",		h: 0,		s: 100,		l: 50,	],
		[ name: "Rosy Brown",				rgb: "#BC8F8F",		h: 0,		s: 25,		l: 65,	],
		[ name: "Royal Blue",				rgb: "#4169E1",		h: 225,		s: 73,		l: 57,	],
		[ name: "Saddle Brown",				rgb: "#8B4513",		h: 25,		s: 76,		l: 31,	],
		[ name: "Salmon",					rgb: "#FA8072",		h: 6,		s: 93,		l: 71,	],
		[ name: "Sandy Brown",				rgb: "#F4A460",		h: 28,		s: 87,		l: 67,	],
		[ name: "Sea Green",				rgb: "#2E8B57",		h: 146,		s: 50,		l: 36,	],
		[ name: "Sea Shell",				rgb: "#FFF5EE",		h: 25,		s: 100,		l: 97,	],
		[ name: "Sienna",					rgb: "#A0522D",		h: 19,		s: 56,		l: 40,	],
		[ name: "Silver",					rgb: "#C0C0C0",		h: 0,		s: 0,		l: 75,	],
		[ name: "Sky Blue",					rgb: "#87CEEB",		h: 197,		s: 71,		l: 73,	],
		[ name: "Slate Blue",				rgb: "#6A5ACD",		h: 248,		s: 53,		l: 58,	],
		[ name: "Slate Gray",				rgb: "#708090",		h: 210,		s: 13,		l: 50,	],
		[ name: "Snow",						rgb: "#FFFAFA",		h: 0,		s: 100,		l: 99,	],
		[ name: "Spring Green",				rgb: "#00FF7F",		h: 150,		s: 100,		l: 50,	],
		[ name: "Steel Blue",				rgb: "#4682B4",		h: 207,		s: 44,		l: 49,	],
		[ name: "Tan",						rgb: "#D2B48C",		h: 34,		s: 44,		l: 69,	],
		[ name: "Teal",						rgb: "#008080",		h: 180,		s: 100,		l: 25,	],
		[ name: "Thistle",					rgb: "#D8BFD8",		h: 300,		s: 24,		l: 80,	],
		[ name: "Tomato",					rgb: "#FF6347",		h: 9,		s: 100,		l: 64,	],
		[ name: "Turquoise",				rgb: "#40E0D0",		h: 174,		s: 72,		l: 56,	],
		[ name: "Violet",					rgb: "#EE82EE",		h: 300,		s: 76,		l: 72,	],
		[ name: "Wheat",					rgb: "#F5DEB3",		h: 39,		s: 77,		l: 83,	],
		[ name: "White Smoke",				rgb: "#F5F5F5",		h: 0,		s: 0,		l: 96,	],
		[ name: "Yellow",					rgb: "#FFFF00",		h: 60,		s: 100,		l: 50,	],
		[ name: "Yellow Green",				rgb: "#9ACD32",		h: 80,		s: 61,		l: 50,	],
	]
}
/************************************************************************************************************
   Version/Copyright/Information/Help
************************************************************************************************************/
private def textAppName() {
	def text = app.label // Parent Name
}	
private def textVersion() {
	def text = "4.0"
}
private def textLicense() {
	def text =
	"Licensed under the Apache License, Version 2.0 (the 'License'); "+
	"you may not use this file except in compliance with the License. "+
	"You may obtain a copy of the License at"+
	" \n"+
	" http://www.apache.org/licenses/LICENSE-2.0"+
	" \n"+
	"Unless required by applicable law or agreed to in writing, software "+
	"distributed under the License is distributed on an 'AS IS' BASIS, "+
	"WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. "+
	"See the License for the specific language governing permissions and "+
	"limitations under the License."
}
private textProfiles() {
def text = childApps.size()     
}
private def textHelp() {
	def text =
		"This smartapp allows you to use an Alexa device to generate a voice or text message on on a different device"
        "See our Wikilinks page for user information!"
		}

/************************************************************************************************************
   Page status and descriptions 
************************************************************************************************************/       
// MAIN PARENT PAGE
def mIntentComplete(){def result = ""
    if (mDevicesComplete() || mFeedbackComplete()) {
    	result = "complete"}
    	result}
def mIntentSettings() {def text = "Tap here to configure" 
	if (mDevicesComplete() || mFeedbackComplete()) {
    	text = "Configured"}
        else text = "Tap here to configure"
		text}
def mCreateComplete(){def result = ""
    if (cCreateComplete()) {
    	result = "Control Profiles Created"}
    	result}
def mCreateSettings() { def text = "No Profiles have been configured. Tap here to begin"
    if (cCreateComplete()) {
        text = "One profile has been configured. Tap here to view and change"}
        text}
// MAIN INTENT PAGE
def mDevicesComplete(){def result = ""
    if (cSwitch || cVent || cFan || cStat || cDoor || cLock || cMotion || cContact || cPresence || cSpeaker || cBattery || cWeather) {
    	result = "complete"	}
   		result}
def mDevicesSettings() {def text = "Tap here to configure settings" 
    if (cSwitch || cVent || cFan || cStat || cDoor || cLock || cMotion || cContact || cPresence || cSpeaker || cBattery || cWeather) {
    	text = "Configured"}
    	else text = "Tap here to choose devices"
		text}
def mFeedbackComplete(){def result = ""
    if (fShowSwitches || fShowContacts || fShowLocks || fShowMotion || fShowPresence || fShowTstat || fShowWeather || fSecFeed) {
    	result = "complete"}
    	result}
def mFeedbackSettings() {def text = ""
    if (fShowSwitches || fShowContacts || fShowLocks || fShowMotion || fShowPresence || fShowTstat || fShowWeather || fSecFeed) {
    	text = "Configured"}
    	else text = "Tap here to configure Feedback"
		text}
def mDefaultsComplete(){def result = ""
    if ("cLevel || cVolLevel || cTemperature || cFilterReplacement || cHigh || cMedium || cLow || cLowBattery || cFanLevel || cInactiveDev || cPIN") {
    	result = "complete"}
    	result}
def mDefaultsSettings() {def text = "" 
    if ("cLevel || cVolLevel || cTemperature || cFilterReplacement || cHigh || cMedium || cLow || cLowBattery || cFanLevel || cInactiveDev || cPIN") {
    	text = "Defaults are Configured"}
    	else text = "Tap here to configure Defaults"
		text}
def mSHMSecComplete(){def result = ""
    if ("fSecFeed") {
    	result = "complete"}
    	result}
def mSHMSecSettings() {def text = "" 
    if ("fSecFeed") {
    	text = "Security options configured"}
    	else text = "Tap here to configure Defaults"
		text}        
// VIEW AND CREATE PROFILES PAGE
def cCreateComplete(){def result = ""
    if (childApps.size()) {
    	result = "complete"}
    	result}
def cCreateSettings() {def text = "No Profiles have been configured. Tap here to begin"
    def ch = childApps.size()     
   		if (ch == 1) {text = "One Profile has been configured. Tap here to view and change"}
    	else {if (ch > 1) { text = "${ch} Profiles have been created"}}
    	text}
def pCreateComplete(){ def result = ""
    if (childApps.size()) {
    	result = "complete"}
    	result}
def pCreateSettings() { def text = "No Profiles have been configured. Tap here to begin"
    def ch = childApps.size()     
    	if (ch > 0) {text = "Profiles have been configured. Tap here to view and change"}
    	text}    
// INSTALL AND SUPPORT PAGE
def mSupportComplete(){def result = ""
    if (notifyOn || feedbackOn) {
    	result = "complete"	}
    	result}
def mSupportSettings() {def text = "Tap here to configure settings" 
    if (notifyOn || feedbackOn) {text = "Installed modules are active"}
    	else text = "There are no Activated Modules"
		text}
// SECURITY DATA PAGE
def mSecurityComplete(){def result = ""
    if (debug || ShowLicense || ShowTokens) {
    	result = "complete"}
    	result}
def mSecuritySettings() {def text = "Tap here to configure settings" 
    if (debug || ShowLicense || ShowTokens) {
    	text = "Configured - Information is now displayed in the IDE Live Logs"}
    	else text = "Configure"
        text}
def dashboardSettings() { def text = "Tap here to view Dashboard" 
    	text}
def dashboardComplete(){def result = ""
    result = "complete"	}
    result
// CONTROL PROFILE
def pSendSettings(){def result = ""
    if (synthDevice || sonosDevice || sendContactText ||sendText || push || notify) {
    	result = "complete"}
   		result}
def pSendComplete() {def text = "Tap here to configure settings" 
    if (synthDevice || sonosDevice || sendContactText ||sendText || push || notify) {
    	text = "Configured"}
    	else text = "Tap to Show"
		text}
def pConfigSettings(){def result = ""
    if (ShowPreMsg || Acustom || Arepeat ||AfeedBack || ContCmds || ContCmdsR) {
    	result = "complete"}
    	result}
def pConfigComplete() {def text = "Tap here to configure settings" 
    if (ShowPreMsg || Acustom || Arepeat ||AfeedBack || ContCmds || ContCmdsR) {
    	text = "Configured"}
    	else text = "Tap to Show"
		text}
def pScenesComplete() {def text = "No Scene have been configured. Tap here to begin"
    def ch = childApps.size()     
    if (ch == 1) {text = "One Scene has been configured. Tap here to view and change"}
    else {if (ch > 1) {text = "${ch} Scenes have been created"}}
    text}
def pScenesSettings(){def result = ""
    if (childApps.size()) {
    	result = "complete"}
    	result}
def pActionsSettings(){def result = ""
    if (switches || otherSwitch || hues ||dimmers || otherDimmers || flashSwitches || newMode || runRoutine) {
    	result = "complete"}
    	result}
def pActionsComplete() {def text = "Tap here to configure settings" 
    if (switches || otherSwitch || hues ||dimmers || otherDimmers || flashSwitches || newMode || runRoutine) {text = "Configured"}
    	else text = "Tap to Show"
        text}
def pRestrictSettings(){ def result = "" 
	if (modes || runDay || hues ||startingX || endingX) {
    	result = "complete"}
        result}
def pRestrictComplete() {def text = "Tap here to configure settings" 
    if (modes || runDay || hues ||startingX || endingX) {
    	text = "Configured"}
    	else text = "Tap to Show"
        text}
 
