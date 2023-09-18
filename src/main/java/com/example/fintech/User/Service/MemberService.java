package com.example.fintech.User.Service;

import com.example.fintech.Exception.BusinessException;
import com.example.fintech.Exception.ExMessage;
import com.example.fintech.Oauth.Entity.OauthToken;
import com.example.fintech.Oauth.Repository.OauthTokenRepository;
import com.example.fintech.User.Dto.MemberDto;
import com.example.fintech.User.Dto.UserInfoAndAccountList;
import com.example.fintech.User.Entity.Member;
import com.example.fintech.User.Repository.MemberRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;




@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final OauthTokenRepository tokenRepository;
    private final MemberRepository memberRepository;

    public MemberDto findByUserSeqNo(Long userSeqNo) {
        Member member = memberRepository.findByUserSeqNo(userSeqNo)
                .orElseThrow(() -> new BusinessException(ExMessage.NOT_FOUND_ERROR));
        return member.toDto();
    }

    @Transactional
    public void saveUser(MemberDto reqDto) {
        if (!memberRepository.existsByUserSeqNo(reqDto.getUserSeqNo())) {
            memberRepository.save(reqDto.toEntity());
        }
    }

    @Transactional(readOnly = true)
    public UserInfoAndAccountList getUserInfo(String userId) {
        OauthToken oauthToken = tokenRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ExMessage.UNDEFINED_ERROR));

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(oauthToken.getAccessToken());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.exchange(
                "https://testapi.openbanking.or.kr/v2.0/user/me?user_seq_no={seq}",
                HttpMethod.GET,
                new HttpEntity(headers),
                String.class,
                oauthToken.getUserSeqNo()
        );

        return new Gson().fromJson(result.getBody(), UserInfoAndAccountList.class);
    }


}
