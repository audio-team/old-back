package org.soundwhere.backend.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RestTemplateController {
    private final RestTemplate restTemplate;

    @Value("${server.port}")
    private String selfPort;

    public RestTemplateController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /***********HTTP GET method*************/
    @RequestMapping(value = "/testGetAction", method = RequestMethod.GET)
    public Object getJson() {
        JSONObject json = new JSONObject();
        json.put("username", "tester");
        json.put("pwd", "123456748");
        return json;
    }

    @RequestMapping(value = "/getApi", method = RequestMethod.GET)
    public String testGet() {
        String url = "http://localhost:" + selfPort + "/testGetAction";
        JSONObject json = restTemplate.getForEntity(url, JSONObject.class).getBody();
        return json.toJSONString();
    }

    /**********HTTP POST method**************/
    @RequestMapping(value = "/testPostAction", method = RequestMethod.POST)
    public Object postJson(@RequestBody JSONObject param) {
        System.out.println(param.toJSONString());
        param.put("action", "post");
        param.put("username", "tester");
        param.put("pwd", "123456748");
        return param;
    }

    @RequestMapping(value = "/postApi", method = RequestMethod.POST)
    public Object testPost() {
        String url = "http://localhost:" + selfPort + "/testPostAction";
        JSONObject postData = new JSONObject();
        postData.put("descp", "request for post");
        JSONObject json = restTemplate.postForEntity(url, postData, JSONObject.class).getBody();
        return json.toJSONString();
    }

    @RequestMapping(value = "/getTag", method = RequestMethod.GET)
    public Object getTag() {
        String url = "http://127.0.0.1:5000/getTag?path={path}";
//        HttpHeaders headers = new HttpHeaders();
//        //根据 headers 的类型不同，ContentType 也不同
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//
//
//        MultiValueMap<String, String> postData = new LinkedMultiValueMap<>();
//        postData.add("path","D:/school/study/four/try/common_voice_zh-CN_18527374.mp3");
//        String postData="path=D:\\school\\study\\four\\毕设\\pythonProject\\data\\common_voice_zh-CN_18527375.mp3";
//        JSONObject json=restTemplate.postForObject(url,postData,JSONObject.class);
//        HttpEntity<MultiValueMap<String, String>> entity= new HttpEntity<>(postData, headers);
        Map map = new HashMap();
        map.put("path", "D:/school/study/four/try/common_voice_zh-CN_18527374.mp3");
        String res = restTemplate.getForObject(url, String.class, map);
        //String res=restTemplate.postForObject(url,entity,String.class);
        System.out.println(res);
        return res;
    }

}
