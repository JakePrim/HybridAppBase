import 'package:flutter/material.dart';
import 'package:prim_fultter_app/model/common_model.dart';
import 'package:prim_fultter_app/components/webview.dart';

///StatelessWidget不需要改变状态的view 所有的widget 都是不可变的@immutable 成员类型必须为final
class SubNav extends StatelessWidget {
  final List<CommonModel> subNavList;

  //@required 标识为必填的参数
  const SubNav({Key key, @required this.subNavList}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        //设置一个装饰器
        color: Colors.white,
        borderRadius: BorderRadius.circular(6), //设置圆角
      ),
      child: Padding(
        padding: EdgeInsets.all(7),
        child: _items(context),
      ), //将子widget包裹在一个padding中
    );
  }

  ///item列表
  _items(BuildContext context) {
    if (subNavList == null || subNavList.length == 0) return null;
    List<Widget> item = [];
    //遍历gridNavList列表
    subNavList.forEach((model) {
      item.add(itemWidget(context, model));
    });
    int subPosition = (subNavList.length / 2 + 0.5).toInt();
    return Column(
      children: <Widget>[
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween, //主轴方向 均分
          children: item.sublist(0, subPosition),
        ),
        Padding(
          padding: EdgeInsets.only(top: 10),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween, //主轴方向 均分
            children: item.sublist(subPosition, subNavList.length),
          ),
        )
      ],
    );
  }

  Widget itemWidget(BuildContext context, CommonModel model) {
    return Expanded(
      flex: 1,
      child: GestureDetector(
        onTap: () {
          Navigator.push(
              context,
              MaterialPageRoute(
                  builder: (context) => WebView(
                        url: model.url,
                        title: model.title,
                        statusBarColor: model.statusBarColor,
                        hideAppBar: model.hideAppBar,
                      )));
        },
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Image.network(
              model.icon,
              width: 18,
              height: 18,
            ),
            Text(
              model.title,
              style: TextStyle(fontSize: 12),
            ),
          ],
        ),
      ),
    );
  }
}
