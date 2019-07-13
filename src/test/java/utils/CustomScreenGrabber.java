package utils;

import com.thoughtworks.gauge.screenshot.ICustomScreenshotGrabber;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static utils.DriverFactory.getDriver;

public class CustomScreenGrabber implements ICustomScreenshotGrabber {
    public byte[] takeScreenshot() {
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
    }
}