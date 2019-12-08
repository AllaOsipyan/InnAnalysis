package org.mycompany.myname;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;

public class OutputInfo {
        public String fullName;//"Полное юридическое наименование:"
        public String abbreviatedName;
        public String director;//"Руководитель:";
        public String dateOfRegistration;//"Дата регистрации:";
        public  String ogrn;//"ОГРН:";
        public String inn;//"ИНН:";
        public String countStaff;//"Численность персонала:";
        public String mainTypeOfActivity;//"Основной вид деятельности:";
        public ArrayList additionTypesOfActivity =new ArrayList();//"Дополнительные виды деятельности:";
        public String profit;//"Чистая прибыль (убыток):";
        public String receivables;//"Дебиторская задолженность:";
        public String proceeds;//"Выручка:";
        public String sumOfExpenses;//"Сумма расходов:";
        public String taxBurden; //налоговая нагрузка
        public String valueAddedTax; //ндс
        public String incomeTax; //налог на прибыль
        public String taxWithType;
        public String PFRContributions; //взносы в ПФР
        public String OMSContributions; //взносы в ОМС
        public String socialContributions; // взносы на соц страхование
        public int countOfTrial; //Кол-во судебных дел
        public int plaintiff; //"Истец:";
        public int defendant; //Ответчик;
        public int thirdPerson; //Третье лицо
        //public int countOfWins; //Кол-во побед
        public String authorizedCapital; //уставной капитал
        public String provider;//поставщик
        public String customer;//заказчик
        public String sumOfSupplies; //сумма поставок
        public String  sumOfOrders; //сумма заказов
        public boolean isInEgrul; //наличие в егрюл
        public String taxDebts; //налоговые задолженности
        public String allSumOfDebts; //все долги

        public String fine; //штраф
        public String penalties;//взыскания
        public String otherFines;
        public String debts;//отстаток задолженности

}
