package com.theesmarty.campulsy;

import android.content.Context;
import android.content.SharedPreferences;

public class UserStat {
    private static final String PREFERENCES_FILE = "UserPreferences";
    private static final String KEY_OTHER_CAMPUS = "OtherCampus";

    private SharedPreferences sharedPreferences;

    public UserStat(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
    }

    public void setOtherCampus(boolean otherCampus) {
        sharedPreferences.edit().putBoolean(KEY_OTHER_CAMPUS, otherCampus).apply();
    }

    public boolean isOtherCampus() {
        return sharedPreferences.getBoolean(KEY_OTHER_CAMPUS, false);
    }
}
