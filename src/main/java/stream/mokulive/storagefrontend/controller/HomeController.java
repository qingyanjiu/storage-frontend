package stream.mokulive.storagefrontend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import stream.mokulive.storagefrontend.utils.AliyunOssHelper;
import stream.mokulive.storagefrontend.utils.Auth0User;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/storage")
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AliyunOssHelper aliyunOssHelper;

    @Value("${custom.backendUrl}")
    private String backendUrl;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    protected String home(Model model, final HttpServletRequest req) {
        Object user = SessionUtils.get(req, "user");
        model.addAttribute("user", user);
        model.addAttribute("backendUrl", backendUrl);
        return "view";
    }

    @RequestMapping(value = "/buildingManage", method = RequestMethod.GET)
    protected String buildingManage(Model model, final HttpServletRequest req) {
        Object user = SessionUtils.get(req, "user");
        model.addAttribute("user", user);
        model.addAttribute("backendUrl", backendUrl);
        return "building-manage";
    }

    @RequestMapping(value = "/hotPointManage", method = RequestMethod.GET)
    protected String hotPointManage(Model model, final HttpServletRequest req) {
        Object user = SessionUtils.get(req, "user");
        model.addAttribute("user", user);
        model.addAttribute("backendUrl", backendUrl);
        return "hotpoint-manage";
    }

    @RequestMapping(value = "/goodsManage", method = RequestMethod.GET)
    protected String goodsManage(Model model, final HttpServletRequest req) {
        Object user = SessionUtils.get(req, "user");
        model.addAttribute("user", user);
//        model.addAttribute("user", new Auth0User());
        model.addAttribute("backendUrl", backendUrl);
        return "goods-manage";
    }

    @RequestMapping(value = "/uploadGoodsImg", method = RequestMethod.POST)
    @ResponseBody
    protected Map uploadGoodsImg(MultipartFile file, HttpServletRequest req) {
        Map result = new HashMap();
        try {
            Auth0User user = JSONObject.toJavaObject((JSON) SessionUtils.get(req, "user")
                    ,Auth0User.class);
            String userId = user.getUserId();
            String fileUrl = aliyunOssHelper.uploadImg(file, userId, AliyunOssHelper.UPLOAD_TYPE_GOODS);
            result.put("success",true);
            result.put("fileUrl",fileUrl);
        } catch (IOException e) {
            result.put("success",false);
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/uploadBuildingImg", method = RequestMethod.POST)
    @ResponseBody
    protected Map uploadBuildingImg(MultipartFile file, HttpServletRequest req) {
        Map result = new HashMap();
        try {
            Auth0User user = JSONObject.toJavaObject((JSON) SessionUtils.get(req, "user")
                    ,Auth0User.class);
            String userId = user.getUserId();
            String fileUrl = aliyunOssHelper.uploadImg(file, userId, AliyunOssHelper.UPLOAD_TYPE_BUILDING);
            result.put("success",true);
            result.put("fileUrl",fileUrl);
        } catch (IOException e) {
            result.put("success",false);
            e.printStackTrace();
        }
        return result;
    }

}
