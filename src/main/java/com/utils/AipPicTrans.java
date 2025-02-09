package com.utils;

import com.baidu.aip.client.BaseClient;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.util.AipClientConst;
import com.baidu.aip.util.Util;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.io.File;

public class AipPicTrans extends BaseClient {
    public AipPicTrans(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }

    public JSONObject picTrans(String from, String to, File imagePath) {
        AipRequest request = new AipRequest();
        this.preOperation(request);
        if (this.isBceKey.get()) {
            return Util.getGeneralError(AipClientConst.OPENAPI_NO_ACCESS_ERROR_CODE, "No permission to access data");
        } else {


            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = null;
            try {
                response = Unirest.post("https://aip.baidubce.com/file/2.0/mt/pictrans/v1?access_token=" + accessToken)
                        .field("image", imagePath)
                        .field("from", from)
                        .field("to", to)
                        .field("v", "3")
                        .asString();
            } catch (UnirestException e) {
                throw new RuntimeException(e);
            }
            return new JSONObject(response.getBody());
        }

    }
}
