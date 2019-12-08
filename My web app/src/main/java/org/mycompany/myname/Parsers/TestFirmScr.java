package org.mycompany.myname.Parsers;
import org.mycompany.myname.OutputInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
public class TestFirmScr implements Parser{
    OutputInfo infoSet = new OutputInfo();
    @Override
    public OutputInfo getPage() throws InterruptedException {
        Info.getDriver().get("https://www.testfirm.ru/");
        Info.getDriver().findElement(By.xpath(".//div[@class='form-group full']/input[@id='searchText']")).sendKeys(Info.inn);
        Info.getDriver().findElement(By.xpath(".//button[@class='btn btn-success btn-lg']")).click();
        Thread.sleep(3000L);
        return infoSet;
    }
}
