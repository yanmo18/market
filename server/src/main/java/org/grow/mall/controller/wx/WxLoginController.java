package org.grow.mall.controller.wx;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import okhttp3.*;
import okhttp3.ResponseBody;
import org.grow.mall.common.Constants;
import org.grow.mall.common.Result;
import org.grow.mall.controller.dto.UsersDTO;
import org.grow.mall.entity.Users;
import org.grow.mall.exception.ServiceException;
import org.grow.mall.service.IUsersService;
import org.grow.mall.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/wx/login")
public class WxLoginController {

    @Autowired
    private IUsersService usersService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
    private String wxUrl = "https://api.weixin.qq.com/sns/jscode2session";
    private String wxAccessKey = "wx763df0d493c0d850";
    private String wxSecretKey = "aa8ab1a83c1c4f488efb7b203bfa945a";
    private String wxjwtSecret = "abcdefghijklmn";

    @PostMapping("/byCode")
    public Result login(@RequestParam("code") String code) {
        System.out.println("微信登录获取的临时code:" + code);
        String openid;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(wxUrl).newBuilder();
        urlBuilder.addQueryParameter("appid", wxAccessKey);
        urlBuilder.addQueryParameter("secret", wxSecretKey);
        urlBuilder.addQueryParameter("js_code", code);
        urlBuilder.addQueryParameter("grant_type", "authorization_code");
        String url = urlBuilder.build().toString();
        System.out.println("build url " + url);
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url)
                .get()
                .build();

        Call call = okHttpClient.newCall(request);
        Response response;
        try {
            response = call.execute();
            ResponseBody body = response.body();
            Map map = objectMapper.readValue(body.charStream(), Map.class);
            openid = (String) map.get("openid");
            System.out.println("远程调用获取的openId：" + openid);


        } catch (IOException e) {
            System.out.println("远程调用获取openId失败");
            openid = "";
        }
        ;

        if (!StringUtils.hasText(openid)) {
            throw new ServiceException(Constants.CODE_600, "获取openid 失败");
        }
        System.out.println("wx - uid : " + openid);
//        此处可能不止一张表 跟注册初始化逻辑差不多
//        UserInfo userInfo = userInfoService.forceGet(openid);
//        Company company = companyService.forceGet(openid);
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", openid);
        Users users = usersService.getOne(queryWrapper);
        if (users == null) {
//           表示初次wx登录，直接添加即可。
            System.out.println("当前wx用户是初次登录本小程序" + openid);
            Users u = new Users();
            u.setUsername(openid);
            u.setPassword("fromWX");
            u.setRealname("WX某用户");
            u.setMoney(1000f);
            u.setPhoto("https://images.unsplash.com/photo-1517841905240-472988babdf9?w=200&h=200&fit=crop&crop=face");
            usersService.save(u);
            users = u;
        }
        System.out.println("当前登录用户的内容是：" + users);


//
//
        String token = TokenUtils.genToken(String.valueOf(users.getId()), wxjwtSecret);
        System.out.println("生成的token：" + token);
        UsersDTO usersDTO = new UsersDTO();
        BeanUtil.copyProperties(users, usersDTO, true);
        usersDTO.setToken(token);
        usersDTO.setPassword(null);
        return Result.success(usersDTO);
    }

    @GetMapping("/currentUID")
    public Result currentUID() {
        Users currentWxUsers = TokenUtils.getCurrentWxUsers();

        System.out.println("----测试登录完整性当前用户--" + currentWxUsers);
        return Result.success(currentWxUsers.getId());
    }

    @PostMapping("/modifyUsers")
    public Result modifyUsers(@RequestBody Users users) {
        Users currentWxUsers = TokenUtils.getCurrentWxUsers();
        Integer uid = currentWxUsers.getId();
        Users fromDb = usersService.getById(uid);
        fromDb.setRealname(users.getRealname());
        fromDb.setPhone(users.getPhone());
        fromDb.setAge(users.getAge());
        usersService.saveOrUpdate(fromDb);
        return Result.success(fromDb);

    }
}
