package com.example.plicoapp.ProfileSetting;

public class MySettingsContract {
    private boolean bShowMen;
    private boolean bShowWomen;
    private boolean bNotifNewMatches;
    private boolean bNotifMsg;
    private boolean bNotifMsgLike;
    private int maxDistance;
    private int minAge;
    private int maxAge;

    public boolean isbShowMen() {
        return bShowMen;
    }

    public void setbShowMen(boolean bShowMen) {
        this.bShowMen = bShowMen;
    }

    public boolean isbShowWomen() {
        return bShowWomen;
    }

    public void setbShowWomen(boolean bShowWomen) {
        this.bShowWomen = bShowWomen;
    }

    public boolean isbNotifNewMatches() {
        return bNotifNewMatches;
    }

    public void setbNotifNewMatches(boolean bNotifNewMatches) {
        this.bNotifNewMatches = bNotifNewMatches;
    }

    public boolean isbNotifMsg() {
        return bNotifMsg;
    }

    public void setbNotifMsg(boolean bNotifMsg) {
        this.bNotifMsg = bNotifMsg;
    }

    public boolean isbNotifMsgLike() {
        return bNotifMsgLike;
    }

    public void setbNotifMsgLike(boolean bNotifMsgLike) {
        this.bNotifMsgLike = bNotifMsgLike;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public MySettingsContract() {
        this.bShowMen = true;
        this.bShowWomen = true;
        this.bNotifNewMatches = true;
        this.bNotifMsg = true;
        this.bNotifMsgLike = true;
        this.maxDistance = 5;
        this.minAge = 18;
        this.maxAge = 50;
    }
}
