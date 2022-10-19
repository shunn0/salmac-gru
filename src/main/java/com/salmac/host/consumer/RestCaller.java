package com.salmac.host.consumer;

import com.salmac.host.entity.ScriptEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class RestCaller {

    @Autowired
    RestTemplate restTemplate;
    public ResponseEntity postMultiCMDAtTargetEngine(String targetURI, String cmds){
        targetURI = targetURI +"/runmulticmd";
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

    public ResponseEntity runScriptOnAgent(String agentBaseURI, ScriptEntity scriptEntity){
        final String URI = agentBaseURI+"/runscript";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("scriptType", scriptEntity.getScriptType().name());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<List> response = restTemplate.getForEntity(URI, List.class, map);
        return response;
    }
}
