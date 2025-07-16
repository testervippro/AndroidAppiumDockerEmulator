
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.maven.CMDUtils;
import org.maven.ExplicitWait;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class LaunchAppTest {

  private AndroidDriver driver;

  private WebDriverWait wait;

  @BeforeClass
  public void setUp() throws IOException {


    CMDUtils.executeCMD("node toggle_record.js");

    UiAutomator2Options options = new UiAutomator2Options()
        .setDeviceName("Samsung Galaxy S10")
        .setUdid("emulator-5554")
        .setPlatformName("Android")
        .setAutomationName("UiAutomator2")
        .setAppPackage("com.saucelabs.mydemoapp.android")
        .setAppActivity("com.saucelabs.mydemoapp.android.view.activities.SplashActivity")
        .setApp("app-android.apk")
        .setNoReset(false);

    URL appiumServerUrl = new URL("http://localhost:4723");

    driver = new AndroidDriver(appiumServerUrl, options);
    wait = new WebDriverWait(driver, Duration.ofSeconds(60_00));

  }


  @Test
  public void placeOrder() {
    // Open menu
    var menuIV = By.id("com.saucelabs.mydemoapp.android:id/menuIV");
    ExplicitWait.waitForElementVisible(wait, menuIV).click();

    // Navigate to login
    var loginLeftMenu = By.xpath("//android.widget.TextView[@content-desc=\"Login Menu Item\"]");
    ExplicitWait.waitForElementVisible(wait, loginLeftMenu).click();

    // Enter credentials
    ExplicitWait.waitForElementVisible(wait, AppiumBy.xpath("//android.widget.EditText[@resource-id=\"com.saucelabs.mydemoapp.android:id/nameET\"]"))
        .sendKeys("bob@example.com");
    ExplicitWait.waitForElementVisible(wait, AppiumBy.xpath("//android.widget.EditText[@resource-id=\"com.saucelabs.mydemoapp.android:id/passwordET\"]"))
        .sendKeys("10203040");

    // Click login
    var btnLogin = By.xpath("//android.widget.Button[@content-desc=\"Tap to login with given credentials\"]");
    ExplicitWait.waitForElementClickable(wait, btnLogin).click();

    // Select product
    var product = By.xpath("(//android.widget.ImageView[@content-desc=\"Product Image\"])[1]");
    ExplicitWait.waitForElementVisible(wait, product).click();

    // Add to cart
    var addToCart = By.xpath("//android.widget.Button[@content-desc=\"Tap to add product to cart\"]");
    ExplicitWait.waitForElementClickable(wait, addToCart).click();

    // Go to cart
    var cart = By.xpath("//android.widget.TextView[@resource-id=\"com.saucelabs.mydemoapp.android:id/cartTV\"]");
    ExplicitWait.waitForElementClickable(wait, cart).click();

    // Proceed to checkout
    var checkoutButton = By.xpath("//android.widget.Button[@content-desc=\"Confirms products for checkout\"]");
    ExplicitWait.waitForElementClickable(wait, checkoutButton).click();

    // Fill out checkout form
    ExplicitWait.waitForElementVisible(wait, By.xpath("//android.widget.EditText[@resource-id=\"com.saucelabs.mydemoapp.android:id/fullNameET\"]"))
        .sendKeys("Test User");
    ExplicitWait.waitForElementVisible(wait, By.xpath("//android.widget.EditText[@resource-id=\"com.saucelabs.mydemoapp.android:id/address1ET\"]"))
        .sendKeys("123 Main St");
    ExplicitWait.waitForElementVisible(wait, By.xpath("//android.widget.EditText[@resource-id=\"com.saucelabs.mydemoapp.android:id/address2ET\"]"))
        .sendKeys("Apt 1");
    ExplicitWait.waitForElementVisible(wait, By.xpath("//android.widget.EditText[@resource-id=\"com.saucelabs.mydemoapp.android:id/cityET\"]"))
        .sendKeys("Test City");
    ExplicitWait.waitForElementVisible(wait, By.xpath("//android.widget.EditText[@resource-id=\"com.saucelabs.mydemoapp.android:id/stateET\"]"))
        .sendKeys("Test State");
    ExplicitWait.waitForElementVisible(wait, By.xpath("//android.widget.EditText[@resource-id=\"com.saucelabs.mydemoapp.android:id/zipET\"]"))
        .sendKeys("12345");
    ExplicitWait.waitForElementVisible(wait, By.xpath("//android.widget.EditText[@resource-id=\"com.saucelabs.mydemoapp.android:id/countryET\"]"))
        .sendKeys("USA");

    // Save checkout info
    var saveCheckout = By.xpath("//android.widget.Button[@content-desc=\"Saves user info for checkout\"]");
    ExplicitWait.waitForElementClickable(wait, saveCheckout).click();

    // Enter payment details
    ExplicitWait.waitForElementVisible(wait, By.xpath("//android.widget.EditText[@resource-id=\"com.saucelabs.mydemoapp.android:id/nameET\"]"))
        .sendKeys("Test User");
    ExplicitWait.waitForElementVisible(wait, By.xpath("//android.widget.EditText[@resource-id=\"com.saucelabs.mydemoapp.android:id/cardNumberET\"]"))
        .sendKeys("4111111111111111");
    ExplicitWait.waitForElementVisible(wait, By.xpath("//android.widget.EditText[@resource-id=\"com.saucelabs.mydemoapp.android:id/expirationDateET\"]"))
        .sendKeys("12/25");
    ExplicitWait.waitForElementVisible(wait, By.xpath("//android.widget.EditText[@resource-id=\"com.saucelabs.mydemoapp.android:id/securityCodeET\"]"))
        .sendKeys("123");

    // Review order
    var reviewOrder = By.xpath("//android.widget.Button[@content-desc=\"Saves payment info and launches screen to review checkout data\"]");
    ExplicitWait.waitForElementClickable(wait, reviewOrder).click();

    // Place order
    var placeOrder = By.xpath("//android.widget.Button[@content-desc=\"Completes the process of checkout\"]");
    ExplicitWait.waitForElementClickable(wait, placeOrder).click();

    // Verify checkout success
    var successMessage = By.xpath("//android.widget.TextView[@resource-id=\"com.saucelabs.mydemoapp.android:id/completeTV\"]");
    String checkOutSuccess = ExplicitWait.waitForElementVisible(wait, successMessage).getText();
    Assert.assertEquals(checkOutSuccess, "Checkout Complete", "Checkout success message mismatch");
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws IOException {
      driver.quit();
      CMDUtils.executeCMD("node toggle_record.js");

  }
}
