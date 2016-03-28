package yjy.com.accounts.function.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import yjy.com.accounts.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by mayongsheng on 16/3/23.
 */
public class KeywordSelectionView extends FrameLayout {

    public interface OnKeywordSelectedListener {
        void onKeywordSelected(List<String> selectedKeywords);
    }

    private String mTitle;
    private List<String> mKeywords = new ArrayList<>();
    private List<String> mSelectedKeywords = new ArrayList<>();
    private OnKeywordSelectedListener mListener;
    private FlowLayout mFlSelectedKeyword;

    public KeywordSelectionView(Context context) {
        super(context);
        init();
    }

    public KeywordSelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KeywordSelectionView(Context context, AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View contentView = inflater.inflate(R.layout.widget_keyword_selection,
                null);
        mFlSelectedKeyword = (FlowLayout) contentView
                .findViewById(R.id.fl_keyword_selected);
        this.addView(contentView);
        contentView.findViewById(R.id.ib_keyword_setting).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KeywordSelectionView.this.showKeywordSelectionDialog();
                    }
                });
    }

    public void setOnKeywordSelectedListener(OnKeywordSelectedListener listener) {
        this.mListener = listener;
    }

    public void setKeywords(String[] keywordList) {
        this.mKeywords.addAll(Arrays.asList(keywordList));
    }

    private void createKeywordFlowlayout(FlowLayout flowLayout,
            List<String> keywords, boolean bindListener) {
        flowLayout.removeAllViews();
        for (final String keyword : keywords) {
            TextView cell = (TextView) inflate(getContext(),
                    R.layout.widget_keyword_cell, null);
            cell.setText(keyword);
            cell.setTag(keyword);
            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(
                    FlowLayout.LayoutParams.WRAP_CONTENT,
                    FlowLayout.LayoutParams.WRAP_CONTENT);
            cell.setLayoutParams(params);
            flowLayout.addView(cell);

            if (!bindListener) {
                continue;
            }
            cell.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (KeywordSelectionView.this.mSelectedKeywords.contains(v
                            .getTag())) {
                        KeywordSelectionView.this.mSelectedKeywords.remove(v
                                .getTag());
                    } else {
                        KeywordSelectionView.this.mSelectedKeywords.add(v
                                .getTag().toString());
                    }
                }
            });

        }
    }

    private void showKeywordSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(this.mTitle);
        final FlowLayout flowLayout = new FlowLayout(getContext());
        createKeywordFlowlayout(flowLayout, this.mKeywords, true);
        builder.setView(flowLayout);
        builder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        KeywordSelectionView.this.createKeywordFlowlayout(
                                KeywordSelectionView.this.mFlSelectedKeyword,
                                KeywordSelectionView.this.mSelectedKeywords,
                                false);
                        if (KeywordSelectionView.this.mListener != null) {
                            KeywordSelectionView.this.mListener
                                    .onKeywordSelected(KeywordSelectionView.this.mSelectedKeywords);
                        }
                    }
                });
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
}
