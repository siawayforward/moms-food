package momsfood.classes;

public class OrderLine {
	
	//Attributes to be used in class
	private int orderId;
	private int quantity;
	private double price;
	final private double DELIVERY=3.25;
	final double TAX=0.0825;
	private double subTotal=0;
	Customer aCustm;
	Meal aMeal;
	Cook aCook;
	private String mealName;
	private String cookName;
	
	/**
	 * No arg constructor
	 */
	public OrderLine() {}
	
	/**
	 * OrderLine constructor method used to display the oderLine
	 * @param mealName the name of the meal
	 * @param cookName the cook preparing the meals
	 * @param price The price of the meals
	 * @param quantity The quantity of meals
	 */
	public OrderLine(String mealName, String cookName, Double price, int quantity)
	{
		this.mealName = mealName;
		this.cookName = cookName;
		this.price = price;
		this.quantity = quantity;
		this.subTotal = price * quantity;
	}	
	
	//Getter and setters
	public double getSubTotal() {
		return subTotal;
	}


	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public String getMealName() {
		return mealName;
	}

	public void setMealName(String mealName) {
		this.mealName = mealName;
	}
	
	public String getNameOfFood() {
		return mealName;
	}

	public void setNameOfFood(String nameOfFood) {
		this.mealName = nameOfFood;
	}

	public Customer getaCustm() {
		return aCustm;
	}


	public void setaCustm(Customer aCustm) {
		this.aCustm = aCustm;
	}


	public Meal getaMeal() {
		return aMeal;
	}


	public void setaMeal(Meal aMeal) {
		this.aMeal = aMeal;
	}


	public Cook getaCook() {
		return aCook;
	}


	public void setaCook(Cook aCook) {
		this.aCook = aCook;
	}


	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public double getDELIVERY() {
		return DELIVERY;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}	

	public String getCookName() {
		return cookName;
	}

	public void setCookName(String cookName) {
		this.cookName = cookName;
	}


	public double calcSubtotal()
	
	{
		double asubTotal= quantity*price;
		this.subTotal=asubTotal;
		return subTotal;
	}
		
	public double calcTax(double beforTax)
	{
		double taxAmount=beforTax*TAX;
		return taxAmount;
	}
	
	
	public double finalPrice(double afterTax) 
	{
		double finalPrice=afterTax*(1-TAX);
		return finalPrice;
	}
}
