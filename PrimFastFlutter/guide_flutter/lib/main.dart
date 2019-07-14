import 'package:english_words/english_words.dart' as prefix0;
import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: RandomWords(),
    );
  }
}

class RandomWords extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new RandomWordState();
  }
}

class RandomWordState extends State<RandomWords> {
  final List<WordPair> _suggestion = new List();
  final Set<WordPair> _saved = new Set();
  final TextStyle textStyle = new TextStyle(fontSize: 18.0);
  Widget _buildSuggestion() {
    return ListView.builder(
      padding: EdgeInsets.all(16.0),
      itemBuilder: (BuildContext context, int index) {
        if (index.isOdd) {
          return Divider();
        }
        final int ins = index ~/ 2;
        if (ins >= _suggestion.length) {
          _suggestion.addAll(generateWordPairs().take(10));
        }
        return _buildRow(_suggestion[ins]);
      },
    );
  }

  Widget _buildRow(WordPair pair) {
     bool _aleradySaved = _saved.contains(pair);
    return ListTile(
      title: Text(
        pair.asPascalCase,
        style: textStyle,
      ),
      trailing: Icon(
        _aleradySaved ? Icons.favorite : Icons.favorite_border,
        color: _aleradySaved ? Colors.red : null,
      ),
      onTap: (){
        setState(() {
          if(_aleradySaved){
            _saved.remove(pair);
          }else{
            _saved.add(pair);
          }
        });
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Startup Name Generate"),
        actions: <Widget>[
          IconButton(icon: Icon(Icons.list),onPressed: _pressedPush,)
        ],
      ),
      body: _buildSuggestion(),
    );
  }

  _pressedPush(){
    
  }
}
