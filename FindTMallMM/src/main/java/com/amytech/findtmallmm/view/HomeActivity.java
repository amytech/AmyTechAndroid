package com.amytech.findtmallmm.view;

import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.amytech.android.framework.api.API;
import com.amytech.android.framework.api.APIList;
import com.amytech.android.framework.api.APIResponse;
import com.amytech.android.framework.utils.ImageUtils;
import com.amytech.android.framework.utils.StringUtils;
import com.amytech.android.framework.view.BaseActivity;
import com.amytech.findtmallmm.R;
import com.amytech.findtmallmm.model.MM;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marktlzhai on 2015/8/8.
 */
public class HomeActivity extends BaseActivity implements API.APIListener, ActionBar.TabListener {

    private ListView mmList;

    private MMAdapter mmAdapter;

    private int currentPage = 0;
    private int maxPage = Integer.MAX_VALUE;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void initActionBar(ActionBar actionBar) {
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    }

    @Override
    protected void initViews() {
        mmList = (ListView) findViewById(R.id.home_mm_list);
        mmAdapter = new MMAdapter();
        mmList.setAdapter(mmAdapter);

        API.getInstance().post(APIList.TMALL_STYLE, null, this);
    }

    private void fillActionbarTab(APIResponse response) {
        if (response.isSuccess()) {
            ActionBar.Tab tab = actionBar.newTab();
            tab.setText(getString(R.string.style_all));
            tab.setTag("");
            tab.setTabListener(this);
            actionBar.addTab(tab);

            JSONArray styleArry = response.result.optJSONArray("allTypeList");
            for (int i = 0; i < styleArry.length(); i++) {
                tab = actionBar.newTab();
                String style = styleArry.optString(i);
                tab.setText(style);
                tab.setTag(style);
                tab.setTabListener(this);
                actionBar.addTab(tab);
            }

            actionBar.setSelectedNavigationItem(0);
        }
    }

    private void fillMMList(APIResponse response) {
        if (response.isSuccess()) {
            JSONArray mmArry = response.result.optJSONObject("pagebean").optJSONArray("contentlist");
            if (mmArry != null && mmArry.length() > 0) {
                List<MM> mmList = new ArrayList<MM>();
                for (int i = 0; i < mmArry.length(); i++) {
                    mmList.add(new MM(mmArry.optJSONObject(i)));
                }
                mmAdapter.setData(mmList);
            }
        }
    }

    @Override
    public void onAPIStart(int api) {

    }

    @Override
    public void onAPIFinish(int api) {

    }

    @Override
    public void onAPISuccess(int api, APIResponse response) {
        switch (api) {
            case APIList.TMALL_STYLE:
                fillActionbarTab(response);
                break;
            case APIList.TMALL_LIST:
                fillMMList(response);
                break;
        }
    }

    @Override
    public void onAPIFailure(int api, int errorCode, String errorMessage) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("page", String.valueOf(++currentPage));
        if (StringUtils.isNotEmpty(tab.getTag().toString())) {
            params.put("type", tab.getTag().toString());
        }
        API.getInstance().post(APIList.TMALL_LIST, params, this);
        mmAdapter.clear();

        showToast("onTabSelected");
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        showToast("onTabUnselected");
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        showToast("onTabReselected");
    }

    class MMAdapter extends BaseAdapter {

        private List<MM> mmDatas = new ArrayList<MM>();

        private LayoutInflater layoutInflater;

        public MMAdapter() {
            this.layoutInflater = LayoutInflater.from(HomeActivity.this);
        }

        private void clear() {
            this.mmDatas.clear();
            notifyDataSetChanged();
        }

        private void setData(List<MM> mmDatas) {
            this.mmDatas.clear();
            this.mmDatas.addAll(mmDatas);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mmDatas.size();
        }

        @Override
        public MM getItem(int position) {
            return mmDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).userId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final MM item = getItem(position);

            ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_mm, parent, false);
                holder = new ViewHolder();
                holder.mmAvatar = (ImageView) convertView.findViewById(R.id.mm_avatar);
                holder.mmName = (TextView) convertView.findViewById(R.id.mm_real_name);
                holder.mmCity = (TextView) convertView.findViewById(R.id.mm_city);
                holder.mmHeightWeight = (TextView) convertView.findViewById(R.id.mm_height_weight);
                holder.mmFanFavor = (TextView) convertView.findViewById(R.id.mm_fans_favor);
                holder.enterChat = (Button) convertView.findViewById(R.id.mm_enter_chat);
                holder.checkImage = (Button) convertView.findViewById(R.id.mm_check_images);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ImageUtils.display(item.avatarUrl, holder.mmAvatar);
            holder.mmName.setText(item.realName);
            holder.mmCity.setText(item.city);
            holder.mmHeightWeight.setText(item.getHeightWeight());
            holder.mmFanFavor.setText(item.getFanFavor());
            holder.enterChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("to chat");
                }
            });

            holder.checkImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("this mm has " + item.images.size() + " images.");
                }
            });

            return convertView;
        }
    }

    class ViewHolder {
        ImageView mmAvatar;
        TextView mmName;
        TextView mmCity;
        TextView mmHeightWeight;
        TextView mmFanFavor;
        Button enterChat;
        Button checkImage;
    }
}
