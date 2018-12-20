package stream.mokulive.storagefrontend.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Component
public class Auth0Helper {

    @Value("${com.auth0.clientId}")
    private String clientId;

    @Value("${com.auth0.clientSecret}")
    private String clientSecret;

    @Value("${com.auth0.domain}")
    private String domain;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getUserId(String accessToken) throws UnirestException{
        //从auth0获取当前登录的用户id
        HttpResponse<String> response = null;
        String result = "";
        response = Unirest.post("https://"+domain+"/userinfo")
                .header("authorization", "Bearer " + accessToken)
                .asString();
        JSONObject json = getJsonObject(response);
        result = json.getString("sub");
        return result;
    }

    public JSONObject getAppAccessToken() throws UnirestException {
        //获取access token
        HttpResponse<String> response = null;
        response = Unirest.post("https://"+domain+"/oauth/token")
                .header("content-type", "application/json")
                .body("{\"client_id\":\"" + clientId + "\",\"client_secret\":\"" + clientSecret + "\",\"audience\":\"https://"+domain+"/api/v2/\",\"grant_type\":\"client_credentials\"}")
                .asString();
//        logger.error("获取access token出错", e);
        JSONObject json = getJsonObject(response);
        return json;
    }

    public JSONObject getUserDetail(String userId, String appAccessToken) throws UnirestException {
        //获取用户详情
        HttpResponse<String> response = null;
        response = Unirest.get("https://"+domain+"/api/v2/users/" + userId)
                .header("authorization", "Bearer " + appAccessToken)
                .asString();
//        logger.error("获取用户信息出错", e);
        JSONObject json = getJsonObject(response);
        return json;
    }

    private JSONObject getJsonObject(HttpResponse<String> response) {
        String body = response.getBody();
        JSONObject json = new JSONObject(body);
        return json;
    }
}
