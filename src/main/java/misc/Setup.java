package misc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class Setup {
	protected int gamesToPlay = 1;
	protected ChromeOptions co = new ChromeOptions();
	protected FirefoxOptions fo = new FirefoxOptions();
	public WebDriver driver;
	protected String environment = "uat";
	protected String driverType = "chrome";
	protected boolean headless = false;
	protected boolean pass = false;
	protected FirefoxProfile profile = new FirefoxProfile();
	protected String email = "darwinuat802@np.alc.ca";
	protected String game = null;
	protected String testname = "";
	protected boolean uxtesting = false;
	protected boolean testPassed = false;
	
	public Setup() {
	}
	
	public boolean isHeadless() {
		return headless;
	}
	
	public void setEnvironment(String envi) {
		environment = envi;
	}
	
	public void enableHeadless() {
		co.addArguments("--headless");
		headless = true;
	}
	
	public void setRemoteDriverChrome(String testName, String buildName) {
//		String proxyHost = "http://proxy.alc.ca:8080";
//		String ignoreProxy = "*.alc.ca,*si-dscqc.sciplay-logic.com,*.gamelogic.com,*.svc,*.np.alc.ca,127.0.0.1,localhost";
//		Proxy p = new Proxy();
//		p.setProxyType(Proxy.ProxyType.MANUAL);
//		p.setHttpProxy(proxyHost);
//		p.setSslProxy(proxyHost);
//		p.setNoProxy(ignoreProxy);
//		co.setCapability("proxy", p);
		if(!uxtesting)
			co.setCapability("recordVideo", false);
		co.setCapability("name", testName);
		if(game!=null)
			co.setCapability("build", buildName);
		else
			co.setCapability("build", environment);
		co.setCapability("tz", "America/Halifax");
		co.setCapability("idleTimeout", 480);
		co.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36");
		co.addArguments("start-maximized");
	}
	
	public void setRemoteDriverFireFox(String testName, String buildName) {
		String proxyHost = "proxy.alc.ca";
		int proxyPort = 8080;
		String ignoreProxy = "*.alc.ca,*si-dscqc.sciplay-logic.com,*.gamelogic.com,*.svc,*.np.alc.ca,127.0.0.1,localhost";
		profile.setPreference("general.useragent.override", "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36");
		profile.setPreference("permissions.default.geo",1); // allows geolocation by default
		profile.setPreference("network.proxy.type", 1);
		profile.setPreference("network.proxy.http", proxyHost);
		profile.setPreference("network.proxy.http_port", proxyPort);
		profile.setPreference("network.proxy.ssl", proxyHost);
		profile.setPreference("network.proxy.ssl_port", proxyPort);
		profile.setPreference("network.proxy.no_proxies_on", ignoreProxy);
	    fo.setProfile(profile);
		
		if(!uxtesting)
			fo.setCapability("recordVideo", false);
		co.setCapability("name", testName);
		if(game!=null)
			fo.setCapability("build", buildName);
		else
			fo.setCapability("build", environment);
		fo.setCapability("tz", "America/Halifax");
		fo.setCapability("idleTimeout", 480);
	}
	
	public void setGameToPlay(int plays) {
		gamesToPlay = plays;
	}
	
	public void testPassFail(boolean b) {
		Cookie cookie = null;
		if(b)
			cookie = new Cookie("zaleniumTestPassed", "true");
		else
			cookie = new Cookie("zaleniumTestPassed", "false");
		driver.manage().addCookie(cookie);
	}
	
	@Parameters({ "suit-envi", "suit-local", "suit-user", "suit-head", "suit-jenkins", "suit-gamesplayed"})
	@BeforeTest
	public void config(@Optional String envi,@Optional String local,@Optional String user, @Optional String head, @Optional String jenk, @Optional String plays) throws MalformedURLException {
		// Uncomment the next line to run in headless mode.
		head = "false";

		if(envi != null)
			setEnvironment(envi);
		if(user != null)
			email = user;
		if(head != null)
			enableHeadless();
		if(plays!=null) {
			int iPlay = Integer.parseInt(plays);
			setGameToPlay(iPlay);
		}
		
		if(jenk != null) {
			System.out.println("Running though Settings for remote jenkins");
			if(driverType.equalsIgnoreCase("firefox")) {
				setRemoteDriverFireFox(testname,email);
				driver = new RemoteWebDriver(new URL("http://zalenium.zalenium.svc:4444/wd/hub"), fo);
				driver.manage().window().maximize();
			}
			else {
				setRemoteDriverChrome(testname,email);
				driver = new RemoteWebDriver(new URL("http://zalenium.zalenium.svc:4444/wd/hub"), co);
			}
		}
		else if(local != null) {
			enableHeadless();
			co.addArguments("--window-size=1920,1080");
			String proxyHost = "http://proxy.alc.ca:8080";
			String ignoreProxy = "*.alc.ca,*si-dscqc.sciplay-logic.com,*.gamelogic.com,*.svc,*.np.alc.ca,127.0.0.1,localhost";
			Proxy p = new Proxy();
			p.setProxyType(Proxy.ProxyType.MANUAL);
			p.setHttpProxy(proxyHost);
			p.setSslProxy(proxyHost);
			p.setNoProxy(ignoreProxy);
			co.setCapability("proxy", p);
			driver = new ChromeDriver(co);
			/*
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"false");
			driver = new FirefoxDriver(fo);
			driver.manage().window().maximize();
			*/
		}
		else {
			System.out.println("Running though settings for local run");
			if(driverType.equalsIgnoreCase("firefox")) {
				System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"firefoxLogs");
				profile.setPreference("permissions.default.geo",1); // allows geolocation by default
				profile.setPreference("media.volume_scale", "0.0");
			    fo.setProfile(profile);
				driver = new FirefoxDriver(fo);
				driver.manage().window().maximize();
			}
			else {
				co.addArguments("start-maximized");
				//co.addArguments("enable-automation");
				//co.addArguments("--window-size=1920,1080");
				//enableHeadless();
				//co.addArguments("--no-sandbox");
				//co.addArguments("--disable-infobars");
				//co.addArguments("--disable-dev-shm-usage");
				//co.addArguments("--disable-browser-side-navigation");
				//co.addArguments("--disable-gpu");
				co.addArguments("--mute-audio");
				driver = new ChromeDriver(co);
			}
		}
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	
	@AfterTest
	public void tearDown() {
		System.out.println("Status of test passed: " + testPassed);
		testPassFail(testPassed);
		driver.quit();
	}
}