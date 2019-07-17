import 'package:flutter/material.dart';
import 'package:flutter_action/unit_converter.dart';
import 'package:meta/meta.dart';
import 'unit.dart';

///定义通用组件
///

final _rowHeight = 100.0;
final _borderRadius = BorderRadius.circular(_rowHeight / 2);

class CategoryWidget {
  final String iconLocation;
  final String text;
  final ColorSwatch color;
  final List<Unit> units;

  CategoryWidget(
      {@required this.iconLocation,
      @required this.text,
      @required this.color,
      @required this.units})
      : assert(iconLocation != null),
        assert(text != null),
        assert(color != null),
        assert(units != null);
}
