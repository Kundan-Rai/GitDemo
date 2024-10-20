package section18;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

// Java Stream approach
public class OnlineShoppingApplication2
{
	@Test
	public void orderProduct() throws InterruptedException
	{
		// Product to add in the cart
		String productName = "ADIDAS ORIGINAL";
		
		// Lunch the chrome browser
		WebDriver driver = new ChromeDriver();
		
		// Add implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		// Maximize the window
		driver.manage().window().maximize();
		
		// Invoke URL
		driver.get("https://rahulshettyacademy.com/client/");
	
		// Login to the application
		// Enter user name
		driver.findElement(By.id("userEmail")).sendKeys("kkrcs.10+rsa@gmail.com");
		
		// Enter password
		driver.findElement(By.cssSelector("[formcontrolname='userPassword']")).sendKeys("Iamking@000");
	
		// Click on login
		driver.findElement(By.cssSelector("#login")).click();
	
		// Verify login is successful
		// Create Explicit wait instance
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@routerlink='/dashboard/']")));
		
		// Get all products
		List<WebElement> allProducts = driver.findElements(By.xpath("//*[@class='card']//h5/b"));
//		for(WebElement product : allProducts)
//		{
//			System.out.println(product.getText());
//		}
		
		
//		// Filter the product using Java Stream concept
		WebElement filteredProduct = allProducts.stream().filter(product -> product.getText().equals(productName)).findFirst().orElse(null);
//		System.out.println(filteredProduct.getText());
		
//		// Click on "Add To Cart"
		driver.findElement(By.xpath("//b[text()='"+filteredProduct.getText()+"']/parent::h5/following-sibling::button[last()]")).click();
//	
//		// Verify "Product Added to cart" toast message/snackbar
		WebElement productAddedToCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-label='Product Added To Cart']")));
		boolean isPresent = productAddedToCart.isDisplayed();
			
		Assert.assertTrue(isPresent);
		
		// Wait till "Product Added to cart" message disappeared
		boolean isDisappeared = wait.until(ExpectedConditions.invisibilityOf(productAddedToCart));
		Assert.assertTrue(isDisappeared);
				
		// Click on Cart
		driver.findElement(By.cssSelector("[routerlink='/dashboard/cart']")).click();
	
		// Check/verify product is successfully added to the cart
		List<WebElement> itemsAddedToCart = driver.findElements(By.cssSelector(".cartSection h3"));
		boolean isItemAdded = itemsAddedToCart.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(isItemAdded);
		
		// Click on checkout
		WebElement checkout = driver.findElement(By.cssSelector(".totalRow>button"));
		Actions actions = new Actions(driver);
		actions.scrollToElement(checkout).build().perform();
		checkout.click();
		
		// Scroll to the "Apply Coupon" input field
		WebElement applyCouponTextBox = driver.findElement(By.cssSelector("input[name='coupon']"));
//		actions.scrollToElement(applyCouponTextBox).build().perform();
		actions.sendKeys(applyCouponTextBox, Keys.PAGE_DOWN).build().perform();
		
		// Enter Country name
//		driver.findElement(By.cssSelector("[placeholder='Select Country']")).sendKeys("Ind");
//		driver.findElement(By.xpath("//span[@class='ng-star-inserted']/following::span")).click();
		
		WebElement countryField = driver.findElement(By.cssSelector(".form-group>input"));
		countryField.sendKeys("Ind");
		Thread.sleep(2000);
		countryField.sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER);
	
		// Place the order
		driver.findElement(By.xpath("//*[contains(text(),'Place Order')]")).click();
	
		// Validate order is placed successfully
		String thankyouMessage = driver.findElement(By.xpath("//*[contains(text(),'Thankyou for the order.')]")).getText();
	
		Assert.assertEquals(thankyouMessage, "THANKYOU FOR THE ORDER.");
		
		driver.close();
	}
}
