package yjy.com.accounts.control;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import yjy.com.accounts.R;
import yjy.com.accounts.application.ACApplication;
import yjy.com.accounts.application.ACUserPreferences;
import yjy.com.accounts.databases.AccountInfo;
import yjy.com.accounts.databases.helper.ACDBHelper;

/**
 * Created by black on 16/3/23.
 */
public class TagsController {

    private static TagsController controller = new TagsController();

    private Context context;
    private List<String> payMethodList;
    private List<String> usageList;

    private TagsController() {
        context = ACApplication.mApp;
    }

    public static TagsController getController() {
        return controller;
    }

    /**
     * 程序启动时需调用初始化.s
     */
    public void initTags() {
        payMethodList = new ArrayList<>();
        payMethodList.add(context.getResources().getString(R.string.pay_apay));
        payMethodList.add(context.getResources().getString(R.string.pay_wx));
        payMethodList.add(context.getResources().getString(R.string.pay_card));
        payMethodList.add(context.getResources().getString(R.string.pay_cash));
        payMethodList.add(context.getResources().getString(R.string.pay_other));

        List<String> customerList = ACUserPreferences.getCustomerPayMethods();
        if(customerList != null){
            for(String tag : customerList){
                payMethodList.add(tag);
            }
        }

        usageList = new ArrayList<>();
        usageList.add(context.getResources().getString(R.string.use_eat));
        usageList.add(context.getResources().getString(R.string.use_market));
        usageList.add(context.getResources().getString(R.string.use_taxi));
        usageList.add(context.getResources().getString(R.string.use_other));

        customerList = ACUserPreferences.getCustomerUsageList();
        if(customerList != null){
            for(String tag : customerList){
                usageList.add(tag);
            }
        }
    }

    public boolean savePayTag(String payMethod) {
        //TODO: save into SharedPerference
        initTags();
        return true;
    }

    public boolean saveUsageTag(String usage) {
        //TODO: save into SharedPerference
        initTags();
        return true;
    }

    public List<String> getPayMethodList() {
        assert payMethodList != null;
        return payMethodList;
    }

    public List<String> getUsageList() {
        assert usageList != null;
        return usageList;
    }

}
