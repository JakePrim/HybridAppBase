import 'package:flutter/material.dart';
import 'package:prim_fultter_app/navigator/tab_navigator.dart';
import 'package:redux/redux.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:prim_fultter_app/redux/state/app_state.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: TabNavigator(),
    );
  }
}
/// 创建Store 初始化状态 必须传入一个reducer来更新状态
//final store = new Store(appReducer,initialState: AppState(
//
//));