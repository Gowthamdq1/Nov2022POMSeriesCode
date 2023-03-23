package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;

	public static String highlight;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * This method is initializing the driver on the basis of given browser name
	 * 
	 * @param browserName
	 * @return this returns the driver
	 */

	public WebDriver initDriver(Properties prop) {

		optionsManager = new OptionsManager(prop);

		highlight = prop.getProperty("highlight").trim();
		String browserName = prop.getProperty("browser").toLowerCase().trim();

		System.out.println("Browser name is : " + browserName);

		//chrome
		if (browserName.equalsIgnoreCase("chrome")) {

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// Run on remote grid
				init_remoteDriver("chrome");
			} else {
				// Local Execution
				// driver=new ChromeDriver(optionsManager.getChromeOptions());
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
		} 
		//firefox
		else if (browserName.equalsIgnoreCase("firefox")) {
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// Run on remote grid
				init_remoteDriver("firefox");
			} else {
				// Local Execution
				tlDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
			}} 
		//edge
		else if (browserName.equalsIgnoreCase("edge")) {
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// Run on remote grid
				init_remoteDriver("edge");
			} else {
				// Local Execution
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}} 
		else {
			System.out.println("Plz pass the right browser name...." + browserName);
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url").trim());
		return getDriver();
	}
	/**
	 * This method is called internally to initialize the driver with RemoteWebDriver
	 * @param browser
	 */
	private void init_remoteDriver(String browser) {
		
		System.out.println("Running tests on grid server:::"+browser);

		try {
			switch (browser.toLowerCase()) {
			case "chrome":

			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
			break;
			
			case "firefox":
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFireFoxOptions()));
			break;

			case "edge":
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
			break;

			default:
			System.out.println("Plz pass the right browser name for remote execution.." + browser);
			throw new FrameworkException("NOREMOTEBROWSEREXCEPTION");
			}} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the local thread copy of the driver
	 * 
	 * @return
	 */
	public synchronized static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * this method is reading the properties from the .properties file
	 * 
	 * @return
	 */
	public Properties initProp() {

		prop = new Properties();
		FileInputStream ip = null;

		// mvn clean install -Denv="stage"

		// The below method to get the environment property
		String envName = System.getProperty("env");
		System.out.println("Running test cases on Env : " + envName);

		try {
			if (envName == null) {
				System.out.println("No Env is passed....Running tests on QA env...");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} else {

				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:
					System.out.println("Wrong env is passed...No need to run the test cases.....");
					throw new FrameworkException("WRONG ENV IS PASSED...");
				// break;
				}
			}
		} catch (FileNotFoundException e) {
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * take screenshot
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot" + System.currentTimeMillis() + ".png";
		File destination = new File(path);

		try {
			FileUtil.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;

	}

}
