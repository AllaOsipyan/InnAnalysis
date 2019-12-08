package org.mycompany.myname.Parsers;

import org.mycompany.myname.OutputInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RusProfScr implements Parser {
    OutputInfo infoSet = new OutputInfo();

    String name = Info.name;
    @Override
    public OutputInfo getPage() throws InterruptedException {
        Info.getDriver().get("https://www.rusprofile.ru/");
        Info.getDriver().findElement(By.xpath(".//form[@id='indexsearchform']//input[@class='index-search-input']")).sendKeys(Info.inn);
        Info.getDriver().findElement(By.xpath(".//form[@id='indexsearchform']//button[@type='submit']")).click();
        Thread.sleep(2000);
        boolean isFind = false;
        try {

            List<WebElement> companies = Info.getDriver().findElements(By.xpath(".//div[@class='search-result__list']//div[@class='company-item']"));
            if(companies.size()==0)
                throw new Exception();
            for (WebElement el : companies) {
                WebElement sdf=el.findElement(By.xpath(".//div[@class='company-item__title']//a"));
                if (sdf.getText().toUpperCase().trim().equals(name.toUpperCase())) {
                    sdf.click();
                    Thread.sleep(1000);
                    getInfo();
                }
            }


        } catch (Exception e) {
            try {
                getInfo();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return infoSet;
    }



    private void getInfo(){
        try {
            infoSet.fullName = Info.getDriver().findElement(By.xpath(".//div[@class='anketa-top']//div[@class='company-name']")).getText();
            findCommonInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<WebElement> blocks = Info.getDriver().findElements(By.xpath(".//div[@class='tile-area td2']/div"));
        for (WebElement block:blocks) {
            try {
                WebElement titleHref = block.findElement(By.xpath(".//h2[@class='tile-item__title']//a"));
                System.out.println(titleHref.getText());
                switch (titleHref.getText().trim()){
                    case ("Госзакупки"):
                        findProcurements(block.findElement(By.xpath(".//div[@class='tile-item tab-parent']/p[@class='tile-item__text']")).getText());
                        break;
                    case("Судебные дела"):
                        findTrials(block);
                        break;

                    case ("Долги"):
                        findDebts(block);
                }
                System.out.println("");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        findFinanceInfo();
        findTypesOfActivity();
    }
    private void findTypesOfActivity(){
        try{
            String href = Info.getDriver().findElement(By.xpath(".//nav[@class='company-menu']//a[@class='flexpoint icon-list gtm_nav_okved']")).getAttribute("href");
            Info.getDriver().get(href);
            Thread.sleep(1000);
            List<WebElement> types = Info.getDriver().findElements(By.xpath(".//div[@class='main-wrap__content']//ul[@class='okved-list']//li"));
            for (WebElement type: types) {
                try {
                    if(type.getAttribute("class").equals("okved-item okved-item--main"))
                        infoSet.mainTypeOfActivity = type.findElement(By.xpath(".//a[@class='okved-item__text']")).getText();
                    else{
                        infoSet.additionTypesOfActivity.add(type.findElements(By.xpath(".//div")).get(1).getText());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findProcurements(String text){

        if(text.contains("отсутств"))
            return;
        else {
            try {
                infoSet.provider = text.substring(text.indexOf("поставщиком в ") + 14, text.indexOf(" гос"));
                infoSet.sumOfSupplies = text.substring(text.indexOf("на сумму ") + 9, text.indexOf(" руб"));
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                infoSet.customer = text.substring(text.indexOf("заказчиком в ")+13, text.lastIndexOf(" гос"));
                infoSet.sumOfOrders = text.substring(text.lastIndexOf("на сумму ")+9, text.lastIndexOf(" руб"));
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    private void  findCommonInfo(){
        List<WebElement> block = Info.getDriver().findElements(By.xpath(".//div[@class='tile-item company-info']//div[@class='clear']/div"));
        List<WebElement> elements = block.get(0).findElements(By.xpath(".//dl"));
        for (WebElement el:elements) {
            try {

                String key = el.findElement(By.tagName("dt")).getText();
                switch (key){
                    case ("ОГРН"):
                        infoSet.ogrn = el.findElement(By.xpath(".//span[@id='clip_ogrn']")).getText();
                        break;
                    case ("ИНН/КПП"):
                        infoSet.inn = el.findElement(By.xpath(".//span[@id='clip_inn']")).getText();
                        break;
                    case("Дата регистрации"):
                        infoSet.dateOfRegistration = el.findElement(By.xpath(".//dd[@class='company-info__text']")).getText();
                        break;
                }


            } catch (Exception e) {
                continue;
            }

        }
        elements = block.get(1).findElements(By.xpath(".//div"));
        for (WebElement el:elements) {
            try {
                String key = el.findElement(By.xpath(".//span[@class='company-info__title']")).getText();
                String value = el.findElement(By.xpath(".//span[@class='company-info__text']")).getText();
                switch (key) {
                    case ("Уставный капитал"):
                        infoSet.authorizedCapital = value;
                        break;
                    case ("Руководитель"):
                        infoSet.director = value;
                        break;
                    case ("Среднесписочная численность "):
                        infoSet.countStaff = el.findElement(By.xpath(".//dd[@class='company-info__text']")).getText().split(" ")[0];
                        break;
                }
            }catch (Exception e){
                System.out.println("findCommonInfo");
            }
        }

    }

    private void findTrials(WebElement block) {
        List<WebElement> texts = block.findElements(By.xpath(".//p[@class='tile-item__text']"));
        if (texts.get(0).getText().contains("отсутств"))
            return;
        else

                try {
                    assignRoles(block.findElements(By.xpath(".//div[@class='connexion-col']/div")),texts.get(0).getText());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                   assignRoles(block.findElements(By.xpath(".//dl[@class='text-dl']/*")), texts.get(texts.size()-1).getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }

    }
    private void assignRoles(List<WebElement> roles, String amount){
        if(roles.size()==0)
            return;
        String substr= amount.substring(amount.indexOf("о")+2);
        infoSet.countOfTrial +=Integer.parseInt(substr.substring(0, substr.indexOf(" ")));

        for (int i = 0; i < roles.size()-1; i++)
            try {
                String role = roles.get(i).getText().toLowerCase();
                int count =Integer.parseInt(roles.get(i+1).getText());
                if (role.contains("ответчик")){
                    infoSet.defendant += count;
                    i++;
                }
                else if (role.contains("ист")){
                    infoSet.plaintiff += count;
                    i++;
                }
                else if (role.contains("треть")){
                    infoSet.thirdPerson += count;
                    i++;
                }
            } catch (NumberFormatException e) {
                System.out.println("findTrials");
            }
    }
    private void findFinanceInfo(){
        try {
            String href = Info.getDriver().findElement(By.xpath(".//nav[@class='company-menu']//a[@class='flexpoint icon-finance gtm_nav_finance']")).getAttribute("href");
            Info.getDriver().get(href);
            Thread.sleep(1000);
            getFinance();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getFinance(){
        List<WebElement> finances = Info.getDriver().findElements(By.xpath(".//div[@class='finance-list']//div[@class='fl-block']"));
        for (WebElement block: finances){
            try {
                String title = block.findElement(By.className("fl-title")).getText();
                String value = block.findElement(By.className("fl-text")).getText();
                if(title.contains("Выручка"))
                    infoSet.proceeds = value;
                else if(title.contains("Прибыль"))
                    infoSet.profit = value;
                else  if(title.contains("Сумма расходов"))
                    infoSet.sumOfExpenses = value;
                else if(title.contains("Налоговая нагрузка"))
                    infoSet.taxBurden = value;
                else if(title.contains("Дебиторская задолженность"))
                    infoSet.receivables = value;
                else if(title.contains("Налог на добавленную стоимость"))
                    infoSet.valueAddedTax = value;
                else if(title.contains("Налог на прибыль"))
                    infoSet.incomeTax = value;
                else if(title.contains("Налог") && (title.contains("УСН") || title.contains("Единый сельскохозяйственный налог") || title.contains("Единый налог на вменённый доход") || title.contains("Общая система налогообложения") || title.contains("ОСНО")))
                    infoSet.taxWithType = value +"("+ title+")";
                else if(title.contains("Страховые и другие взносы в ПФР"))
                    infoSet.PFRContributions = value;
                else if(title.contains("Страховые взносы на ОМС"))
                    infoSet.OMSContributions = value;
                else if(title.contains("Взносы на соц. страхование"))
                    infoSet.socialContributions = value;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void findDebts(WebElement block){
        try {
            String title = block.findElement(By.xpath(".//p[@class='tile-item__text']")).getText();
            if(title.contains("не найден"))
                return;
            List<WebElement> elementsInBlock  = block.findElements(By.xpath(".//div[@class='col-50']/div[@class='connexion-col']"));
            for (WebElement el:elementsInBlock){
                try {
                    List<WebElement> texts = el.findElements(By.xpath(".//div[@class='connexion-col__title tosmall']"));
                    //String count = text.substring(text.indexOf(" ")+1);
                   // String value = el.findElement(By.xpath(".//div[@class='connexion-col__num tosmall']")).getText();
                    for (WebElement text:texts) {
                        try {
                            if (text.getText().contains("Штрафы"))
                                infoSet.fine = text.getText().substring(text.getText().indexOf(" ") + 1);
                            else if (text.getText().contains("Взыскания"))
                                infoSet.penalties = text.getText().substring(text.getText().indexOf(" ") + 1);
                            else if (text.getText().contains("Прочие"))
                                infoSet.otherFines = text.getText().substring(text.getText().indexOf(" ") + 1);
                            else if (text.getText().contains("На сумму"))
                                infoSet.allSumOfDebts = el.findElement(By.xpath(".//div[@class='connexion-col__num tosmall']")).getText();
                            else if (text.getText().contains("Остаток задолженности"))
                                infoSet.debts = el.findElement(By.xpath(".//div[@class='connexion-col__num tosmall']")).getText();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



