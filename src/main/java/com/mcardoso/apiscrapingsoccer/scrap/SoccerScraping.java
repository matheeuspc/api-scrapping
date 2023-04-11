package com.mcardoso.apiscrapingsoccer.scrap;

import com.mcardoso.apiscrapingsoccer.helper.SSLHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class SoccerScraping {

    public static void main(String[] args) throws InterruptedException {
        String url = "https://www.flashscore.com/football/europe/champions-league/results/";
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);
        Thread.sleep(10000);
        driver.findElement(By.id("onetrust-reject-all-handler")).click();
        Thread.sleep(5000);
        try {
            while(driver.findElement(By.className("event__more--static")).isDisplayed()) {
                driver.findElement(By.className("event__more--static")).click();
                Thread.sleep(5000);
            }
        } catch (Exception ex) {
            System.out.println("Error while finding show more options");
        }
        Document doc = Jsoup.parse(driver.getPageSource());

        List<Element> matches =
                doc.getElementsByAttributeValueContaining("class","event__match--static");

        ArrayList<String> matchesId = new ArrayList<>();
        for (Element match : matches) {
            matchesId.add(match.id().substring(4));
        }

        ArrayList<Match> results = new ArrayList<>();

        for (String id: matchesId) {
//            if(id.equals("xv1NLc9p")) break;
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
            driver.get("https://www.flashscore.com/match/"+id+"/#/match-summary/match-statistics/0");
            Thread.sleep(3000);
            doc = Jsoup.parse(driver.getPageSource());
            String roundText = doc.getElementsByClass("tournamentHeader__country").first().text();
            String round = roundText.substring(roundText.length()-2).strip();
            List<Element> teamsName = doc.getElementsByClass("participant__participantName");
            String score = doc.getElementsByClass("detailScore__wrapper").first().text();
//            List<Element> stats = doc.getElementsByClass("stat__row");
            Element ballPossession = doc.getElementsContainingText("Ball Possession").last().parent();
            Element cornerKicks = doc.getElementsContainingText("Corner Kicks").last().parent();
            Element dangerousAttacks = doc.getElementsContainingText("Dangerous Attacks").last().parent();
            Element goalAttempts = doc.getElementsContainingText("Goal Attempts").last().parent();
            Element shotsOnGoal = doc.getElementsContainingText("Shots on Goal").last().parent();
            Element shotsOffGoal = doc.getElementsContainingText("Shots off Goal").last().parent();
            Element blockedShots = doc.getElementsContainingText("Blocked Shots").last().parent();
            Element goalkeeperSaves = doc.getElementsContainingText("Goalkeeper Saves").last().parent();
            results.add(Match.builder()
                            .round(round)
                            .homeTeam(teamsName.get(0).text())
                            .awayTeam(teamsName.get(2).text())
                            .homeTeamScore(Integer.valueOf(score.substring(0,1)))
                            .awayTeamScore(Integer.valueOf(score.substring(2,3)))
                            .homeBallPossession(Integer.parseInt(ballPossession.getElementsByClass("stat__homeValue").first().text().replace("%","")))
                            .awayBallPossession(Integer.parseInt(ballPossession.getElementsByClass("stat__awayValue").first().text().replace("%","")))
                            .homeGoalAttempts(Integer.parseInt(goalAttempts.getElementsByClass("stat__homeValue").first().text()))
                            .awayGoalAttempts(Integer.parseInt(goalAttempts.getElementsByClass("stat__homeValue").first().text()))
//                            .goalAttempts(getTotalStatValue(stats,1))
//                            .homeShotsOnGoal(getStatValue(stats,2,"stat__homeValue"))
//                            .awayShotsOnGoal(getStatValue(stats,2,"stat__awayValue"))
//                            .shotsOnGoal(getTotalStatValue(stats,2))
//                            .homeShotsOffGoal(getStatValue(stats,3,"stat__homeValue"))
//                            .awayShotsOffGoal(getStatValue(stats,3,"stat__awayValue"))
//                            .shotsOffGoal(getTotalStatValue(stats,3))
//                            .homeBlockedShots(getStatValue(stats,4,"stat__homeValue"))
//                            .awayBlockedShots(getStatValue(stats,4,"stat__awayValue"))
//                            .blockedShots(getTotalStatValue(stats,4))
//                            .homeFreeKicks(getStatValue(stats,5,"stat__homeValue"))
//                            .awayFreeKicks(getStatValue(stats,5,"stat__awayValue"))
//                            .freeKicks(getTotalStatValue(stats,5))
                            .homeCornerKicks(Integer.parseInt(cornerKicks.getElementsByClass("stat__homeValue").first().text()))
                            .awayCornerKicks(Integer.parseInt(cornerKicks.getElementsByClass("stat__awayValue").first().text()))
//                            .cornerKicks(getTotalStatValue(stats,6))
//                            .homeOffsides(getStatValue(stats,7,"stat__homeValue"))
//                            .awayOffsides(getStatValue(stats,7,"stat__awayValue"))
//                            .offsides(getTotalStatValue(stats,7))
//                            .homeThrowIn(getStatValue(stats,8,"stat__homeValue"))
//                            .awayThrowIn(getStatValue(stats,8,"stat__awayValue"))
//                            .throwIn(getTotalStatValue(stats,8))
//                            .homeGoalkeeperSaves(getStatValue(stats,9,"stat__homeValue"))
//                            .awayGoalkeeperSaves(getStatValue(stats,9,"stat__awayValue"))
//                            .goalkeeperSaves(getTotalStatValue(stats,9))
//                            .homeFouls(getStatValue(stats,10,"stat__homeValue"))
//                            .awayFouls(getStatValue(stats,10,"stat__awayValue"))
//                            .fouls(getTotalStatValue(stats,10))
//                            .homeYellowCards(Optional.of(getStatValue(stats,11,"stat__homeValue")).orElse(0))
//                            .awayYellowCards(Optional.of(getStatValue(stats,11,"stat__awayValue")).orElse(0))
//                            .yellowCards(Optional.of(getTotalStatValue(stats,11)).orElse(0))
//                            .homeTotalPasses(getStatValue(stats,12,"stat__homeValue"))
//                            .awayTotalPasses(getStatValue(stats,12,"stat__awayValue"))
//                            .totalPasses(getTotalStatValue(stats,12))
//                            .homeCompletedPasses(getStatValue(stats,13,"stat__homeValue"))
//                            .awayCompletedPasses(getStatValue(stats,13,"stat__awayValue"))
//                            .completedPasses(getTotalStatValue(stats,13))
//                            .homeTackles(getStatValue(stats,14,"stat__homeValue"))
//                            .awayTackles(getStatValue(stats,14,"stat__awayValue"))
//                            .tackles(getTotalStatValue(stats,14))
//                            .homeAttacks(getStatValue(stats,15,"stat__homeValue"))
//                            .awayAttacks(getStatValue(stats,15,"stat__awayValue"))
//                            .attacks(getTotalStatValue(stats,15))
                            .homeDangerousAttacks(Integer.parseInt(dangerousAttacks.getElementsByClass("stat__homeValue").first().text()))
                            .awayDangerousAttacks(Integer.parseInt(dangerousAttacks.getElementsByClass("stat__awayValue").first().text()))
//                            .dangerousAttacks(getTotalStatValue(stats,16))
                    .build());
                System.out.println(teamsName.get(0).text() + " X " + teamsName.get(2).text());
        }

        writeToCSV(results);
        driver.quit();
    }
    private static final String CSV_SEPARATOR = ",";
    private static void writeToCSV(List<Match> matches) {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("matches-champions.csv"), "UTF-8"));
            String header = "Rodada,EscanteioCasa,AtkPerigosoCasa,PosseCasa,Casa,ScoreCasa,X,ScoreVisitante,Visitante,PosseVisitante,AtkPerigosoVisitante,EscanteioVisitante";
            bw.write(header);
            bw.newLine();
            for (Match match : matches)
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(match.getRound());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(match.getHomeCornerKicks());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(match.getHomeDangerousAttacks());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(match.getHomeBallPossession());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(match.getHomeTeam());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(match.getHomeTeamScore());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append("X");
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(match.getAwayTeamScore());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(match.getAwayTeam());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(match.getAwayBallPossession());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(match.getAwayDangerousAttacks());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(match.getAwayCornerKicks());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }

    private static Integer getStatValue(List<Element> elements, int index, String className) {
        return Integer.valueOf(elements.get(index).getElementsByClass(className).first().text());
    }

    private static Integer getPossessionStatValue(List<Element> elements, int index, String className) {
        return Integer.valueOf(elements.get(index).getElementsByClass(className).first().text().replace("%",""));
    }

    private static Integer getTotalStatValue(List<Element> elements, int index) {
        return Integer.parseInt(elements.get(index).getElementsByClass("stat__homeValue").first().text()) +
                Integer.parseInt(elements.get(index).getElementsByClass("stat__awayValue").first().text());
    }

    private static Integer getTotalPossessionStatValue(List<Element> elements, int index) {
        return Integer.parseInt(elements.get(index).getElementsByClass("stat__homeValue").first().text().replace("%","")) +
                Integer.parseInt(elements.get(index).getElementsByClass("stat__awayValue").first().text().replace("%",""));
    }

}
