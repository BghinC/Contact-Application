package isen.java2.project.util;

import isen.java2.project.model.Person;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class PersonValueFactory implements Callback<ListView<Person>, ListCell<Person>>{
	
	
	
	@Override
	public ListCell<Person> call(ListView<Person> param) {
		// TODO Auto-generated method stub
		return new PersonListCell();
	}
	
}
