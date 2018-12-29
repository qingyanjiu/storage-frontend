package stream.mokulive.storagefrontend.controller;

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

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ResponseBody
    protected Map uploadImg(MultipartFile file, HttpServletRequest req) {
        Map result = new HashMap();
        try {
            Auth0User user = (Auth0User) SessionUtils.get(req, "user");
            String userId = user.getUserId();
//            String userId = "weibo|1479540023";
            String fileUrl = aliyunOssHelper.simpleUpload(file, userId);
            result.put("success",true);
            result.put("fileUrl",fileUrl);
        } catch (IOException e) {
            result.put("success",false);
            e.printStackTrace();
        }
        return result;
    }

}
