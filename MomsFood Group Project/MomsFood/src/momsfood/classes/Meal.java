package momsfood.classes;
import javafx.scene.image.Image;
import java.sql.*;
import java.util.ArrayList;

public class Meal {
	
	//Variables to be used throughout the class
	//Also used to create get set methods
	int mealID;
	String mealName;
	int cookID;
	String cookName;
	String mealDescription;
	String foodCategory;
	String Country;
	Image picture;
	String pictureName;
	double unitPrice;
	double rating;	
	double cookRating;

	/*
	 * no ARG constructor
	 */
	public Meal()
	{
		
	}
	
	/** Constructor to simply retrieve a particular meal
	 * @Param mealID: the ID of the particular meal in question
	 */
	public Meal(int id)
	{
		id = this.getMealID();		
	}
	
	/**
	 * Display constructor (Display a new meal)
	 * @param mealname : name of the particular meal
	 * @param mealpicture: a blob/ image of the meal
	 * @param mealDescription: a description of the meal prepared by the cook
	 * @param foodCategory: a description of the food category that the meal falls under
	 * @param country: the country the meal is originally from
	 * @param mealPrice: the price of the meal
	 * @throws SQLException
	 * @throws Exception
	 */
	public Meal(String mealName, Blob mealPicture, String mealDescription, 
			String foodCategory, String country, double mealPrice) throws SQLException, Exception
	{
		this.mealName = mealName;
		this.mealDescription = mealDescription;		
		this.foodCategory = foodCategory;		
		this.Country = country;
		this.unitPrice = mealPrice;
		
	}	
	
	/**
	 * Insertion constructor (Add new meal to the database)
	 * @param mealname : name of the particular meal
	 * @param rating : a rating of the meal
	 * @param cookName: the name of the cook preparing the meal
	 * @param cookRating: the average rating of the cook
	 * @param price: the price of the meal
	 * @param mealDescription: a description of the meal prepared by the cook
	 * @param mealpicture: a blob/ image of the meal
	 */	
	public Meal(String mealName, Double rating, String cookName, Double cookRating, Double price, String mealDescription, String pictureName) throws Exception {
		
		this.mealName = mealName;
		this.rating = rating;
		this.cookName = cookName;
		this.cookRating = cookRating;
		this.unitPrice = price;
		this.mealDescription = mealDescription;
		this.pictureName = pictureName;		
	}
	
	/** 
	 * Display/Update constructors (Add new meal to the database, Display meals on the search page)
	 * @param mealId: the ID of the particular meal in question
	 * @param mealname : name of the particular meal
	 * @param cookName: the name of the cook preparing the meal
	 * @param cookRating: the average rating of the cook
	 * @param price: the price of the meal
	 * @param mealDescription: a description of the meal prepared by the cook
	 * @param mealpicture: a blob/ image of the meal
	 * @param foodCategory: a string of the category the meal best fits
	 * @param country:The country the meal originates from
	 */	
	public Meal(Integer mealID, String mealName,  Double price, String mealDescription, Blob mealPicture, String foodCategory, String country ) throws Exception {
		
		this.mealID = mealID;
		this.mealName = mealName;
		this.unitPrice = price;
		this.mealDescription = mealDescription;
		this.foodCategory = foodCategory;
		this.Country = country;
	}
	
	/** 
	 * Display/Update constructors (Display meals on the search page)
	 * @param mealId: the ID of the particular meal in question
	 * @param mealname : name of the particular meal
	 * @param cookName: the name of the cook preparing the meal
	 * @param cookRating: the average rating of the cook
	 * @param price: the price of the meal
	 * @param mealDescription: a description of the meal prepared by the cook
	 * @param mealpicture: a blob/ image of the meal
	 * @param foodCategory: a string of the category the meal best fits
	 * @param rating: average rating of the meal
	 */	
	public Meal(Integer mealID, String mealName,  Double price, String mealDescription, Blob mealPicture, String foodCategory, String country, Double rating) throws Exception {
		
		this.mealID = mealID;
		this.mealName = mealName;
		this.unitPrice = price;
		this.mealDescription = mealDescription;
		this.foodCategory = foodCategory;
		this.Country = country;
		this.rating = rating;
	}
	
	/**
	 * HomePage meal display constructor
	 * @param mealName string name of the meal
	 * @param price double price of the meal
	 * @param mealPicture blob image for the meal
	 * @param cookName string name of the cook who prepares the meal
	 * @param mealRating average rating of the meal
	 * @throws Exception
	 */
	public Meal(String mealName, Double price, byte[] mealPicture, String cookName, Double mealRating, int mealID, int cookID) throws Exception {		
		this.mealID = mealID;
		this.cookID = cookID;
		this.mealName = mealName;
		this.unitPrice = price;		
		this.cookName = cookName;
		this.rating = mealRating;
	}
	
	/**
	 * Detailed meal display constructor
	 * @param mealName string name of the meal
	 * @param price double price of the meal
	 * @param mealPicture blob image for the meal
	 * @param cookName string name of the cook who prepares the meal
	 * @param mealRating average rating of the meal
	 * @param cookRating average rating of the cook
	 * @throws Exception
	 */
	public Meal(String mealName, String desc, Double price, Blob mealPicture, String cookName, Double mealRating, Double cookRating) throws Exception {		
		this.mealName = mealName;
		this.mealDescription = desc;
		this.unitPrice = price;
		this.cookName = cookName;
		this.rating = mealRating;
		this.cookRating = cookRating;
	}
	//Getters and setters for all the variables	

	public int getMealID() {
		return mealID;
	}
	public void setMealID(int mealID) {
		this.mealID = mealID;
	}
	public String getMealName() {
		return mealName;
	}
	public void setMealName(String mealName) {
		this.mealName = mealName;
	}
	public int getCookID() {
		return cookID;
	}
	public void setCookID(int cookID) {
		this.cookID = cookID;
	}
	public String getMealDescription() {
		return mealDescription;
	}
	public void setMealDescription(String mealDescription) {
		this.mealDescription = mealDescription;
	}
	public String getFoodCategory() {
		return foodCategory;
	}
	public void setFoodCategory(String foodCategory) {
		this.foodCategory = foodCategory;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		this.Country = country;
	}
	public Image getPicture() {
		return picture;
	}
	public void setPicture(Image picture) {
		this.picture = picture;
	}
	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}	
	public String getCookName() {
		return cookName;
	}
	public void setCookName(String cookName) {
		this.cookName = cookName;
	}
	public double getCookRating() {
		return cookRating;
	}
	public void setCookRating(double cookRating) {
		this.cookRating = cookRating;
	}
	
	/**
	 * Method to get the comment history for a particular meal
	 * @return a string of all the comments with customer name and date
	 */
	public String getMealCommentHistory() {
		String commentHistory = "";
		ArrayList<Comment> list = new CommentDAO().getMealComments(this.mealID);
		for(int i = 0; i < list.size(); i++) {
			commentHistory += list.get(i).toString() + "\n";
		}
		return commentHistory;
	}
}