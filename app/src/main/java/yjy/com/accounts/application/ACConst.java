package yjy.com.accounts.application;

import android.content.Context;

import yjy.com.accounts.R;

/**
 * Created by yujinyang on 16/3/21.
 */
public class ACConst {
    public static final String DATABASE_NAME = "account_db";

    public static final int WAY_APAY = 0x1024;
    public static final int WAY_WX = 0x1025;
    public static final int WAY_CARD = 0x1026;
    public static final int WAY_CASH = 0x1027;
    public static final int WAY_OTHER = 0x1028;


    public static final int USE_EAT = 0x2048;
    public static final int USE_TAXI = 0x2049;
    public static final int USE_MARKET = 0x2050;
    public static final int USE_OTHER = 0x2051;

    public static final int[] uses = new int[]{ACConst.USE_EAT,ACConst.USE_TAXI,ACConst.USE_MARKET,ACConst.USE_OTHER};

    public static int getUse(int index){
        if(index < 0 || index > uses.length){
            return -1;
        }
        return uses[index];
    }

    public static String getWayString(int way){
        int stringR;
        switch (way){
            case WAY_APAY:
                stringR =  R.string.way_apay;
                break;
            case WAY_WX:
                stringR =  R.string.way_wx;
                break;
            case WAY_CARD:
                stringR =  R.string.way_card;
                break;
            case WAY_CASH:
                stringR =  R.string.way_cash;
                break;
            default:
                stringR =  R.string.way_other;
                break;
        }
        return ACApplication.context.getResources().getString(stringR);
    }

    public static String getUseString(int use){
        int stringR;
        switch (use){
            case USE_EAT:
                stringR =  R.string.use_eat;
                break;
            case USE_TAXI:
                stringR =  R.string.use_taxi;
                break;
            case USE_MARKET:
                stringR =  R.string.use_market;
                break;
            default:
                stringR =  R.string.use_other;
                break;
        }
        return ACApplication.context.getResources().getString(stringR);
    }
}
