package momsfood.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Order {
	private Date orderDate;
	private Date deliveryDate;
	private int isCancelled=0;
	private int isDelivered=0; 
	private int isConfirmed=0;
	private ArrayList<OrderLine> detailedList; 
	
	//no arg constructor
	public Order() {}
	
	/**
	 * Order insertion constructor to add order to database
	 * @param oDate order date
	 * @param dDate delivery date
	 * @param confirm int value indicating whether or not order is confirmed
	 * @param cancel int value indicating whether or not order is cancelled
	 * @param deliver int value indicating whether or not order is delivered
	 * @throws ParseException 
	 */
	public Order(String oDate, String dDate, int confirm, int cancel, int deliver) throws ParseException {
		SimpleDateFormat form = new SimpleDateFormat("MM/DD/YYYY");
		this.orderDate = form.parse(oDate);
		this.deliveryDate = form.parse(dDate);
		this.isConfirmed = confirm;
		this.isCancelled = cancel;
		this.isDelivered = deliver;
	}

	//getters and setters
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public int getIsCancelled() {
		return isCancelled;
	}

	public void setIsCancelled(boolean status) {
		if(status)
		this.isCancelled =1;		
	}

	public int getIsDelivered() {
		return isDelivered;
	}

	public void setIsDelivered(boolean status) {
		if(status)
		this.isDelivered = 1;
	}

	public int getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(boolean status) {
		if(status)
		this.isConfirmed =1;
	}
	
	public void setDetailedList(ArrayList<OrderLine> detailedList) {
		this.detailedList = detailedList;
	}
	
	public void addOrderDetail(OrderLine items)
	{
		detailedList.add(items);
	}
}
