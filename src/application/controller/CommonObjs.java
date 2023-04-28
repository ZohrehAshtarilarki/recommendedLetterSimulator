package application.controller;

import java.util.List;

import application.model.Recommendation;

public class CommonObjs {
	private Recommendation activeRecommendation;
	private List<Recommendation> searchedRecommendations;

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

	public List<Recommendation> getSearchedRecommendations() {
		return searchedRecommendations;
	}

	public void setSearchedRecommendations(List<Recommendation> searchedRecommendations) {
		this.searchedRecommendations = searchedRecommendations;
	}
	
}
