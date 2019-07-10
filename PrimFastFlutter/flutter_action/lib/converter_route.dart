import 'package:flutter/widgets.dart';
import 'package:flutter/material.dart';
import 'widget/unit.dart';
import 'package:meta/meta.dart';

class ConverterRoute extends StatelessWidget {
  final List<Unit> units;
  final ColorSwatch color;
  final String name;

  const ConverterRoute(
      {@required this.units, @required this.color, @required this.name})
      : assert(units != null),
        assert(color != null),
        assert(name != null);

  AppBar appBar(BuildContext context) {
    return AppBar(
      leading: Builder(builder: (BuildContext context) {
        return IconButton(
            icon: const Icon(Icons.close),
            onPressed: () {
              Navigator.pop(context);
            });
      }),
      title: Text(
        name,
        style: Theme.of(context).textTheme.title,
      ),
      centerTitle: true,
      backgroundColor: color,
      elevation: 1.0,
    );
  }

  @override
  Widget build(BuildContext context) {
    final unitWidgets = units.map((Unit unit) {
      return Container(
        margin: EdgeInsets.all(8.0),
        padding: EdgeInsets.all(16.0),
        child: Column(
          children: <Widget>[
            Text(
              unit.name,
              style: Theme.of(context).textTheme.headline,
            ),
            Text(
              'Conversion: ${unit.conversion}',
              style: Theme.of(context).textTheme.subhead,
            )
          ],
        ),
      );
    }).toList();

    return Scaffold(
        appBar: appBar(context),
        body: Container(
          color: color,
          child: ListView(
            children: unitWidgets,
          ),
        ));
  }
}
