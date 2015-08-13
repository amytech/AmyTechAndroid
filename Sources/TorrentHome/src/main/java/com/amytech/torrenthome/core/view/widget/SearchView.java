package com.amytech.torrenthome.core.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.amytech.torrenthome.R;
import com.amytech.torrenthome.core.controller.SearchController;

/**
 * Created by marktlzhai on 2015/8/12.
 */
public class SearchView extends LinearLayout {

    public interface OnSearchListener {
        void doSearch(String query);
    }

    private OnSearchListener listener;
    private EditText searchInput;
    private View searchButton;

    public SearchView(Context context) {
        super(context);
        initView();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.custom_search_view, null, false);
        searchInput = (EditText) rootView.findViewById(R.id.searchview_input);
        searchButton = rootView.findViewById(R.id.searchview_submit);
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.doSearch(searchInput.getText().toString());
                    searchInput.setText("");
                }
            }
        });
        addView(rootView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    public void setOnSearchListener(OnSearchListener listener) {
        this.listener = listener;
    }
}
