package com.rohan.dragoncell.GameUtils.Display;

import com.rohan.dragoncell.GameScenes.MainScreen;

public class Leveling {



    private int level = 0;
    private int subLevelPoints = 0;
    private int subLevelGoal = 5;

    public void update() {
        if(subLevelPoints == subLevelGoal) {
            level++;
            subLevelPoints = 0;
            subLevelGoal += 1;
            MainScreen.headsUp.alert.put("alert_text", "Leveled Up");
            MainScreen.headsUp.alert.put("alert_description", "Level " + level);
        }

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSubLevelPoints() {
        return subLevelPoints;
    }

    public int getSubLevelGoal() {
        return subLevelGoal;
    }

    public void setSubLevelPoints(int subLevelPoints) {
        this.subLevelPoints = subLevelPoints;
    }

    public void setSubLevelGoal(int subLevelGoal) {
        this.subLevelGoal = subLevelGoal;
    }
}
