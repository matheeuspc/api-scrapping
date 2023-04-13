package com.mcardoso.apiscrapingsoccer.scrap;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventAcademia {
    private String leagueName;
    private String leagueAverage;
    private String homeTeam;
    private String awayTeam;
    private String homeTeamOverTwo;
    private String homeTeamOverTwoTotal;
    private String homeTeamOverTwoLastGames;
    private String homeTeamOverTwoTotalLastGames;
    private String awayTeamOverTwo;
    private String awayTeamOverTwoTotal;
    private String awayTeamOverTwoLastGames;
    private String awayTeamOverTwoTotalLastGames;
    private String homeTeamOverOneGoal;
    private String homeTeamOverTwoGoals;
    private String homeTeamOverThreeGoals;
    private String homeTeamOverOneGoalTotal;
    private String homeTeamOverTwoGoalsTotal;
    private String homeTeamOverThreeGoalsTotal;
    private String awayTeamOverOneGoal;
    private String awayTeamOverTwoGoals;
    private String awayTeamOverThreeGoals;
    private String awayTeamOverOneGoalTotal;
    private String awayTeamOverTwoGoalsTotal;
    private String awayTeamOverThreeGoalsTotal;
}
