package org.mycompany.myname.Parsers;

import org.mycompany.myname.OutputInfo;
import org.openqa.selenium.By;

public class EgrulSrc implements Parser {


    OutputInfo infoSet = new OutputInfo();

    @Override
    public OutputInfo getPage() throws InterruptedException {
        try {


            Info.getDriver().get("https://egrul.nalog.ru/index.html#");
            Info.getDriver().findElement(By.xpath(".//div[@id='uni_text_1']//input[@id='query']")).sendKeys(Info.inn);
            Info.getDriver().findElement(By.xpath(".//div[@class='buttons']//button[@id='btnSearch']")).click();
            Thread.sleep(2000);
            try {
                if ((Info.getDriver().findElements(By.xpath(".//div[@id='resultContent']//div")).size() != 0)||!Info.getDriver().findElement(By.xpath(".//div[@id='resultPanel']//div[@id='tooManyRows']/span[@id='totTxt']")).getText().equals("0"))
                    infoSet.isInEgrul = true;
            } catch (Exception e) {
              //  if (!Info.getDriver().findElement(By.xpath(".//div[@id='resultPanel']//div[@id='tooManyRows']/span[@id='totTxt']")).getText().equals("0"))
                   // infoSet.isInEgrul = true;
            }




        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return infoSet;
    }
}
