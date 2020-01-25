import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Autotest {
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
        webDriver.get("http://www.rgs.ru");

        WebDriverWait wait = new WebDriverWait(webDriver, 30);

       // webDriver.findElement(By.xpath("//a[contains(text(),'Меню') and not (contains(@class, 'coll'))]")).click();
        webDriver.findElement(By.xpath("//a[contains(text(),'Меню') and (contains(@class, 'hidden'))]")).click();
        webDriver.findElement(By.xpath("//a[contains(text(),'ДМС')]")).click();

        WebElement element = webDriver.findElement(By.xpath("//h1"));

        wait.until(ExpectedConditions.visibilityOf(element));
        String title = element.getText();

        Assert.assertTrue(title.contains("добровольное медицинское страхование"));

        webDriver.findElement(By.xpath("//a[contains(text(),'Отправить')]")).click();

        element = webDriver.findElement(By.xpath("//b[contains(text(), 'Зая')]"));

        wait.until(ExpectedConditions.visibilityOf(element));
        title = element.getText();

        Assert.assertTrue(title.contains("Заявка на добровольное медицинское страхование"));

        WebElement LastName = webDriver.findElement(By.xpath("//input[@name='LastName']"));
        WebElement Name = webDriver.findElement(By.xpath("//input[@name='FirstName']"));
        WebElement MiddleName = webDriver.findElement(By.xpath("//input[@name='MiddleName']"));
        WebElement Phone = webDriver.findElement(By.xpath("//input[contains(@data-bind,'Phone')]"));
        WebElement Email = webDriver.findElement(By.xpath("//input[contains(@data-bind,'Email')]"));
        WebElement Commit = webDriver.findElement(By.xpath("//textarea[contains(@data-bind,'')]"));

        LastName.sendKeys("Фамилия");
        Name.sendKeys("Имя");
        MiddleName.sendKeys("Отчество");
        Phone.sendKeys(Keys.CONTROL + "a");
        Phone.sendKeys("9999999999");
        Thread.sleep(5000);
        Email.sendKeys("qwertyqwerty");
        Commit.sendKeys("Комментарий");

        element = webDriver.findElement(By.xpath("//select"));

        Select select = new Select(element);

        select.selectByValue("77");

        webDriver.findElement(By.xpath("//input[@class='checkbox']")).click();

        Assert.assertEquals(LastName.getAttribute("value"), "Фамилия");
        Assert.assertEquals(Name.getAttribute("value"), "Имя");
        Assert.assertEquals(MiddleName.getAttribute("value"), "Отчество");
        Assert.assertEquals(Phone.getAttribute("value"), "+7 (999) 999-99-99");
        Assert.assertEquals(Email.getAttribute("value"), "qwertyqwerty");
        Assert.assertEquals(Commit.getAttribute("value"), "Комментарий");

        webDriver.findElement(By.xpath("//button[@id='button-m']")).click();
        title = webDriver.findElement(By.xpath("//input[contains(@data-bind,'Email')]/..//span[@class='validation-error-text']")).getText();

        Assert.assertTrue(title.contains("Введите адрес электронной почты"));
    }
}
