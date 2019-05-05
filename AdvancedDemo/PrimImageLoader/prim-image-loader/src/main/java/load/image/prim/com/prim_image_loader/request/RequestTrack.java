package load.image.prim.com.prim_image_loader.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * @author prim
 * @version 1.0.0
 * @desc 用来对request进行操作
 * @time 2018/8/28 - 下午11:39
 */
public class RequestTrack {
    //多个request
    private Set<Request> requestSet = Collections.newSetFromMap(new WeakHashMap<Request, Boolean>());

    /**
     * 如果停止了请求，则Request不在执行，Request只有弱引用可能会被回收调
     * 如果继续了请求，用来防止停止掉到请求被回收
     */
    private List<Request> pendingRequests = new ArrayList<>();

    public void runRequest(Request request){

    }

    public void resumeRequest() {

    }

    public void pauseRequest() {

    }

    public void clearRequest() {

    }
}
