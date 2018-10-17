package com.cxp.mybaselibrary.retrofit;

import android.content.Context;

import com.cxp.mybaselibrary.MyApplication;
import com.cxp.mybaselibrary.R;
import com.cxp.mybaselibrary.utils.Logger;
import com.cxp.mybaselibrary.utils.UIUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/5/19.
 */

public class RRetrofit {
    public static final String BASE_URL = "http://myqianyi.com:81/"; //正式线上环境
    public static final String BASE_URL_ = "http://myqianyi.com:81"; //正式线上环境
    private static Context mContext;
    private static Retrofit.Builder builder;
    private static int retryNum;
    private static int maxRetry = 10;
    private OkHttpClient httpClient;

    private RRetrofit(Context context) {
        mContext = context;
        //每次网络请求携带请求头(token上传，校验唯一登陆)
        httpClient = getHttpClient(context);
        builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient);
    }

    private RRetrofit(Context context, String basUrl) {
        mContext = context;
        //每次网络请求携带请求头(token上传，校验唯一登陆)
        httpClient = getHttpClient(context);
        builder = new Retrofit.Builder()
                .baseUrl(basUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient);
    }

    private static RRetrofit retrofit;

    public static RRetrofit getInstance(Context context) {
        if (retrofit == null) {
            synchronized (RRetrofit.class) {
                retrofit = new RRetrofit(context);
                return retrofit;
            }
        }
        return retrofit;
    }

    public static RRetrofit getInstance(Context context, String baseUrl) {
        if (retrofit == null) {
            synchronized (RRetrofit.class) {
                retrofit = new RRetrofit(context, baseUrl);
                return retrofit;
            }
        }
        return retrofit;
    }

    public ApiService getApiService() {
        Retrofit retrofit = builder.build();
        return retrofit.create(ApiService.class);
    }


    /**
     * 构造httpclient
     *
     * @return
     */
    private static OkHttpClient getHttpClient(final Context context) {

        //设置连接超时时间 ;
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new CookieManger(context))
//                .connectTimeout(20, TimeUnit.SECONDS)
//                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
//                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                                .addHeader("Accept-Encoding", "gzip, deflate")
//                                .addHeader("Connection", "keep-alive")
//                                .addHeader("Accept", "")
//                                .addHeader("TOKEN", token)
                        Response proceed = chain.proceed(request);
                        int code = proceed.code();
                        if (code == 500) {
                            MyApplication.mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    UIUtils.showToastShort(mContext, context.getString(R.string.text_server_error));
                                }
                            });
                        }
                        Logger.e("proceed=" + proceed);
                        return proceed;
                    }
                })
                .build();

        try {

            SSLContext sslContext = SSLContext.getInstance("SSL");

            sslContext.init(null, null, new SecureRandom());

            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            client.newBuilder().sslSocketFactory(sslSocketFactory, new X509TrustManager() {


                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }).hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return client;
    }

    public static <T> ObservableTransformer<T, T> setThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static void showServerError(Context mActivity) {
        UIUtils.showToastShort(mActivity, mActivity.getResources().getString(R.string.text_server_error));
    }
}
