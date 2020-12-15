package TestCases;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import PageObject.HomePage;
import Resources.TestBase;

public class CompareIphonePriceFlipAndAmazon extends TestBase{
	HomePage homePage;
	String amazonUrl="https://www.amazon.in/";
	String flipkartUrl="https://www.flipkart.com/";
	double amazonPriceIphone=0.0;
	double flipkartPriceIphone=0.0;

	//@BeforeTest
	@BeforeTest
	public void setUp() throws FileNotFoundException {
		initialization();
		launchAut(amazonUrl);
		homePage= new HomePage();

	}
	//getting the title of the page
	@Test(priority=1)
	public void verifyAmazonTitlePageTest()
	{
		Assert.assertEquals(driver.getTitle(), "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in");
	}


	//Search the given Item in the search bar
	@Test(priority=2)
	public void searchItemTest()
	{
		HomePage.amazonSearchTextBox.sendKeys("iPhone 7 32 gb(black)");
		HomePage.amazonGoButton.click();

	}
	//Select The particular item from the item list
	@Test(priority=3)
	public void selectAppleIphone() {
		HomePage.clickAppleIphone.click();
		Set<String> handles= driver.getWindowHandles();
		System.out.println(handles);
		Iterator<String> itr= handles.iterator();

		itr.next();
		String handle2= itr.next();

		driver.switchTo().window(handle2);
	}

	//Getting price of the selected item
	@Test(priority=4)
	public void getPrice()
	{
		String amazonPrice=HomePage.getPriceOfProd.getText();

		String amazonPriceOrg= amazonPrice.substring(2, amazonPrice.length()-1);
		int getIndex= amazonPriceOrg.indexOf(',');
		String amazonPriceOrg1=amazonPriceOrg.substring(0, getIndex) + amazonPriceOrg.substring(getIndex + 1);
		amazonPriceIphone=Double.parseDouble(amazonPriceOrg1);
		System.out.println("This is original price "+amazonPriceIphone);


	}

	//Nevigating to the flipkart and doing the same as done above and getting the price of the item and compare with amazon product price
	@Test(priority=5)
	public void nevigateToFlipkartFormAzazon() throws InterruptedException
	{
		launchAut(flipkartUrl);
		Thread.sleep(3000);
		HomePage.flipkartCloseWindow.click();
		Thread.sleep(3000);
		HomePage.flipkartSearchTextBox.sendKeys("iPhone 7 32 gb(black)");
		Thread.sleep(3000);
		HomePage.clickOnSearchIcon.click();
		Thread.sleep(3000);
		HomePage.selectIphone.click();
		Thread.sleep(3000);
		String  flipkartPrice=HomePage.getPriceOfProdFlipkart.getText();

		String flipkartPriceOrg= flipkartPrice.substring(1, flipkartPrice.length());
		int getIndex= flipkartPriceOrg.indexOf(',');
		String flipkartPriceOrg1=flipkartPriceOrg.substring(0, getIndex) + flipkartPriceOrg.substring(getIndex + 1);
		flipkartPriceIphone=Integer.parseInt(flipkartPriceOrg1);
		System.out.println(flipkartPriceIphone);


	}

	//finding the lesser price 
	@Test(priority=6) 
	public void comparePrice1() {

		if(flipkartPriceIphone>amazonPriceIphone) 
		{ 
			System.out.println("Printing the lowest price of the product"+amazonPriceIphone); 
		} 
		else
		{
			System.out.println(flipkartPriceIphone);
		} 
	}


	@AfterTest
	public void closeSetUp()
	{
		driver.quit();
	}

}
