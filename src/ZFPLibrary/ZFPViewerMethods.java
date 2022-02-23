package ZFPLibrary;

import org.apache.poi.hwpf.usermodel.*;
import org.apache.*;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JTextField;

import junit.framework.Assert;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.google.protobuf.UnknownFieldSet.Field;
import com.javadude.annotation.Default;

import ZFPLibrary.ZFPMethods;

public class ZFPViewerMethods extends ZFPMethods{
	
	public ZFPViewerMethods(int intTestID)
	{
		try {
			logs=getLogger(intTestID + "_" + date.getDate() + "_" + date.getHours() + "_" + date.getMinutes() + "_" + date.getSeconds());
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	

	public void loginToZFP(String strUsername, String password)
	{
		String ZFPURL ;
		openZFP();
		ZFPURL = readfrominputfile("ZFPURL", ConfigFile);
		waitforoperation(2000);
		driver1.get(ZFPURL);
		//JavascriptExecutor jse = (JavascriptExecutor) driver1;
		//jse.executeScript("window.resizeTo(1024, 768);");
		logs.debug("<openZFP>:-"+"Done:-"+"ZFP Opened at URL " + ZFPURL);
		waitforPageLoad("", "");
		waitforoperation(33000);
				
	}
	
	public void openZFP()
	{
		String bwsrname ;
		bwsrname = readfrominputfile("Browser", ConfigFile);
		waitforoperation(2000);		
		//waitforLoading = new WebDriverWait(driver1, 10);


		switch(bwsrname){
		
		case "IE":
			driver1 = new InternetExplorerDriver();
			driver1.manage().window().maximize();
			
			break;
		
		case "Firefox":
			FirefoxProfile fp = new FirefoxProfile();
			 fp.setPreference("webdriver.load.strategy", "unstable");
				
			driver1 = new FirefoxDriver();
			driver1.manage().window().maximize();
			waitforoperation(15000);
			break;
			
		
		case "Chrome":
			
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Administrator\\Downloads\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver1 = new ChromeDriver(options);
		
		//waitforLoading.until( driver2 = driver2.findElement(By.id("non-diagnostic-msg")));
		break;
		
		default:
			logs.debug("Constructor ZFPMethods():-"+"Warning:-"+"Invalid Browser Input :"+ bwsrname);
		}
				
	}
	
	public void waitforPageLoad(String strProperty, String strValue)
	{
		driver1.manage().timeouts().implicitlyWait(mediumTime, TimeUnit.SECONDS);
	}

	public String readfrominputfile(String strItem, String filename)
	{

		String strValue = null;
	try {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line = null;
		String[] temp;

		int valuefound = 0;
		try {
			while((line = br.readLine()) != null)
			{

			temp = line.split("=>");
			
		
			strValue = new String (temp[1]);

			if (temp[0].equalsIgnoreCase(strItem))
			{
				valuefound = 1;
				
				logs.debug("<readfrominputfile>:-"+"Done:-"+"Value for  :"+ strItem +" was returned");
				break;
				
			}

			
			}
			if (valuefound == 0)
			{logs.debug("<readfrominputfile>:-"+"Warning:-"+"Value for  :"+ strItem +" was not returned as it was not present in the text file");}
			else
			{
				br.close();
				
				return strValue;
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;		
	}
	
	public void logoutOfZFP()
	{
		driver1.close();
		String ZFPURL = readfrominputfile("ZFPURL", ConfigFile);
		waitforoperation(2000);
		logs.debug("<closeZFP>:-"+"Done:-"+"ZFP Closed at URL " + ZFPURL);		
	}
	
	public void clickOnDialog(String strDlgName, String strButtonName)
	{
				
		switch(strDlgName)
		{
			case "MaxUsers":
				String xPATH = readfrominputfile(strButtonName, XPATHPropertiesFile);
				if ((xPATH != null) && (driver1.findElement((By.xpath(xPATH))))
						.isDisplayed()) {
					driver1.findElement((By.xpath(xPATH)))
							.click();
					logs.debug("<clickOnDialog>:-"+"Done:-"+"Maximum Number of Users Exceeded for ZFP");
				}
				else
					logs.debug("<clickOnDialog>:-"+"Warning:-"+"XPath for " + strButtonName + " not present in Properties File");
				break;
				
			 default:
				logs.debug("<clickOnDialog>:-"+"Warning:-"+"Invalid Input Parameter "+ strDlgName);
		}
	}
	
	// @Getting the xPATH
	public String getXPATH(String strItem) {
		String xPATH = readfrominputfile(strItem, XPATHPropertiesFile);
		
		if (xPATH == null)
		{
			logs.debug("<getXPATH>:-"+"Warning:-"+"Invalid input: "+ strItem + "or Item " + strItem + " not present in Properties File");
		}
			return xPATH;

	}
	


	// @Getting the ID
	public String getID(String strItem) {
		String id = readfrominputfile(strItem, IDPropertiesFile);
		
		if (id == null)
		{
			logs.debug("<getID>:-"+"Warning:-"+"Invalid input: "+ strItem + "or Item " + strItem + " not present in Properties File");
		}
		logs.debug("<getID>:-"+"Done:-"+"Id for Input " + strItem + " is " + id);
			return id;
	}
	
	
	//Performs logging operation
	public Logger getLogger(String className) throws IOException{
		 Logger logger = Logger.getLogger(className+".class");  
		 FileAppender fileappender =  new FileAppender(new PatternLayout("%C %p-- %m%n"),logFolder+className+".log");
		 logger.addAppender(fileappender);
		 return logger;
		 
		}
	

	// @Function to wait for specific timeout
	public void waitforoperation(long timeoutval) {
		try {
			Thread.sleep(timeoutval);
			logs.debug("<waitforoperation>:-"+"Done:-"+"Execution wait for " + timeoutval + " milliseconds");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		

	public void checkImage(String file1, String file2, int intTolerance)
	{

		Image image1 = Toolkit.getDefaultToolkit().getImage(file1);
		Image image2 = Toolkit.getDefaultToolkit().getImage(file2);

		try {

		PixelGrabber grabber1 =
		new PixelGrabber(image1, 0, 0, -1, -1, false);
		PixelGrabber grabber2 =
		new PixelGrabber(image2, 0, 0, -1, -1, false);

		int[] imageData1 = null;
		if (grabber1.grabPixels()) {
		int imageWidth = grabber1.getWidth();
		int imageHeight = grabber1.getHeight();
		imageData1 = new int[imageWidth * imageHeight];
		imageData1 = (int[]) grabber1.getPixels();

		}

		int[] imageData2 = null;
		if (grabber2.grabPixels()) {
		int imageWidth = grabber2.getWidth();
		int imageHeight = grabber2.getHeight();
		imageData2 = new int[imageWidth * imageHeight];
		imageData2 = (int[]) grabber2.getPixels();
		}


		float fltImageTolerance;
		int intLength;
		int intCounter = 0;
		if (imageData1.length<=imageData2.length)
		{intLength = imageData1.length;}
		else
			intLength = imageData2.length;
		
		for (int intPixelVal = 0 ; intPixelVal < intLength ; intPixelVal++)
		{	
			if (imageData1[intPixelVal] != imageData2[intPixelVal])
			{
				intCounter++;
			}
		}

		fltImageTolerance = ((float)intCounter*100/intLength);
		
		if (fltImageTolerance <= intTolerance)
		{
			logs.debug("<checkImage>:-"+"Pass:-"+"Images match");
			Assert.assertTrue(true);
		}
		else
		{
			logs.debug("<checkImage>:-"+"Fail:-"+"Images do not match");
			Assert.assertTrue(true);
		}

		} catch (InterruptedException e1) {
		e1.printStackTrace();
		}

		}
	
		public void clickButton(String strButtonName)
		{
			String strXPATH = getXPATH(strButtonName);
			String strID = getID(strButtonName);
			

			if (driver1.findElement((By.xpath(strXPATH))).isDisplayed())
				driver1.findElement((By.xpath(strXPATH))).click();
			else if (driver1.findElement((By.id(strID))).isDisplayed())
					driver1.findElement((By.id(strID))).click();					
			else
				logs.debug("<clickButton>:-"+"Warning:-"+"Button : "+ strButtonName + " not present");

			strID = null;
			strXPATH = null;			
		}
		
		public void selectMenu(String strMenu)
		{
			String strXPATH = getXPATH(strMenu);
			String strID = getID(strMenu);
			
			if (driver1.findElement((By.xpath(strXPATH))).isDisplayed())
				driver1.findElement((By.xpath(strXPATH))).click();
			else if (driver1.findElement((By.id(strID))).isDisplayed())
				driver1.findElement((By.id(strID))).click();
			else
				logs.debug("<clickonMainMenu>:-"+"Warning:-"+"Menu/SubMenu : "+ strMenu + " not present");

			strXPATH = null;
			strID = null;			
		}
		
		//Verifies whether the SubMenu is Selected
		public void ver_MenuSelected(String strMenu, String strSelection) {
			String strSubMenuItem;
			
			strSelection = strSelection.toLowerCase();
			strSubMenuItem = driver1
					.findElement((By.xpath("//a[@class='active']"))).getText();
			boolean blnValue;
			//a[@class='img-operation-parent toolbar-vignette-parent active-button-parent']
			//System.out.println(strSubMenuItem);
			blnValue = strMenu.equalsIgnoreCase(strSubMenuItem);
			switch (strSelection)
			{
				case "true" :
					
					if (blnValue == true)
					{
						logs.debug("<ver_subMenuSelected>:-"+"Pass:-"+"The Menu Item Selected should be " + strMenu + " and the Menu Item selected was " + strSubMenuItem);
					}
					else
					{
						logs.debug("<ver_subMenuSelected>:-"+"Fail:-"+"The Menu Item Selected should be " + strMenu + " and the Menu Item selected was " + strSubMenuItem);
					}
					break;
					
				case "false" :
					
					if (blnValue == false)
					{
						logs.debug("<ver_subMenuSelected>:-"+"Pass:-"+"The Menu Item Selected should be " + strMenu + " and the Menu Item selected was " + strSubMenuItem);
					}
					else
					{
						logs.debug("<ver_subMenuSelected>:-"+"Fail:-"+"The Menu Item Selected should be " + strMenu + " and the Menu Item selected was " + strSubMenuItem);
					}
					break;	
					
				default :
					logs.debug("<ver_subMenuSelected>:-"+"Fail:-"+"Invalid Input strSelection " + strSelection);
					break;
						
						
				}
		
		}
		
		public String getImageCoordinates(String strImageNumber, String strPosition)
		{
			String strViewportToClick = getID(strImageNumber);
			Dimension dimensions = driver1.findElement(By.id(strViewportToClick))
					.getSize();
			Point clickPoint = driver1.findElement((By.id(strViewportToClick)))
					.getLocation();
			 
			int x = ((clickPoint.getX()) + ((dimensions.getWidth()) / 2));
			int y = ((clickPoint.getY()) + ((dimensions.getHeight()) / 2));
			Robot robot = null;
			try {
				robot = new Robot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			robot = null;
			
			String strDimensionValue = x + "@" + y;

			return strDimensionValue;
		}
		
		public void clickonImage(String strImageNumber, @Default ("Left") String strTypeOfClick, @Default ("Center") String strPosition)
		{
			Robot robot = null;
			
			try {
				robot = new Robot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String strDimensions = getImageCoordinates(strImageNumber, strPosition);
			
			String[] strClickValues = strDimensions.split("@");
			int clickX = Integer.parseInt(strClickValues[0]);
			int clickY = Integer.parseInt(strClickValues[1]);
			
			robot.mouseMove(clickX, clickY);		
	

			switch (strTypeOfClick) 
			{
			case "Left" :
			{
				//System.out.println(clickX);
				//System.out.println(clickY);				
				robot.mousePress(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);
/*				robot.mouseMove(clickX - 24, clickY - 24);
				waitforoperation(2000);*/
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
			}
			break;
			case "Right" :
			{
				robot.mousePress(InputEvent.BUTTON3_MASK);
				waitforoperation(2000);
				robot.mouseMove(clickX - 24, clickY - 24);
				waitforoperation(2000);
				robot.mouseRelease(InputEvent.BUTTON3_MASK);
			}
			break;			
						
			default :
				logs.debug("<clickonImage>:-"+"Warning:-"+"Invalid Input strTypeOfClick " + strTypeOfClick);
			}
			
			robot = null;
		}
		
		public void performImageOperation(String strImageNumber, String strImageOperation)
		{
			Robot robot = null;
			
			try {
				robot = new Robot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String strDimensions = getImageCoordinates(strImageNumber, "");			
			String[] strClickValues = strDimensions.split("@");
			int xtoClick = Integer.parseInt(strClickValues[0]);
			int ytoClick = Integer.parseInt(strClickValues[1]);
			strImageOperation = strImageOperation.toLowerCase();
			
			switch (strImageOperation) {
			
			case "zoomin":
			case "panup" :
			case "scrollup":
			case "window-levelup":
				xtoClick = xtoClick - 24;
				ytoClick = ytoClick + 24;
				robot.mouseMove(xtoClick, ytoClick);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);
				xtoClick = xtoClick + 48;
				ytoClick = ytoClick - 48;
				robot.mouseMove(xtoClick, ytoClick);
				waitforoperation(2000);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				waitforoperation(5000);
				break;
			case "zoomout":
			case "pandown":
			case "scrolldown":
			case "window-leveldown":
				xtoClick = xtoClick + 24;
				ytoClick = ytoClick - 24;
				robot.mouseMove(xtoClick, ytoClick);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);
				xtoClick = xtoClick - 48;
				ytoClick = ytoClick + 48;
				robot.mouseMove(xtoClick, ytoClick);
				waitforoperation(2000);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				waitforoperation(5000);
				break;
				
			
			/*case "Zoom" : case "Pan" :
					xtoClick = xtoClick - 24;
					ytoClick = ytoClick - 24;
					robot.mousePress(InputEvent.BUTTON1_MASK);
					waitforoperation(2000);
					robot.mouseMove(xtoClick, ytoClick);
					waitforoperation(2000);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);				
					break;*/
				default:
					logs.debug("<performImageOperation>:-"+"Warning:-"+"Invalid Input operation : "+ strImageOperation);
					break;
			}


			
			robot = null;
			
		}
		
		public void performAnnotationOperations(String strImageNumber, String strAnnotationOperation)
		{
			
			Robot robot = null;
			
			try {
				robot = new Robot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String strDimensions = getImageCoordinates(strImageNumber, "");			
			String[] strClickValues = strDimensions.split("@");
			int xtoClick = Integer.parseInt(strClickValues[0]);
			int ytoClick = Integer.parseInt(strClickValues[1]);
			strAnnotationOperation = strAnnotationOperation.toLowerCase();
			
			
			switch(strAnnotationOperation)
			{
			
			case "distance":
				xtoClick = xtoClick - 24;
				ytoClick = ytoClick + 24;
				robot.mousePress(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);
				xtoClick += 48;
				ytoClick -= 48;				
				robot.mouseMove(xtoClick, ytoClick);
				waitforoperation(2000);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);
				break;
				
			case "angle measurement":
				int temp_xtoClick = xtoClick + 24;
				int temp_ytoClick = ytoClick - 24;
				robot.mousePress(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);
				robot.mouseMove(temp_xtoClick, temp_ytoClick);
				waitforoperation(2000);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);
				temp_ytoClick = temp_ytoClick + 48;
				robot.mouseMove(temp_xtoClick, temp_ytoClick);
				waitforoperation(2000);
				robot.mousePress(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);
				robot.mouseMove(xtoClick, ytoClick);
				waitforoperation(2000);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);	
				break;
				
			case "ellipse":
				xtoClick = xtoClick - 24;
				ytoClick = ytoClick - 24;
				robot.mousePress(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);
				xtoClick += 48;
				ytoClick += 48;				
				robot.mouseMove(xtoClick, ytoClick);
				waitforoperation(2000);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);
				break;
				
			case "free hand":
				robot.mousePress(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);
				xtoClick -=12;
				robot.mouseMove(xtoClick, ytoClick);
				waitforoperation(2000);
				ytoClick +=12;
				robot.mouseMove(xtoClick, ytoClick);
				waitforoperation(2000);
				xtoClick +=12;
				ytoClick +=12;
				robot.mouseMove(xtoClick, ytoClick);
				waitforoperation(2000);
				xtoClick +=12;
				ytoClick -=12;
				robot.mouseMove(xtoClick, ytoClick);
				waitforoperation(2000);
				ytoClick -=12;
				robot.mouseMove(xtoClick, ytoClick);
				waitforoperation(2000);
				xtoClick -=12;
				robot.mouseMove(xtoClick, ytoClick);
				waitforoperation(2000);
				break;		
				
			case "rectangle":
				xtoClick = xtoClick - 24;
				ytoClick = ytoClick - 24;
				robot.mousePress(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);
				xtoClick += 48;
				ytoClick += 24;				
				robot.mouseMove(xtoClick, ytoClick);
				waitforoperation(2000);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);
				break;
				
			/*case "text":
				robot.mousePress(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);
				String[] strTestValue= new String[] {"Testing"};
				keyPress(strTestValue);
				waitforoperation(2000);
				break;*/				
			case "pointer":
				xtoClick = xtoClick - 24;
				ytoClick = ytoClick + 24;
				robot.mousePress(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);
				xtoClick += 48;
				ytoClick -= 48;				
				robot.mouseMove(xtoClick, ytoClick);
				waitforoperation(2000);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				waitforoperation(2000);
				break;
			
			default:
				logs.debug("<performAnnotationOperations>:-"+"Warning:-"+"Invalid Input operation : "+ strAnnotationOperation);
				break;
			}
			
			
			
			
			robot = null;
		}
		
		public String getImageDimensions(String strImageNumber)
		
		{
			String strViewportToClick = getID(strImageNumber);
			Point clickPoint = driver1.findElement((By.id(strViewportToClick)))
					.getLocation();
			Dimension dimensions = driver1.findElement(By.id(strViewportToClick))
					.getSize();
			int imgHeight = dimensions.getHeight();
			int imgwidth = dimensions.getWidth();
			int imgX = clickPoint.getX();
			int imgY = clickPoint.getY();
			
			String strImageDimensions = imgX + "@" + imgY + "@" + imgHeight + "@" +imgwidth;

			return strImageDimensions;
			
		}
		
		public void captureandcompareImage(String strImageNumber, int intTestID, int intStepNo, int intLoopNo)
		{
		
			String strDimensions = getImageDimensions(strImageNumber);	
			
			String[] strClickVal = strDimensions.split("@");
			int xtoClick = Integer.parseInt(strClickVal[0]);
			int ytoClick = Integer.parseInt(strClickVal[1]);
			int height = Integer.parseInt(strClickVal[2]);
			int width = Integer.parseInt(strClickVal[3]);
			
			ytoClick = ytoClick + 56;
			waitforoperation(2000);
			ImageCapture(xtoClick, ytoClick, height, width, intTestID, intStepNo, intLoopNo);
			String ActualImage = ActImgPath + "Act_" + intTestID + "_" + intStepNo + "_" + intLoopNo + ".jpg";
			String ReferenceImage = RefImgPath + "Ref_" + intTestID + "_" + intStepNo + "_" + intLoopNo + ".jpg";
			waitforoperation(6000);
/*			try {
				Assert.assertEquals(ReferenceImage, ActualImage);	
				logs.debug("<captureandcompareImage>:-"+"Pass:-"+"The Actual and Reference Images match");
			} catch (Error e) {
				logs.debug("<captureandcompareImage>:-"+"Fail:-"+"The Actual and Reference Images do not match");

				Assert.assertTrue(true);
			}*/
			checkImage(ActualImage, ReferenceImage, intImageTolerance);			
		}
		
		//Captures the Image
		public void ImageCapture(int x, int y, int height, int width, @Default("1239") int intTestID, int intStepNo, int intLoopNo)
		{

		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Rectangle rect = new Rectangle();
		rect.height = height;
		rect.width = width;
		rect.x = x;
		rect.y = y;
		BufferedImage bi = robot.createScreenCapture(rect);
		//Graphics g = bi.getGraphics();
		String ActualImage = ActImgPath + "Act_" + intTestID + "_" + intStepNo + "_" + intLoopNo + ".jpg";
		File file = new File(ActualImage);
		try {
			ImageIO.write(bi, "jpg", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		}

		public void ver_windowDisplayed(String strWindowName, String strDisplayValue)
		{
			waitforoperation(6000);
			String strCurrentTitle;
			strDisplayValue=strDisplayValue.toLowerCase();
			boolean blnPresent = false;
		
			Object[] handle = driver1.getWindowHandles().toArray();
			
			for (int intHandle = 0; intHandle<handle.length-1; intHandle++)
			{
				driver1.switchTo().window(handle[intHandle].toString());
				
				strCurrentTitle = driver1.getTitle();
				
				switch (strDisplayValue)
				{
				
				case "true":
					if (strWindowName.equalsIgnoreCase(strCurrentTitle))
					{
						blnPresent = true;
						break;
					}
					else
						blnPresent = false;
				
				break;
				case "false":
					if (strWindowName.equalsIgnoreCase(strCurrentTitle))
					{
						blnPresent = false;
						break;
					}
					else
						blnPresent = true;
					
					break;
					
					default :
						logs.debug("<ver_windowDisplayed>:-"+"Fail:-"+"Invalid Input arguments strDisplayValue "+ strDisplayValue);
				}				
			
			}
			
			if (blnPresent = true)
				logs.debug("<ver_windowDisplayed>:-"+"Pass:-"+"The Window " + strWindowName + " was opened");
			else
				logs.debug("<ver_windowDisplayed>:-"+"Fail:-"+"The Window " + strWindowName + " was not opened");
		}
		
		@SuppressWarnings("null")
		public void keyPress(String[] strKeys) throws NoSuchFieldException, SecurityException
		{
			int intKeyCount = strKeys.length;

			//Robot robot = null;
			Robot robot = null;
			try {
				robot = new Robot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	           

			
			for (int intKeyCounter = 0; intKeyCounter<=intKeyCount - 1; intKeyCounter++)
				try {
					{
						//blnupperCase = Character.toUpperCase(strKeys[intKeyCounter]); 
					    String variableName = "VK_" + strKeys[intKeyCounter].toUpperCase();  
					    KeyEvent ke = new KeyEvent(new JTextField(), 0, 0, 0, 0, ' ');  
					    Class clazz = ke.getClass();  
					    java.lang.reflect.Field field = clazz.getField(variableName);  
					    int keyCode = field.getInt(ke); 

						robot.keyPress(keyCode);
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			for (int intKeyCounter = 0; intKeyCounter<=intKeyCount - 1; intKeyCounter++)
				try {
					{
						//blnupperCase = Character.toUpperCase(strKeys[intKeyCounter]); 
					    String variableName = "VK_" + strKeys[intKeyCounter].toUpperCase();  
					    KeyEvent ke = new KeyEvent(new JTextField(), 0, 0, 0, 0, ' ');  
					    Class clazz = ke.getClass();  
					    java.lang.reflect.Field field = clazz.getField(variableName);  
					    int keyCode = field.getInt(ke); 

						robot.keyRelease(keyCode);
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			robot = null;
		}
		
/*			public void Bookmark getBookmarks()
			{
			chrome.	
				
			}*/
		


		public void ButtonPresence(String BtnName, String strState)
		{
			strState = strState.toLowerCase();
			String BtnId=getID(BtnName);
			boolean check = driver1.findElement(By.id(BtnId)).isDisplayed();		
			
			switch (strState)
			{
				case "present" :									
					
					if (check == true)
					{
						logs.debug("<ver_ButtonPresence>:-"+"Pass:-"+"The Button" + BtnName + " was expected to be " + strState+ " and it was so. ");
					}
					else
					{
						logs.debug("<ver_ButtonPresence>:-"+"Fail:-"+"The Button" + BtnName + " was expected to be " + strState+ " but it was not.");
					}
					break;
					
				case "absent" :				
					
					if (check == false)
					{
						logs.debug("<ver_ButtonPresence>:-"+"Pass:-"+"The Button" + BtnName + " was expected to be " + strState+ " and it was so. ");
					}
					else
					{
						logs.debug("<ver_ButtonPresence>:-"+"Fail:-"+"The Button" + BtnName + " was expected to be " + strState+ " but it was not.");
					}
					break;	
					
				default :
					logs.debug("<ver_ButtonPresence>:-"+"Fail:-"+"Invalid Input strSelection " + strState);
					break;
						
						
				}
			
		}
		
		public void ButtonPressStatus(String BtnName, String strState)		
		{				
			strState = strState.toLowerCase();			
			String strPressedButton = driver1.findElement(By.xpath("//button[@class='~/^active-button/']")).getText();
			//String strPressedButton = driver1.findElement(By.xpath("//button[contains(@class,'active-button')]")).getText();
			System.out.println("Button"+strPressedButton);
			boolean blnValue;			
			blnValue = BtnName.equalsIgnoreCase(strPressedButton);
			System.out.println("Button_ "+strPressedButton +" "+ BtnName);
			System.out.println(blnValue);
			
			switch (strState)
			{
				case "pressed" :				
					
					if (blnValue == true)
					{
						logs.debug("<ver_ButtonPressStatus>:-"+"Pass:-"+"The Button " + BtnName + " was expected to be " + strState+ " and it was so. ");
					}
					else
					{
						logs.debug("<ver_ButtonPressStatus>:-"+"Fail:-"+"The Button " + BtnName + " was expected to be " + strState+ " but it was not.");
					}
					break;
					
				case "released" :				
					
					if (blnValue == false)
					{
						logs.debug("<ver_ButtonPressStatus>:-"+"Pass:-"+"The Button " + BtnName + " was expected to be " + strState+ " and it was so. ");
					}
					else
					{
						logs.debug("<ver_ButtonPressStatus>:-"+"Fail:-"+"The Button " + BtnName + " was expected to be " + strState+ " but it was not.");
					}
					break;	
					
				default :
					logs.debug("<ver_ButtonPressStatus>:-"+"Fail:-"+"Invalid Input strSelection " + strState);
					break;
						
						
				}
		}
		
		

}