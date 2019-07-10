import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'widget/category.dart';
import 'converter_route.dart';
import 'widget/unit.dart';

///a list of [Category]
///
///

final _backgroundColor = Colors.green[100];

class CategoryRoute extends StatelessWidget {
  static const _categoryNames = <String>[
    'Length',
    'Area',
    'Volume',
    'Time',
    'Digital Storage',
    'Energy',
    'Currency'
  ];

  static const _baseColors = <Color>[
    Colors.teal,
    Colors.orange,
    Colors.pinkAccent,
    Colors.blueAccent,
    Colors.yellow,
    Colors.greenAccent,
    Colors.purpleAccent,
    Colors.red,
  ];

  List<Unit> _retrieveUnitList(String categoryName) {
    return List.generate(10, (int i) {
      i += 1;
      return Unit(name: '$categoryName Unit $i', conversion: i.toDouble());
    });
  }

  Widget _buildCategoryWidget(List<CategoryWidget> categorys) {
    return ListView.builder(
      itemBuilder: (context, position) {
        return categorys[position];
      },
      itemCount: categorys.length,
    );
  }

  @override
  Widget build(BuildContext context) {
    final categorys = <CategoryWidget>[];

    for (int i = 0; i < _categoryNames.length; i++) {
      categorys.add(CategoryWidget(
          iconData: Icons.cake,
          text: _categoryNames[i],
          color: _baseColors[i],
          units: _retrieveUnitList(_categoryNames[i])));
    }

    final listView = Container(
      child: _buildCategoryWidget(categorys),
    );

    final appbar = AppBar(
      elevation: 0,
      title: Text(
        'Converter',
        style: TextStyle(color: Colors.black, fontSize: 30.0),
      ),
      centerTitle: true,
      backgroundColor: _backgroundColor,
    );

    return Scaffold(
      appBar: appbar,
      body: Container(
        color: _backgroundColor,
        padding: EdgeInsets.symmetric(horizontal: 8.0),
        child: listView,
      ),
    );
  }
}
