package ZFPLibrary;

/*Importing the methods required for this class*/
//Methods:-
import org.apache.log4j.*;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
//import java.sql.Date;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;


import ZFPLibrary.ZFPConstants;

public abstract class ZFPMethods extends ZFPConstants{
	/**
	 * @param args
	 */

	//Selenium selenium;appending comment

	WebDriver driver1;
	//WebDriverWait waitforLoading;
	
	Date date = new Date();
	
	//static Logger log = Logger.getLogger(ZFPMethods.class.getName()); 

	//private static String ZFPURL = "http://3.232.165.108/ZFP?model=true&sui=1.2.840.113619.2.55.3.185255957.691.1221132133.601";
	//private static String ZFPURL = "http://3.204.41.183/ZFP?model=true&sui=1.2.840.113619.2.55.3.346852148.29.1225341202.166";
	Logger logs;

	// @Constructor to initialize the Chrome Drivers
	
	abstract void loginToZFP(String strUsername, String password);
	
	//Reading from the text file
	abstract String readfrominputfile(String strItem, String filename);	
	
	abstract void waitforPageLoad(String strProperty, String strValue);
	
	abstract void logoutOfZFP();
	
	abstract void clickOnDialog(String strDlgName, String strButtonName);
	
	abstract Logger getLogger(String className) throws IOException;

	abstract void waitforoperation(long timeoutval);

	abstract void checkImage(String file1, String file2, int intTolerance);

	abstract void clickButton(String strButtonName);
	
	abstract void selectMenu(String strMenuName);
	
	abstract void ver_MenuSelected(String strMenu, String strSelection);
	
	abstract void clickonImage(String strImageNumber, String strTypeOfClick, String strPosition);
	
	abstract String getImageCoordinates(String strImageNumber, String strPosition);
	
	abstract void performImageOperation(String strImageNumber, String imgOperation);
	
	abstract void captureandcompareImage(String strImageNumber, int intTestID,  int intStepNo, int intLoopNo);
	
	//abstract void ImageCapture(int x, int y, int height, int width, int intTestID);
	
	abstract void ver_windowDisplayed(String strWindowName, String strDisplayValue);
	
	abstract void keyPress(String[] steKeys) throws NoSuchFieldException, SecurityException;
	
}
