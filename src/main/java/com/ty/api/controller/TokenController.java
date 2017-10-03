package com.ty.api.controller;

import com.ty.api.entity.AuthToken;
import com.ty.api.entity.Role;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedHashMap;

@Controller
public class TokenController {

    private static final String AUTH_SERVER_URI = "http://localhost:8000/oauth/token?grant_type=password&";

    @ResponseBody
    @RequestMapping(value = "/oauth/token/{username}/{password}", method = RequestMethod.GET)
    public AuthToken get_auth_token(@PathVariable("username") String username, @PathVariable("password") String password) {

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
        ResponseEntity<Object> response = restTemplate.exchange(AUTH_SERVER_URI+"username="+username+"&password="+password, HttpMethod.POST, request, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();
        AuthToken token = null;
        if(map!=null){
            token = new AuthToken();
            token.setAccess_token((String)map.get("access_token"));
            token.setToken_type((String)map.get("token_type"));
            token.setRefresh_token((String)map.get("refresh_token"));
            token.setExpires_in((Integer)map.get("expires_in"));
            token.setScope((String)map.get("scope"));
            System.out.println(token);
        }
        return token;
    }

    private static HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static HttpHeaders getHeadersWithClientCredentials(){
        String plainClientCredentials="my-trusted-client:secret";
        String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
        HttpHeaders headers = getHeaders();
        headers.add("Authorization", "Basic " + base64ClientCredentials);
        return headers;
    }
}
