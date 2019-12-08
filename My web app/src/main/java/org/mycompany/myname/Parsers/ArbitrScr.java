package org.mycompany.myname.Parsers;

import com.google.common.collect.Iterables;
import org.mycompany.myname.OutputInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Stream;

public class ArbitrScr implements Parser{

    private int attemptCount =0;
    String name =Info.name;
    OutputInfo infoSet = new OutputInfo();

    public OutputInfo getPage() throws InterruptedException {
        try {
            Info.getDriver().get("http://kad.arbitr.ru/");
            int number = 1;

            Info.getDriver().findElement(By.xpath(".//div[@class='tag']/textarea[@class='g-ph']")).sendKeys(Info.inn);
            Info.getDriver().findElement(By.xpath(".//div[@class='b-button-container']/button[@type='submit']")).click();
            Thread.sleep(2000L);
            analise();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return infoSet;

    }

    private void analise() throws InterruptedException {
        List<WebElement> pages = Info.getDriver().findElements(By.xpath(".//div[@id='b-footer-pages']//ul[@id='pages']//li"));
        if (pages.size()<5)
            findRole(Info.getDriver().findElements(By.xpath(".//div[@id='table']//tbody//tr")));
        else {
            int lastPage = Integer.parseInt(pages.get(pages.size() - 3).getText());
            for (int i = 0; i < lastPage; i++) {
                WebElement currPage = Info.getDriver().findElement(By.xpath(".//div[@id='b-footer-pages']//ul[@id='pages']//li[@class='active']"));
                List<WebElement> table = Info.getDriver().findElements(By.xpath(".//div[@id='table']//tbody//tr"));
                findRole(table);
                System.out.println(currPage.getText());
                System.out.println(infoSet.plaintiff);
                System.out.println(infoSet.defendant);
                try {
                    if (i < lastPage - 1) {
                        Info.getDriver().findElement(By.xpath(".//div[@id='b-footer-pages']//ul[@id='pages']//li[@class='rarr']")).click();
                        Thread.sleep(3000L);
                    }
                } catch (Exception e) {
                    System.out.println(i);
                }
            }
        }

    }
    private void findRole(List<WebElement> table) throws InterruptedException {
        /*Info.getDriver().get(href);
        List<WebElement> table = Info.getDriver().findElements(By.xpath(".//div[@id='gr_case_partps']//tbody//tr//td"));
        */
        for (int i=0;i<table.size();i++) {
            WebElement el = table.get(i);
            try {

                if (el.findElement(By.xpath(".//td[@class='plaintiff']")).getText().toUpperCase().contains(name.toUpperCase()))
                    infoSet.plaintiff++;
                else if (el.findElement(By.xpath(".//td[@class='respondent']")).getText().toUpperCase().contains(name.toUpperCase()))
                    infoSet.defendant++;
            } catch (Exception e) {
                System.out.println(e);
                attemptCount++;
                if(attemptCount > 1)
                    break;
                Thread.sleep(1000L);
                findRole(table);

            }


        }
    }


}
