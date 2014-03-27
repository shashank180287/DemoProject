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
		ResultSet maxId=null;
		try {
			maxId = jdbcTemplate.executeQuery(query);
			while(maxId.next())
					return maxId.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally{
			if(maxId!=null)
				try {
					maxId.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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

	public static void removePersonDetails(PersonDetails personDetail) {
		if(personDetail.getId()==null)
			throw new IllegalArgumentException("Id can not be null");
		
		String query = "DELETE person_details WHERE id="+personDetail.getId();
		jdbcTemplate.executeUpdate(query);	
	}
	
	public static List<PersonDetails> getPersonDetails() {
		String query = "SELECT * FROM person_details ORDER BY id desc";
		List<PersonDetails> personDetails = new ArrayList<PersonDetails>();
		ResultSet personDetailsData = null;
		try{
			personDetailsData = jdbcTemplate.executeQuery(query);
			while(personDetailsData.next()){
				personDetails.add(new PersonDetails(personDetailsData.getInt("id"), personDetailsData.getString("first_name"),
						personDetailsData.getString("last_name"), personDetailsData.getString("gender"),
						personDetailsData.getInt("age"), personDetailsData.getString("address"),
						personDetailsData.getString("panchayat")));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(personDetailsData!=null)
				try {
					personDetailsData.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return personDetails;
	}
}
