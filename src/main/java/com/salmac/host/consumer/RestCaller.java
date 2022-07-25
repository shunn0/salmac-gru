package com.salmac.host.consumer;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.Inet4Address;
import java.time.LocalDateTime;

public class RestCaller {


    public static ResponseEntity  postMulticmdAtTargetEngine(String targetURI, String cmds){
        targetURI = targetURI +"/runmulticmd";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("cmds", cmds);
        System.out.println("#############"+cmds+"#############");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity response = restTemplate.postForEntity(targetURI, request, String.class);
        System.out.println("##############"+targetURI);
        System.out.println(response.getStatusCodeValue()+"###############"+response.getBody());
        return response;
    }
}
