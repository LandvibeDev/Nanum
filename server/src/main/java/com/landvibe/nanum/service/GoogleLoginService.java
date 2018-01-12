package com.landvibe.nanum.service;

import com.landvibe.nanum.model.GoogleToken;
import com.landvibe.nanum.model.GoogleUser;
import com.landvibe.nanum.model.User;
import com.landvibe.nanum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.Encoder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class GoogleLoginService {

    @Autowired
    UserRepository userRepository;

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

//    @Value("${login.google.scope-list}")
//    private String[] googleScopeList;

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


    public String getUriForCode() throws UnsupportedEncodingException {
        // 블로그에서 가져온 것. 됨
//        String uri = googleOAuthEndPoint + "?"
//                + "scope=" + googleScope
//                + "&redirect_uri=" + googleRedicrectUri
//                + "&response_type=" + googleResponseType
//                + "&client_id=" + googleClientId
//                + "&approval_prompt=" + googleApprovalPrompt;

//        StringBuffer googleScope = new StringBuffer();
//        for (int i=0; i<googleScopeList.length; i++){
//            googleScope.append(googleScopeList[i] + " ");
//        }
//        String googleScope = "https://www.googleapis.com/auth/plus.login" + " "
//                + "https://www.googleapis.com/auth/plus.me";
//        String encodedScope = URLEncoder.encode(googleScope, "utf-8");

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

    public void login(String code, HttpSession session, CookieGenerator cookieGenerator, HttpServletResponse response)
            throws UnsupportedEncodingException {
        // Get code from google api server
        RestTemplate restTemplate = new RestTemplate(); // template for REST API request
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String uriForToken = this.getUriForToken() + "&code=" + code;

        // Get access_token, id_token, refresh_token(only once) from google api server using code
        GoogleToken googleToken = restTemplate.postForObject(uriForToken, "", GoogleToken.class);

        // Get user information from google api server using access_token
        String uriForUserInfo = this.getUriForUserInfo()
                + "?access_token=" + googleToken.getAccessToken();
//        GoogleUser googleUser = restTemplate.getForObject(uriForUserInfo, GoogleUser.class);
        User loginUser = restTemplate.getForObject(uriForUserInfo, GoogleUser.class);

        // Find if user already exists
        User user = null;
        user = userRepository.findByEmail(loginUser.getEmail());
        if (user == null) {
            user = new User();
            user.setUsername(loginUser.getUsername());
            user.setEmail(loginUser.getEmail());
            user.setSnsId(loginUser.getSnsId());
            user.setProfileImage(loginUser.getProfileImage());
            userRepository.save(user);
//            userRepository.saveAndFlush(user);
        }
        session.setAttribute("userId", loginUser.getId());
//        session.setAttribute("username", googleUser.getUsername());
        session.setAttribute("user", user);

//        cookieGenerator.setCookieName("sessionId");
//        cookieGenerator.addCookie(response, session.getId());
        cookieGenerator.setCookieName("isLogin");
        cookieGenerator.addCookie(response, "true");
        cookieGenerator.setCookieName("username");
        cookieGenerator.addCookie(response, URLEncoder.encode(loginUser.getUsername(), "utf-8"));
        cookieGenerator.setCookieMaxAge(600);
    }
}
