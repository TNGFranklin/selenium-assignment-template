package listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

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
        try {
            java.lang.reflect.Field driverField =
                    result.getInstance().getClass()
                          .getSuperclass().getDeclaredField("driver");
            driverField.setAccessible(true);
            WebDriver driver = (WebDriver) driverField.get(result.getInstance());

            if (driver != null) {
                Files.createDirectories(Paths.get(SCREENSHOT_DIR));
                java.io.File src =
                        ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String timestamp =
                        new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String dest = SCREENSHOT_DIR + result.getName() + "_" + timestamp + ".png";
                Files.copy(src.toPath(), Paths.get(dest));
                System.out.println("Screenshot saved: " + dest);
            }
        } catch (Exception e) {
            System.err.println("ScreenshotListener: " + e.getMessage());
        }
    }

    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSuccess(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}
}
