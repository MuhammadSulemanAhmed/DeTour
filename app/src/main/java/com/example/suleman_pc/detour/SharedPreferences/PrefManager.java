package com.example.suleman_pc.detour.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    Context context;

    public PrefManager(Context context) {
        this.context = context;
    }

    public void saveExpenseKey(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ExpenseKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Key", key);
        editor.apply();
    }
    public String getExpenseKey() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ExpenseKey", Context.MODE_PRIVATE);
        return sharedPreferences.getString("Key", "");
    }
    public void saveLocationSwitchState(String state) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SwitchState", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("State", state);
        editor.apply();
    }
    public boolean getLocationSwitchState() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SwitchState", Context.MODE_PRIVATE);
        return Boolean.parseBoolean(sharedPreferences.getString("State", ""));
    }

}
