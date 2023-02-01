package com.onofftaxi.selenium;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;
/*
* todo: Zoptymalizować czasy wywyołań poszczególny akcji*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
//@TestPropertySource(properties = "application-geodb.properties")
public class Tests {

    @LocalServerPort
    private int port;
    private ChromeDriver chromeDriver;
    private WebDriverWait wait;

    @BeforeClass
    public static void setupClass() {
//      W przypadku gdy przeglądarka się nie uruchamia, należy porównać wersje Chroma i ChromeDriver'a
//        Obecnie najwyższa wersja ChromeDriver, działająca pod "Chrome v.77", to '77.0.3865.40'.
        WebDriverManager.getInstance(DriverManagerType.CHROME).version("77.0.3865.40").setup();
    }

    @Before
    public void setupWebDriver() {
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        wait = new WebDriverWait(chromeDriver, 10);
        MockitoAnnotations.initMocks(this);


    }
    @After
    public void teardown() {
        waitForSeconds(5);
        if (chromeDriver != null)
            chromeDriver.quit();
    }

    @Test
    public void shouldLoginFuxyDriver() {
        chromeDriver.navigate().to(String.format("http://localhost:%s",port));
        WebElement element = chromeDriver.findElement(By.id("ok-btn"));
        element.click();
        wait.until(ExpectedConditions.invisibilityOf(chromeDriver.findElement(By.id("ok-btn"))));
        chromeDriver.findElement(By.className("v-system-error")).click();
        chromeDriver.findElement(By.id("log-btn")).click();

        chromeDriver.findElement(By.name("username")).sendKeys("fuxy");

        chromeDriver.findElement(By.name("password")).sendKeys("drv1");

        chromeDriver.findElement(By.id("submitbutton")).click();
        wait.until(ExpectedConditions.visibilityOf(chromeDriver.findElement(By.id("greetingTxt"))));
        Assert.assertEquals(chromeDriver.findElement(By.id("greetingTxt")).getText(),"Witaj Ninomysł, ustaw status:");
    }
    @Test
    public void SimpleDriverRegistration() {
        chromeDriver.navigate().to(String.format("http://localhost:%s",port));
        wait.until(ExpectedConditions.visibilityOf(chromeDriver.findElement(By.id("ok-btn"))));
        chromeDriver.findElement(By.id("ok-btn")).click();
        wait.until(ExpectedConditions.invisibilityOf(chromeDriver.findElement(By.id("ok-btn"))));
        chromeDriver.findElement(By.id("registerBtn")).click();
        wait.until(ExpectedConditions.elementToBeClickable(chromeDriver.findElement(By.id("drv-reg-btn"))));
        chromeDriver.findElement(By.id("drv-reg-btn")).click();
        wait.until(ExpectedConditions.elementToBeClickable(chromeDriver.findElement(By.id("reg-first-btn"))));
        chromeDriver.findElement(By.id("loginTxtFld")).sendKeys("test");
        chromeDriver.findElement(By.id("emailTxtFld")).sendKeys("ireksenger@wp.pl");
        chromeDriver.findElement(By.id("phoneTxtFld")).sendKeys("123456789");
        chromeDriver.findElement(By.id("passwordTxtFld")).sendKeys("Test123456");
        chromeDriver.findElement(By.id("confPasswTxtFld")).sendKeys("Test123456",Keys.TAB);
        chromeDriver.findElement(By.id("reg-first-btn")).click();
        wait.until(ExpectedConditions.elementToBeClickable(chromeDriver.findElement(By.id("reg-second-btn"))));
        chromeDriver.findElement(By.id("nameTxtFld")).sendKeys("Jan");
        chromeDriver.findElement(By.id("surnameTxtFld")).sendKeys("Nowak");
        chromeDriver.findElement(By.id("comNameTxtFld")).sendKeys("Jan Nowak Company");
        chromeDriver.findElement(By.id("regonTxtFld")).sendKeys("123456789");
        chromeDriver.findElement(By.id("nipTxtFld")).sendKeys("1234567899");
        chromeDriver.findElement(By.id("cityTxtFld")).sendKeys("Kartuzy");
        chromeDriver.findElement(By.id("postTxtFld")).sendKeys("12-123");
        chromeDriver.findElement(By.id("streetTxtFld")).sendKeys("Cicha 1",Keys.TAB);
        chromeDriver.findElement(By.id("reg-second-btn")).click();
        wait.until(ExpectedConditions.visibilityOf(chromeDriver.findElement(By.id("drv-third-btn"))));
        chromeDriver.findElement(By.id("dispNameTxtFld")).sendKeys("Jan Nowak Company");
        chromeDriver.findElement(By.id("licenceNrTxtFld")).sendKeys("Jan123");
        chromeDriver.findElement(By.id("privacyChBox")).click();
        chromeDriver.findElement(By.id("acceptTermsChBox")).click();
        WebElement operCityNameTxtFld = chromeDriver.findElement(By.id("operCityNameTxtFld"));
        operCityNameTxtFld.sendKeys("Kartu");
        waitForSeconds(1);
        operCityNameTxtFld.sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
        waitForSeconds(1);
        chromeDriver.findElement(By.id("drv-third-btn")).click();
        waitForSeconds(3);
        System.out.println(chromeDriver.getTitle());
    }

    public void waitForSeconds(int i) {
        try {
            Thread.sleep(i*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
