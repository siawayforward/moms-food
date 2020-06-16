package momsfood.classes;
import java.sql.*;

import javafx.scene.image.Image;

public class Country {

	//country attributes include name, abbreviation (3 letter ISO code), continent, and flag.
	private String countryName;
	private String continent;
	private Image countryFlag;
	
	//no arg constructor
	public Country() {}
	
	public Country(String country) {
		this.countryName = country;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String country) {
		this.countryName = country; //from user selection
	}
	/**
	 * Method to return the name of the continent that a country is in
	 * @return continent string value
	 * @throws SQLException
	 */
	public String getContinent() throws SQLException {
		//get continent from database based on country
		String cont = new CountryDAO().getContinent(this.getCountryName());
		this.continent = cont;
		return continent;
	}
	
	/**
	 * Method to return the flag of a country as a large binary object
	 * @return flag object as image (blob)
	 */
	public Image getCountryFlag() {
		//get continent from database based on country
		Image flag = new CountryDAO().getCountryFlag(this.getCountryName());
		this.countryFlag = flag;
		return countryFlag;
	}
	
}
