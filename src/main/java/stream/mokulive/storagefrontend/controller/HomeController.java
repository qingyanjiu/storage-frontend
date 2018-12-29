package stream.mokulive.storagefrontend.controller;

import com.auth0.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/storage")
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
        model.addAttribute("backendUrl", backendUrl);
        return "goods-manage";
    }

}
