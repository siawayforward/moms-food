package momsfood.classes;
import java.awt.image.BufferedImage;
import java.sql.*;
import javax.imageio.ImageIO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class CountryDAO {
	// Declare the JDBC objects.
	Connection conn = null;
	Statement stmt = null;
	ResultSet table = null; 
	//query to run
	String query = "";
	/**
	 * Method to get the name of the continent that a country is in
	 * @param country for which we want to search continent (String)
	 * @return continent string value
	 */
	public String getContinent(String country) {
		query = "SELECT Continent FROM Tbl_Country WHERE CountryName = '" + country + "'";
		String continent = ""; //return value
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement();
			table = stmt.executeQuery(query);
			//extract continent name			
			while(table.next()) 
				continent = table.getString("Continent");
			//close connection
			conn.close();
		}	// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}	//return continent
		return continent;
	}
	/**
	 * Method to get the image of the flag for a country
	 * @param country string = for which we want the flag
	 * @return flag as a binary large object image
	 */
	public Image getCountryFlag(String country)  {
		query = "SELECT FlagPicture FROM Tbl_Country WHERE CountryName = '" + country + "'";
		Image image = null; //return value
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement();
			table = stmt.executeQuery(query);
			//extract picture to pass as result
			while(table.next()) {
				BufferedImage img = ImageIO.read(table.getBlob("FlagPicture").getBinaryStream());
				image = SwingFXUtils.toFXImage(img, null);
			}
			//close connection
			conn.close();	
		} // Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		} //return picture 
		return image;
	}
	
	/**
	 * Method to get the country names from database
	 * @return country names
	 */
	public ObservableList<String> getCountryNames() {
		query = "SELECT CountryName FROM Tbl_Country";
		ObservableList<String> countries = FXCollections.observableArrayList(); //return value
		try {
			// Establish the connection.
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(MomsFoodDBConstants.DB_URL, MomsFoodDBConstants.USER_NAME, MomsFoodDBConstants.PASSWORD);
			
			// Create and execute an SQL statement that returns some data.
			stmt = conn.createStatement();
			table = stmt.executeQuery(query);
			//extract picture to pass as result
			while(table.next()) 
				countries.add(table.getString("CountryName"));
				//close connection
				conn.close();	
		} // Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		} //return picture 
		return countries;
	}
}
