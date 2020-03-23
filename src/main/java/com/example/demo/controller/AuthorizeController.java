package com.example.demo.controller;

import com.example.demo.dto.AccesstTokenDTO;
import com.example.demo.dto.GithubUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @auther Allen
 * @date 2020/3/21 - 22:56
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String clientUri;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                            @RequestParam(name="state") String state,
                            HttpServletRequest request){
        AccesstTokenDTO accesstTokenDTO = new AccesstTokenDTO();
        accesstTokenDTO.setCode(code);
        accesstTokenDTO.setClient_id(clientId);
        accesstTokenDTO.setClient_secret(clientSecret);
        accesstTokenDTO.setRedirect_uri(clientUri);
        accesstTokenDTO.setState(state);
        githubProvider.getAccessToken(accesstTokenDTO);
        String accessToken=githubProvider.getAccessToken(accesstTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        if(user!=null){
            User user1 = new User();
            user1.setToken(UUID.randomUUID().toString());
            user1.setName(user.getName());
            user1.setAccountId(String.valueOf(user.getId()));
            user1.setGmtCreate(System.currentTimeMillis());
            user1.setGmtModified(user1.getGmtCreate());
            userMapper.Insert(user1);
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}
