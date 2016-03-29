package yjy.com.accounts.application;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import yjy.com.accounts.function.utility.DateRule;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mayongsheng on 16/3/29.
 */
public class ACUserPreferences {

    private static final String USER_PREFERENCE_FILE_NAME = "account_user_preference.perf";
    private static final String USER_PREFERENCE_SELECTED_PAY_METHODS = "user_preference_selected_pay_methods";
    private static final String USER_PREFERENCE_SELECTED_COST_WAY = "user_preference_selected_cost_way";
    private static final String USER_PREFERENCE_SELECTED_DATERULE = "user_preference_selected_daterule";
    private static final String USER_PREFERENCE_SELECTED_FROM_DATE = "user_preference_selected_from_date";
    private static final String USER_PREFERENCE_SELECTED_TO_DATE = "user_preference_selected_to_date";

    public static List<String> getPayMethods() {
        SharedPreferences preferences = ACApplication.getInstance()
                .getSharedPreferences(USER_PREFERENCE_FILE_NAME,
                        Context.MODE_PRIVATE);
        Set<String> result = preferences.getStringSet(
                USER_PREFERENCE_SELECTED_PAY_METHODS, null);
        if (result != null) {
            return Arrays.asList((String[]) result.toArray());
        }

        return null;
    }

    public static List<String> getCostWay() {
        SharedPreferences preferences = ACApplication.getInstance()
                .getSharedPreferences(USER_PREFERENCE_FILE_NAME,
                        Context.MODE_PRIVATE);
        Set<String> result = preferences.getStringSet(
                USER_PREFERENCE_SELECTED_COST_WAY, null);
        if (result != null) {
            return Arrays.asList((String[]) result.toArray());
        }

        return null;
    }

    public static DateRule getDateRule() {
        SharedPreferences preferences = ACApplication.getInstance()
                .getSharedPreferences(USER_PREFERENCE_FILE_NAME,
                        Context.MODE_PRIVATE);
        int rule = preferences.getInt(USER_PREFERENCE_SELECTED_DATERULE,
                DateRule.NONE.getDay());

        return DateRule.valueOf(rule);
    }

    public static String getFromDate() {
        SharedPreferences preferences = ACApplication.getInstance()
                .getSharedPreferences(USER_PREFERENCE_FILE_NAME,
                        Context.MODE_PRIVATE);
        return preferences.getString(USER_PREFERENCE_SELECTED_FROM_DATE, null);
    }

    public static String getToDate() {
        SharedPreferences preferences = ACApplication.getInstance()
                .getSharedPreferences(USER_PREFERENCE_FILE_NAME,
                        Context.MODE_PRIVATE);
        return preferences.getString(USER_PREFERENCE_SELECTED_TO_DATE, null);
    }

    public static void savePayMethods(List<String> methods) {
        SharedPreferences preferences = ACApplication.getInstance()
                .getSharedPreferences(USER_PREFERENCE_FILE_NAME,
                        Context.MODE_PRIVATE);
        Set<String> methodSet = new HashSet<>();
        methods.addAll(methods);
        preferences.edit()
                .putStringSet(USER_PREFERENCE_SELECTED_PAY_METHODS, methodSet)
                .commit();
    }

    public static void saveCostWay(List<String> ways) {
        SharedPreferences preferences = ACApplication.getInstance()
                .getSharedPreferences(USER_PREFERENCE_FILE_NAME,
                        Context.MODE_PRIVATE);
        Set<String> waySet = new HashSet<>();
        waySet.addAll(ways);
        preferences.edit()
                .putStringSet(USER_PREFERENCE_SELECTED_COST_WAY, waySet)
                .commit();
    }

    public static void saveDateRule(DateRule rule) {
        SharedPreferences preferences = ACApplication.getInstance()
                .getSharedPreferences(USER_PREFERENCE_FILE_NAME,
                        Context.MODE_PRIVATE);
        preferences.edit()
                .putInt(USER_PREFERENCE_SELECTED_DATERULE, rule.getDay())
                .commit();
    }

    public static void saveFromDate(String fromDate) {
        SharedPreferences preferences = ACApplication.getInstance()
                .getSharedPreferences(USER_PREFERENCE_FILE_NAME,
                        Context.MODE_PRIVATE);
        preferences.edit()
                .putString(USER_PREFERENCE_SELECTED_FROM_DATE, fromDate)
                .commit();
    }

    public static void saveToDate(String toDate) {
        SharedPreferences preferences = ACApplication.getInstance()
                .getSharedPreferences(USER_PREFERENCE_FILE_NAME,
                        Context.MODE_PRIVATE);
        preferences.edit().putString(USER_PREFERENCE_SELECTED_TO_DATE, toDate)
                .commit();
    }
}
