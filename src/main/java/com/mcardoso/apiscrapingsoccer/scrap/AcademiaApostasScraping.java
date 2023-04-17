package com.mcardoso.apiscrapingsoccer.scrap;

import jdk.jfr.Event;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AcademiaApostasScraping {

    public static void main(String[] args) throws InterruptedException {
        String url = "https://www.academiadasapostasbrasil.com/";
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(url);
        Thread.sleep(2000);
        driver.findElement(By.id("show_tab1")).click();
        Thread.sleep(2000);

//        se comentado faz os jogos do dia
//        int daysAhead = 0;
//        while(daysAhead > 0) {
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/div[3]/div[2]/div[1]/div[2]/span[2]"))
                .click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"main-container\"]/div[3]/div[2]/div[1]/div[2]/span[2]"))
                .click();
        Thread.sleep(2000);
//        driver.findElement(By.xpath("//*[@id=\"main-container\"]/div[3]/div[2]/div[1]/div[2]/span[2]"))
//                .click();
//        Thread.sleep(2000);
//        driver.findElement(By.xpath("//*[@id=\"main-container\"]/div[3]/div[2]/div[1]/div[2]/span[2]"))
//                .click();
//        Thread.sleep(2000);
        Document doc = Jsoup.parse(driver.getPageSource());

        List<Element> events =
                doc.getElementsByAttributeValueContaining("class","live-subscription");

        ArrayList<String> eventsId = new ArrayList();
        for (Element event : events) {
            eventsId.add(event.attr("eventId"));
        }
        System.out.println(eventsId);
        int count = 0;
        List<EventAcademia> eventsAcademia = new ArrayList<>();
        for(String id : eventsId) {
            try {
//                if(count==1) break;
//                count++;
//                driver.get("https://www.academiadasapostasbrasil.com/stats/match/1nXYLWa2pQ3RB");
            driver.get("https://www.academiadasapostasbrasil.com/stats/match/"+id);
                Thread.sleep(2000);
                doc = Jsoup.parse(driver.getPageSource());

                String leagueName = doc.selectXpath("//*[@id=\"s\"]/div[1]/div/div[1]/table/tbody/tr/td[2]/ul[2]/li[2]/a").text();
                String leagueUrl = doc.selectXpath("//*[@id=\"s\"]/div[1]/div/div[1]/table/tbody/tr/td[2]/ul[2]/li[2]/a").attr("href");
                String teamHomeName = doc.selectXpath("//*[@id=\"s\"]/div[1]/div/div[1]/div/div[4]/a[1]").text();
                String teamHomeUrl = doc.selectXpath("//*[@id=\"s\"]/div[1]/div/div[1]/div/div[4]/a[1]").attr("href");
                String teamAwayName = doc.selectXpath("//*[@id=\"s\"]/div[1]/div/div[1]/div/div[4]/a[2]").text();
                String teamAwayUrl = doc.selectXpath("//*[@id=\"s\"]/div[1]/div/div[1]/div/div[4]/a[2]").attr("href");

                String homeOverTwoGoals = doc.selectXpath("//h3[contains(text(),'Gols')]/./../../../../..//tbody[2]/tr[1]/td[1]/table/tbody[1]/tr[6]/td[2]")
                        .text().replace("%","").replace("-","0");
                String homeOverTwoGoalsGeneral = doc.selectXpath("//h3[contains(text(),'Gols')]/./../../../../..//tbody[2]/tr[1]/td[1]/table/tbody[1]/tr[6]/td[4]")
                        .text().replace("%","").replace("-","0");
                String awayOverTwoGoals = doc.selectXpath("//h3[contains(text(),'Gols')]/./../../../../..//tbody[2]/tr[1]/td[2]/table/tbody[1]/tr[6]/td[3]")
                        .text().replace("%","").replace("-","0");
                String awayOverTwoGoalsGeneral = doc.selectXpath("//h3[contains(text(),'Gols')]/./../../../../..//tbody[2]/tr[1]/td[2]/table/tbody[1]/tr[6]/td[4]")
                        .text().replace("%","").replace("-","0");

                Thread.sleep(10000);
                Actions actions = new Actions(driver);
                WebElement last10Games = driver.findElement(By.xpath("//h3[contains(text(),'Gols')]/./../ul/li[2]"));
                actions.moveToElement(last10Games).perform();
                Thread.sleep(2000);
                actions.click(last10Games).click().perform();

//            driver.findElement(By.xpath("//h3[contains(text(),'Gols')]/./../ul/li[2]"))
//                    .click();


                Thread.sleep(5000);
                doc = Jsoup.parse(driver.getPageSource());

                String homeOverTwoGoalsLastGames = doc.selectXpath("//h3[contains(text(),'Gols')]/./../../../../..//tbody[2]/tr[1]/td[1]/table/tbody[1]/tr[6]/td[2]")
                        .text().replace("%","").replace("-","0");
                String homeOverTwoGoalsGeneralLastGames = doc.selectXpath("//h3[contains(text(),'Gols')]/./../../../../..//tbody[2]/tr[1]/td[1]/table/tbody[1]/tr[6]/td[4]")
                        .text().replace("%","").replace("-","0");
                String awayOverTwoGoalsLastGames = doc.selectXpath("//h3[contains(text(),'Gols')]/./../../../../..//tbody[2]/tr[1]/td[2]/table/tbody[1]/tr[6]/td[3]")
                        .text().replace("%","").replace("-","0");
                String awayOverTwoGoalsGeneralLastGames = doc.selectXpath("//h3[contains(text(),'Gols')]/./../../../../..//tbody[2]/tr[1]/td[2]/table/tbody[1]/tr[6]/td[4]")
                        .text().replace("%","").replace("-","0");


                Thread.sleep(2000);
                String oddOverOne = "0";
                String oddOverTwo = "0";
                String oddUnderFour = "0";
                if(existsElement(driver, "//*[@id=\"odds\"]")) {
                    WebElement oddStats = driver.findElement(By.id("odds"));
                    actions.moveToElement(oddStats).perform();
                    Thread.sleep(2000);
                    actions.click(oddStats).click().perform();
                    Thread.sleep(2000);
                    actions.moveToElement(driver.findElement(By.className("boxed-title"))).perform();
                    Thread.sleep(2000);

                    if(existsElement(driver, "//li[contains(text(),'+/- 1.5')]")) {
                        driver.findElement(By.xpath("//li[contains(text(),'+/- 1.5')]")).click();
                        Thread.sleep(2000);
                        doc = Jsoup.parse(driver.getPageSource());
                        Thread.sleep(1000);
//                        oddOverOne = doc.selectXpath("//li[contains(text(),'+/- 1.5')]/./../..//th[contains(text(),'Mais de 1.5 Gols')][1]/./../../..//tbody/tr[1]/td[3]/span[1]/a").text();
                        oddOverOne = doc.selectXpath("(//li[contains(text(),'+/- 1.5')]/./../..//th[contains(text(),'Mais de 1.5 Gols')][1]/./../../..//tbody/tr[1]/td[3]/span)[1]").text();
                        Thread.sleep(2000);
                        if(oddOverOne.equals("")) oddOverOne = "0";

                    }

                    if(existsElement(driver, "//li[contains(text(),'+/- 2.5')]")) {
                        driver.findElement(By.xpath("//li[contains(text(),'+/- 2.5')]")).click();
                        Thread.sleep(2000);
                        doc = Jsoup.parse(driver.getPageSource());
                        Thread.sleep(2000);
//                        oddOverTwo = doc.selectXpath("//li[contains(text(),'+/- 2.5')]/./../..//th[contains(text(),'Mais de 2.5 Gols')][1]/./../../..//tbody/tr[1]/td[3]/span[1]/a").text();
                        oddOverTwo = doc.selectXpath("(//li[contains(text(),'+/- 2.5')]/./../..//th[contains(text(),'Mais de 2.5 Gols')][1]/./../../..//tbody/tr[1]/td[3]/span)[1]").text();
                        Thread.sleep(2000);
                        if(oddOverTwo.equals("")) oddOverTwo = "0";
                    }

                    if(existsElement(driver, "//li[contains(text(),'+/- 3.5')]")) {
                        driver.findElement(By.xpath("//li[contains(text(),'+/- 3.5')]")).click();
                        Thread.sleep(2000);
                        doc = Jsoup.parse(driver.getPageSource());
                        Thread.sleep(2000);
//                        oddUnderFour = doc.selectXpath("//li[contains(text(),'+/- 2.5')]/./../..//th[contains(text(),'Mais de 2.5 Gols')][1]/./../../..//tbody/tr[1]/td[3]/span/a").text();
                        oddUnderFour = doc.selectXpath("(//li[contains(text(),'+/- 3.5')]/./../..//th[contains(text(),'Mais de 3.5 Gols')][1]/./../../..//tbody[1]/tr[1]/td[2]/span)[1]").text();
                        if(oddUnderFour.equals("")) oddUnderFour = "0";
                    }
                }

                Thread.sleep(2000);
                driver.get(teamHomeUrl+"#tab=t_stats");
                Thread.sleep(2000);
                actions.moveToElement(driver.findElement(By.className("boxed-title"))).perform();
                actions.click().perform();
                Thread.sleep(2000);

                driver.findElement(By.xpath("//*[@id=\"s\"]/div/div/div[1]/div/div[3]/div[2]/div[3]/div[1]/div/div[1]//*[text()='" + leagueName + "']")).click();

                Thread.sleep(2000);
                doc = Jsoup.parse(driver.getPageSource());

                String averageLeagueGoals = doc.selectXpath("/html/body/div[7]/div[2]/div/div/div[1]/div/div[3]/div[2]/div[3]/div[4]/div[2]/div/table/tfoot/tr/td[2]").text().replace("%","").replace("-","0");

                String homeTeamOverOneTotal = doc.selectXpath("//span[contains(text(),'Mais de 1,5')]/./../../../../tbody/tr[1]/td[4]").text().replace("%","").replace("-","0");
                String homeTeamOverTwoTotal = doc.selectXpath("//span[contains(text(),'Mais de 1,5')]/./../../../../tbody/tr[1]/td[5]").text().replace("%","").replace("-","0");
                String homeTeamOverThreeTotal = doc.selectXpath("//span[contains(text(),'Mais de 1,5')]/./../../../../tbody/tr[1]/td[6]").text().replace("%","").replace("-","0");

                driver.findElement(By.xpath("//h4[contains(text(),'Mais/Menos gols')]/./../div//*[text()='CASA']"))
                        .click();

                Thread.sleep(2000);
                doc = Jsoup.parse(driver.getPageSource());

                String homeTeamOverOne = doc.selectXpath("//span[contains(text(),'Mais de 1,5')]/./../../../../tbody/tr[2]/td[4]").text().replace("%","").replace("-","0");
                String homeTeamOverTwo = doc.selectXpath("//span[contains(text(),'Mais de 1,5')]/./../../../../tbody/tr[2]/td[5]").text().replace("%","").replace("-","0");
                String homeTeamOverThree = doc.selectXpath("//span[contains(text(),'Mais de 1,5')]/./../../../../tbody/tr[2]/td[6]").text().replace("%","").replace("-","0");

                //away team
                Thread.sleep(2000);
                driver.get(teamAwayUrl+"#tab=t_stats");
                Thread.sleep(2000);
                actions.moveToElement(driver.findElement(By.className("boxed-title"))).perform();
                Thread.sleep(2000);
                driver.findElement(By.xpath("//*[@id=\"s\"]/div/div/div[1]/div/div[3]/div[2]/div[3]/div[1]/div/div[1]//*[text()='" + leagueName + "']")).click();

                Thread.sleep(2000);
                doc = Jsoup.parse(driver.getPageSource());

                String awayTeamOverOneTotal = doc.selectXpath("//span[contains(text(),'Mais de 1,5')]/./../../../../tbody/tr[1]/td[4]").text().replace("%","").replace("-","0");
                String awayTeamOverTwoTotal = doc.selectXpath("//span[contains(text(),'Mais de 1,5')]/./../../../../tbody/tr[1]/td[5]").text().replace("%","").replace("-","0");
                String awayTeamOverThreeTotal = doc.selectXpath("//span[contains(text(),'Mais de 1,5')]/./../../../../tbody/tr[1]/td[6]").text().replace("%","").replace("-","0");

                driver.findElement(By.xpath("//h4[contains(text(),'Mais/Menos gols')]/./../div//*[text()='FORA']"))
                        .click();
                Thread.sleep(2000);
                doc = Jsoup.parse(driver.getPageSource());

                String awayTeamOverOne = doc.selectXpath("//span[contains(text(),'Mais de 1,5')]/./../../../../tbody/tr[3]/td[4]").text().replace("%","").replace("-","0");
                String awayTeamOverTwo = doc.selectXpath("//span[contains(text(),'Mais de 1,5')]/./../../../../tbody/tr[3]/td[5]").text().replace("%","").replace("-","0");
                String awayTeamOverThree = doc.selectXpath("//span[contains(text(),'Mais de 1,5')]/./../../../../tbody/tr[3]/td[6]").text().replace("%","").replace("-","0");

                EventAcademia eventAcademia = EventAcademia.builder()
                        .leagueName(leagueName)
                        .leagueAverage(averageLeagueGoals)
                        .homeTeam(teamHomeName)
                        .awayTeam(teamAwayName)
                        .homeTeamOverTwo(homeOverTwoGoals)
                        .homeTeamOverTwoTotal(homeOverTwoGoalsGeneral)
                        .homeTeamOverTwoLastGames(homeOverTwoGoalsLastGames)
                        .homeTeamOverTwoTotalLastGames(homeOverTwoGoalsGeneralLastGames)
                        .awayTeamOverTwo(awayOverTwoGoals)
                        .awayTeamOverTwoTotal(awayOverTwoGoalsGeneral)
                        .awayTeamOverTwoLastGames(awayOverTwoGoalsLastGames)
                        .awayTeamOverTwoTotalLastGames(awayOverTwoGoalsGeneralLastGames)
                        .homeTeamOverOneGoal(homeTeamOverOne)
                        .homeTeamOverTwoGoals(homeTeamOverTwo)
                        .homeTeamOverThreeGoals(homeTeamOverThree)
                        .homeTeamOverOneGoalTotal(homeTeamOverOneTotal)
                        .homeTeamOverTwoGoalsTotal(homeTeamOverTwoTotal)
                        .homeTeamOverThreeGoalsTotal(homeTeamOverThreeTotal)
                        .awayTeamOverOneGoal(awayTeamOverOne)
                        .awayTeamOverTwoGoals(awayTeamOverTwo)
                        .awayTeamOverThreeGoals(awayTeamOverThree)
                        .awayTeamOverOneGoalTotal(awayTeamOverOneTotal)
                        .awayTeamOverTwoGoalsTotal(awayTeamOverTwoTotal)
                        .awayTeamOverThreeGoalsTotal(awayTeamOverThreeTotal)
                        .oddOverOne(oddOverOne)
                        .oddOverTwo(oddOverTwo)
                        .oddUnderFour(oddUnderFour)
                        .build();

                eventsAcademia.add(eventAcademia);
            }
            catch (Exception ignored) {};
        }
        writeToCSV(eventsAcademia);
        driver.close();
    }

    private static final String CSV_SEPARATOR = ",";
    private static void writeToCSV(List<EventAcademia> events) {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("eventsAcademiaApostas"+ LocalDate.now() + LocalDateTime.now().getHour()
                            + LocalDateTime.now().getMinute() +".csv"), StandardCharsets.UTF_8));
