package stream.mokulive.storagefrontend.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.IdentityVerificationException;
import com.auth0.SessionUtils;
import com.auth0.Tokens;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import stream.mokulive.storagefrontend.utils.Auth0Helper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("unused")
@Controller
public class CallbackController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Auth0Helper auth0Helper;

    @Autowired
    private AuthController controller;
    private final String redirectOnFail;
    private final String redirectOnSuccess;

    public CallbackController() {
        this.redirectOnFail = "/login";
        this.redirectOnSuccess = "/storage/home";
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    protected void getCallback(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        handle(req, res);
    }

    @RequestMapping(value = "/callback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    protected void postCallback(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        handle(req, res);
    }

    private void handle(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Tokens tokens = controller.handle(req);
            SessionUtils.set(req, "accessToken", tokens.getAccessToken());
            SessionUtils.set(req, "idToken", tokens.getIdToken());

            String userId = auth0Helper.getUserId(tokens.getAccessToken());
            logger.info(userId+" 登录了");

            JSONObject user = updateUser(userId);
            if(user != null){
                SessionUtils.set(req,"user",JSONObject.toJavaObject(user,Map.class));
            }

            res.sendRedirect(redirectOnSuccess);
        } catch (IdentityVerificationException e) {
            logger.error("auth0认证出错", e);
            res.sendRedirect(redirectOnFail);
        } catch (UnirestException e) {
            logger.error("从auth0获取当前登录的用户id出错", e);
            res.sendRedirect(redirectOnFail);
        } catch (Exception e) {
            logger.error("更新用户信息出错", e);
            res.sendRedirect(redirectOnFail);
        }
    }

    private JSONObject updateUser(String userId) throws Exception{
        JSONObject result = null;
        JSONObject user = auth0Helper.checkUser(userId);
        if(user == null) {
            String accessToken = auth0Helper.getAppAccessToken();
            if (accessToken != null) {
                JSONObject userDetail = auth0Helper.getUserDetail(userId, accessToken);
                user = auth0Helper.createUser(userDetail);
            }
        }
        result = user;
        return result;
    }

}