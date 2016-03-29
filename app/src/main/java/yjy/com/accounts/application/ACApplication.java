package yjy.com.accounts.application;

import yjy.com.accounts.databases.DaoMaster;
import yjy.com.accounts.databases.DaoSession;

import android.app.Application;

/**
 * Created by yujinyang on 16/3/21.
 */
public class ACApplication extends Application {

    public static ACApplication mApp;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    /**
     * 取得DaoMaster
     * @return
     */
    public static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(mApp,
                    ACConst.DATABASE_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     * @return
     */
    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static ACApplication getInstance() {
        return mApp;
    }
}
