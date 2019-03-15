package isen.java2.project.service;

import java.util.List;

import isen.java2.project.db.daos.PersonDao;
import isen.java2.project.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonService {

	private ObservableList<Person> people;

	private PersonService() {
		people = FXCollections.observableArrayList();
		
		PersonDao pDao = new PersonDao();
		List<Person> list = pDao.listPeople();
		
		people.addAll(list);
		
	}

	public static ObservableList<Person> getPeople() {
		return PersonServiceHolder.INSTANCE.people;
	}

	public static void addPerson(Person person) {
		PersonServiceHolder.INSTANCE.people.add(person);
	}

	private static class PersonServiceHolder {
		private static final PersonService INSTANCE = new PersonService();
	}

}