//            String header = "Liga,MediaLiga,TimeCasa,TimeFora,fair odd over 1.5,fair odd over 2.5,fair odd over 3.5, " +
//                    "fair odd under 3.5, Over 2.5 Casa, Over 2.5 Casa Total, Over 2.5 Casa Ult Jogos, Over 2.5 Casa Total Ult Jogos," +
//                    "Over 2.5 Fora, Over 2.5 Fora Total,Over 2.5 Fora Ult Jogos, Over 2.5 Fora Total Ult Jogos," +
//                    "Over 1.5 Casa, Over 1.5 Casa Total, Over 2.5 Casa, Over 2.5 Casa Total, Over 3.5 Casa, Over 3.5 Casa Total," +
//                    "Over 1.5 Fora, OVer 1.5 Fora Total, OVer 2.5 Fora, Over 2.5 Fora Total, Over 3.5 Fora, Over 3.5 Fora Total";
//            String header = "Liga,MediaLiga,TimeCasa,TimeFora,fair odd over 1.5,fair odd over 2.5,fair odd over 3.5, fair odd under 3.5";
            String header = "Liga,Casa,Visitante,fair odd over 1.5, odd over 1.5, % valor, fair odd over 2.5, odd over 2.5, % valor, fair odd under 3.5, odd under 3.5, % valor";
            bw.write(header);
            bw.newLine();
            for (EventAcademia event : events)
            {
                System.out.println("Event: " + event.getHomeTeam() + " X " + event.getAwayTeam());
                BigDecimal fairOddOverOne = fairOddOverOne(event);
                BigDecimal fairOddOverTwo = fairOddOverTwo(event);
                BigDecimal fairOddUnderFour = fairOddUnderFour(event);

                if(fairOddOverOne.compareTo(BigDecimal.valueOf(1.0)) > 0 &&
                        fairOddOverTwo.compareTo(BigDecimal.valueOf(1.0)) > 0 &&
                        fairOddUnderFour.compareTo(BigDecimal.valueOf(1.0)) > 0) {
                    StringBuffer oneLine = new StringBuffer();
                    oneLine.append(event.getLeagueName());
                    oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getLeagueAverage());
//                oneLine.append(CSV_SEPARATOR);
                    oneLine.append(event.getHomeTeam());
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(event.getAwayTeam());
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(fairOddOverOne);
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(event.getOddOverOne());
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(calculateValueBet(Double.valueOf(String.valueOf(fairOddOverOne)), Double.valueOf(event.getOddOverOne())));
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(fairOddOverTwo);
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(event.getOddOverTwo());
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(calculateValueBet(Double.valueOf(String.valueOf(fairOddOverTwo)), Double.valueOf(event.getOddOverTwo())));
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(fairOddUnderFour);
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(event.getOddUnderFour());
                    oneLine.append(CSV_SEPARATOR);
                    oneLine.append(calculateValueBet(Double.valueOf(String.valueOf(fairOddUnderFour)), Double.valueOf(event.getOddUnderFour())));
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getHomeTeamOverTwo());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getHomeTeamOverTwoTotal());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getHomeTeamOverTwoLastGames());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getHomeTeamOverTwoTotalLastGames());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getAwayTeamOverTwo());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getAwayTeamOverTwoTotal());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getAwayTeamOverTwoLastGames());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getAwayTeamOverTwoTotalLastGames());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getHomeTeamOverOneGoal());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getHomeTeamOverOneGoalTotal());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getHomeTeamOverTwoGoals());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getHomeTeamOverTwoGoalsTotal());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getHomeTeamOverThreeGoals());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getHomeTeamOverThreeGoalsTotal());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getAwayTeamOverOneGoal());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getAwayTeamOverOneGoalTotal());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getAwayTeamOverTwoGoals());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getAwayTeamOverTwoGoalsTotal());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getAwayTeamOverThreeGoals());
//                oneLine.append(CSV_SEPARATOR);
//                oneLine.append(event.getAwayTeamOverThreeGoalsTotal());
                    bw.write(oneLine.toString());
                    bw.newLine();
                }
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }

    private static BigDecimal fairOddOverTwo(EventAcademia event) {
        double pound = 8.4;
        Double homeOverTwo = Double.valueOf(event.getHomeTeamOverTwo());
        Double homeOverTwoTotal = Double.valueOf(event.getHomeTeamOverTwoTotal());
        Double homeOverTwoLastGames = Double.valueOf(event.getHomeTeamOverTwoLastGames());
        Double homeOverTwoTotalLastGames = Double.valueOf(event.getHomeTeamOverTwoTotalLastGames());
        Double awayOverTwo = Double.valueOf(event.getAwayTeamOverTwo());
        Double awayOverTwoTotal = Double.valueOf(event.getAwayTeamOverTwoTotal());
        Double awayOverTwoLastGames = Double.valueOf(event.getAwayTeamOverTwoLastGames());
        Double awayOverTwoTotalLastGames = Double.valueOf(event.getAwayTeamOverTwoTotalLastGames());

        BigDecimal eventProbability = BigDecimal.valueOf((homeOverTwo + homeOverTwoTotal + awayOverTwo + awayOverTwoTotal +
                (1.1*homeOverTwoLastGames) + (1.1*homeOverTwoTotalLastGames) + (1.1*awayOverTwoLastGames) +
                (1.1*awayOverTwoTotalLastGames))/pound);

        return !eventProbability.equals(BigDecimal.valueOf(0.0)) ?
                BigDecimal.valueOf(100.0).divide(eventProbability, 3,RoundingMode.CEILING) : BigDecimal.valueOf(0);
    }

    private static BigDecimal fairOddOverOne(EventAcademia event) {
        Double homeOverOne = Double.valueOf(event.getHomeTeamOverOneGoal());
        Double homeOverOneTotal = Double.valueOf(event.getHomeTeamOverOneGoalTotal());
        Double awayOverOne = Double.valueOf(event.getAwayTeamOverOneGoal());
        Double awayOverOneTotal = Double.valueOf(event.getAwayTeamOverOneGoalTotal());

        BigDecimal eventProbability =
                BigDecimal.valueOf((homeOverOne + homeOverOneTotal + awayOverOne + awayOverOneTotal)/4.0);
        return !eventProbability.equals(BigDecimal.valueOf(0.0)) ?
                BigDecimal.valueOf(100.0).divide(eventProbability, 3,RoundingMode.CEILING) :
                BigDecimal.valueOf(0);
    }

    private static BigDecimal fairOddOverThree(EventAcademia event) {
        Double homeOverThree = Double.valueOf(event.getHomeTeamOverThreeGoals());
        Double homeOverThreeTotal = Double.valueOf(event.getHomeTeamOverThreeGoalsTotal());
        Double awayOverThree = Double.valueOf(event.getAwayTeamOverThreeGoals());
        Double awayOverThreeTotal = Double.valueOf(event.getAwayTeamOverThreeGoalsTotal());

        BigDecimal eventProbability =
                BigDecimal.valueOf((homeOverThree + homeOverThreeTotal + awayOverThree + awayOverThreeTotal)/4.0);
        return !eventProbability.equals(BigDecimal.valueOf(0.0)) ?
                BigDecimal.valueOf(100.0).divide(eventProbability, 3,RoundingMode.CEILING) :
                BigDecimal.valueOf(0);
    }

    private static BigDecimal fairOddUnderFour(EventAcademia event) {
        Double homeOverThree = 100 - Double.valueOf(event.getHomeTeamOverThreeGoals());
        Double homeOverThreeTotal = 100 - Double.valueOf(event.getHomeTeamOverThreeGoalsTotal());
        Double awayOverThree = 100 - Double.valueOf(event.getAwayTeamOverThreeGoals());
        Double awayOverThreeTotal = 100 - Double.valueOf(event.getAwayTeamOverThreeGoalsTotal());

        BigDecimal eventProbability =
                BigDecimal.valueOf((homeOverThree + homeOverThreeTotal + awayOverThree + awayOverThreeTotal)/4.0);
        return !eventProbability.equals(BigDecimal.valueOf(0.0)) ?
                BigDecimal.valueOf(100.0).divide(eventProbability, 3,RoundingMode.CEILING) :
                BigDecimal.valueOf(0);
    }

    private static BigDecimal calculateValueBet(Double fairOdd, Double offeredOdd) {
        return fairOdd > 0 ? BigDecimal.valueOf((offeredOdd - fairOdd ) / fairOdd) : BigDecimal.valueOf(0);
    }

    public static boolean existsElement(WebDriver driver, String xpath){
        try{
            driver.findElement(By.xpath(xpath));
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
    }

}
