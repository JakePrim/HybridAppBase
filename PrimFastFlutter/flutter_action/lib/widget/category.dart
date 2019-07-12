import 'package:flutter/material.dart';
import 'package:meta/meta.dart';
import 'unit.dart';
import 'package:flutter_action/converter_route.dart';

///定义通用组件
///

final _rowHeight = 100.0;
final _borderRadius = BorderRadius.circular(_rowHeight / 2);

class CategoryWidget extends StatefulWidget {
  final IconData iconData;
  final String text;
  final ColorSwatch color;
  final List<Unit> units;
  void Function() onTabClick;

  CategoryWidget(
      {Key key,
      @required this.iconData,
      @required this.text,
      @required this.color,
      @required this.units,
      this.onTabClick})
      : assert(iconData != null),
        assert(text != null),
        assert(color != null),
        assert(units != null),
        super(key: key);

  void _navigateToConverter(BuildContext context) {
    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) => ConverterRoute(
                  units: units,
                  color: color,
                  name: text,
                )));
  }

  @override
  State<StatefulWidget> createState() {
    return new CategoryPage();
  }
}

class CategoryPage extends State<CategoryWidget> {

  @override
  void initState() {
    super.initState();
  }
  @override
  Widget build(BuildContext context) {
    return Material(
      color: Colors.transparent,
      child: Container(
        height: _rowHeight,
        child: InkWell(//InkWell of this click change color
          highlightColor: widget.color,
          splashColor: widget.color,
          child: Padding(
            padding: EdgeInsets.all(8.0),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: <Widget>[
                Padding(
                  padding: EdgeInsets.all(16.0),
                  child: Icon(
                    widget.iconData,
                    size: 60,
                  ),
                ),
                Center(
                  child: Text(
                    widget.text,
                    textAlign: TextAlign.center,
                    style: TextStyle(fontSize: 24.0),
                  ),
                )
              ],
            ),
          ),
          borderRadius: _borderRadius,
          onTap: () {
            widget._navigateToConverter(context);
          },
        ),
      ),
    );
  }
}
