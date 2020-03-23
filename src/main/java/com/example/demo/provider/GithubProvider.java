package com.example.demo.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.AccesstTokenDTO;
import com.example.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @auther Allen
 * @date 2020/3/21 - 23:02
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccesstTokenDTO accesstTokenDTO){
       MediaType mediaType= MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accesstTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/authorize")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string= response.body().string();
            System.out.println(string);
            return string;
        } catch (IOException e) {
        }
        return null;
    }
    public GithubUser getUser(String accesstToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.github.com/user?access_token="+accesstToken).build();
        try {
            Response response = client.newCall(request).execute();
            String string =response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
            return null;
        }
    }
