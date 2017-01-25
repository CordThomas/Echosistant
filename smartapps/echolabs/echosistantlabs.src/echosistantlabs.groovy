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
				page name: "mDevices"
                page name: "mDefaults"            
    		page name: "mProfiles"
            page name: "mSettings"
           		page name: "mSkill"
            		page name: "mProfileDetails"
            		page name: "mDeviceDetails" 
                page name: "mTokens"
                    page name: "mConfirmation"            
                    	page name: "mTokenReset"            
}            
//dynamic page methods
page name: "mainParentPage"
    def mainParentPage() {	
       dynamicPage(name: "mainParentPage", title:"", install: true, uninstall:false) {
       		section ("") {
                href "mIntent", title: "Main Home",
                	image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/app-Echosistant.png"    
				href "mProfiles", title: "Room Details",
                	image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_msg.png"
				href "mSettings", title: "General Settings",
                	image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Config.png"
            }
		}
	}
page name: "mIntent"
    def mIntent() {
    	dynamicPage (name: "mIntent", title: "", install: false, uninstall: false) {
			section("") {
	            href "mDevices", title: "Select Devices",
                image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_devices.png"    
			}               
            section ("") {
                href "mDefaults", title: "Change Defaults",
                image: "https://raw.githubusercontent.com/BamaRayne/Echosistant/master/smartapps/bamarayne/echosistant.src/Echosistant_Extra.png"
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
                /*
                section ("Weather Alerts") {
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
                */
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
                     section ("Security") {  
                        input "cPIN", "password", title: "Set a PIN number to prevent unathorized use of Voice Control", default: false, required: false
                    }
                }
        }
page name: "mProfiles"    
    def mProfiles() {
        dynamicPage (name: "mProfiles", title: "", install: true, uninstall: false) {
        	if (childApps.size()) { 
            	section(childApps.size()==1 ? "One Room configured" : childApps.size() + " Room configured" )
            }
            if (childApps.size()) {  
            	section("Rooms",  uninstall: false){
                	app(name: "rooms", appName: "Rooms", namespace: "EchoLabs", title: "Create a new Room", multiple: true,  uninstall: false)
            	}
            }
            else {
            	section("Rooms",  uninstall: false){
            		paragraph "NOTE: Looks like you haven't created any Rooms yet.\n \nPlease make sure you have installed the Rooms Smart App Add-on before creating a new Room!"
            		app(name: "room", appName: "Rooms", namespace: "EchoLabs", title: "Create a new Room", multiple: true,  uninstall: false)
        		}
            }
       }
}
page name: "mSettings"  
	def mSettings(){
        dynamicPage(name: "mSettings", uninstall: true) {
 			section ("Directions, How-to's, and Troubleshooting") { 
 				href url:"http://thingsthataresmart.wiki/index.php?title=EchoSistant", title: "EchoSistant Wiki", description: none
            input "debug", "bool", title: "Enable Debug Logging", default: false, submitOnChange: true 
            }
            section ("Amazon AWS Skill Details") { 
				href "mSkill", title: "Tap to view setup data for the AWS Main Intent Skill...", description: ""
            }                
            section ("Application ID and Token") {
            	input "showTokens", "bool", title: "Show IDs", default: false, submitOnChange: true
            		if (showTokens) paragraph "The Security Tokens are now displayed in the Live Logs section of the IDE"
    				if (showTokens) log.info "STappID = '${app.id}' , STtoken = '${state.accessToken}'"
            		if (showTokens) paragraph 	"Access token:\n"+
                                       			"${state.accessToken}\n"+
                                        		"Application ID:\n"+
                                        		"${app.id}"
            
             	href "mTokens", title: "Revoke/Reset Security Access Token", description: none
             	def msg = state.accessToken != null ? state.accessToken : 	"Could not create Access Token. OAuth may not be enabled. "+
             																"Go to the SmartApp IDE settings to enable OAuth."            
            
            }
             section("Tap below to remove the ${textAppName()} application.  This will remove ALL Profiles and the App from the SmartThings mobile App."){
             }	
    	}	            	
	}

page name: "mSkill"
    def mSkill(){
            dynamicPage(name: "mSkill", uninstall: false) {
 			section ("List of Profiles") { 
				href "mProfileDetails", title: "View your List of Profiles for copy & paste to the AWS Skill...", description: "", state: "complete" 
            }
            section ("List of Devices") {
				href "mDeviceDetails", title: "View your List of Devices for copy & paste to the AWS Skill...", description: "", state: "complete" 
				}
            }
        }
    
    page name: "mProfileDetails"
        def mProfileDetails(){
                dynamicPage(name: "mProfileDetails", uninstall: false) {
                section ("LIST_OF_PROFILES") { 
                    def ProfileList = getProfileDetails()   
                        paragraph ("${ProfileList}")
                        log.info "\nLIST_OF_PROFILES \n${ProfileList}"
                            }
                        }
                    } 
    
    page name: "mDeviceDetails"
        def mDeviceDetails(){
                dynamicPage(name: "mDeviceDetails", uninstall: false) {
                section ("LIST_OF_DEVICES") { 
                    def DeviceList = getDeviceDetails()
                        paragraph ("${DeviceList}")
                        log.info "\nLIST_OF_DEVICES \n${DeviceList}"
                            }
                        }
                    }    
    page name: "mTokens"
        def mTokens(){
                dynamicPage(name: "mTokens", title: "Security Tokens", uninstall: false){
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
                    section ("Reset Access Token / Application ID"){
                        href "mConfirmation", title: "Reset Access Token and Application ID", description: none
                        }
                    }
                } 
        page name: "mConfirmation"
            def mConfirmation(){
                    dynamicPage(name: "mConfirmation", title: "Reset/Renew Access Token Confirmation", uninstall: false){
                        section {
                            href "mTokenReset", title: "Reset/Renew Access Token", description: "Tap here to confirm action - READ WARNING BELOW"
                            paragraph "PLEASE CONFIRM! By resetting the access token you will disable the ability to interface this SmartApp with your Amazon Echo."+
                            "You will need to copy the new access token to your Amazon Lambda code to re-enable access." +
                            "Tap below to go back to the main menu with out resetting the token. You may also tap Done above."
                            }
                        section(" "){
                            href "mainParentPage", title: "Cancel And Go Back To Main Menu", description: none 
                            }
                        }
                    }
                page name: "mTokenReset"
                    def mTokenReset(){
                            dynamicPage(name: "mTokenReset", title: "Access Token Reset", uninstall: false){
                                section{
                                    revokeAccessToken()
                                    state.accessToken = null
                                    OAuthToken()
                                    def msg = state.accessToken != null ? "New access token:\n${state.accessToken}\n\n" : "Could not reset Access Token."+
                                    "OAuth may not be enabled. Go to the SmartApp IDE settings to enable OAuth."
                                    paragraph "${msg}"
                                    paragraph "The new access token and app ID are now displayed in the Live Logs tab of the IDE."
                                    log.info "New IDs: STappID = '${app.id}' , STtoken = '${state.accessToken}'"
                                }
                                section(" "){ 
                                    href "mainParentPage", title: "Tap Here To Go Back To Main Menu", description: none 
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
        sendLocationEvent(name: "echoSistant", value: "refresh", data: [profiles: getProfileList()] , isStateChange: true, descriptionText: "echoSistant Profile list refresh")
        def children = getChildApps()
    	if (debug) log.debug "Refreshing Profiles for CoRE, ${getChildApps()*.label}"
		if (!state.accessToken) {
        	if (debug) log.error "Access token not defined. Attempting to refresh. Ensure OAuth is enabled in the SmartThings IDE."
                OAuthToken()
			}
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
                state.pContCmdsR = null
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
    
    if (fDevice == "undefined" || fQuery == "undefined" || fOperand == "undefined" || fCommand == "undefined") {

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
	}
        outputTxt = "Sorry, I didn't get that, "
        state.pTryAgain = true
        return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
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
        ctPIN = ctPIN == "?" ? "undefined" : ctPIN
        if (ctNum == "undefined" || ctNum =="?") {ctNum = 0 } 
        if (ctCommand =="?") {ctCommand = "undefined"} 
        ctNum = ctNum as int

    	if (ctCommand == "undefined" || ctNum == "undefined" || ctPIN == "undefined" || ctDevice == "undefined" || ctUnit == "undefined" || ctGroup == "undefined") {
        
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
            if (deviceType == "volume" || deviceType == "general" || deviceType == "light") {      
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
                        if (command == "disable" | command == "deactivate"|| command == "stop") {command = "off"}
                        if (command == "enable" | command == "activate"|| command == "start") {command = "on"}    
                    	if (ctNum > 0 && ctUnit == "minutes") {
                        	device = device.label
                            delay = true
                            data = [type: "cSwitch", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            runIn(ctNum*60, controlHandler, [data: data])
                            if (command == "on" || command == "off" ) {outputTxt = "Ok, turning " + ctDevice + " " + command + ", in " + numText}
                            else if (command == "decrease") {outputTxt = "Ok, decreasing the " + ctDevice + " level in " + numText}
                            else if (command == "increase") {outputTxt = "Ok, increasing the " + ctDevice + " level in " + numText}
                            return ["outputTxt":outputTxt, "pContCmds":state.pContCmds, "pContCmdsR":state.pContCmdsR, "pTryAgain":state.pTryAgain, "pPIN":pPIN]
                    	}
                        else {
                        	delay = false
                        	data = [type: "cSwitch", command: command, device: device, unit: ctUnit, num: ctNum, delay: delay]
                            outputTxt = controlHandler(data)
                            if (command == "decrease" || command == "increase" ) {state.pContCmdsR = "level"}
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
    	outputTxt = "Sorry, I didn't get that, "
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
    if (command == "cancel" || command == "stop" || command == "disable" || command == "deactivate" || command == "unschedule") {
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
			if(state.usePIN_D == true || state.usePIN_T == true || state.usePIN_L == true || state.usePIN_S != null) {
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
	if (command == "start" || command == "enable" || command == "activate" || command == "schedule") {
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
			if (group == "thermostats" || group == "locks" || group == "doors") {
            	if (group == "thermostats") {state.usePIN_T = true}
            	if (group == "locks") {state.usePIN_L = true}
            	if (group == "doors") {state.usePIN_D = true}
            	if (debug) log.debug "Group:'${group}' PIN: T- '${state.usePIN_T}', L-'${state.usePIN_L}', D-'${state.usePIN_D}' "
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
            if (data == "disablelocks" || data == "disablethermostats" || data == "disabledoors"){ 
                if (data == "disablelocks"){state.usePIN_L = false}
                else if (data == "disablethermostats") {state.usePIN_T = false}
                else if (data == "disabledoors") {state.usePIN_D = false}  
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
        if (command == "colder" || command =="not cold enough" || command =="too hot" || command == "too warm") {
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
        if (command == "lock" || command == "unlock") {
			deviceType = "lock"                  
		}
        else if (command == "open" || command == "close") {
			deviceType = "door"                  
		}
    }
    return ["deviceType":deviceType, "command":command ]                          
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
/************************************************************************************************************
   SOUNDS
************************************************************************************************************/
private loadText() {
		switch (actionType) {
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
		case "Piano":
			state.sound = [uri: "http://s3.amazonaws.com/smartapp-media/sonos/piano2.mp3", duration: "10"]
			break;
		case "Lightsaber":
			state.sound = [uri: "http://s3.amazonaws.com/smartapp-media/sonos/lightsaber.mp3", duration: "10"]
			break;
		default:
			state.sound = [uri: "http://s3.amazonaws.com/smartapp-media/sonos/bell1.mp3", duration: "10"]
			break;
        }
}

