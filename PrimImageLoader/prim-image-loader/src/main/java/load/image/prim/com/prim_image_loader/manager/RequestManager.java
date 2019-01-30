package load.image.prim.com.prim_image_loader.manager;

import load.image.prim.com.prim_image_loader.request.Request;
import load.image.prim.com.prim_image_loader.request.RequestTrack;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/8/23 - 下午11:36
 */
public class RequestManager implements LifecycleListener {

    RequestTrack requestTrack;

    Lifecycle lifecycle;

    public RequestManager(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
        //注册生命周期回调监听
        lifecycle.addListener(this);
        requestTrack = new RequestTrack();
    }

    @Override
    public void onStart() {
        resumeRequest();

    }

    @Override
    public void onStop() {
        pauseRequest();
    }

    @Override
    public void onDestory() {
        lifecycle.removeListener(this);
        requestTrack.clearRequest();
    }

    public void pauseRequest() {
        requestTrack.pauseRequest();
    }

    public void resumeRequest() {
        requestTrack.resumeRequest();
    }

    public RequestBuilder load(String string) {
        return new RequestBuilder(this).load(string);
    }

    /**
     * 管理Request
     */
    public void track(Request request) {
        requestTrack.runRequest(request);
    }
}
