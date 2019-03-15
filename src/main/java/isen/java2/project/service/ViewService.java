package isen.java2.project.service;

import java.io.IOException;

import isen.java2.project.app.ContactApp;
import javafx.fxml.FXMLLoader;

public class ViewService {

	public static <T> T getView(String id) {
		return getLoader(id).getRoot();
	}

	private static FXMLLoader getLoader(String id) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ContactApp.class.getClassLoader().getResource("isen/contactApp/view/" + id + ".fxml"));
			loader.load();
			return loader;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}
