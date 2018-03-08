package com.w77996.seckill.service;

import com.w77996.seckill.dao.UserDao;
import com.w77996.seckill.domain.User;
import com.w77996.seckill.exception.GlobalException;
import com.w77996.seckill.redis.RedisService;
import com.w77996.seckill.redis.UserPrefixKey;
import com.w77996.seckill.result.CodeMsg;
import com.w77996.seckill.utils.MD5Util;
import com.w77996.seckill.utils.UUIDUtil;
import com.w77996.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {
    public static final String COOKI_NAME_TOKEN = "token";

    @Autowired
    private UserDao userDao;

    @Autowired
    RedisService redisService;

    public User getById(long id){
        return userDao.getById(id);
    }

    public User getByToken(HttpServletResponse response,String token){
        if(StringUtils.isEmpty(token)){
            return null;
        }
        User user = redisService.get(UserPrefixKey.token,token,User.class);
        if(user != null){
            addCookie(response,token,user);
        }
        return user;
    }

    public boolean login(HttpServletResponse response,LoginVo loginVo){
        if(loginVo == null){
            throw  new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();

        User user =  getById(Long.parseLong(mobile));
        if(user == null){
            throw  new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass,saltDB);
        if(!calcPass.equals(dbPass)){
           throw  new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUIDUtil.uuid();
        addCookie(response,token,user);
        return true;
    }
    private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set(UserPrefixKey.token,token,user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN,token);
        cookie.setMaxAge(UserPrefixKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
