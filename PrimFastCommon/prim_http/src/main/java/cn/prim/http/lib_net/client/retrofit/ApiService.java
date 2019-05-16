package cn.prim.http.lib_net.client.retrofit;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc 通用的API接口，不用再去写其他的API接口
 * 1.基础API，减少API冗余
 * 2.支持所有类型的方式访问网络
 * @time 2019/1/2 - 5:29 PM
 */
public interface ApiService {
    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, Object> params);

    @POST
    Observable<String> postBody(@Url String url, @Body RequestBody body);

    @FormUrlEncoded
    @PUT
    Observable<ResponseBody> put(@Url String url, @FieldMap Map<String, Object> params);

    @PUT
    Observable<String> putBody(@Url String url, @Body RequestBody body);

    @DELETE
    Observable<ResponseBody> delete(@Url String url, @QueryMap Map<String, Object> params);

    //下载是直接到内存,所以需要 @Streaming
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

    //上传文件 字符串等
    @Multipart
    @POST
    Observable<ResponseBody> uploadFile(@Url String url, @Part MultipartBody.Part file);

    @Multipart
    @POST
    Observable<ResponseBody> uploadFiles(@Url String url, @PartMap Map<String, ResponseBody> maps);

    @Multipart
    @POST
    Observable<ResponseBody> uploadFiles(@Url String url, @Part List<MultipartBody.Part> parts);
}
