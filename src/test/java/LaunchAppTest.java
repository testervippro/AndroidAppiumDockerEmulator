import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URL;

public class LaunchAppTest {
  public static void main(String[] args) {
    try {
      UiAutomator2Options options = new UiAutomator2Options()
          .setDeviceName("Samsung Galaxy S10")
          .setUdid("emulator-5554") // Check with `adb devices`
          .setPlatformName("Android")
          .setAutomationName("UiAutomator2")
          .setAppPackage("com.android.chrome")
          .setAppActivity("com.google.android.apps.chrome.Main")
          .setNoReset(true);

      AndroidDriver driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), options);
      System.out.println("✅ App launched!");
      driver.quit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
