package yjy.com.accounts.control;

import android.content.Context;

import java.util.Calendar;
import java.util.List;

import yjy.com.accounts.application.ACApplication;
import yjy.com.accounts.application.ACConst;
import yjy.com.accounts.databases.AccountInfo;
import yjy.com.accounts.databases.helper.ACDBHelper;

/**
 * Created by black on 16/3/23.
 */
public class AccountController {

    private static AccountController controller = new AccountController();

    private Context context;
    private AccountController(){
        context = ACApplication.context;
    }

    public static AccountController getController(){
        return controller;
    }


    public boolean saveAccount(double coast,int way,int use,String remark){
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setCost(coast);
        accountInfo.setWay(way);
        accountInfo.setUse(use);
        accountInfo.setRemark(remark);
        accountInfo.setDate(Calendar.getInstance().getTime());
        return ACDBHelper.getInstance(context).saveAccount(accountInfo) > 0;
    }

    public List<AccountInfo> getAllAccountList(){
         return ACDBHelper.getInstance(context).loadAllAccount();
    }
}
