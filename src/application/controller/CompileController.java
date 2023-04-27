package application.controller;

import java.sql.SQLException;

import application.dal.CommonDAOs;
import application.model.Faculty;
import application.model.Recommendation;
import application.utils.CompilerInt;
import application.utils.TxtCompiler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class CompileController {
	private CompilerInt compiler;

	@FXML Button compileButton;
	@FXML TextArea textArea;
	@FXML AnchorPane mainBox;
	@FXML Button newRecommendationButton;
	@FXML Button logoutButton;
	
	public CompileController() {
		this.compiler = new TxtCompiler();
	}
	
	@FXML public void compileOp() {
		CommonObjs commObj = CommonObjs.getInstance();
		CommonDAOs commDAOs = CommonDAOs.getInstance();
		Recommendation currentRecommednation = commObj.getActiveRecommendation();
		Faculty faculty = null;
		try {
			faculty = commDAOs.getFacultyDAO().getFaculty();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		String recommednationText = compiler.compile(faculty, currentRecommednation);
		textArea.setText(recommednationText);
	}
	
	@FXML public void logOutOp() {}
	@FXML public void newRecommendationOp() {}

}
