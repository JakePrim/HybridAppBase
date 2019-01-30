package load.image.prim.com.prim_image_loader.manager;

import android.support.v4.app.Fragment;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/8/23 - 下午11:49
 */
public class SupportRequestManagerFragment extends Fragment {

    RequestManager requestManager;

    //生命周期管理类  回调给所有注册的生命周期的接口
    private ActivityFragmentLifecycle lifecycle;

    public SupportRequestManagerFragment() {
        lifecycle = new ActivityFragmentLifecycle();
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }

    public void setRequestManager(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public ActivityFragmentLifecycle getMLifecycle() {
        return lifecycle;
    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycle.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        lifecycle.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        lifecycle.onDestory();
    }
}
