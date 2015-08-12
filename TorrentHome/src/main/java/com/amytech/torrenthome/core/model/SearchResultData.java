package com.amytech.torrenthome.core.model;

import java.io.Serializable;

/**
 * Created by marktlzhai on 2015/8/12.
 */
public class SearchResultData implements Serializable {
    public String name = "";
    public String updateTime = "";
    public String downloadURL = "";
    public String fileSize = "";

    public SearchResultData(String name, String updateTime, String downloadURL, String fileSize) {
        this.name = name;
        this.updateTime = updateTime;
        this.downloadURL = downloadURL;
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return name + "::" + updateTime + "::" + fileSize;
    }
}
