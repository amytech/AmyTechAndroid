package com.amytech.torrenthome.core.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by marktlzhai on 2015/8/11.
 */
public class TorrentHomeSearchTop extends BmobObject {
    //公共属性
    private int order = 1;
    private String name = "名称";
    private String description = "描述";
    private String imageURL = "图片地址";
    private String tabName = "标签";
    private String type = "类型";
    private String area = "地区";
    private String watchURL = "播放地址";

    //电影,电视剧
    private String director = "导演";
    private String starring = "主演";

    //综艺节目
    private String source = "来源";
    private String compere = "主持人";

    private TorrentHomeSearchTop() {

    }

    /**
     * @param order       顺序
     * @param name        名称
     * @param description 描述
     * @param imageURL    图片
     * @param tabName     标签
     * @param type        类型
     * @param area        地区
     * @param director    导演
     * @param starring    主演
     * @param source      来源
     * @param compere     主持人
     */
    public TorrentHomeSearchTop(int order, String name, String description, String imageURL, String tabName, String type, String area, String director, String starring, String source, String compere, String watchURL) {
        this.order = order;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.tabName = tabName;
        this.type = type;
        this.area = area;
        this.director = director;
        this.starring = starring;
        this.source = source;
        this.compere = compere;
        this.watchURL = watchURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCompere() {
        return compere;
    }

    public void setCompere(String compere) {
        this.compere = compere;
    }

    public String getWatchURL() {
        return watchURL;
    }

    public void setWatchURL(String watchURL) {
        this.watchURL = watchURL;
    }

    @Override
    public String toString() {
        return name;
    }
}
