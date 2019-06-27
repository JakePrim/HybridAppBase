import 'package:flutter/material.dart';

//新建主题类型
enum ThemeType { BLACK, BLUE, WHITE }

//Action 产生新状态 更新主题的类型
class RefreshThemeAction {
  final ThemeType type;

  RefreshThemeAction({this.type});
}
