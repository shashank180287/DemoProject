package com.mobile.health.demo.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mobile.health.demo.entity.PersonDetails;

public class BakDBManger {

	private static List<PersonDetails> personDetails = new ArrayList<PersonDetails>();
	
	public static int getCurrentIdCursor() {
		return personDetails.size();
	}

	public static void addPersonDetails(PersonDetails personDetail) {
		personDetails.add(personDetail);
	}

	public static void updatePersonDetails(PersonDetails personDetail) {
		if(personDetail.getId()==null)
			throw new IllegalArgumentException("Id can not be null");
		
		for (PersonDetails perDetail : personDetails) {
			if(perDetail.getId().equals(personDetail.getId())){
				perDetail.setFirstName(personDetail.getFirstName());
				perDetail.setLastName(personDetail.getLastName());
				perDetail.setGender(personDetail.getGender());
				perDetail.setAge(personDetail.getAge());
				perDetail.setAddress(personDetail.getAddress());
				perDetail.setPanchayat(personDetail.getPanchayat());
			}
		}
	}

	public static void removePersonDetails(PersonDetails personDetail) {
		if(personDetail.getId()==null)
			throw new IllegalArgumentException("Id can not be null");
		
		Iterator<PersonDetails> personDetailItr = personDetails.iterator();
		while (personDetailItr.hasNext()) {
			PersonDetails perDetail = (PersonDetails) personDetailItr.next();
			if(perDetail.getId().equals(personDetail.getId())){
				personDetailItr.remove();
			}
		}
	}
	
	public static List<PersonDetails> getPersonDetails() {
		return personDetails;
	}
}
