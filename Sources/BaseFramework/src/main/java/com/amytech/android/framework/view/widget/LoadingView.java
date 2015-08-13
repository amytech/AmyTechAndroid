package com.amytech.android.framework.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amytech.android.framework.R;
import com.amytech.android.framework.view.widget.progressbar.CircleProgress;

/**
 * Title: AmyAndroidLib <br>
 * Description: <br>
 * Copyright: Copyright (c) 2015 <br>
 * Create DateTime: 2015年7月8日 下午6:38:54 <br>
 * SVN create person: marktlzhai <br>
 * SVN create DateTime: 2015年7月8日 <br>
 *
 * @author marktlzhai
 */
@SuppressLint("InflateParams")
public class LoadingView extends RelativeLayout {

    public interface LoadingInterface {
        void reload();
    }

    private CircleProgress loadingProgress;
    private ImageView loadingErrorIcon;
    private TextView loadingText;
    private Button reloadButton;

    private LoadingInterface loadingInterface;

    public LoadingView(Context context) {
        super(context);
        initViews();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    public void setLoadingInterface(LoadingInterface loadingInterface) {
        this.loadingInterface = loadingInterface;
    }

    private void initViews() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_loading, null, false);

        loadingProgress = (CircleProgress) rootView.findViewById(R.id.circle_progress);
        loadingErrorIcon = (ImageView) rootView.findViewById(R.id.loading_error_icon);
        loadingText = (TextView) rootView.findViewById(R.id.loading_text);
        reloadButton = (Button) rootView.findViewById(R.id.reload_button);

        addView(rootView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    public void startLoading() {
        loadingProgress.setVisibility(View.VISIBLE);
        loadingProgress.setProgress(0);

        loadingErrorIcon.setVisibility(View.GONE);
        loadingText.setText(R.string.waitting);

        reloadButton.setVisibility(View.GONE);

    }

    public void loadingProgress(int countFor100) {
        loadingProgress.setProgress(countFor100);
    }

    public void loadingError() {
        loadingProgress.setVisibility(View.GONE);

        loadingErrorIcon.setVisibility(View.VISIBLE);

        loadingText.setText(R.string.loading_error);

        reloadButton.setVisibility(View.VISIBLE);
        reloadButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startLoading();
                if (loadingInterface != null) {
                    loadingInterface.reload();
                }
            }
        });

    }
}
