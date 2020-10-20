package br.com.inmetrics.teste.support;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserFactory {
	private static WebDriver driver;
	public static BrowserFactory instance;
	
	private BrowserFactory(){
	}
	
	public static BrowserFactory getInstance() {
		if(instance==null) {
			instance = new BrowserFactory();
		}
		return instance;
	}
	 
	
	public static WebDriver getChromeDriver(){
		if(driver==null){
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			options.addArguments("start-maximized"); 
			options.addArguments("enable-automation"); 
			options.addArguments("--no-sandbox"); 
			options.addArguments("--disable-infobars");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--disable-browser-side-navigation"); 
			options.addArguments("--disable-gpu"); 
			options.setPageLoadStrategy(PageLoadStrategy.NONE);
			System.setProperty("webdriver.chrome.driver", ConfigManager.getInstance().getConfigs().get("chromeDriverPath"));
			driver = new ChromeDriver(options); 
			
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
		}
		return driver;
	}
	 
	public static WebDriver getIEDriver(){
		//implements specific setup of IE
		return new InternetExplorerDriver();
	}
	
	public static WebDriver getFirefoxDriver(){
		//implements specific setup of firefox
		return new FirefoxDriver();
	}
	
	public static WebDriver getDriver(String browserName){
		if(driver==null){
			
			if(browserName.equalsIgnoreCase("firefox"))
			{
				driver= getFirefoxDriver();
						
			}else if(browserName.equalsIgnoreCase("chrome"))
			{	
				driver= getChromeDriver();				
			
			}else if(browserName.equalsIgnoreCase("IE"))
			{
				driver= getIEDriver();	
			}
			
		}
		return driver;
	}
}
