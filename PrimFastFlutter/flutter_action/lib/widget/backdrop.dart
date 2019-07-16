import 'package:flutter/foundation.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter/material.dart';
import 'package:flutter_action/widget/category.dart';
import 'dart:math' as math;

const double _kFlingVelocity = 2.0;

class _BackdropPanel extends StatelessWidget {
  const _BackdropPanel(
      {Key key,
      this.onTap,
      this.onVerticalDragUpdate,
      this.onVerticalDragEnd,
      this.title,
      this.child})
      : super(key: key);

  ///[VoidCallback] is Function
  final VoidCallback onTap;
  //手势移动的监听
  final GestureDragUpdateCallback onVerticalDragUpdate;
  //手势结束的监听
  final GestureDragEndCallback onVerticalDragEnd;
  //title
  final Widget title;
  //child
  final Widget child;

  @override
  Widget build(BuildContext context) {
    return Material(
      elevation: 2.0, //投影设置
      borderRadius: BorderRadius.only(
        topLeft: Radius.circular(6.0),
        topRight: Radius.circular(6.0),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch, //横轴设置
        children: <Widget>[
          GestureDetector(
            behavior: HitTestBehavior.opaque,
            onVerticalDragUpdate: onVerticalDragUpdate,
            onVerticalDragEnd: onVerticalDragEnd,
            onTap: onTap,
            child: Container(
              height: 48.0,
              padding: EdgeInsetsDirectional.only(start: 16.0),
              alignment: AlignmentDirectional.centerStart,
              child: DefaultTextStyle(
                style: Theme.of(context).textTheme.subhead,
                child: title,
              ),
            ),
          ),
          Divider(
            height: 1.0,
          ),
          Expanded(
            child: child,
          )
        ],
      ),
    );
  }
}

class _BackdropTitle extends AnimatedWidget {
  final Widget frontTitle;
  final Widget backTitle;

  const _BackdropTitle(
      {Key key, Listenable listenable, this.frontTitle, this.backTitle})
      : super(key: key, listenable: listenable);

  @override
  Widget build(BuildContext context) {
    final Animation<double> animation = this.listenable;

    return DefaultTextStyle(
      style: Theme.of(context).primaryTextTheme.title,
      softWrap: false,
      overflow: TextOverflow
          .ellipsis, //overflow 表示超出空间的子widget如何处理 ellipsis已...的形式显示 fade :将溢出的文本淡化为透明 clip:裁剪溢出的文本
      ///通过stack 类似FrameLayout frontTitle 与 backTitle 交叉显示
      child: Stack(
        children: <Widget>[
          Opacity(
            opacity: CurvedAnimation(
              parent: ReverseAnimation(animation),
              curve: Interval(0.5, 1.0),
            ).value,
            child: backTitle,
          ),
          Opacity(
            opacity: CurvedAnimation(
              parent: animation,
              curve: Interval(0.5, 1.0),
            ).value,
            child: frontTitle,
          )
        ],
      ),
    );
  }
}

class Backdrop extends StatefulWidget {
  final CategoryWidget currentCategory;
  final Widget frontPanel;
  final Widget backPanel;
  final Widget frontTitle;
  final Widget backTitle;

  const Backdrop(
      {@required this.currentCategory,
      @required this.frontPanel,
      @required this.backPanel,
      @required this.frontTitle,
      @required this.backTitle})
      : assert(currentCategory != null),
        assert(frontPanel != null),
        assert(backPanel != null),
        assert(frontTitle != null),
        assert(backTitle != null);

  @override
  State<StatefulWidget> createState() => _BackdropState();
}
///_表示为private 通过 SingleTickerProviderStateMixin实现动画效果
class _BackdropState extends State<Backdrop> with SingleTickerProviderStateMixin{
  ///[GlobalKey] 必须继承State<> GlobalKey 可以保证全局唯一,能够依附在不同的节点上 可以让不同的页面复用视图
  final GlobalKey _backdropKey = GlobalKey(debugLabel: 'Backdrop');
  AnimationController _controller;

