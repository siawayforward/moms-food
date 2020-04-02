package momsfood.classes;

public class MealRating {
	//Attributes/Variables
	int mealRatingID;
	private double mealRating;
	
	MealRating(){}
	
	//Set meal Rating
	public void setMealRating(double mealRating) {
		this.mealRating = mealRating;
	}
	
	//Add a new Meal rating given MealID and CustID
	public boolean addMealRating(int mealID, int custID) {		
		//from user input
		return new MealRatingDAO().addMealRating(mealID, custID, this.mealRating);
	}

}