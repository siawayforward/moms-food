package momsfood.classes;

public class CookRating {

	int cookRatingID;
	double cookRating;
	
	//CookRating constructor
	public CookRating(double cookRating) {
		this.cookRating = cookRating;
	}
	
	public boolean addCookRating(int cookID, int custID) {		
		//from user input
		return new CookRatingDAO().addCookRating(cookID, custID, this.cookRating);
	}


}