  @override
  void initState() {
    super.initState();
    _controller = AnimationController(
      duration: Duration(milliseconds: 300),
      value: 1.0,
      vsync: this,
    );
  }

  /// 当组件的状态改变时,触发该方法.比如调用了[setState]
  /// 这个函数一般用于比较新、老widget,看那些属性改变了,并对State做一些调整
  @override
  void didUpdateWidget(Backdrop oldWidget) {
    super.didUpdateWidget(oldWidget);
    //点击的category 不等于当前的category,则进行切换
    if(widget.currentCategory != oldWidget.currentCategory){
      setState(() {
        _controller.fling(
          //velocity < 0 reverse
          velocity: _backdropPanelVisible ? -_kFlingVelocity : _kFlingVelocity
        );
      });
    }else if(!_backdropPanelVisible){//如果面板没有显示则显示出来的
      setState(() {
        _controller.fling(velocity: _kFlingVelocity);
      });
    }
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  ///判断弹出面板是否显示
  bool get _backdropPanelVisible{
    final AnimationStatus status = _controller.status;
    return status == AnimationStatus.completed || status == AnimationStatus.forward;
  }

  void _toggleBackdropPanelVisability(){
    //??? 面板显示后设置焦点
    FocusScope.of(context).requestFocus(FocusNode());
    _controller.fling(
      velocity: _backdropPanelVisible ? - _kFlingVelocity : _kFlingVelocity
    );
  }

  ///获取面板的高度
  double get _backdropHeight{
    //查找存储的节点
    final RenderBox renderBox = _backdropKey.currentContext.findRenderObject();
    return renderBox.size.height;
  }

  void _handleDragUpdate(DragUpdateDetails details){
    if(_controller.isAnimating || _controller.status == AnimationStatus.completed) return;
    _controller.value -= details.primaryDelta / _backdropHeight;
  }

  void _handleDragEnd(DragEndDetails details){
    if(_controller.isAnimating || _controller.status == AnimationStatus.completed) return;

    final double flingVelocity = details.velocity.pixelsPerSecond.dy / _backdropHeight;
    if(flingVelocity < 0.0){
      _controller.fling(velocity: math.max(_kFlingVelocity,-flingVelocity));
    }else if(flingVelocity > 0.0){
      _controller.fling(velocity: math.min(-_kFlingVelocity, -flingVelocity));
    }else{
      _controller.fling(
        velocity: _controller.value < 0.5 ? -_kFlingVelocity : _kFlingVelocity
      );
    }
  }

  ///确定部件有多大可以使用LayoutBuilder
  Widget _buildStack(BuildContext context,BoxConstraints constraints){
    const double panelTitleHeight = 48.0;
    final Size panelSize = constraints.biggest;
    final double panelTop = panelSize.height - panelTitleHeight;

    Animation<RelativeRect> panelAnimation = RelativeRectTween(
      begin: RelativeRect.fromLTRB(0.0, panelTop, 0.0, panelTop - panelSize.height),
      end: RelativeRect.fromLTRB(0.0, 0.0, 0.0, 0.0)
    ).animate(_controller.view);

    return Container(
      key: _backdropKey,
      color: widget.currentCategory.color,
      child: Stack(
        children: <Widget>[
          widget.backPanel,
          PositionedTransition(
            rect: panelAnimation,
            child: _BackdropPanel(
              onTap: _toggleBackdropPanelVisability,
              onVerticalDragUpdate: _handleDragUpdate,
              onVerticalDragEnd: _handleDragEnd,
              title: Text(widget.currentCategory.text),
              child: widget.frontPanel,
            ),
          )
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: widget.currentCategory.color,
        elevation: 0.0,
        ///改变左侧按钮
        leading: IconButton(
            icon: AnimatedIcon(
              icon: AnimatedIcons.close_menu,
              progress: _controller.view,
            ),
            onPressed: _toggleBackdropPanelVisability,
        ),
        title: _BackdropTitle(
          listenable: _controller.view,
          frontTitle: widget.frontTitle,
          backTitle: widget.backTitle,
        ),
      ),
      body: LayoutBuilder(
        builder: _buildStack,
      ),
    );
  }
}
