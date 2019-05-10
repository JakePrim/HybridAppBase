package cn.prim.http.lib_net.cache;

import android.text.TextUtils;
import cn.prim.http.lib_net.utils.PrimHttpLog;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.ResponseBody;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-05-10 - 15:32
 */
public class PrimCache {

    private static final String TAG = "PrimCache";

    public PrimCache() {

    }



    public Observable<ResponseBody> getCache() {
        return Observable.create(new ObservableOnSubscribe<ResponseBody>() {
            @Override
            public void subscribe(ObservableEmitter<ResponseBody> emitter) throws Exception {
//                String cache = CacheManage.getInstance().get("cache");
//                if (!TextUtils.isEmpty(cache)) {
//                    PrimHttpLog.e(TAG, "subscribe: 读取缓存数据 -》" + cache);
//                    emitter.onNext(cache);
//                    //这里可以更具设置的缓存时长 超过了缓存的时长 请求网络 更新缓存数据 更新UI
//                    emitter.onComplete();
//                } else {
//                    PrimHttpLog.e(TAG, "subscribe: 没有缓存请求网络");
//                    emitter.onComplete();
//                }
            }
        });
    }


}
