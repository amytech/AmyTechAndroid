package com.amytech.android.framework.utils.http;

import com.amytech.android.framework.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhaitianlong on 15/8/9.
 */
class HttpRequest {
    public String requestMethod = "get";
    public String returnType = "json";
    public String url;
    public Map<String, String> params;

    public HttpRequest(String requestMethod, String url) {
        this(requestMethod, url, null);
    }

    public HttpRequest(String requestMethod, String url, Map<String, String> params) {
        this(requestMethod, url, null, "json");
    }

    public HttpRequest(String requestMethod, String url, Map<String, String> params, String returnType) {
        this.requestMethod = requestMethod;
        this.url = url;
        this.params = new HashMap<String, String>();
        if (params != null && params.size() > 0) {
            this.params.putAll(params);
        }
        if (StringUtils.isNotEmpty(returnType)) {
            this.returnType = returnType;
        }
    }

    @Override
    public String toString() {
        return "[" + requestMethod + "]->" + url;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HttpRequest) {
            if (((HttpRequest) o).url.equals(url)) {
                if (((HttpRequest) o).returnType.equals(returnType)) {
                    if (((HttpRequest) o).requestMethod.equals(requestMethod)) {
                        if (((HttpRequest) o).params.size() == params.size()) {
                            Set<String> oKeys = ((HttpRequest) o).params.keySet();
                            Set<String> keys = params.keySet();
                            for (String key : oKeys) {
                                if (!StringUtils.isEquals(((HttpRequest) o).params.get(key), params.get(key))) {
                                    return false;
                                }
                                return true;
                            }
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
