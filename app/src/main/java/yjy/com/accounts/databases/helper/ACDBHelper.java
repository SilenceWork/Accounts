package yjy.com.accounts.databases.helper;

import android.content.Context;
import java.util.List;
import yjy.com.accounts.application.ACApplication;
import yjy.com.accounts.databases.AccountInfo;
import yjy.com.accounts.databases.AccountInfoDao;
import yjy.com.accounts.databases.DaoSession;

/**
 * Created by yujinyang on 16/3/21.
 */
public class ACDBHelper {

    private static final String TAG = ACDBHelper.class.getSimpleName();
    private static ACDBHelper instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private AccountInfoDao accountInfoDao;


    private ACDBHelper() {
    }

    public static ACDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ACDBHelper();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = ACApplication.getDaoSession(context);
            instance.accountInfoDao = instance.mDaoSession.getAccountInfoDao();
        }
        return instance;
    }


    public AccountInfo loadAccount(long id) {
        return accountInfoDao.load(id);
    }

    public List<AccountInfo> loadAllAccount(){
        return accountInfoDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<AccountInfo> queryAccount(String where, String... params){
        return accountInfoDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param account
     * @return insert or update note id
     */
    public long saveAccount(AccountInfo account){
        return accountInfoDao.insertOrReplace(account);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveAccountLists(final List<AccountInfo> list){
        if(list == null || list.isEmpty()){
            return;
        }
        accountInfoDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    AccountInfo account = list.get(i);
                    accountInfoDao.insertOrReplace(account);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllNote(){
        accountInfoDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        accountInfoDao.deleteByKey(id);
    }

    public void deleteNote(AccountInfo note){
        accountInfoDao.delete(note);
    }

}
