import 'package:flutter/material.dart';
import 'package:flutter_action/widget/category.dart';
import 'package:meta/meta.dart';

const _rowHeight = 100.0;
final _borderRadius = BorderRadius.circular(_rowHeight / 2);

class CategoryTitle extends StatelessWidget {
  final CategoryWidget categoryWidget;
  final ValueChanged<CategoryWidget> onTap;

  const CategoryTitle(
      {Key key, @required this.categoryWidget, @required this.onTap})
      : assert(categoryWidget != null),
        assert(onTap != null),
        super(key: key);

  @override
  Widget build(BuildContext context) {
    return Material(
      color: Colors.transparent,
      child: Container(
        height: _rowHeight,
        child: InkWell(
          onTap: ()=> onTap(categoryWidget),
          borderRadius: _borderRadius,
          highlightColor: categoryWidget.color['highlight'],
          splashColor: categoryWidget.color['splash'],
          child: Padding(
            padding: EdgeInsets.all(8.0),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: <Widget>[
                Padding(
                  padding: EdgeInsets.all(16.0),
                  child: Image.asset(categoryWidget.iconLocation),
                ),
                Center(
                  child: Text(
                    categoryWidget.text,
                    textAlign: TextAlign.center,
                    style: Theme.of(context).textTheme.headline,
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
