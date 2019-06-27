import 'package:flutter/material.dart';
import 'package:prim_fultter_app/model/common_model.dart';
import 'package:prim_fultter_app/components/webview.dart';
import 'package:prim_fultter_app/model/sales_box_model.dart';

///底部卡片活动实现
class SalesBox extends StatelessWidget {
  final SalesBoxModel salesBoxModel;

  //@required 标识为必填的参数
  const SalesBox({Key key, @required this.salesBoxModel}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        //设置一个装饰器
        color: Colors.white,
      ),
      child: _items(context), //将子widget包裹在一个padding中
    );
  }

  ///item列表
  _items(BuildContext context) {
    if (salesBoxModel == null) return null;
    List<Widget> item = [];
    item.add(_doubleItem(
        context, salesBoxModel.bigCard1, salesBoxModel.bigCard2, true, false));
    item.add(_doubleItem(context, salesBoxModel.smallCard1,
        salesBoxModel.smallCard2, false, false));
    item.add(_doubleItem(context, salesBoxModel.smallCard3,
        salesBoxModel.smallCard4, false, true));
    //遍历gridNavList列表
    return Column(
      children: <Widget>[
        Container(
          height: 45,
          margin: EdgeInsets.only(left: 10),
          decoration: BoxDecoration(
            border: Border(
                bottom:
                    BorderSide(width: 1, color: Color(0xfff2f2f2))), //设置底部分割线
          ),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: <Widget>[
              Image.network(
                salesBoxModel.icon,
                fit: BoxFit.fill,
                height: 15,
              ),
              Container(
                  padding: EdgeInsets.fromLTRB(10, 1, 8, 1),
                  margin: EdgeInsets.only(right: 7),
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(12), //设置圆角
                    gradient: LinearGradient(
                      colors: [
                        Color(0xffff4e63),
                        Color(0xffff6cc9),
                      ],
                      begin: Alignment.centerLeft,
                      end: Alignment.centerRight,
                    ), //设置线性渐变
                  ),
                  child: GestureDetector(
                    onTap: () {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => WebView(
                                    url: salesBoxModel.moreUrl,
                                    title: '更多活动',
                                  )));
                    },
                    child: Text(
                      '获取更多福利 >',
                      style: TextStyle(color: Colors.white, fontSize: 12),
                    ),
                  )),
            ],
          ),
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: item.sublist(0, 1),
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: item.sublist(1, 2),
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: item.sublist(2, 3),
        ),
      ],
    );
  }

  Widget _doubleItem(BuildContext context, CommonModel leftModel,
      CommonModel rightModel, bool big, bool last) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        itemWidget(context, leftModel, big, last, true),
        itemWidget(context, rightModel, big, last, false),
      ],
    );
  }

  Widget itemWidget(
      BuildContext context, CommonModel model, bool big, bool last, bool left) {
    BorderSide side = BorderSide(color: Color(0xfff2f2f2), width: 0.8);
    return GestureDetector(
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
        child: Container(
          decoration: BoxDecoration(
            border: Border(
                right: left ? side : BorderSide.none,
                bottom: last ? side : BorderSide.none),
          ),
          child: Image.network(
            model.icon,
            fit: BoxFit.fill,
            width: MediaQuery.of(context).size.width / 2 -
                10, //MediaQuery.of(context).size.width获取屏幕的宽度
            height: big ? 129 : 80,
          ),
        ));
  }
}
