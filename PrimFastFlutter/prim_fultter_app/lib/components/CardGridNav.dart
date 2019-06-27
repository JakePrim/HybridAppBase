import 'package:flutter/material.dart';
import 'package:prim_fultter_app/components/webview.dart';
import 'package:prim_fultter_app/model/common_model.dart';
import 'package:prim_fultter_app/model/grid_nav_model.dart';
import 'package:prim_fultter_app/model/grid_nav_item.dart';

///卡片网格布局
class CardGridNav extends StatefulWidget {
  final GridNavModel gridNavModel;

  const CardGridNav({Key key, this.gridNavModel}) : super(key: key);

  @override
  _CardGridNavState createState() => _CardGridNavState();
}

class _CardGridNavState extends State<CardGridNav> {
  @override
  Widget build(BuildContext context) {
    return PhysicalModel(//整体多个Widget设置圆角
      color: Colors.transparent,
      borderRadius: BorderRadius.circular(6),
      clipBehavior: Clip.antiAlias,
      child: Column(
        children: _gridNavItems(context),
      ),
    );
  }

  /// 大卡片
  _gridNavItems(BuildContext context) {
    List<Widget> items = [];
    if (widget.gridNavModel == null) return items;
    if (widget.gridNavModel.hotel != null) {
      items.add(_gridNavItem(context, widget.gridNavModel.hotel, true));
    }
    if (widget.gridNavModel.flight != null) {
      items.add(_gridNavItem(context, widget.gridNavModel.flight, false));
    }
    if (widget.gridNavModel.travel != null) {
      items.add(_gridNavItem(context, widget.gridNavModel.travel, false));
    }
    return items;
  }

  ///一行卡片
  _gridNavItem(BuildContext context, GridNavItem item, bool first) {
    List<Widget> items = [];
    items.add(_mainItem(item.mainItem));
    items.add(_doubleItem(context, item.item1, item.item2));
    items.add(_doubleItem(context, item.item3, item.item4));
    List<Widget> expandItem = []; //设置所有item填充
    items.forEach((item) {
      expandItem.add(Expanded(
        child: item,
        flex: 1,
      ));
    });
    Color startColor = Color(int.parse('0xff' + item.startColor));
    Color endColor = Color(int.parse('0xff' + item.endColor));
    return Container(
      height: 88,
      margin: first ? null : EdgeInsets.only(top: 3),
      decoration: BoxDecoration(
        //设置装饰器
        //线性渐变
        gradient: LinearGradient(colors: [startColor, endColor]),
      ),
      child: Row(
        children: expandItem,
      ),
    );
  }

  ///具体的主卡片widget
  _mainItem(CommonModel model) {
    return _warpGesture(
        Stack(
          alignment: AlignmentDirectional.topCenter,
          //设置为容器布局
          children: <Widget>[
            Image.network(
              model.icon,
              fit: BoxFit.contain, //居中
              width: 88,
              height: 121,
              alignment: AlignmentDirectional.bottomEnd, //图片局下
            ),
            Padding(
              padding: EdgeInsets.only(top: 10),
              child: Text(
                model.title,
                style: TextStyle(
                  fontSize: 14,
                  color: Colors.white,
                ),
              ),
            )
          ],
        ),
        model);
  }

  /// 上下附属卡片
  _doubleItem(
      BuildContext context, CommonModel topIem, CommonModel bottomItem) {
    return Column(
      children: <Widget>[
        Expanded(child: _item(context, topIem, true)), //垂直方向上展开
        Expanded(child: _item(context, bottomItem, false)),
      ],
    );
  }

  //具体widget
  _item(BuildContext context, CommonModel item, bool first) {
    BorderSide side = BorderSide(width: 0.8, color: Colors.white);
    return FractionallySizedBox(
      //水平方向上展开
      widthFactor: 1, //设置宽度撑满父布局
      child: Container(
        //设置装饰器
        decoration: BoxDecoration(
          border: Border(
              //设置边框
              left: side,
              bottom: first ? side : BorderSide.none),
        ),
        child: _warpGesture(
            Center(
              //设置一个居中布局
              child: Text(
                item.title,
                textAlign: TextAlign.center,
                style: TextStyle(
                  fontSize: 14,
                  color: Colors.white,
                ),
              ),
            ),
            item),
      ),
    );
  }

  /// 封装点击事件
  _warpGesture(Widget widget, CommonModel model) {
    return GestureDetector(
      child: widget,
      onTap: () {
        //点击事件
        Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => WebView(
                      url: model.url,
                      hideAppBar: model.hideAppBar,
                      statusBarColor: model.statusBarColor,
                    )));
      },
    );
  }
}
