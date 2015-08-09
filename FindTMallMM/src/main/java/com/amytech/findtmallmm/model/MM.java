package com.amytech.findtmallmm.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaitianlong on 15/8/9.
 */
public class MM implements Serializable {

    public long userId = 0;

    public String avatarUrl = "";

    public String city = "";

    public int height = 0;

    public int weight = 0;

    public String link = "";

    public String realName = "";

    public int totalFanNum = 0;

    public int totalFavorNum = 0;

    public String type = "";

    public List<String> images = new ArrayList<String>();

    public MM(JSONObject json) {
        if (json == null) {
            return;
        }
        this.userId = json.optLong("userId");
        this.avatarUrl = json.optString("avatarUrl");
        this.city = json.optString("city");
        this.height = json.optInt("height");
        this.weight = json.optInt("weight");
        this.link = json.optString("link");
        this.realName = json.optString("realName");
        this.totalFanNum = json.optInt("totalFanNum");
        this.totalFavorNum = json.optInt("totalFavorNum");
        this.type = json.optString("type");
        this.images.clear();
        JSONArray imgArry = json.optJSONArray("imgList");
        if (imgArry != null && imgArry.length() > 0) {
            for (int i = 0; i < imgArry.length(); i++) {
                this.images.add(imgArry.optString(1));
            }
        }
    }

    public String getHeightWeight() {
        return String.valueOf(height) + " CM / " + String.valueOf(weight) + " KG";
    }

    public String getFanFavor() {
        return String.valueOf(totalFanNum) + " 粉丝 / " + String.valueOf(totalFavorNum) + " 关注";
    }
}
