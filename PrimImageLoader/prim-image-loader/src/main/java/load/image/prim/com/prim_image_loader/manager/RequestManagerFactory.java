package load.image.prim.com.prim_image_loader.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc 获得一个RequestManager
 * @time 2018/8/23 - 下午11:39
 */
public class RequestManagerFactory implements Handler.Callback {

    RequestManager applicationManager;

    private static final String FRAG_TAG = "prim-fragment";

    private Map<FragmentManager, SupportRequestManagerFragment> supportFragments = new HashMap<>();

    private Handler H;

    private static final int REMOVE_SUPPOT_FRAGMENT = 1;

    public RequestManagerFactory() {
        H = new Handler(Looper.getMainLooper(), this);
    }

    /**
     * 给Application不能够进行生命周期的管理
     *
     * @return
     */
    private RequestManager getApplicationManager() {
        if (applicationManager == null) {
            applicationManager = new RequestManager(new ApplicationLifecycle());
        }
        return applicationManager;
    }

    /**
     * 获取RequestManager
     *
     * @param context
     * @return
     */
    public RequestManager get(Context context) {
        //判断context的类型
        if (!(context instanceof Application)) {
            if (context instanceof FragmentActivity) {//绑定fragment
                return get((FragmentActivity) context);
            } else if (context instanceof Activity) {//绑定Activity

            } else if (context instanceof ContextWrapper) {

            }
        }
        return getApplicationManager();
    }

    private RequestManager get(FragmentActivity fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        //查找是不是已经添加了一个fragment
        return supportFragmentGet(fragmentManager);
    }

    private RequestManager supportFragmentGet(FragmentManager fragmentManager) {
        //从fragmentManager 中查找 RequestManagerFragment
        SupportRequestManagerFragment fragment = getSupportRequestManagerFragment(fragmentManager);
        //将RequestManager 交给Fragment来保存
        RequestManager requestManager = fragment.getRequestManager();
        if (requestManager == null) {//如果RequestManager 为null就new一个如果RequestManager
            requestManager = new RequestManager(fragment.getMLifecycle());
            fragment.setRequestManager(requestManager);//设置到fragment中去
        }
        return requestManager;
    }

    private SupportRequestManagerFragment getSupportRequestManagerFragment(FragmentManager fm) {
        SupportRequestManagerFragment fragment = (SupportRequestManagerFragment) fm.findFragmentByTag(FRAG_TAG);
        if (fragment == null) {
            fragment = supportFragments.get(fm);
            if (null == fragment) {
                fragment = new SupportRequestManagerFragment();
            }
            //将新建的fragment加入到集合中
            supportFragments.put(fm, fragment);
            //FM 绑定fragment
            fm.beginTransaction().add(fragment, FRAG_TAG).commitAllowingStateLoss();//
            //通过handler发送移除集合消息
            //连续调用两次：
            //Glide.with(this).load(url_1).into(mImageView_1) --> m1
            //Glide.with(this).load(url_1).into(mImageView_1) --> m4
            //第一次创建Fragment 但是由于FM.add 不是commitAllowingStateLoss 就马上绑定上去了 而是通过handle去执行到 --> m2
            //由于Android中的主线程是一个闭环，通过handler发送消息到MessageQueue,然后通过Looper轮询获取消息并交给handler处理。
            //执行顺序是 m1 --> m2 --> m3  m2到消息并不会被马上处理 也就是说fragment并不会马上被绑定。此时执行m4
            //fm.findFragmentByTag(FRAG_TAG); 得到到fragment一定是空的，如果没有supportFragments 就会重新创建fragment
            //所以可能导致第二次调用仍然创建fragment
            //所以使用一个supportFragments 临时集合  等当前的fragment 都执行完后 最后才会执行这段代码，将FM移除临时集合
            H.obtainMessage(REMOVE_SUPPOT_FRAGMENT, fm).sendToTarget();// 移除集合消息 --> m3
        }
        return fragment;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case REMOVE_SUPPOT_FRAGMENT:
                FragmentManager manager = (FragmentManager) msg.obj;
                supportFragments.remove(manager);
                break;
        }
        return false;
    }
}
