import 'package:flutter/material.dart';

///语音识别页面
class SpeakPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _SpeakPageState();
  }
}

class _SpeakPageState extends State<SpeakPage>
    with SingleTickerProviderStateMixin {
  String speakText = '按住说话';
  Animation<double> animation;
  AnimationController controller;

  @override
  void initState() {
    controller = AnimationController(
        vsync: this, duration: Duration(milliseconds: 1000));
    animation = CurvedAnimation(parent: controller, curve: Curves.easeIn)
      ..addStatusListener((status) {
        if (status == AnimationStatus.completed) {
          controller.reverse(); //循环执行
        } else if (status == AnimationStatus.dismissed) {
          controller.forward();//开始执行
        }
      });
    super.initState();
  }

  @override
  void dispose() {
    controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Padding(
      padding: EdgeInsets.all(30),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: <Widget>[
          _topItem(),
          _bottomItem(),
        ],
      ),
    ));
  }

  _topItem() {
    return Center(
      child: Text('你可以这样说'),
    );
  }

  _bottomItem() {
    return FractionallySizedBox(
      widthFactor: 1, //水平方向填满屏幕的宽度
      child: Stack(
        //
        children: <Widget>[
          GestureDetector(
            onTapDown: _onSpeakStart(),
            onTapCancel: _onSpeakStop(),
            onTapUp: _onSpeakStop(),
            child: Center(
              child: Column(
                children: <Widget>[
                  Padding(
                    padding: EdgeInsets.all(10),
                    child: Text(
                      speakText,
                      style: TextStyle(
                        color: Colors.blue,
                        fontSize: 12,
                      ),
                    ),
                  ),
                  Stack(
                    children: <Widget>[
                      //占坑 防止动画变大缩小 改变布局的宽度和高度 设置一个大小固定的
                      Container(
                        height: MIC_SIZE,
                        width: MIC_SIZE,
                      ),
                      Center(
                        child: AnimateMic(
                          animation: animation,
                        ),
                      ),
                    ],
                  )
                ],
              ),
            ),
          ),
          //绝对定位
          Positioned(
            child: GestureDetector(
              onTap: () {
                Navigator.pop(context);
              },
              child: Icon(
                Icons.close,
                size: 20,
              ),
            ),
            right: 0,
            bottom: 20,
          ),
        ],
      ),
    );
  }

  _onSpeakStart() {
    controller.forward();
  }

  _onSpeakStop() {
//    controller.dispose();
  }
}

const double MIC_SIZE = 80;

/// 带有动画的widget
class AnimateMic extends AnimatedWidget {
  //widget 透明度的变化
  static final _opacityTween = Tween<double>(begin: 1, end: 0.5);
  static final _sizeTween = Tween<double>(begin: MIC_SIZE, end: MIC_SIZE - 20);

  AnimateMic({Key key, Animation<double> animation})
      : super(key: key, listenable: animation);

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Opacity(
      opacity: _opacityTween.evaluate(listenable),
      child: Container(
        height: _sizeTween.evaluate(listenable),
        width: _sizeTween.evaluate(listenable),
        decoration: BoxDecoration(
          color: Colors.blue,
          borderRadius: BorderRadius.circular(MIC_SIZE / 2),
        ),
        child: Icon(
          Icons.mic,
          color: Colors.white,
          size: 30,
        ),
      ),
    );
  }
}
