package com.mcardoso.apiscrapingsoccer.scrap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AcademiaApostasScraping {
    public static void main(String[] args) throws InterruptedException {
        String url = "https://www.academiadasapostasbrasil.com/";
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);
        Thread.sleep(10000);
        driver.findElement(By.id("show_tab1")).click();
        driver.findElement(By.className("aa-icon-next date-increment")).click();


    }
}
