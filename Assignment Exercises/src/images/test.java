package images;
/*import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.*;
import javax.imageio.ImageIO;*/
import java.util.ArrayList;

import momsfood.classes.Admin;
import momsfood.classes.Cook;
import momsfood.classes.Country;
import momsfood.classes.DayOfTheWeek;



public class test {

	public static void main(String[] args) throws Exception {
		
		
		Cook cook = new Cook(5);
		Admin admin = new Admin(1);
		
		Country coun = new Country("Albania");
		ArrayList<DayOfTheWeek> avail = new ArrayList<DayOfTheWeek>();
		ArrayList<DayOfTheWeek> unavail = new ArrayList<DayOfTheWeek>();
		avail.add(DayOfTheWeek.MONDAY);
		avail.add(DayOfTheWeek.FRIDAY);
		
		cook.setAvailability(avail);
		System.out.print(cook.getAvailability());
		
		//cook.setAvailability("Mon Fri Sat ");
		
		System.out.print("\n");
		//get date
		unavail.add(DayOfTheWeek.MONDAY);
		System.out.println(cook.updateAvailability(unavail));
		
		/*
		//query tests
		System.out.println(admin.updateAdminStatus(true));
		System.out.println(coun.getContinent());
		
		//image test		
		InputStream in = blob.getBinaryStream();
		BufferedImage image = ImageIO.read(in);
		System.out.println(image);
		
		System.out.println(coun.getCountryFlag());
		System.out.println(cook.getCookRating());	
		*/		
		System.out.println("done");		
	}
}


/*ResultSet rs = stmt.executeQuery(<Your Query SQL>);  
java.sql.Blob blob = rs.getBlob(column);  
InputStream in = blob.getBinaryStream();  
BufferedImage image = ImageIO.read(in);
*/

