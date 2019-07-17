import 'package:flutter/material.dart';

import 'category_route.dart';

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
        fontFamily: 'Raleway',
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
