import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_webview_plugin/flutter_webview_plugin.dart';

const CATCH_URLS = ['m.ctrip.com/', 'm.ctrip.com/html5/', 'm.ctrip.com/html5'];

class WebView extends StatefulWidget {
  final String url;
  final String title;
  final String statusBarColor;
  final bool hideAppBar;
  final bool backForbid;

  const WebView(
      {Key key,
      this.url,
      this.title,
      this.statusBarColor,
      this.hideAppBar,
      this.backForbid = false})
      : super(key: key);

  @override
  _WebViewState createState() => _WebViewState();
}

class _WebViewState extends State<WebView> {
  final webviewReference = FlutterWebviewPlugin();

  StreamSubscription<String> _onUrlChanged;
  StreamSubscription<WebViewStateChanged> _onStateChanged;
  StreamSubscription<WebViewHttpError> _onHttpError;

  bool exiting = false;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    webviewReference.close(); //关闭页面 防止重新打开
    //URL 改变的监听
    _onUrlChanged = webviewReference.onUrlChanged.listen((url) {});

    //监听加载状态
    _onStateChanged = webviewReference.onStateChanged.listen((state) {
      switch (state.type) {
        case WebViewState.startLoad: //开始加载
          if (_isToMain(state.url) && !exiting) {
            if (widget.backForbid) {
              webviewReference.launch(widget.url);
            } else {
              Navigator.pop(context);
              exiting = true;
            }
          }
          break;
        default:
          break;
      }
    });

    //监听错误状态
    _onHttpError = webviewReference.onHttpError.listen((error) {
      print(error);
    });
  }

  _isToMain(String url) {
    bool contain = false;
    for (final value in CATCH_URLS) {
      if (url?.endsWith(value) ?? false) {
        contain = true;
        break;
      }
    }
    return contain;
  }

  @override
  void dispose() {
    _onUrlChanged.cancel();
    _onStateChanged.cancel();
    _onHttpError.cancel();
    webviewReference.dispose();
    super.dispose();

    ///销毁web view
  }

  @override
  Widget build(BuildContext context) {
    String statusBorColor = widget.statusBarColor ?? 'ffffff';
    Color backColor;
    if (statusBorColor == 'ffffff') {
      backColor = Color(0xff000000);
    } else {
      backColor = Color(0xffffffff);
    }
    return Scaffold(
      body: Column(
        children: <Widget>[
          _appBar(Color(int.parse('0xff' + statusBorColor)), backColor),
          //撑满整个界面
          Expanded(
              //撑满整个界面
              child: WebviewScaffold(
            url: widget.url,
            //加载的URL
            withZoom: true,
            //允许缩放
            withLocalStorage: true,
            //本地缓存
            hidden: true,
            //默认状态隐藏
            initialChild: Container(
              color: Colors.white,
              child: Center(
                child: Text('Wiating...'),
              ),
            ), //设置初始化界面
          ))
        ],
      ),
    );
  }

  _appBar(Color backgroundColor, Color backColor) {
    if (widget.hideAppBar ?? false) {
      return Container(
        color: backgroundColor,
        height: 30,
      );
    }
    return Container(
      color: backgroundColor,
      padding: EdgeInsets.fromLTRB(0, 40, 0, 10),
      child: FractionallySizedBox(
        //FractionallySizedBox设置撑满整个屏幕的宽度或者高度
        widthFactor: 1, //设置宽度的撑满
        child: Stack(
          children: <Widget>[
            GestureDetector(
              child: Container(
                margin: EdgeInsets.only(left: 10),
                child: Icon(
                  Icons.close,
                  size: 26,
                  color: backColor,
                ),
              ),
              onTap: () {
                Navigator.of(context).pop();
              },
            ),
            Positioned(
                left: 0,
                right: 0,
                child: Center(
                  child: Text(
                    widget.title ?? '',
                    style: TextStyle(
                      color: backColor,
                      fontSize: 20,
                    ),
                  ),
                )), //绝对定位
          ],
        ),
      ),
    );
  }
}
