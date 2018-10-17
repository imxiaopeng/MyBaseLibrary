package com.cxp.mybaselibrary.retrofit;


import com.cxp.mybaselibrary.base.BaseBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/5/19.
 */

public interface ApiService {
    String baseUrl = RRetrofit.BASE_URL;

    @POST("index.php?s=index/inface/marketnav")
    Observable<ResponseBody> test();

    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("index/Sys/maill")
    Observable<BaseBean> getEmailCode(@Field("email") String email);

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("index/Index/register")
    Observable<BaseBean> register(@Field("email") String eamil
            , @Field("tel") String tel
            , @Field("password") String password
            , @Field("password2") String password2
            , @Field("qrcode") String qrcode
            , @Field("emailcode") String emailcode
            , @Field("code") String code
            , @Field("regid") String regid
    );

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("index/Login/signin")
    Observable<BaseBean> signin(@Field("type") String type//type=1 邮箱或手机号+密码登录  type=2 手机+验证码
            , @Field("name") String name
            , @Field("password") String password
            , @Field("code") String code
            , @Field("regid") String regid
    );

    /**
     * 找回密码
     */
    @FormUrlEncoded
    @POST("index/Index/reset")
    Observable<BaseBean> resetPwd(@Field("uid") String uid
            , @Field("telemail") String telemail
            , @Field("password") String password
            , @Field("password2") String password2
            , @Field("code") String code
    );

}
