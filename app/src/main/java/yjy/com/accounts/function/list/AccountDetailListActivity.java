package yjy.com.accounts.function.list;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import yjy.com.accounts.R;
import yjy.com.accounts.application.ACConst;
import yjy.com.accounts.application.ACUserPreferences;
import yjy.com.accounts.databases.AccountInfo;
import yjy.com.accounts.databases.helper.ACDBHelper;
import yjy.com.accounts.function.utility.DateRule;
import yjy.com.accounts.function.widget.DateRangeSelectionView;
import yjy.com.accounts.function.widget.KeywordSelectionView;
import yjy.com.accounts.function.widget.MoneyRangeSelectionView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;

/**
 * Created by mayongsheng on 16/3/23.
 */
public class AccountDetailListActivity extends Activity implements
        DateRangeSelectionView.DateSelectedListener {

    private DateRangeSelectionView mDateSelectionView;
    private KeywordSelectionView mPayMethodSelectionView;
    private KeywordSelectionView mCostWaySelectionView;
    private MoneyRangeSelectionView mMoneyRangeSelectionView;
    private TableView<String[]> mAccountTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_account_detail_list);
        this.mDateSelectionView = (DateRangeSelectionView) findViewById(R.id.drsv_date_range);
        this.mPayMethodSelectionView = (KeywordSelectionView) findViewById(R.id.ksv_pay_method);
        this.mCostWaySelectionView = (KeywordSelectionView) findViewById(R.id.ksv_cost_way);
        this.mMoneyRangeSelectionView = (MoneyRangeSelectionView) findViewById(R.id.mrsv_money_range);

        this.mDateSelectionView.setTitle(getString(R.string.date));
        this.mDateSelectionView.setDateSelectedListener(this);
        this.mPayMethodSelectionView
                .setOnKeywordSelectedListener(mPayMethodSelectedListener);
        this.mPayMethodSelectionView.setKeywords(getResources().getStringArray(
                R.array.pay_method));
        this.mCostWaySelectionView
                .setOnKeywordSelectedListener(mCostWaySelectedListener);
        this.mCostWaySelectionView.setKeywords(getResources().getStringArray(
                R.array.usages));
        this.mAccountTable = (TableView<String[]>) findViewById(R.id.tv_cost_detail);
    }

    private KeywordSelectionView.OnKeywordSelectedListener mPayMethodSelectedListener = new KeywordSelectionView.OnKeywordSelectedListener() {

        @Override
        public void onKeywordSelected(List<String> selectedKeywords) {
            Log.d("Account", selectedKeywords.toString());
            ACUserPreferences.savePayMethods(selectedKeywords);
            refreshData();
        }
    };

    private KeywordSelectionView.OnKeywordSelectedListener mCostWaySelectedListener = new KeywordSelectionView.OnKeywordSelectedListener() {

        @Override
        public void onKeywordSelected(List<String> selectedKeywords) {
            Log.d("Account", selectedKeywords.toString());
            ACUserPreferences.saveCostWay(selectedKeywords);
            refreshData();
        }
    };

    @Override
    public void onFromDateSelected(int year, int monthOfYear, int dayOfMonth) {
        Log.d("Account",
                getString(R.string.format_year_month_day, year, monthOfYear,
                        dayOfMonth));
        ACUserPreferences.saveFromDate(year + monthOfYear + dayOfMonth + "");
        refreshData();
    }

    @Override
    public void onToDateSelected(int year, int monthOfYear, int dayOfMonth) {
        Log.d("Account",
                getString(R.string.format_year_month_day, year, monthOfYear,
                        dayOfMonth));
        ACUserPreferences.saveFromDate(year + monthOfYear + dayOfMonth + "");
        refreshData();
    }

    @Override
    public void onDateRuleSelected(DateRule rule) {
        Log.d("Account", rule.toString());
        ACUserPreferences.saveDateRule(rule);
        refreshData();
    }

    private void refreshData() {
        List<String> payMethod = ACUserPreferences.getPayMethods();
        List<String> costWay = ACUserPreferences.getCostWay();
        DateRule rule = ACUserPreferences.getDateRule();
        String fromDate, toDate;
        if (rule == DateRule.NONE) {
            fromDate = ACUserPreferences.getFromDate();
            toDate = ACUserPreferences.getToDate();
        } else {
            int lastDay = rule.getDay();
            Calendar calendar = Calendar.getInstance();
            Date nowDate = calendar.getTime();
            int nowYear = calendar.get(Calendar.YEAR);
            int nowMonthOfYear = calendar.get(Calendar.MONTH);
            int nowWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
            if (rule == DateRule.LAST_WEEK) {
                calendar.set(Calendar.WEEK_OF_YEAR, nowWeekOfYear - 1);
            } else if (rule == DateRule.LAST_MONTH) {
                calendar.set(Calendar.MONTH, nowMonthOfYear - 1);
            } else if (rule == DateRule.LAST_YEAR) {
                calendar.set(Calendar.YEAR, nowYear - 1);
            }
            fromDate = nowDate.toString();
            toDate = calendar.getTime().toString();
        }

        ACDBHelper helper = ACDBHelper
                .getInstance(this.getApplicationContext());
        StringBuilder queryWhereClause = new StringBuilder();
        List<String> queryParam = new ArrayList<String>();
        queryWhereClause.append("where way IN (");
        for (int i = 0; i < payMethod.size(); i++) {
            queryWhereClause.append("?,");
            queryParam.add(String.valueOf(ACConst.getWayInt(payMethod.get(i))));
        }
        queryWhereClause.deleteCharAt(queryWhereClause.length() - 1);
        queryWhereClause.append(") AND use IN (");
        for (int j = 0; j < costWay.size(); j++) {
            queryWhereClause.append("?,");
            queryParam.add(String.valueOf(ACConst.getUseInt(costWay.get(j))));
        }
        queryWhereClause.deleteCharAt(queryWhereClause.length() - 1);
        queryWhereClause.append(");");
//        queryWhereClause.append(") AND date >= ? AND date <= ?;");
//        queryParam.add(fromDate);
//        queryParam.add(toDate);

        Log.v("sql","where:" + queryWhereClause);

        String[] queryParmArray = new String[queryParam.size()];
        List<AccountInfo> accountInfos = helper.queryAccount(
                queryWhereClause.toString(),  "4133","4133","4133","4133","4133","8264","8264","8264","8264","8264");


        int size = accountInfos.size();
        String[][] data = new String[5][size];
        for (int k = 0; k < size; k++) {
            String[] properties = convertAccountInfoToArray(accountInfos.get(k));
            for (int t = 0; t < 5; t++) {
                data[t][k] = properties[t];
            }
        }

        this.mAccountTable
                .setDataAdapter(new SimpleTableDataAdapter(this, data));
    }

    private String[] convertAccountInfoToArray(AccountInfo info) {
        String[] result = new String[5];
        result[0] = String.valueOf(info.getDate());
        result[1] = String.valueOf(info.getWay());
        result[2] = String.valueOf(info.getUse());
        result[3] = String.valueOf(info.getCost());
        result[4] = info.getRemark();

        return result;
    }
}
