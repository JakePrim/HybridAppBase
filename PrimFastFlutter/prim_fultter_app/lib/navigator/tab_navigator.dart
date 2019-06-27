import 'package:flutter/material.dart';
import 'package:prim_fultter_app/pages/home_page.dart';
import 'package:prim_fultter_app/pages/mine_page.dart';
import 'package:prim_fultter_app/pages/search_page.dart';
import 'package:prim_fultter_app/pages/travel_page.dart';

///搭建项目整体框架
class TabNavigator extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _TabNavigatorState();
}

//默认颜色
final _defaultColor = Colors.grey;
//选中的颜色
final _activityColor = Colors.blue;

//通过控制_controllerIndex来控制哪个页面显示
var _controllerIndex = 0;

class _TabNavigatorState extends State<TabNavigator>
    {
  //页面控制器 通过调用jumpToPage 实现页面的跳转
  final PageController _controller = PageController(initialPage: 0);

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: PageView(
        controller: _controller,
        children: <Widget>[
          //page的页面
          HomePage(),
          SearchPage(
            hideLeft: true,
          ),
          TravelPage(),
          MinePage(),
        ],
        onPageChanged: (int index) {
          //滑动page的监听
          setState(() {
            //改变tab状态
            _controllerIndex = index;
          });
        },
      ),
//      IndexedStack(
//        children: <Widget>[
//          //page的页面
//          HomePage(),
//          SearchPage(
//            hideLeft: true,
//          ),
//          TravelPage(),
//          MinePage(),
//        ],
//        index: _controllerIndex,
//      ),

      ///     PageView 每次进入页面都会 重新渲染view

      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _controllerIndex, //当前的index
        onTap: (index) {
          //点击tab
          _controller.jumpToPage(index); //跳转到具体的页面
          //注意改变_controllerIndex的状态
          setState(() {
            _controllerIndex = index;
          });
        },
        type: BottomNavigationBarType.fixed, //固定
        items: [
          _bottomItem(Icons.home, "首页", 0),
          _bottomItem(Icons.search, "搜索", 1),
          _bottomItem(Icons.near_me, "旅拍", 2),
          _bottomItem(Icons.supervised_user_circle, "我的", 3),
        ],
      ),
    );
  }


}

final List<BottomNavigationBarItem> _bottomWidgets = [];

/// 底部item
_bottomItem(IconData icon, String text, int index) {
  print("_controllerIndex:${_controllerIndex} index:${index}");
  return BottomNavigationBarItem(
      icon: Icon(
        //定义默认状态下的图片以及颜色
        icon,
        color: _defaultColor,
      ),
      activeIcon: Icon(
        //定义选中状态下的图片以及颜色
        icon,
        color: _activityColor,
      ),
      title: Text(
        //定义文字
        text,
        style: TextStyle(
          color: _controllerIndex != index ? _defaultColor : _activityColor,
        ),
      ));
}
