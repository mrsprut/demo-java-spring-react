package org.tyaa.demo.java.spring.springbootreactjs.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.tyaa.demo.java.spring.springbootreactjs.SpringbootReactjsApplication;
import org.tyaa.demo.java.spring.springbootreactjs.ui.pagefactory.SignInPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = SpringbootReactjsApplication.class
)
public class SignInPageTest extends AbstractPageTest {

    private SignInPage signInPage;

    @BeforeEach
    public void setupCase() {
        signInPage = indexPage.clickSignIn();
    }

    @Test
    @Order(1)
    public void performSignInWithCorrectAdminUserNameAndPassword() {
        signInPage.loginWithValidCredentials("admin", "AdminPassword1");
        assertEquals("http://localhost:8090/springreact/", driver.getCurrentUrl());
        String logOutButtonText = indexPage.getLogOutButtonText();
        assertNotNull(logOutButtonText);
        assertEquals("Sign out (admin)", logOutButtonText);
    }

    @Test
    @Order(2)
    public void failSignInWithWrongUserNameAndCorrectAdminPassword() {
        //signInPage =
            signInPage.loginWithInvalidCredentials("wrong", "AdminPassword1");
        String logOutButtonText = indexPage.getLogOutButtonText();
        // assertEquals("", logOutButtonText);
        assertNull(logOutButtonText);
        String errorText = signInPage.getErrorText();
        assertNotNull(errorText);
        assertEquals("Login or password is wrong", errorText);
    }
}
