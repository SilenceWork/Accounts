package yjy.com.accounts.function.utility;

/**
 * Created by mayongsheng on 16/3/29.
 */
public enum DateRule {
    LAST_WEEK(7),
    LAST_MONTH(30),
    LAST_YEAR(365),
    NONE(0);

    DateRule(int day) {
        this.day = day;
    }

    private int day;

    public int getDay() {
        return this.day;
    }

    public static DateRule valueOf(int rule) {
        DateRule result;
        switch (rule) {
        case 7:
            result = LAST_WEEK;
            break;
        case 30:
            result = LAST_MONTH;
            break;
        case 365:
            result = LAST_YEAR;
            break;
        default:
            result = NONE;
            break;
        }

        return result;
    }
}
