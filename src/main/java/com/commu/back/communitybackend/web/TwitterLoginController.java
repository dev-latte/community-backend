package com.commu.back.communitybackend.web;

import com.commu.back.communitybackend.domain.social.User;
import com.commu.back.communitybackend.service.social.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TwitterLoginController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static String clientId, clientSecret, callbackUri;

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;


    @PostConstruct
    private void init() {
        clientId = env.getProperty("twitter.consumerKey");
        clientSecret = env.getProperty("twitter.consumerSecret");
        callbackUri = env.getProperty("twitter.callbackUri");
    }

    @GetMapping(value = "/login/twitter")
    public ResponseEntity twitter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OAuth1Operations operations = new TwitterConnectionFactory(clientId, clientSecret).getOAuthOperations();
        OAuthToken oAuthToken = operations.fetchRequestToken(callbackUri, null);
        String authenticationUrl = operations.buildAuthenticateUrl(oAuthToken.getValue(), new OAuth1Parameters());
        request.getServletContext().setAttribute("token", oAuthToken);
        return new ResponseEntity(authenticationUrl, HttpStatus.OK);
//        response.sendRedirect(authenticationUrl);
    }

    @GetMapping(value = "/twitter/complete")
    public void twitterComplete(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "oauth_token") String oauthToken, @RequestParam(name = "oauth_verifier") String oauthVerifier) throws IOException {
        Connection<Twitter> connection = getAccessTokenToConnection(request, oauthVerifier);
        Map<String, String> userMap = getUserInfoMAp(connection);
        setAuthentication(userMap);
        saveUser(connection, userMap);
        response.sendRedirect("http://localhost:3000");
    }

    private Connection<Twitter> getAccessTokenToConnection(HttpServletRequest request, @RequestParam(name = "oauth_verifier") String oauthVerifier) {
        TwitterConnectionFactory twitterConnectionFactory = new TwitterConnectionFactory(clientId, clientSecret);
        OAuth1Operations operations = twitterConnectionFactory.getOAuthOperations();
        OAuthToken requestToken = (OAuthToken) request.getServletContext().getAttribute("token");
        request.getServletContext().removeAttribute("token");
        OAuthToken accessToken = operations.exchangeForAccessToken(new AuthorizedRequestToken(requestToken, oauthVerifier), null);

        return twitterConnectionFactory.createConnection(accessToken);
    }

    private Map<String, String> getUserInfoMAp(Connection<Twitter> connection) {
        Map<String, String> result = new HashMap<>();
        String userPrincipal = connection.getKey().getProviderUserId();
        String userName = connection.getDisplayName();
        result.put("name", userName);
        result.put("id", userPrincipal);

        return result;
    }

    private void setAuthentication(Map<String, String> map) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(map.get("id"),
                "N/A", Arrays.asList(new GrantedAuthority[]{new SimpleGrantedAuthority("twitter")}));
        authenticationToken.setDetails(map);
        OAuth2Request oAuth2Request = new OAuth2Request(null, map.get("id"), null, true, null, null, null, null, null);
        Authentication authentication = new OAuth2Authentication(oAuth2Request, authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void saveUser(Connection<Twitter> connection, Map<String, String> map){
        logger.debug(connection.getDisplayName());
        if(userService.isNotExistUser(map.get("id"))){
            userService.saveUser(User.builder()
                    .userPrincipal(map.get("id"))
                    .userImage(connection.getImageUrl())
                    .displayName(connection.getDisplayName())
                    .build());
        }
    }

}
