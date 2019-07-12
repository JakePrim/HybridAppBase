import 'package:flutter/widgets.dart';
import 'package:flutter/material.dart';
import 'widget/unit.dart';
import 'package:meta/meta.dart';

class ConverterRoute extends StatefulWidget {

  ///Units for this [CategoryWidget]
  final List<Unit> units;

  ///color for this [CategoryWidget]
  final Color color;

  ///This [CategoryWidget]'s name
  final String name;

  const ConverterRoute(
      {@required this.units, @required this.color, @required this.name})
      : assert(units != null),
        assert(color != null),
        assert(name != null);

  @override
  State<StatefulWidget> createState() {
    return new ConverterPage();
  }
}

class ConverterPage extends State<ConverterRoute>{
//TODO Set some variables,such as for keeping track of the user's input
//value and units

//TODO :Determine whether you need to override anything,such as initState()

  @override
  void initState() {
    super.initState();
  }

  //TODO: Add other helper functions. We've given you one,_format()

  ///Clean up conversion;trim trailing zeros, e.g. 5.500 -> 5.5 10.0 -> 10
  String _format(double conversion){
    var outputNum = conversion.toStringAsPrecision(7);//??
    if(outputNum.contains('.') && outputNum.endsWith('0')){
      var i = outputNum.length - 1;
      while(outputNum[i] == '0'){
        i -= 1;
      }
      outputNum = outputNum.substring(0,outputNum.length - 1);
    }
    if(outputNum.endsWith('.')){
      return outputNum.substring(0,outputNum.length - 1);
    }
    return outputNum;
  }

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
        widget.name,
        style: Theme.of(context).textTheme.title,
      ),
      centerTitle: true,
      backgroundColor: widget.color,
      elevation: 1.0,
    );
  }



  @override
  Widget build(BuildContext context) {
    final unitWidgets = widget.units.map((Unit unit) {
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
          color: widget.color,
          child: ListView(
            children: unitWidgets,
          ),
        ));
  }
}
