import 'package:flutter/material.dart';

class LoadingWidget extends StatelessWidget {
  final bool isLoading;
  final bool cover;
  final Widget child;

  //required必须传递的参数
  const LoadingWidget(
      {Key key,
      @required this.isLoading,
      this.cover = false,
      @required this.child})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return !cover
        ? !isLoading ? child : _loadingView
        : Stack(
            children: <Widget>[child, isLoading ? _loadingView : null],
          );
  }

  Widget get _loadingView {
    return Center(
      child: CircularProgressIndicator(), //圆形的进度条
    );
  }
}
