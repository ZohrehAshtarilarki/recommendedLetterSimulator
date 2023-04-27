package application.controller;

import application.model.Recommendation;

public class CommonObjs {
	private Recommendation activeRecommendation;

	private final static CommonObjs commonObj = new CommonObjs();
	
	private CommonObjs() {
		
	}
	
	public static CommonObjs getInstance() {
		return commonObj;
	}

	public Recommendation getActiveRecommendation() {
		return activeRecommendation;
	}

	public void setActiveRecommendation(Recommendation activeRecommendation) {
		this.activeRecommendation = activeRecommendation;
	}
	
}
