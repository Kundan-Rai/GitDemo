package section18;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

// Conventional approach
public class OnlineShoppingApplication
{
	@Test
	public void addProductToCart()
	{
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
	
		// Get all products name
		List<WebElement> allProducts = driver.findElements(By.xpath("//h5/b"));
	
		// Get all "Add to Cart" elements
		List<WebElement> addToCart = driver.findElements(By.xpath("//*[contains(text(),'Add To Cart')]"));
		
		// Add product(ZARA COAT 3) to the cart
		for(int i = 0; i < allProducts.size(); i++)
		{
			if(allProducts.get(i).getText().equals("ZARA COAT 3"))
			{
				addToCart.get(i).click();
			}
		}
		
		driver.close();
	}
}
