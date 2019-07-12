import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'widget/category.dart';
import 'converter_route.dart';
import 'widget/unit.dart';

///a list of [Category]
///
/// this of backgroundColor
final _backgroundColor = Colors.green[100];

class CategoryRoute extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new CategoryPage();
  }
}

class CategoryPage extends State<CategoryRoute> {
  final categorys = <CategoryWidget>[];

  static const _categoryNames = <String>[
    'Length',
    'Area',
    'Volume',
    'Mass',
    'Time',
    'Digital Storage',
    'Energy',
    'Currency'
  ];

  static const _baseColors = <ColorSwatch>[
    ColorSwatch(0xFF6AB7A8, {
      'highLight': Color(0xFF6AB7A8),
      'splash': Color(0xFF0ABC9B),
    }),
    ColorSwatch(0xFFFFD28E, {
      'highLight': Color(0xFFFFD28E),
      'splash': Color(0xFFFFA41C),
    }),
    ColorSwatch(0xFFFFB7DE, {
      'highLight': Color(0xFFFFB7DE),
      'splash': Color(0xFFF94CBF),
    }),
    ColorSwatch(0xFF8899A8, {
      'highLight': Color(0xFF8899A8),
      'splash': Color(0xFFA9CAE8),
    }),
    ColorSwatch(0xFFEAD37E, {
      'highLight': Color(0xFFEAD37E),
      'splash': Color(0xFFFFE070),
    }),
    ColorSwatch(0xFF81A56F, {
      'highLight': Color(0xFF81A56F),
      'splash': Color(0xFF7CC159),
    }),
    ColorSwatch(0xFFD7C0E2, {
      'highLight': Color(0xFFD7C0E2),
      'splash': Color(0xFFCA90E5),
    }),
    ColorSwatch(0xFFCE9A9A, {
      'highlight': Color(0xFFCE9A9A),
      'splash': Color(0xFFF94D56),
      'error': Color(0xFF912D2D),
    }),
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
  void initState() {
    super.initState();
    for (int i = 0; i < _categoryNames.length; i++) {
      categorys.add(CategoryWidget(
          iconData: Icons.cake,
          text: _categoryNames[i],
          color: _baseColors[i],
          units: _retrieveUnitList(_categoryNames[i])));
    }
  }

  @override
  Widget build(BuildContext context) {
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
