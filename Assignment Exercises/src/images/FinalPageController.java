package images;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FinalPageController {
	
	@FXML
    private Label receiptLabel;
	
	String string = "";

	public void initialize()
	{
		
	}
	
	public void initData(String string)
	{
		this.string = string;
		
		OpenPage();
	}
	
	public void OpenPage()
	{
		receiptLabel.setText(string);
		System.out.println(this.string);
	}
}