package yjy.com.accounts.function.note;

import java.text.SimpleDateFormat;
import java.util.List;

import yjy.com.accounts.R;
import yjy.com.accounts.control.AccountController;
import yjy.com.accounts.control.TagsController;
import yjy.com.accounts.databases.AccountInfo;
import yjy.com.accounts.function.list.AccountDetailListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AccountActivity extends AppCompatActivity {

    private EditText cost_edt;
    private RadioGroup pay_radiogroup;

    private Spinner use_spinner;
    private EditText remark_edt;

    private String mPayMethod = "";
    private String mUsage = "";
    private double mCost = 0;
    ;
    private String mRemark;

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
                Snackbar.make(view, "Replace with your own action",
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        initView();
    }

    private void initView() {

        cost_edt = (EditText) findViewById(R.id.cost_edt);

        pay_radiogroup = (RadioGroup) findViewById(R.id.pay_radiogroup);
        pay_radiogroup
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        mPayMethod = ((RadioButton) findViewById(i)).getText().toString();
                    }
                });
        pay_radiogroup.check(R.id.pay_card_radio);

        use_spinner = (Spinner) findViewById(R.id.use_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, TagsController.getController().getUsageList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        use_spinner.setAdapter(adapter);
        use_spinner
                .setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        mUsage = TagsController.getController().getUsageList().get(arg2);
                    }

                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

        remark_edt = (EditText) findViewById(R.id.remark_edt);

        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AccountInfo> accounts = AccountController.getController()
                        .getAllAccountList();
                StringBuilder sb = new StringBuilder();
                for (AccountInfo accountInfo : accounts) {
                    sb.append(convert2String(accountInfo) + "\n");
                }
                ((TextView) findViewById(R.id.text)).setText(sb.toString());
                AccountActivity.this.startActivity(new Intent(
                        AccountActivity.this, AccountDetailListActivity.class));
            }
        });

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAccount();
            }
        });

    }

    private void addAccount() {
        if (checkAccount()) {
            boolean result = AccountController.getController().saveAccount(
                    mCost, mPayMethod, mUsage, mRemark);
            if (result) {
                Toast.makeText(this, "已入账", Toast.LENGTH_LONG).show();
                reset();
            } else {
                Toast.makeText(this, "数据存储出现问题，请检查数据库", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    private void reset() {
        cost_edt.setText("");
        pay_radiogroup.check(R.id.pay_apay_radio);
        use_spinner.setSelection(0);
        remark_edt.setText("");
    }

    private boolean checkAccount() {
        String costStr = cost_edt.getText().toString();
        try {
            mCost = Double.parseDouble(costStr);
        } catch (NumberFormatException e) {
        }
        if (mCost <= 0) {
            Toast.makeText(this, "花费不能为空", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(mPayMethod) || TextUtils.isEmpty(mUsage)) {
            Toast.makeText(this, "用途、方式 数据异常。 检查常量对应关系", Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        mRemark = remark_edt.getText().toString();
        if (TextUtils.isEmpty(mRemark)) {
            Toast.makeText(this, "没写备注！", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
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

    private String convert2String(AccountInfo info) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("KEY ID:" + info.getId() + "\n");
        stringBuilder.append("COST:" + info.getCost() + "\n");
        stringBuilder.append("PayMethod:" + info.getPaymethod()
                + "\n");
        stringBuilder.append("Usage:" + info.getUsage()
                + "\n");
        stringBuilder.append("REMARK:" + info.getRemark() + "\n");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        stringBuilder.append("DATE:" + sdf.format(info.getDate()) + "\n");
        return stringBuilder.toString();
    }
}
