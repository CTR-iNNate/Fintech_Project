package com.example.fintech.Oauth.Service;

import com.example.fintech.Exception.BusinessException;
import com.example.fintech.Oauth.Dto.AuthorizeReqDto;
import com.example.fintech.Oauth.Dto.OauthTokenRes;
import com.example.fintech.Oauth.Entity.OauthToken;
import com.example.fintech.Oauth.Repository.OauthTokenRepository;
import com.example.fintech.RestTemplete.RestTemplateService;
import com.example.fintech.User.Dto.MemberDto;
import com.example.fintech.User.Repository.MemberRepository;
import com.example.fintech.User.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OauthService {

    private final OauthTokenRepository oauthTokenRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final RestTemplateService rest;

    @Value("${open_api.state}")
    private String openApiState;

    @Value("${oauth.client_id}")
    private String client_id;

    @Value("${oauth.client_secret}")
    private String client_secret;

    @Value("${oauth.redirect_uri}")
    private String redirect_uri;

    public AuthorizeReqDto createAuthorizeCode(Map<String, String> map) {
        return AuthorizeReqDto.builder()
                .code(map.get("code"))
                .clientInfo(map.get("client_info"))
                .scope(map.get("scope"))
                .state(map.get("state"))
                .build();
    }

    @Transactional
    public OauthToken saveAuthorizeToken(
            String code, String scope, String client_info, String state
    ) {
        String[] userInfos = client_info.split("-");
        String userId = userInfos[0];
        String userName = userInfos[1];
        String userMobile = userInfos[2];

        if (!state.equals(openApiState)) {
            throw new BusinessException("사전 정보 오류");
        }

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", client_id);
        params.add("client_secret", client_secret);
        params.add("redirect_uri", redirect_uri);
        params.add("grant_type", "authorization_code");

        ResponseEntity<OauthTokenRes> response = rest
                .postRestTemplate(
                        "oauth/2.0/token", MediaType.APPLICATION_FORM_URLENCODED,
                        MediaType.APPLICATION_JSON, params, OauthTokenRes.class
                );

        log.info("인가 코드 : " + code);
        log.info("응답 코드 : " + response.getStatusCode());

        if (response.getStatusCode() == HttpStatus.OK
                && response.getBody() != null
                && response.getBody().getAccess_token() != null) {
            OauthToken oauthToken = response.getBody().toEntity();
            oauthToken.setUserId(userId);
            oauthToken.setUserName(userName);
            oauthToken.setUserMobile(userMobile);
            oauthToken.setRegDate(LocalDateTime.now());

            if (!memberRepository.findByUserSeqNo(oauthToken.getUserSeqNo()).isPresent()) {
                memberService.saveUser(MemberDto.builder()
                        .userId(userId)
                        .userSeqNo(oauthToken.getUserSeqNo())
                        .userName(oauthToken.getUserName())
                        .userMobile(oauthToken.getUserMobile())
                        .build());
            }

            saveOauthToken(oauthToken);
            return oauthToken;
        }
        throw new BusinessException("응답 토큰 비어있음");
    }

    @Transactional
    public void saveOauthToken(OauthToken token) {
        try {
            Optional<OauthToken> userOpt = oauthTokenRepository.findByUserSeqNo(token.getUserSeqNo());
            if (userOpt.isPresent()) {
                OauthToken oauthToken = userOpt.get();
                oauthToken.setAccessToken(token.getAccessToken());
                oauthToken.setRefreshToken(token.getRefreshToken());
                oauthToken.setExpiresIn(token.getExpiresIn());
                oauthToken.setRegDate(LocalDateTime.now());
                oauthTokenRepository.save(oauthToken);
            } else {
                oauthTokenRepository.save(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }
}
