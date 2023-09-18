package com.example.fintech.Oauth.Controller;

import com.example.fintech.Exception.BusinessException;
import com.example.fintech.Exception.ExMessage;
import com.example.fintech.Oauth.Entity.OauthToken;
import com.example.fintech.Oauth.Service.OauthService;
import com.example.fintech.Response.ResponseService;
import com.example.fintech.Response.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/oauth")
@Controller
@RequiredArgsConstructor
public class OauthController {

    @Value("${open_api.state}")
    String state;
    private ResponseService responseService;
    private OauthService oauthService;

    @GetMapping("/authorizeCode")
    public String requestAuthorizeCode(
            @RequestParam String userId,
            @RequestParam String userName,
            @RequestParam String userMobile
    ) {
        String url = "https://testapi.openbanking.or.kr/oauth/2.0/authorize" +
                "?response_type=code" +
                "&client_id=24340178-8da0-4bae-94fc-fc731cd5c200" +
                "&redirect_uri=http://localhost:8080/oauth/callback.html" +
                "&scope=login inquiry transfer" +
                "&client_info=" + userId + "-" + userName + "-" + userMobile +
                "&state=" + state +
                "&auth_type=0" +
                "&lang=kor" +
                "&cellphone_cert_yn=Y" +
                "&authorized_cert_yn=N" +
                "&account_hold_auth_yn=N";


        return "redirect:" + url;

    }
    @PostMapping("/callback.html")
    public SingleResult<OauthToken> requestToken(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "scope") String scope,
            @RequestParam(name = "client_info") String clientInfo,
            @RequestParam(name = "state") String state
    ) {
        try {
            OauthToken oauthToken = oauthService.saveAuthorizeToken(code, scope, clientInfo, state);
            return responseService.getSingleResult(oauthToken);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ExMessage.UNDEFINED_ERROR);
        }
    }

}
