package isen.java2.project.app;

import isen.java2.project.service.StageService;
import isen.java2.project.service.ViewService;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ContactApp extends Application {
	
	public ContactApp() {

	}

	@Override
	public void start(Stage primaryStage) {
		StageService.initPrimaryStage(primaryStage);
		StageService.showView(ViewService.getView("HomeScreen"));
		StageService.getPrimaryStage().getIcons().add(new Image("/isen/contactApp/img/contact.png"));
	}

	public static void main(String[] args) {
		launch(args);
	}

}