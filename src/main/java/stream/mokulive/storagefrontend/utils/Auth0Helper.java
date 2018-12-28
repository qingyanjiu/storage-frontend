package stream.mokulive.storagefrontend.utils;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Auth0Helper {

    @Value("${com.auth0.clientId}")
    private String clientId;

    @Value("${com.auth0.clientSecret}")
    private String clientSecret;

    @Value("${com.auth0.domain}")
    private String domain;

    @Value("${custom.backendUrl}")
    private String backendUrl;

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

    //从backend获取access token
    public String getAppAccessToken() throws Exception {
        HttpResponse<String> response = null;
        String result = null;
        try {
            response = Unirest.post(backendUrl + "/token/get")
                    .header("content-type", "application/json")
                    .asString();
            JSONObject json = getJsonObject(response);
            if(json != null && json.getBoolean("success")) {
                result = json.getString("accessToken");
            }
        } catch (Exception e){
            logger.error("从backend获取accessToken出错 -- getAppAccessToken", e);
            throw new Exception(e);
        }
        return result;
    }

    //从backend查看userid是否已经存在
    public JSONObject checkUser(String userId) throws Exception {
        HttpResponse<String> response = null;
        JSONObject user = null;
        try {
            response = Unirest.post(backendUrl + "/user/"+userId)
                    .header("content-type", "application/json")
                    .asString();
            JSONObject json = getJsonObject(response);
            if(json != null && json.getBoolean("success")) {
                user = json.getJSONObject("user");
            }
        } catch (Exception e){
            logger.error("从backend获取user出错 -- checkUser", e);
            throw new Exception(e);

        }
        return user;
    }

    public JSONObject getUserDetail(String userId, String appAccessToken) throws Exception {
        //获取用户详情
        HttpResponse<String> response = null;
        JSONObject json = null;
        try {
            response = Unirest.get("https://"+domain+"/api/v2/users/" + userId)
                    .header("authorization", "Bearer " + appAccessToken)
                    .asString();
            json = getJsonObject(response);
        } catch (Exception e){
            logger.error("从auth0获取用户详情出错", e);
            throw new Exception(e);
        }
        return json;
    }

    //将user信息保存到backend数据库中
    public void createUser(JSONObject user) throws Exception {
        HttpResponse<String> response = null;
        try {
            Auth0User body = new Auth0User();
            body.setUserId(user.getString("user_id"));
            body.setNick(user.getString("nickname"));
            body.setAvatar(user.getString("picture"));
            body.setEmail(user.getString("email").isEmpty() ? null : user.getString("email"));
            String bodyStr = JSONObject.toJSONString(body);
            response = Unirest.post(backendUrl + "/user/add")
                    .header("content-type", "application/json")
                    .body(bodyStr)
                    .asString();
            JSONObject json = getJsonObject(response);
            if(json != null && !json.getBoolean("success")) {
                logger.error("将user信息保存到backend数据库中出错");
            }
        } catch (Exception e){
            logger.error("将user信息保存到backend数据库中出错", e);
            throw new Exception(e);
        }
    }

    private JSONObject getJsonObject(HttpResponse<String> response) {
        String body = response.getBody();
        JSONObject json = JSONObject.parseObject(body);
        return json;
    }
}
