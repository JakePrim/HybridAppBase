import 'dart:async';
import 'dart:convert';
import 'dart:core' as prefix0;
import 'dart:core';

import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_action/widget/backdrop.dart';
import 'package:flutter_action/widget/category_title.dart';
import 'widget/category.dart';
import 'unit_converter.dart';
import 'widget/unit.dart';
import 'io/api.dart';
import 'dart:io';

///a list of [Category]
///
class CategoryRoute extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new CategoryPage();
  }
}

class CategoryPage extends State<CategoryRoute> {
  // Keep track of a default [Category], and the currently-selected
  CategoryWidget _currentCategory;
  CategoryWidget _defaultCategory;
  final _categorys = <CategoryWidget>[];
  static const _icons = <String>[
    'assets/icons/length.png',
    'assets/icons/area.png',
    'assets/icons/volume.png',
    'assets/icons/mass.png',
    'assets/icons/time.png',
    'assets/icons/digital_storage.png',
    'assets/icons/power.png',
    'assets/icons/currency.png'
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

  /// 绘制列表 根据屏幕的方向改变widget
  Widget _buildCategoryWidget(Orientation deviceOrientation) {
    if (deviceOrientation == Orientation.portrait) {
      return ListView.builder(
        itemBuilder: (context, position) {
          return CategoryTitle(
            categoryWidget: _categorys[position],
            onTap: _onCategorytap,
          );
        },
        itemCount: _categorys.length,
      );
    } else {
      return GridView.count(
        crossAxisCount: 2,
        childAspectRatio: 3.0,
        children: _categorys.map((CategoryWidget c) {
          return CategoryTitle(
            categoryWidget: c,
            onTap: _onCategorytap,
          );
        }).toList(),
      );
    }
  }

  Future<void> _retrieveApiCategories() async {
    setState(() {
      _categorys.add(CategoryWidget(
        text: apiCategory['name'],
        units: [],
        color: _baseColors.last,
        iconLocation: _icons.last
      ));
    });
    final api = Api();
      final jsonUnits = await api.getUnits(apiCategory['route']);
      if(jsonUnits != null){
        final units = <Unit>[];
        for(var unit in jsonUnits){
          units.add(Unit.fromJson(unit));
        }
        setState(() {
          _categorys.removeLast();
          _categorys.add(CategoryWidget(
            text: apiCategory['name'],
            units: units,
            color: _baseColors.last,
            iconLocation: _icons.last
          ));
        });
      }
  }

  Future<void> _retrieveLocalCategories() async {
    // Consider omitting the types for local variables. For more details on Effective
    // Dart Usage, see https://www.dartlang.org/guides/language/effective-dart/usage
    final json = DefaultAssetBundle.of(context)
        .loadString('assets/data/regular_units.json');
    final data = JsonDecoder().convert(await json);
    if (data is! Map) {
      throw ('Data retrieved from API is not a Map');
    }
    var categoryIndex = 0;
    data.keys.forEach((key) {
      final List<Unit> units =
          data[key].map<Unit>((dynamic data) => Unit.fromJson(data)).toList();

      var category = CategoryWidget(
        text: key,
        units: units,
        color: _baseColors[categoryIndex],
        iconLocation: _icons[categoryIndex],
      );
      print('_defaultCategory:$_defaultCategory categoryIndex:$categoryIndex');
      setState(() {
        if (categoryIndex == 0) {
          _defaultCategory = category;
        }
        _categorys.add(category);
      });
      categoryIndex += 1;
    });
  }

  //todo Fill out this function
  void _onCategorytap(CategoryWidget categoryWidget) {
    setState(() {
      _currentCategory = categoryWidget;
    });
  }

  @override
  Future<void> didChangeDependencies() async {
    super.didChangeDependencies();
    print('didChangeDependencies:$_categorys');
    if (_categorys.isEmpty) {
      await _retrieveLocalCategories();
      await _retrieveApiCategories();
    }
  }

  @override
  Widget build(BuildContext context) {
    assert(debugCheckHasMediaQuery(context));
    final listView = Container(
      // color: _backgroundColor,
      padding: EdgeInsets.only(
        left: 8.0,
        right: 8.0,
        bottom: 48.0,
      ),
      child: _buildCategoryWidget(MediaQuery.of(context).orientation),
    );
    print(
        'currentCategory:$_currentCategory defaultCategory:$_defaultCategory');
    if (_currentCategory == null && _defaultCategory == null) {
      //显示加载中的提示
      return Container(
        color: Colors.grey[600],
        child: Text(
          '加载中...',
          style: TextStyle(color: Colors.black),
        ),
      );
    }
    return Backdrop(
      currentCategory:
          _currentCategory == null ? _defaultCategory : _currentCategory,
      frontPanel: _currentCategory == null
          ? UnitConverter(
              categoryWidget: _defaultCategory,
            )
          : UnitConverter(
              categoryWidget: _currentCategory,
            ),
      backPanel: listView,
      frontTitle: Text('Unit Converter'),
      backTitle: Text('Select a Category'),
    );
  }
}
