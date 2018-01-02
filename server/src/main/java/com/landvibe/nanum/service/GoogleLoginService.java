package com.landvibe.nanum.service;

import com.landvibe.nanum.model.GoogleToken;
import com.landvibe.nanum.model.GoogleUser;
import com.landvibe.nanum.repository.GoogleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleLoginService {

    @Autowired
    GoogleUserRepository googleUserRepository;

    @Value("${login.google.client-id}")
    private String googleClientId;

    @Value("${login.google.client-secret}")
    private String googleClientSecret;

    @Value("${login.google.redirect-uri}")
    private String googleRedicrectUri;

    @Value("${login.google.grant-type}")
    private String googleGrantType;

    @Value("${login.google.oauth-endpoint}")
    private String googleOAuthEndPoint;

    @Value("${login.google.scope}")
    private String googleScope;

    @Value("${login.google.response-type}")
    private String googleResponseType;

    @Value("${login.google.approval-prompt}")
    private String googleApprovalPrompt;

    @Value("${login.google.access-type}")
    private String googleAccessType;

    @Value("${login.google.token-endpoint}")
    private String googleTokenEndpoint;

    @Value("${login.google.user-info-endpoint}")
    private String googleUserInfoEndpoint;

    @Value("${login.google.include-granted-scopes}")
    private String includeGrantedScopes;

    @Value("${login.google.state}")
    private String state;


    public String getUriForCode() {
        // 블로그에서 가져온 것. 됨
//        String uri = googleOAuthEndPoint + "?"
//                + "scope=" + googleScope
//                + "&redirect_uri=" + googleRedicrectUri
//                + "&response_type=" + googleResponseType
//                + "&client_id=" + googleClientId
//                + "&approval_prompt=" + googleApprovalPrompt;

        // google dev 에서 가져온 것. 됨
        String uri2 = googleOAuthEndPoint + "?"
                + "scope=" + googleScope
                + "&access_type=" + googleAccessType
                + "&include_granted_scopes=" + includeGrantedScopes
                + "&state=" + state
                + "&redirect_uri=" + googleRedicrectUri
                + "&response_type=" + googleResponseType
                + "&client_id=" + googleClientId;

        return uri2;
    }

    public String getUriForToken() {
        String uri = googleTokenEndpoint + "?"
                + "&client_id=" + googleClientId
                + "&client_secret=" + googleClientSecret
                + "&redirect_uri=" + googleRedicrectUri
                + "&grant_type=" + googleGrantType;

        return uri;
    }

    public String getUriForUserInfo() {
        String uri = googleUserInfoEndpoint;
        return uri;
    }

    public void login(String code){
        // 1. code 를 받아서 2. google 에 요청 보내서 token 받음
        RestTemplate restTemplate = new RestTemplate(); // 내부적으로 새로운 서버에 REST API 요청을 하기 위한 Rest Template 도구
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String uriForToken = this.getUriForToken() + "&code=" + code;
        GoogleToken googleToken = restTemplate.postForObject(uriForToken, "", GoogleToken.class);
        // 3. access_token을 다시 보내서 user 정보 받음
        String uriForUserInfo = this.getUriForUserInfo()
                + "?access_token=" + googleToken.getAccessToken();
        GoogleUser googleUser = restTemplate.getForObject(uriForUserInfo, GoogleUser.class);

        // 이미 로그인 한 기록이 있는 유저이면
        // //세션추가하고 넘억가기(세션에 들어가는 것: 유저정보, 토큰
        GoogleUser findUser = null;
        findUser = googleUserRepository.findBySnsId(googleUser.getSnsId());
        if(findUser != null){
            System.out.println("there is already exist!" + googleUser);
        } else {
            // 없으면
            // 저장
            googleUserRepository.save(googleUser);
        // //유저 추가 + 세션추가하고 넘어가기

        }
    }
}
