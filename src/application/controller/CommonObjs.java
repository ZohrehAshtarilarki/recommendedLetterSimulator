package application.controller;


public class CommonObjs {

	private final static CommonObjs commonObj = new CommonObjs();
	
	private CommonObjs() {
		
	}
	
	public static CommonObjs getInstance() {
		return commonObj;
	}
	
}
