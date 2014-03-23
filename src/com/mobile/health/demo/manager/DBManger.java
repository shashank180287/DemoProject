package com.mobile.health.demo.manager;

import java.util.ArrayList;
import java.util.List;

import com.mobile.health.demo.entity.PersonDetails;
import com.mobile.health.demo.entity.PersonDetailsTableModel;

public class DBManger {

	private static List<PersonDetails> personDetails;
	
	public static List<PersonDetails> fetchAllPersonDetails() {
		return null;
		
	}
	
	public static PersonDetailsTableModel fetchPersonDetailsTableModel() {
		PersonDetailsTableModel personDetailsTableModel = new PersonDetailsTableModel();
		personDetails = new ArrayList<PersonDetails>();
		personDetails.add(new PersonDetails("Ram", "Das", "Male", 23, "Lucknow", "Lucknow"));
		personDetails.add(new PersonDetails("Shyam", "Das", "Male", 37, "Lucknow", "Lucknow"));
		personDetails.add(new PersonDetails("Shila", "Devi", "Female", 28, "Lucknow", "Lucknow"));
		personDetails.add(new PersonDetails("Ramu", "Das", "Male", 13, "Lucknow", "Lucknow"));
		personDetails.add(new PersonDetails("Ram", "Das", "Male", 23, "Lucknow", "Lucknow"));
		personDetails.add(new PersonDetails("Shyam", "Das", "Male", 37, "Lucknow", "Lucknow"));
		personDetails.add(new PersonDetails("Shila", "Devi", "Female", 28, "Lucknow", "Lucknow"));
		personDetails.add(new PersonDetails("Ramu", "Das", "Male", 13, "Lucknow", "Lucknow"));
		personDetailsTableModel.setPersonDetails(personDetails);
		return personDetailsTableModel;
	}
	
	public static void addPersonDetails(PersonDetails personDetail) {
		personDetails.add(personDetail);
	}

	public static int generateId() {
		int id = personDetails.size()+1;
		return id;
	}

	public static void updatePersonDetails(int index,
			PersonDetails personDetail) {
		personDetails.set(index, personDetail);
		
	}

	public static void removePersonDetailAtIndex(int index) {
		personDetails.remove(index);		
	}
	
	public static List<PersonDetails> getPersonDetails() {
		return personDetails;
	}
}
