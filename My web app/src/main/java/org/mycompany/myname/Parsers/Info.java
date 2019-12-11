package org.mycompany.myname.Parsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Info {
    private static WebDriver driver;
    public static String inn="";
    public static String name="a";
    public String BaseUrl;

    public static WebDriver getDriver() {
        return driver;
    }

    public static void createDriver(){
        //ChromeOptions options = new ChromeOptions();
       // options.addArguments("headless");
        try{
            System.setProperty("webdriver.chrome.driver","C:\\Users\\odint\\Desktop\\My web app-browser\\My web app\\chromedriver.exe");
            Info.driver = new ChromeDriver(/*options*/);
        }
        catch (Exception e)
        {}


    }

}
