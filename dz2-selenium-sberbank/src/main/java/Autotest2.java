import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Autotest2 {
    public static WebDriver webDriver = null;

    @BeforeClass
    public static void TestUp(){
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
    }

    @AfterClass
    public static void CleanUp(){
        webDriver.close();
        webDriver.quit();
    }

    @Test
    public void RGS() throws InterruptedException {
        webDriver.get("http://www.sberbank.ru/ru/person");

        WebDriverWait wait = new WebDriverWait(webDriver, 30);
        String title;

        webDriver.findElement(By.xpath("//header//a[contains(@class,'hd-ft-region')]")).click();

        WebElement element = webDriver.findElement(By.xpath("//h4"));
        wait.until(ExpectedConditions.visibilityOf(element));

        webDriver.findElement(By.xpath("//input[(@type='search') and contains(@class,'kit')]")).sendKeys("Нижегородская область");
        webDriver.findElement(By.xpath("//a[contains(@class,'city')]")).click();

        element = webDriver.findElement(By.xpath("//header//a[contains(@aria-label, 'Текущий')]"));
        title = element.getText();
        Assert.assertEquals("Ошибка. Регион не совпадает.",title,"Нижегородская область");

        element = webDriver.findElement(By.xpath("//span[contains(@class,'footer__social_fb')]"));
        (((Locatable)element).getCoordinates()).inViewPort();


        List<WebElement> elementList= webDriver.findElements(By.xpath("//span[contains(@class,'footer__social')]"));
        for (WebElement webElement:elementList){
            Assert.assertTrue(webElement.isDisplayed());
        }

    }
}
