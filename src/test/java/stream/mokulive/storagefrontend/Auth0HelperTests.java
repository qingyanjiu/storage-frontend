package stream.mokulive.storagefrontend;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import stream.mokulive.storagefrontend.utils.Auth0Helper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Auth0HelperTests {

	@Autowired
	private Auth0Helper auth0Helper;

	@Test
	public void getUserDetailTest() {
		try {
			String accessToken = auth0Helper.getAppAccessToken().getString("access_token");
			JSONObject userDetail = auth0Helper.getUserDetail("weibo|1479540023", accessToken);
			System.out.println(accessToken);
			System.out.println(userDetail);
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}

