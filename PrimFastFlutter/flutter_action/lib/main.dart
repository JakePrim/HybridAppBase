import 'package:flutter/material.dart';

import 'category_route.dart';

const _categoryName = 'Cake';
const _categoryColor = Colors.green;
const _categoryIcon = Icons.cake;

class HelloRectangle extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(child: CategoryRoute());
  }
}

AppBar getAppBar() {
  return AppBar(
    title: Text("Hello Rectangle"),
  );
}

void main() {
  runApp(UnitConverterApp());
}

class UnitConverterApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false, //移除右上角debug角标标识
      title: 'Unit Converter',
      theme: ThemeData(
        textTheme: Theme.of(context).textTheme.apply(
              bodyColor: Colors.black,
              displayColor: Colors.grey[600],
            ),
        primaryColor: Colors.grey[500],
        textSelectionHandleColor: Colors.grey[500],
      ),
      home: CategoryRoute(),
    );
  }
}
