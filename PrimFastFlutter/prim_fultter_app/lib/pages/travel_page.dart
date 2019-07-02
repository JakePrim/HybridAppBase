import 'package:flutter/material.dart';

//旅拍类型接口
const TRAVEL_URL = "http://www.devio.org/io/flutter_app/json/travel_page.json";

///旅拍界面
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
