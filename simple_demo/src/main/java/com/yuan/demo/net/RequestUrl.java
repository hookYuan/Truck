package com.yuan.demo.net;

import com.yuan.demo.bean.LoginBean;
import java.util.Map;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
/**
 * Created by YuanYe on 2017/7/6.
 */

public interface RequestUrl {

    /**
     * retrofit使用注意事项：
     * 1、注意，baseUrl必须以'/'结束。
     * 2、使用@Field注解的时候，必须和@FormUrlEndcoded同时使用
     * 3、call 必须加上泛型
     * 4、对于Retrofit可以使用转化器，即addConverterFactory(ScalarsConverterFactory.create())
     * 添加Gson转化器，如果不想使用转化器，需要给Call返回加上泛型<ResponseBody>,这样就自己处理
     * ResponseBody.
     */
    @FormUrlEncoded
    @POST("{url}")
    Observable post(@Path("url") String url, @FieldMap Map map);

    @GET("{url}")
    Observable<LoginBean>  get(@Path("url") String url);


    @FormUrlEncoded
    @POST("{url}")
    Call<ResponseBody> postFile(@Path("url") String url);


    @Multipart
    @POST("rs/android/moblie/uploadAttchment")
    Observable<ResponseBody> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);

    /**
     * 使用一下方法可以给后台传输json数据
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头部
    @POST("TestApplication/servlet/ModifyUserInfo")
    Call<ResponseBody> putGson(@Body RequestBody route); //传入的参数为RequestBody
}
