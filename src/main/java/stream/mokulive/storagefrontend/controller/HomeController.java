package stream.mokulive.storagefrontend.controller;

import com.auth0.SessionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import stream.mokulive.storagefrontend.utils.Auth0User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/storage")
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    protected String home(Model model, final HttpServletRequest req) {
        Object user = SessionUtils.get(req, "user");
        model.addAttribute("user", user);
        return "view";
    }

    @RequestMapping(value = "/buildingManage", method = RequestMethod.GET)
    protected String buildingManage(Model model, final HttpServletRequest req) {
        Object user = SessionUtils.get(req, "user");
        model.addAttribute("user", user);
        return "building-manage";
    }

    @RequestMapping(value = "/hotPointManage", method = RequestMethod.GET)
    protected String hotPointManage(Model model, final HttpServletRequest req) {
        Object user = SessionUtils.get(req, "user");
        model.addAttribute("user", user);
        return "hotpoint-manage";
    }

    @RequestMapping(value = "/goodsManage", method = RequestMethod.GET)
    protected String goodsManage(Model model, final HttpServletRequest req) {
        Object user = SessionUtils.get(req, "user");
        model.addAttribute("user", user);
        return "goods-manage";
    }

}
