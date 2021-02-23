package com.prim_player_cc.cover_cc.event;

/**
 * @author prim
 * @version 1.0.0
 * @desc Cover 视图发送的事件码 通过该事件码 和解码器进行桥接
 * @time 2018/7/26 - 下午3:42
 */
public class CoverEventCode {
    public static final int COVER_EVENT_PAUSE = 800;

    //手动触发暂停
    public static final int COVER_EVENT_MANUAL_PAUSE = 618;

    public static final int SEND_COVER_MANUAL_PAUSE = 978;

    public static final int COVER_EVENT_RESUME = 682;

    public static final int COVER_EVENT_RESET = 780;

    public static final int COVER_EVENT_SEEK = 819;

    public static final int COVER_EVENT_STOP = 674;

    public static final int COVER_EVENT_START = 267;

    //自动播放
    public static final int COVER_EVENT_AUTO_START = 268;

    //点击下载按钮
    public static final int COVER_EVENT_DOWNLOAD = 394;

    public static final int COVER_EVENT_FULL_SCREEN = 245;

    public static final int COVER_EVENT_VERTICAL_SCREEN = 845;

    public static final int COVER_EVENT_VERTICAL_FULL_SCREEN = 493;

    public static final int COVER_EVENT_EXIT_VERTICAL_FULL_SCREEN = 491;

    public static final int COVER_EVENT_CANCEL_AUTO_PLAY_NEXT = 500;

    //播放下一个
    public static final int COVER_EVENT_START_PLAY_NEXT = 361;

    public static final int COVER_EVENT_VIDEO_MORE_INFO = 501;

    public static final int COVER_EVENT_NET_CHANGE = 367;

    public static final int COVER_EVENT_CLOSE_ALL = 239;

    public static final int COVER_EVENT_COUNT = 290;

    //静音
    public static final int COVER_EVENT_MUTE = 783;

    //有声
    public static final int COVER_EVENT_SOUND = 421;

    public static final int COVER_EVENT_ERROR = 908;

    //重新设置资源
    public static final int COVER_EVENT_RESET_SOURCE = 604;

    //分享
    public static final int COVER_EVENT_SHARE = 625;

    //评论
    public static final int COVER_EVENT_COMMENT = 961;

    //点赞
    public static final int COVER_EVENT_LIKE = 3671;

    //seek 开始渲染视频
    public static final int COVER_EVENT_SEEK_RENDERING_START = 643;

    //seek 开始跳转
    public static final int COVER_EVENT_SEEK_LOADING_START = 64;

    public static final int COVER_EVENT_SHORT_CUT = 901;

    //竖屏视频的发送标志
    public static final int COVER_EVENT_SMALL_TYPE = 723;

    //普通视频的发送标志
    public static final int COVER_EVENT_COMMON_TYPE = 724;

    //小窗播放完成视图 - 通信码
    public static final int COVER_EVENT_FINISH_1 = 725;

    //小窗切换为普通视频 - 通信码
    public static final int COVER_EVENT_FINISH_2 = 726;

    //推荐视频列表
    public static final int COVER_ADD_RECOMMEND_VIDEO = 727;

    //发送高斯模糊图片
    public static final int COVER_ADD_BLUR = 728;

    public static final int COVER_FEEDBACK_POPUP = 729;

    public static final int COVER_FEEDBACK_CLICK = 730;

}
