package yjy.com.accounts;

import android.accounts.Account;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import yjy.com.accounts.application.ACConst;
import yjy.com.accounts.databases.AccountInfo;
import yjy.com.accounts.databases.helper.ACDBHelper;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();
    }

    private void initView() {
        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AccountInfo> accounts = ACDBHelper.getInstance(AccountActivity.this).loadAllAccount();
                StringBuilder sb = new StringBuilder();
                for(AccountInfo accountInfo : accounts){
                    sb.append(accountInfo.toString()+"\n");
                }
                ((TextView)findViewById(R.id.text)).setText(sb.toString());
            }
        });

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAccount();
            }
        });
    }

    public int index;
    private void addAccount() {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setCost(30.3 + 10*index);
        accountInfo.setWay(ACConst.WAY_APAY);
        accountInfo.setUse(ACConst.USE_EAT);
        accountInfo.setRemark("中午吃饭 " + index);
        accountInfo.setDate(Calendar.getInstance().getTime());

        ACDBHelper.getInstance(this).saveAccount(accountInfo);
        index ++;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
