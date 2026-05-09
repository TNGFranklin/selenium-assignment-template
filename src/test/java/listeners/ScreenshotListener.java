package listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TestNG listener that automatically takes a screenshot whenever a test fails.
 * Screenshots are saved to build/screenshots/ with the test name and timestamp.
 */
public class ScreenshotListener implements ITestListener {

    private static final String SCREENSHOT_DIR = "build/screenshots/";

    @Override
    public void onTestFailure(ITestResult result) {
        Object instance = result.getInstance();

        // Try to get the WebDriver from the test class via reflection
        try {
            java.lang.reflect.Field driverField =
                    instance.getClass().getSuperclass().getDeclaredField("driver");
            driverField.setAccessible(true);
            WebDriver driver = (WebDriver) driverField.get(instance);

            if (driver != null) {
                takeScreenshot(driver, result.getName());
            }
        } catch (Exception e) {
            System.err.println("ScreenshotListener: could not capture screenshot — " + e.getMessage());
        }
    }

    private void takeScreenshot(WebDriver driver, String testName) {
        try {
            Files.createDirectories(Paths.get(SCREENSHOT_DIR));

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = SCREENSHOT_DIR + testName + "_" + timestamp + ".png";

            Files.copy(src.toPath(), Paths.get(fileName));
            System.out.println("Screenshot saved: " + fileName);
        } catch (IOException e) {
            System.err.println("ScreenshotListener: failed to save screenshot — " + e.getMessage());
        }
    }

    // Unused lifecycle methods — required by ITestListener
    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSuccess(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}
}
