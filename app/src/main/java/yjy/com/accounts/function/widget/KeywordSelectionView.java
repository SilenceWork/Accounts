package yjy.com.accounts.function.widget;

import yjy.com.accounts.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by mayongsheng on 16/3/23.
 */
public class KeywordSelectionView extends FrameLayout {
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
        this.addView(contentView);
    }
}
