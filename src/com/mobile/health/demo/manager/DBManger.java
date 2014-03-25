package com.mobile.health.demo.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mobile.health.demo.entity.PersonDetails;

public class DBManger {

	private static JdbcTemplate jdbcTemplate = JdbcTemplate.getMySQLJdbcTemplate();
	
	public static int getCurrentIdCursor() {
		String query = "SELECT MAX(id) FROM person_details";
		try {
			return jdbcTemplate.executeQuery(query).getInt(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void addPersonDetails(PersonDetails personDetail) {
		String query = "INSERT INTO person_details VALUES ("+personDetail.getId()+",'"+personDetail.getFirstName()+"','"+personDetail.getLastName()+"','"
					+personDetail.getGender()+"',"+personDetail.getAge()+",'"+personDetail.getAddress()+"','"+personDetail.getPanchayat()+"')";
		jdbcTemplate.executeUpdate(query);
	}

	public static void updatePersonDetails(PersonDetails personDetail) {
		if(personDetail.getId()==null)
			throw new IllegalArgumentException("Id can not be null");
		
		String query = "UPDATE person_details SET first_name='"+ personDetail.getFirstName()+"',last_name='"+personDetail.getLastName()+"',gender='"
				+personDetail.getGender()+"',age="+personDetail.getAge()+",address='"+personDetail.getAddress()+"',panchayat='"+personDetail.getPanchayat()
				+"' WHERE id="+personDetail.getId();
		jdbcTemplate.executeUpdate(query);
	}

	public static void removePersonDetailAtIndex(PersonDetails personDetail) {
		if(personDetail.getId()==null)
			throw new IllegalArgumentException("Id can not be null");
		
		String query = "DELETE person_details WHERE id="+personDetail.getId();
		jdbcTemplate.executeUpdate(query);	
	}
	
	public static List<PersonDetails> getPersonDetails() {
		String query = "SELECT * FROM person_details ORDER BY id";
		List<PersonDetails> personDetails = new ArrayList<PersonDetails>();
		try{
			ResultSet personDetailsData = jdbcTemplate.executeQuery(query);
			while(personDetailsData.next()){
				personDetails.add(new PersonDetails(personDetailsData.getInt("id"), personDetailsData.getString("first_name"),
						personDetailsData.getString("last_name"), personDetailsData.getString("gender"),
						personDetailsData.getInt("age"), personDetailsData.getString("address"),
						personDetailsData.getString("panchayat")));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return personDetails;
	}
}
