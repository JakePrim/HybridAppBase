import 'package:flutter/material.dart';

///首页页面
class TravelPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _TravelPageState();
  }
}

class _TravelPageState extends State<TravelPage> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Text('旅拍'),
      ),
    );
  }
}
