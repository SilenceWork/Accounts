package yjy.com.accounts.application;

import android.app.Application;
import android.content.Context;

import yjy.com.accounts.databases.DaoMaster;
import yjy.com.accounts.databases.DaoSession;

/**
 * Created by yujinyang on 16/3/21.
 */
public class ACApplication extends Application{

    public static Context context;

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;


    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context)
    {
        if (daoMaster == null)
        {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, ACConst.DATABASE_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }
    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context)
    {
        if (daoSession == null)
        {
            if (daoMaster == null)
            {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
