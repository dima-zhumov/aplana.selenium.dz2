import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Autotest2 {
    public static WebDriver webDriver = null;

    @BeforeClass
    public static void TestUp(){
        System.setProperty("webdriver.chrome.driver", "C:/Users/Дмитрий/IdeaProjects/testing/driver/chromedriver.exe");
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

        webDriver.findElement(By.xpath("//a[contains(@class,'hd-ft-region')][1]")).click();

        WebElement element = webDriver.findElement(By.xpath("//h4"));
        wait.until(ExpectedConditions.visibilityOf(element));
        String title = element.getText();
        Assert.assertTrue(title.contains("Выбор региона"));

        WebElement Area = webDriver.findElement(By.xpath("//input[(@type='search') and contains(@class,'kit')]"));
        Area.sendKeys("Нижегородская область");
        webDriver.findElement(By.xpath("//a[contains(@class,'city')]")).click();

        element = webDriver.findElement(By.xpath("//a[contains(@aria-label, 'Текущий')][1]"));
        title = element.getText();
        Assert.assertTrue(title.contains("Нижегородская область"));

        element = webDriver.findElement(By.xpath("//a[contains(@class,'footer__social')][1]"));
        (((Locatable)element).getCoordinates()).inViewPort();
        Thread.sleep(5000);

        element=webDriver.findElement(By.xpath("//span[contains(@class,'footer__social')]"));
        Assert.assertTrue(element.isDisplayed());

    }
}
