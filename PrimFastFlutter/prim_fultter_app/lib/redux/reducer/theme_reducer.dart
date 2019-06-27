import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';
import 'package:prim_fultter_app/redux/action/theme_action.dart';

///Reducer 用于根据Action产生新状态：ThemeType
final ThemeReducer = combineReducers<ThemeType>([
  ///state action
  TypedReducer<ThemeType, RefreshThemeAction>(_refresh),
]);
///(State state, dynamic action)
ThemeType _refresh(ThemeType type, dynamic action) {
  type = action.type;
  return type;
}
