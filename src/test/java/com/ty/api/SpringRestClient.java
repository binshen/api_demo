package com.ty.api;

import com.ty.api.entity.AuthToken;
import com.ty.api.entity.User;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class SpringRestClient {

    public static final String REST_SERVICE_URI = "http://localhost:8000/";

    public static final String AUTH_SERVER_URI = REST_SERVICE_URI + "oauth/token";

    public static final String QPM_PASSWORD_GRANT = "?grant_type=password&username=bob&password=abc123";

    public static final String QPM_ACCESS_TOKEN = "?access_token=";

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

    private static AuthToken sendTokenRequest(){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
        ResponseEntity<Object> response = restTemplate.exchange(AUTH_SERVER_URI+QPM_PASSWORD_GRANT, HttpMethod.POST, request, Object.class);
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
        }else{
            System.out.println("No user exist----------");
        }
        return token;
    }

    private static void listAllUsers(AuthToken token){
        Assert.notNull(token, "Authenticate first please......");

        System.out.println("\nTesting listAllUsers API-----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI+"/userList/"+QPM_ACCESS_TOKEN+token.getAccess_token(), HttpMethod.GET, request, List.class);
        List<LinkedHashMap<String, Object>> usersMap = (List<LinkedHashMap<String, Object>>)response.getBody();
        if(usersMap!=null){
            System.out.println("Call listAllUsers:");
            for(LinkedHashMap<String, Object> map : usersMap){
                System.out.println("User : id="+map.get("id")+", UserName="+map.get("username")+", Password="+map.get("password"));;
            }
        }else{
            System.out.println("No user exist----------");
        }
    }

    private static void getUser(AuthToken token){
        Assert.notNull(token, "Authenticate first please......");
        System.out.println("\nTesting getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI+"/user/1"+QPM_ACCESS_TOKEN+token.getAccess_token(), HttpMethod.GET, request, User.class);
        User user = response.getBody();
        System.out.println("Call getUser => " + user.getUsername());
    }

    public static void main(String args[]){
        AuthToken token = sendTokenRequest();
        listAllUsers(token);
        getUser(token);
    }
}
