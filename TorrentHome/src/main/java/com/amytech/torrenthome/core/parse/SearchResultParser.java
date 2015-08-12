package com.amytech.torrenthome.core.parse;

import com.amytech.torrenthome.core.model.SearchResultData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marktlzhai on 2015/8/12.
 */
public class SearchResultParser {
    public static List<SearchResultData> parse(String html) {
        List<SearchResultData> result = new ArrayList<SearchResultData>();

        Document doc = Jsoup.parse(html);
        if (doc != null) {
            String name = "";
            String updateTime = "";
            String downloadURL = "";
            String fileSize = "";
            Elements divElements = doc.getElementsByAttributeValue("class", "i_info");
            if (divElements != null && divElements.size() > 0) {
                for (int i = 0; i < divElements.size(); i++) {
                    Elements updateTimeElements = divElements.get(i).getElementsByTag("h5");
                    if (updateTimeElements != null && updateTimeElements.size() > 0) {
                        updateTime = updateTimeElements.get(0).text();
                    }

                    Elements detailElements = divElements.get(i).getElementsByTag("a");
                    if (detailElements != null && detailElements.size() == 2) {
                        name = detailElements.get(0).attr("title");
                        downloadURL = detailElements.get(1).attr("href");
                    }

                    Elements sizeElements = divElements.get(i).getElementsByTag("span");
                    if (sizeElements != null && sizeElements.size() > 0) {
                        fileSize = sizeElements.get(0).text();
                    }

                    result.add(new SearchResultData(name, updateTime, downloadURL, fileSize));
                }
            }
        }

        return result;
    }
}
