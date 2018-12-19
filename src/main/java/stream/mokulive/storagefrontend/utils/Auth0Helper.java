package stream.mokulive.storagefrontend.utils;

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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public HttpResponse getUserId(String accessToken) {
        //从auth0获取当前登录的用户id
        HttpResponse<String> response = null;
        try {
            response = Unirest.post("https://mokulive.auth0.com/oauth/token")
                    .header("content-type", "application/json")
                    .body("{\"client_id\":\"" + clientId + "\",\"client_secret\":\"" + clientSecret + "\",\"audience\":\"https://mokulive.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}")
                    .asString();
        } catch (UnirestException e) {
            logger.error("从auth0获取当前登录的用户id出错", e);
        }
        return response;
    }

    public HttpResponse getAppAccessToken() {
        //获取access token
        HttpResponse<String> response = null;
        try {
            response = Unirest.post("https://mokulive.auth0.com/oauth/token")
                    .header("content-type", "application/json")
                    .body("{\"client_id\":\"" + clientId + "\",\"client_secret\":\"" + clientSecret + "\",\"audience\":\"https://mokulive.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}")
                    .asString();
        } catch (UnirestException e) {
            logger.error("获取access token出错", e);
        }
        return response;
    }

    public HttpResponse getUserDetail(String userId, String accessToken) {
        //获取access token
        HttpResponse<String> response = null;
        try {
            response = Unirest.post("https://mokulive.auth0.com/api/v2/users/" + userId)
                    .header("authorization", "Bearer " + accessToken)
                    .asString();
        } catch (UnirestException e) {
            logger.error("获取access token出错", e);
        }
        return response;
    }
}
