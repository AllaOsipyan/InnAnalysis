package org.mycompany.myname.Parsers;

import org.mycompany.myname.OutputInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;

public class ListOrgScr implements Parser {

    String name =Info.name;
    OutputInfo infoSet = new OutputInfo();

    @Override
    public OutputInfo getPage() throws InterruptedException {
        Info.getDriver().get("https://www.list-org.com/");
        Info.getDriver().findElement(By.xpath(".//div[@class='bord search_div']//input[@class='search_input']")).sendKeys(Info.inn);

        Info.getDriver().findElement(By.xpath(".//button[@class='search_btn btn btn-default']")).click();
        Thread.sleep(2000);
        List<WebElement> list = Info.getDriver().findElements(By.xpath(".//div[@class='org_list']/p"));
        String href="";
        if (list.size()!=1) {
            for (WebElement el : list) {
                try {
                    WebElement path = el.findElement(By.xpath(".//label//a"));
                    if (path.getText().toUpperCase().contains(name.toUpperCase())) {
                        href = path.getAttribute("href");
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }

            }
        }
        else href = list.get(0).findElement(By.xpath(".//label//a")).getAttribute("href");
        if(!href.equals(""))
            getInfo(href);
        return infoSet;
    }

    private void getInfo(String href){
        Info.getDriver().get(href);
        List<WebElement> divsInCont = Info.getDriver().findElements(By.xpath(".//div[@class='content']/div"));
        for(int i=2; i<divsInCont.size()-1;i++){
            WebElement el = divsInCont.get(i);
            WebElement nextEl = divsInCont.get(i+1);
            String zagol = el.getText();
            try {
                if (zagol.contains("не найден")) {continue;}
                if (i==2) {
                    getCommonInfo(el);

                }

                else if(el.getAttribute("id").equals("div_arb")){
                    getInfoAboutTrial(el.findElement(By.xpath(".//tbody")));
                }
                else if( el.getAttribute("id").equals("div_debtor")){
                    //getDept(el.findElements(By.xpath(".//tbody//tr")))
                }
                else if(el.getAttribute("id").equals("stat")){
                    //getMoneyTurnover();
                }
                else if(el.getAttribute("class").equals("c2")) {
                    try {

                        System.out.println(zagol);
                        if (nextEl.getAttribute("class").equals("c2m")) {
                            if (zagol.contains("Реквизиты компании")) {
                                getRecv(nextEl);
                            } else if (zagol.contains("Виды деятельности")) {
                                getTypeOfActivity(nextEl);
                            }
                            i++;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {}
        }
    }



    private void getCommonInfo(WebElement el){
        infoSet.fullName = el.findElement(By.xpath(".//p/a")).getText();
        List<WebElement> table=el.findElements(By.xpath(".//tbody/tr"));
        for (WebElement row:table) {
            List<WebElement> block = row.findElements(By.xpath(".//td"));
            String key = block.get(0).findElement(By.tagName("i")).getText();
            String value = block.get(1).getText();
            switch (key){
                case("Руководитель:"):
                    infoSet.director = value;
                    break;
                case("Уставной капитал:"):
                    infoSet.authorizedCapital = value;
                    break;
                case("Численность персонала:"):
                    infoSet.countStaff = value;
                    break;
                case("Дата регистрации:"):
                    infoSet.dateOfRegistration = value;
            }

        }
    }

    private void getRecv(WebElement el) {
        List<WebElement> data = el.findElements(By.tagName("p"));
        for (WebElement row : data) {
            String key = row.findElement(By.tagName("i")).getText();
            String value = row.getText().replace(key, "").trim();
            switch (key) {
                case ("ИНН:"):
                    infoSet.inn = value;
                    break;
                case ("ОГРН:"):
                    infoSet.ogrn = value;
            }
        }
    }
    private void  getTypeOfActivity(WebElement el){

        String str = el.findElement(By.tagName("p")).getText();
        infoSet.mainTypeOfActivity = str.split("-")[1].trim();
        try{
        List<WebElement> table = el.findElements(By.xpath(".//div//tbody//tr"));
        for (WebElement row: table) {
            infoSet.additionTypesOfActivity.add(row.findElements(By.tagName("td")).get(1).getText());
        }
        }
        catch (Exception e){
            System.out.println("Error in type of activity");
        }
    }

    private void getMoneyTurnover(WebElement el){
        //переанализировать
    }

    private void getInfoAboutTrial(WebElement tbody) throws InterruptedException {
        List<WebElement> table = tbody.findElements(By.xpath(".//tr"));
        WebElement lastEl = table.get(table.size()-1);
        if(lastEl.getText().contains("Показать еще" )) {
            lastEl.findElement(By.xpath(".//a[@id='id_all_arb']")).click();
            Thread.sleep(3000);
            table  = tbody.findElements(By.xpath(".//tr"));

        }
        for(int i=0;i<table.size();i++/*WebElement row:table*/){
            if(i == 4)
                continue;
            WebElement row = table.get(i);
            System.out.println(row.getText());
            String role = row.findElements(By.xpath(".//td")).get(2).getText();

            switch (role.toLowerCase()) {
                case ("истец"):
                    infoSet.plaintiff++;
                    break;
                case("ответчик"):
                    infoSet.defendant++;
                    break;
                case ("третье лицо"):
                    infoSet.thirdPerson++;
                default: break;
            }
        }
    }
}
