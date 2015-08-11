package com.amytech.android.framework.ttk;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Title: AmyImageLibrary <br>
 * Description: <br>
 * Copyright: Copyright (c) 2015 <br>
 * Create DateTime: 2015年7月31日 上午10:45:50 <br>
 * SVN create person: marktlzhai <br>
 * SVN create DateTime: 2015年7月31日 <br>
 *
 * @author marktlzhai
 */
class AmyImageUploader extends AsyncTask<UploadRequest, Long, UploadResponse> {

    private static final String UPLOAD_URL = "http://up.tietuku.com/";

    private String token;

    private TieTuKuManager.UploadListener listener;

    private static final String BOUNDARY = "*****";
    private static final String NEW_LINE = "\r\n";
    private static final String TWO_HYPHENS = "--";

    public AmyImageUploader(String token) {
        this.token = token;
    }

    public void setUploadListener(TieTuKuManager.UploadListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        if (listener != null) {
            listener.onStart();
        }
    }

    @Override
    protected UploadResponse doInBackground(UploadRequest... params) {
        UploadRequest request = params[0];
        if (request == null || request.uploadFile == null || !request.uploadFile.exists() || !request.uploadFile.isFile()) {
            return null;
        }

        try {
            URL url = new URL(UPLOAD_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set size of every block for post
            connection.setChunkedStreamingMode(128 * 1024);// 128KB

            // Allow Inputs & Outputs
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            // Enable POST method
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());

            // write parameters
            outputStream.writeBytes(TWO_HYPHENS + BOUNDARY + NEW_LINE);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"Token\"" + NEW_LINE);
            outputStream.writeBytes("Content-Type: text/plain;charset=UTF-8" + NEW_LINE);
            outputStream.writeBytes("Content-Length: " + token.length() + NEW_LINE);
            outputStream.writeBytes(NEW_LINE);
            outputStream.writeBytes(token + NEW_LINE);
            outputStream.writeBytes(TWO_HYPHENS + BOUNDARY + NEW_LINE);
            outputStream.flush();

            // write file
            int bytesWrittenForCurrentFile = 0;
            byte[] buffer = new byte[100];
            int bytesWritten = 0;
            long filesize = request.uploadFile.length();
            FileInputStream fileInputStream = new FileInputStream(request.uploadFile);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + request.uploadFile.getName() + "\"" + NEW_LINE);
            outputStream.writeBytes("Content-Type: application/octet-stream" + NEW_LINE);
            outputStream.writeBytes("Content-Transfer-Encoding: binary" + NEW_LINE);
            outputStream.writeBytes(NEW_LINE);
            outputStream.flush();

            while (bytesWrittenForCurrentFile < filesize) {
                int bytesRead = fileInputStream.read(buffer, 0, buffer.length);
                outputStream.write(buffer, 0, bytesRead);
                outputStream.flush();
                bytesWrittenForCurrentFile += bytesRead;
                bytesWritten += bytesRead;
                publishProgress((long) bytesWritten, filesize);
            }

            outputStream.writeBytes(NEW_LINE);
            outputStream.writeBytes(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + NEW_LINE);
            outputStream.flush();
            fileInputStream.close();
            outputStream.close();

            int retCode = connection.getResponseCode();
            if (retCode == 200) {
                InputStream is = connection.getInputStream();
                int ch;
                StringBuffer b = new StringBuffer();
                while ((ch = is.read()) != -1) {
                    b.append((char) ch);
                }
                String result = b.toString().trim();
                if (result != null && result.length() > 0) {
                    JSONObject jsonResult = new JSONObject(result);
                    UploadResponse response = new UploadResponse();
                    response.width = jsonResult.optInt("width");
                    response.height = jsonResult.optInt("height");
                    response.type = jsonResult.optString("type");
                    response.size = jsonResult.optLong("size");
                    response.imageURL = jsonResult.optString("linkurl");
                    response.s_imageURL = jsonResult.optString("s_url");
                    response.t_imageURL = jsonResult.optString("t_url");
                    return response;
                }
                return null;
            }

            return null;

        } catch (Exception e) {
            Log.e("AmyImageUploader", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(UploadResponse result) {
        if (listener != null) {
            if (result != null) {
                listener.onSuccess(result);
            } else {
                listener.onFailure();
            }
        }
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        long uploadedSize = values[0];
        long totalSize = values[1];
        if (listener != null) {
            listener.onProgressUpdate(uploadedSize, totalSize);
        }
    }
}
