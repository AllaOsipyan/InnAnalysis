package org.mycompany.myname.Parsers;

import org.mycompany.myname.OutputInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NalogScr implements Parser {


    OutputInfo infoSet = new OutputInfo();

    @Override
    public OutputInfo getPage() throws InterruptedException {
        Info.getDriver().get("https://pb.nalog.ru/index.html");
        Info.getDriver().findElement(By.xpath(".//div[@id='uni_text_1']//input[@id='query']")).sendKeys(Info.inn);
        Info.getDriver().findElement(By.xpath(".//button[@class='btn-search']")).click();
        Thread.sleep(1000);
        Info.getDriver().findElement(By.xpath(".//div[@class='result-group']")).click();
        Thread.sleep(1000);
        analise();
        return infoSet;
    }

    private void analise(){
        List<WebElement> directInfo = Info.getDriver().findElements(By.xpath(".//div[@class='pane-collabsible crd-company']//div[@class='field']"));
        for (WebElement field:directInfo) {
            try {String fieldName=field.findElement(By.xpath(".//div")).getText();



                String fieldValue = field.findElement(By.xpath(".//div[@class='field-value']")).getText();
                switch (fieldName){
                    case ("Полное наименование:"):
                        infoSet.fullName = fieldValue;
                        break;
                    case("Сокращенное наименование:"):
                        infoSet.abbreviatedName = fieldValue;
                        break;
                    case("Основной вид деятельности (ОКВЭД):"):
                        infoSet.mainTypeOfActivity = fieldValue.substring(fieldValue.indexOf(" ") +1);
                        break;
                    case("Сведения об уставном капитале (складочном капитале, уставном фонде, паевых взносах):"):
                        infoSet.authorizedCapital = fieldValue.split("Вид")[0];
                        break;
                    case ("ОГРН:"):
                        infoSet.ogrn = fieldValue;
                        break;
                    case("ИНН:"):
                        infoSet.inn = fieldValue;
                        break;

                }

            } catch (Exception e) {
                continue;
            }

        }

    }

}
