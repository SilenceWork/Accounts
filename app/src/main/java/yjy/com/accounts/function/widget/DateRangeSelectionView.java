package yjy.com.accounts.function.widget;

import java.util.Calendar;
import java.util.Date;

import yjy.com.accounts.R;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by mayongsheng on 16/3/23.
 */
public class DateRangeSelectionView extends FrameLayout implements
        View.OnClickListener {

    public interface DateSelectedListener {
        void onFromDateSelected(int year, int monthOfYear, int dayOfMonth);

        void onToDateSelected(int year, int monthOfYear, int dayOfMonth);
    }

    private TextView mTvTitle;
    private TextView mTvSelectedFromDate;
    private TextView mTvSelectedToDate;
    private DateSelectedListener mListener;

    public DateRangeSelectionView(Context context) {
        super(context);
        init();
    }

    public DateRangeSelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DateRangeSelectionView(Context context, AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View contentView = inflater.inflate(
                R.layout.widget_date_range_selection, null);
        this.mTvTitle = (TextView) contentView
                .findViewById(R.id.tv_date_selected_title);
        this.mTvSelectedFromDate = (TextView) contentView
                .findViewById(R.id.tv_selected_from_date);
        this.mTvSelectedToDate = (TextView) contentView
                .findViewById(R.id.tv_selected_to_date);
        this.mTvSelectedFromDate.setOnClickListener(this);
        this.mTvSelectedToDate.setOnClickListener(this);
        this.addView(contentView);
    }

    public void setTitle(String title) {
        this.mTvTitle.setText(title);
    }

    public void setDateSelectedListener(DateSelectedListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        Date today = new Date();
        calendar.setTime(today);
        final int widgetid = v.getId();
        DatePickerDialog dialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                            int monthOfYear, int dayOfMonth) {
                        String formatDate = DateRangeSelectionView.this
                                .getResources().getString(
                                        R.string.format_year_month_day, year,
                                        monthOfYear, dayOfMonth);
                        if (widgetid == R.id.tv_selected_from_date) {
                            if (DateRangeSelectionView.this.mListener != null) {
                                DateRangeSelectionView.this.mListener
                                        .onFromDateSelected(year, monthOfYear,
                                                dayOfMonth);
                            }
                            DateRangeSelectionView.this.mTvSelectedFromDate
                                    .setText(formatDate);
                        } else if (widgetid == R.id.tv_selected_to_date) {
                            if (DateRangeSelectionView.this.mListener != null) {
                                DateRangeSelectionView.this.mListener
                                        .onToDateSelected(year, monthOfYear,
                                                dayOfMonth);
                            }
                            DateRangeSelectionView.this.mTvSelectedToDate
                                    .setText(formatDate);
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}
