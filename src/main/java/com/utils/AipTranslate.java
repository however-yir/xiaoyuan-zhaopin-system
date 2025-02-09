package com.utils;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.http.EBodyFormat;
import com.baidu.aip.util.AipClientConst;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AipTranslate extends BaseClient {
    public AipTranslate(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    public JSONObject textTrans(String from, String to, String q) {
        AipRequest request = new AipRequest();
        this.preOperation(request);
        if (this.isBceKey.get()) {
            return Util.getGeneralError(AipClientConst.OPENAPI_NO_ACCESS_ERROR_CODE, "No permission to access data");
        } else {
            request.addBody("from", from);
            request.addBody("to", to);
            request.addBody("q", q);
            HashMap<String, String> params = new HashMap<>();
            params.put("access_token", this.accessToken);
            request.setParams(params);
            request.setUri("https://aip.baidubce.com/rpc/2.0/mt/texttrans/v1");
            request.setBodyFormat(EBodyFormat.RAW_JSON);
            request.addHeader("Content-Type", "application/json");
            return this.requestServer(request);
        }
    }

}
