package application.dal;

import java.sql.SQLException;
import java.util.List;

import application.model.Recommendation;

public interface RecommendationDAOInt {
	public Recommendation getRecommendation(int recommendationId) throws SQLException;
//	adjust parameters once model an DB stuff figure out
	public Recommendation addRecommendation() throws SQLException;
	public void deleteRecommendation(int recommendationId) throws SQLException;
	public void updateRecommendation(Recommendation recommendation) throws SQLException;
	public List<Recommendation> searchRecommendationByLastName(String lastName) throws SQLException;
	
}
