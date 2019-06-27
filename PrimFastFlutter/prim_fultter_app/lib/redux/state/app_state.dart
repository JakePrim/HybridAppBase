import 'package:flutter/material.dart';
import 'package:prim_fultter_app/redux/action/theme_action.dart';
import 'package:prim_fultter_app/redux/reducer/theme_reducer.dart';

/// app 全局的Redux store 对象
/// 保存整个app的 颜色字体主题 语言等state
class AppState {
  final ThemeType themeType; //app的主题
  final Locale locale; // app中的语言
  final TextStyle mainStyle; //主字体样式
  final TextStyle subStyle; //次字体样式
  final Color mainColor; //主颜色
  final Color subColor; //辅助颜色

  AppState(
      {this.themeType,
      this.locale,
      this.mainStyle,
      this.subStyle,
      this.mainColor,
      this.subColor});
}

//创建Reducer
///创建Reducer  用于创建 store
///接收Reducer返回的新的状态
///typedef State Reducer<State>(State state, dynamic action);
AppState appReducer(AppState state, dynamic action) {
  return AppState(
    themeType: ThemeReducer(state.themeType, action),//ThemeReducer 根据action产生新的状态
  );
}
