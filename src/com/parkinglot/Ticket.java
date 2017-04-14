package com.parkinglot;

public class Ticket {

	private Integer parkingSlot;
	private String registrationNo;
	private String carColor;
	
	
	public Ticket(String aRegistrationNo,String aCarColor,Integer aParkingSlot){
		registrationNo = aRegistrationNo;
		carColor = aCarColor;
		parkingSlot = aParkingSlot;
	}
	
	public String getRegistrationNo(){
		return registrationNo;
	}
	
	public String getCarColor(){
		return carColor;
	}
	
	public Integer getParkingSlot(){
		return parkingSlot;
	}
	
}
