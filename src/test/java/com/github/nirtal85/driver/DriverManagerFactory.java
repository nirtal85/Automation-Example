package com.github.nirtal85.driver;

public class DriverManagerFactory {

	public static DriverManager getManager(DriverType type) {
		DriverManager driverManager;

		switch (type) {
		case CHROME:
			driverManager = new ChromeDriverManager();
			break;
		case FIREFOX:
			driverManager = new FirefoxDriverManager();
			break;
		case OPERA:
			driverManager = new OperaDriverManager();
			break;
		default:
			driverManager = new ChromeDriverManager();
			break;
		}
		return driverManager;
	}
}