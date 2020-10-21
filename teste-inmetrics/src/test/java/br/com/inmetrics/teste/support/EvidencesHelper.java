package br.com.inmetrics.teste.support;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class EvidencesHelper {

		public static EvidencesHelper instance;
		private EvidencesHelper(){}
		
		public static EvidencesHelper getInstance() {
			if(instance == null) {
				instance = new EvidencesHelper();
			}
			return instance;
		}
		 
		public static void saveScreenShot(WebDriver driver, String filepath) {
			try 
			{
				System.out.println("FilePath: " + filepath);
				TakesScreenshot ts = (TakesScreenshot)driver;
				File source = ts.getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(source, new File(filepath));
			} catch (WebDriverException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
