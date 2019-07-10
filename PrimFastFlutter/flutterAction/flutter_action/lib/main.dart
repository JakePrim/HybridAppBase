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
  runApp(
    MaterialApp(
      debugShowCheckedModeBanner: false, //移除右上角debug角标标识
//      home: Scaffold(
//        backgroundColor: Colors.green[100],
//        appBar: getAppBar(),
//        body: HelloRectangle(),
//      ),
      home: CategoryRoute(),
    ),
  );
}
