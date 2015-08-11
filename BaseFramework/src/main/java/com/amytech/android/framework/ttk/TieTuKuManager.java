package com.amytech.android.framework.ttk;

import android.content.Context;

import com.amytech.android.framework.ttk.model.TTKAlbum;
import com.amytech.android.framework.ttk.model.TTKPicture;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: AmyAndroidLib <br>
 * Description: <br>
 * Copyright: Copyright (c) 2015 <br>
 * Create DateTime: 2015年7月31日 下午5:53:58 <br>
 * SVN create person: marktlzhai <br>
 * SVN create DateTime: 2015年7月31日 <br>
 *
 * @author marktlzhai
 */
public class TieTuKuManager {

    public static class UploadAdapter implements UploadListener {

        @Override
        public void onStart() {

        }

        @Override
        public void onProgressUpdate(long uploadedSize, long totalSize) {

        }

        @Override
        public void onSuccess(UploadResponse response) {

        }

        @Override
        public void onFailure() {

        }
    }

    public interface UploadListener {
        void onStart();

        void onProgressUpdate(long uploadedSize, long totalSize);

        void onSuccess(UploadResponse response);

        void onFailure();
    }

    public interface GetAlbumListener {
        void getAlbumSuccess(List<TTKAlbum> albumList, int total);

        void getAlbumFailure(String errorMessage);
    }

    public interface GetAlbumPicListener {
        void getAlbumPicSuccess(List<TTKPicture> result, int total);

        void getAlbumPicFailure(String errorMessage);
    }

    private Context context;

    private static final AsyncHttpClient CLIENT = new AsyncHttpClient();

    private static TieTuKuManager instance;

    private String token;

    private TieTuKuManager(Context context, String token) {
        this.context = context;
        this.token = token;
        CLIENT.setTimeout(3000);
        CLIENT.setMaxRetriesAndTimeout(0, 0);
    }

    public static TieTuKuManager getInstance(Context context, String token) {
        if (instance == null) {
            instance = new TieTuKuManager(context, token);
        }
        return instance;
    }

    public void uploadFile(File imageFile, UploadListener listener) {
        AmyImageUploader uploader = new AmyImageUploader(token);
        UploadRequest request = new UploadRequest(imageFile);
        uploader.setUploadListener(listener);
        uploader.execute(request);
    }

    public void getAlbumPictures(String openKey, int albumID, int page, final GetAlbumPicListener listener) {
        String url = "http://api.tietuku.com/v2/api/getpiclist";
        RequestParams params = new RequestParams();
        params.add("key", openKey);
        params.add("returntype", "json");
        params.add("aid", String.valueOf(albumID));
        params.add("p", String.valueOf(page));
        CLIENT.post(url, params, new JsonHttpResponseHandler() {
            private boolean success = false;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                success = true;

                if (listener != null) {

                    List<TTKPicture> result = new ArrayList<TTKPicture>();

                    int total = response.optInt("total");
                    JSONArray picArry = response.optJSONArray("pic");
                    if (picArry != null && picArry.length() > 0) {
                        TTKPicture item = null;
                        for (int i = 0; i < picArry.length(); i++) {
                            JSONObject picObj = picArry.optJSONObject(i);

                            int id = picObj.optInt("id");
                            String name = picObj.optString("name");
                            String linkurl = picObj.optString("linkurl");
                            String showurl = picObj.optString("showurl");
                            String ext = picObj.optString("ext");
                            int width = picObj.optInt("width");
                            int height = picObj.optInt("height");

                            item = new TTKPicture(id, linkurl);
                            item.name = name;
                            item.showURL = showurl;
                            item.ext = ext;
                            item.width = width;
                            item.height = height;

                            result.add(item);
                        }
                    }

                    listener.getAlbumPicSuccess(result, total);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                success = false;

                if (listener != null) {
                    listener.getAlbumPicFailure(responseString);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (listener != null && !success) {
                    listener.getAlbumPicFailure("网络不可用");
                }
            }
        });
    }

    public void getAlbum(String openKey, int page, final GetAlbumListener listener) {
        String url = "http://api.tietuku.com/v2/api/getalbum";
        RequestParams params = new RequestParams();
        params.add("key", openKey);
        params.add("returntype", "json");
        params.add("p", String.valueOf(page));
        CLIENT.post(url, params, new JsonHttpResponseHandler() {

            private boolean success = false;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                success = true;
                if (listener != null) {
                    ArrayList<TTKAlbum> result = new ArrayList<TTKAlbum>();

                    int total = response.optInt("total");
                    JSONArray albumArray = response.optJSONArray("album");

                    if (albumArray != null && albumArray.length() > 0) {
                        TTKAlbum item = null;
                        for (int i = 0; i < albumArray.length(); i++) {
                            JSONObject albumObj = albumArray.optJSONObject(i);
                            if (albumObj != null) {
                                item = new TTKAlbum();
                                item.aid = albumObj.optInt("aid");
                                item.albumname = albumObj.optString("albumname");
                                JSONArray picArray = albumObj.optJSONArray("pic");
                                if (picArray != null && picArray.length() > 0) {
                                    for (int j = 0; j < albumArray.length(); j++) {
                                        JSONObject picObj = picArray.optJSONObject(j);
                                        if (picObj != null) {
                                            item.pics.add(new TTKPicture(picObj.optInt("id"), picObj.optString("url")));
                                        }
                                    }
                                }
                                result.add(item);
                            }
                        }
                    }

                    listener.getAlbumSuccess(result, total);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                success = false;
                if (listener != null) {
                    listener.getAlbumFailure(responseString);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (listener != null && !success) {
                    listener.getAlbumFailure("网络不可用");
                }
            }
        });
    }
}
