package org.tyaa.demo.java.spring.springbootreactjs.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tyaa.demo.java.spring.springbootreactjs.SpringbootReactjsApplication;
import org.tyaa.demo.java.spring.springbootreactjs.ui.pagefactory.HomePage;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = SpringbootReactjsApplication.class
)
public class HomePageTest extends AbstractPageTest {

    private HomePage homePage;

    @BeforeEach
    public void setupCase() {
        driver.get("http://localhost:8090/springreact/");
        homePage = new HomePage(driver);
    }

    @Test
    public void main() throws InterruptedException {
        Thread.sleep(5000);
    }
}
