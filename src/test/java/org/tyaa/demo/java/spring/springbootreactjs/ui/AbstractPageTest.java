package org.tyaa.demo.java.spring.springbootreactjs.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.tyaa.demo.java.spring.springbootreactjs.ui.pagefactory.IndexPage;

import java.util.concurrent.TimeUnit;

public abstract class AbstractPageTest {

    protected static WebDriver driver;
    protected IndexPage indexPage;

    @BeforeAll
    private static void setupAll() {
        System.setProperty("webdriver.gecko.driver", "driver/geckodriver");
    }

    @BeforeEach
    private void setupEach() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://localhost:8090/springreact/");
        indexPage = new IndexPage(driver);
    }

    @AfterEach
    private void disposeEach() {
        driver.quit();
    }
}
