package com.prim.prim_test.http

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.TextView
import cn.prim.http.lib_net.PrimHttp
import com.prim.prim_test.R
import com.prim.prim_test.http.modle.SearchModel
import kotlinx.android.synthetic.main.activity_base_http.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.w3c.dom.Text
import cn.prim.http.lib_net.callback.Callback as Callback1

class BaseHttpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_http)
        tv_get_request.onClick {
            PrimHttp.getInstance().get<SearchModel>("search")
                .params("keywords", "海阔天空")
                .tag(this)
                .enqueue(callback())
            tv_state.text
        }
    }

    //注意声明内部类inner 标记才能访问外部类的成员
    inner class callback : Callback1<SearchModel>() {

        override fun uploadProgress() {
        }

        override fun downloadProgress() {
        }

        override fun onStart() {
            tv_state.text = "开始请求网络"
        }

        override fun onSuccess(t: SearchModel?) {
            tv_state.text = "请求网络成功"
        }

        override fun onCacheSuccess() {
        }

        override fun onError() {
            tv_state.text = "请求网络失败"
        }

        override fun onFinish() {
        }


    }
}
