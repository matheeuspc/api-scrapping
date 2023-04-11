package com.mcardoso.apiscrapingsoccer.scrap;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    private String round;
    private String homeTeam;
    private String awayTeam;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private Integer homeBallPossession;
    private Integer awayBallPossession;
    private Integer ballPossession;
    private Integer homeGoalAttempts;
    private Integer awayGoalAttempts;
    private Integer goalAttempts;
    private Integer homeShotsOnGoal;
    private Integer awayShotsOnGoal;
    private Integer shotsOnGoal;
    private Integer homeShotsOffGoal;
    private Integer awayShotsOffGoal;
    private Integer shotsOffGoal;
    private Integer homeBlockedShots;
    private Integer awayBlockedShots;
    private Integer blockedShots;
    private Integer homeFreeKicks;
    private Integer awayFreeKicks;
    private Integer freeKicks;
    private Integer homeCornerKicks;
    private Integer awayCornerKicks;
    private Integer cornerKicks;
    private Integer homeOffsides;
    private Integer awayOffsides;
    private Integer offsides;
    private Integer homeThrowIn;
    private Integer awayThrowIn;
    private Integer throwIn;
    private Integer homeGoalkeeperSaves;
    private Integer awayGoalkeeperSaves;
    private Integer goalkeeperSaves;
    private Integer homeFouls;
    private Integer awayFouls;
    private Integer fouls;
    private Integer homeYellowCards;
    private Integer awayYellowCards;
    private Integer yellowCards;
    private Integer homeTotalPasses;
    private Integer awayTotalPasses;
    private Integer totalPasses;
    private Integer homeCompletedPasses;
    private Integer awayCompletedPasses;
    private Integer completedPasses;
    private Integer homeTackles;
    private Integer awayTackles;
    private Integer tackles;
    private Integer homeAttacks;
    private Integer awayAttacks;
    private Integer attacks;
    private Integer homeDangerousAttacks;
    private Integer awayDangerousAttacks;
    private Integer dangerousAttacks;
}
