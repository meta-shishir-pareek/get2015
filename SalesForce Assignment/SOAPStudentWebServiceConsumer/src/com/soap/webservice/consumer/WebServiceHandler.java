package com.soap.webservice.consumer;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.Query;
import com.sforce.soap.partner.SessionHeader;
import com.sforce.soap.partner.SforceServiceLocator;
import com.sforce.soap.partner.SoapBindingStub;
import com.sforce.soap.schemas._class.WebServiceContact.WebServiceContactBindingStub;
import com.sforce.soap.schemas._class.WebServiceContact.WebServiceContactServiceLocator;


public class WebServiceHandler{
	public static void main(String args[]){
		try{
			SoapBindingStub bind = (SoapBindingStub)new SforceServiceLocator().getSoap();
			LoginResult lr=bind.login("shishirpareek@metacube.com", "Bh00taya1234)(*&5^AY0OlGs2mXQXAm1zYpIzhRHp1");
			SessionHeader sh = new SessionHeader();
			sh.setSessionId(lr.getSessionId());
			WebServiceContactServiceLocator locator = new WebServiceContactServiceLocator();
			URL url = new URL(locator.getWebServiceContactAddress());
			WebServiceContactBindingStub stub=new WebServiceContactBindingStub(url, locator);
		    stub.setHeader(locator.getWebServiceContactAddress(),"SessionHeader",sh);
		    String query = "SELECT Id,Name,Field_Experience__c,Languages__c,Level__c,Next_Birthday__c,Subjects__c FROM Contact";
		    System.out.println(stub.queryForContact(query));
		    System.out.println(stub.createNewStudent("Chota AJ","Height Kam Fight Zayada","a0828000008V87E","MALE",new Date("28/02/1993")));
		}
		 catch (Exception e) {
			e.printStackTrace(); 
		}  
	}
}