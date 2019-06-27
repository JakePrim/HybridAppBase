import 'package:flutter/material.dart';
import 'package:prim_fultter_app/components/search_bar.dart';
import 'package:prim_fultter_app/components/webview.dart';
import 'package:prim_fultter_app/dao/SearchDao.dart';
import 'dart:async';
import 'dart:convert';
import 'package:prim_fultter_app/model/search_model.dart';
import 'package:flutter/services.dart';
import 'package:prim_fultter_app/pages/speak_page.dart';

///首页页面
class SearchPage extends StatefulWidget {
  final String keyWord;
  final String hint;
  final bool hideLeft;
  final String searchUrl;

  const SearchPage(
      {Key key,
      this.keyWord,
      this.hint,
      this.hideLeft,
      this.searchUrl = SEARCH_URL})
      : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _SearchPageState();
  }
}

const SEARCH_URL =
    'https://m.ctrip.com/restapi/h5api/searchapp/search?source=mobileweb&action=autocomplete&contentType=json&keyword=';

const TYPE = [
  'channelgroup',
  'gs',
  'plane',
  'train',
  'cruise',
  'district',
  'food',
  'hotel',
  'huodong',
  'shop',
  'sight',
  'ticket',
  'travelgroup'
];

class _SearchPageState extends State<SearchPage>
    with AutomaticKeepAliveClientMixin {
  List<SearchItem> list;

  SearchModel searchModel;
  String key;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: <Widget>[
          MediaQuery.removePadding(
            removeTop: true,
            context: context,
            child: _appBar(),
          ),

          ///自适应布局的宽高
          MediaQuery.removePadding(
            removeTop: true,

            ///移除顶部的padding
            context: context,
            child: Expanded(
              ///自适应宽度
              flex: 1,
              child: ListView.builder(
                itemCount: searchModel?.data?.length ?? 0,
                itemBuilder: (BuildContext context, int position) {
                  return _item(position);
                },
              ),
            ),
          ),
        ],
      ),
    );
  }

  /// item的widget
  _item(int position) {
    if (searchModel == null || searchModel.data == null) return null;
    //搜索item的数据
    SearchItem searchItem = searchModel.data[position];
    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => WebView(
                  url: searchItem.url,
                  title: '详情',
                ),
          ),
        );
      },
      child: Container(
        padding: EdgeInsets.all(10),
        decoration: BoxDecoration(
            border: Border(bottom: BorderSide(color: Colors.grey, width: 0.3))),
        child: Row(
          //上下布局
          children: <Widget>[
            Container(
              child: Image(
                image: AssetImage(_typeImages(searchItem.type)),
                width: 26,
                height: 26,
              ),
            ),
            Column(
              children: <Widget>[
                Container(
                  width: 300,
                  child: _title(searchItem),
                ),
                Container(
                  width: 300,
                  child: _subTitle(searchItem),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  /// 顶部appbar
  _appBar() {
    return Container(
      decoration: BoxDecoration(
          gradient: LinearGradient(
        colors: [Color(0x66000000), Colors.transparent],
        begin: Alignment.topCenter,
        end: Alignment.bottomCenter,
      )),
      child: Container(
        padding: EdgeInsets.only(top: 20), //距离顶部20dp
        height: 80,
        child: SearchBar(
          hideLeft: widget.hideLeft,
          hint: widget.hint,
          defultText: widget.keyWord,
          leftButtonClick: () {
            Navigator.of(context).pop();
          },
          onChanged: _textChanged,
          rightButtonClick: () {},
          speakClick: () {
            Navigator.push(
                context, MaterialPageRoute(builder: (context) => SpeakPage()));
          },
          searchType: SearchType.normal,
        ),
      ),
    );
  }

  /// 输入文字监听
  _textChanged(String text) {
    key = text;
    if (text.length == 0) {
      setState(() {
        searchModel = null;
      });
      return;
    } else {
      String url = widget.searchUrl + key;
      SearchDao.fetch(url, key).then((value) {
        //当服务端返回的内容与当前输入的内容一致时才进行渲染
        if (value.keyWord == key) {
          setState(() {
            searchModel = value;
          });
        }
      }).catchError((error) {
        print(error.toString());
      });
    }
  }

  /// 获取不同类型的image
  String _typeImages(String type) {
    String path = 'images/type_channelgroup.png';
    if (type == null) return path;
    for (final t in TYPE) {
      if (type.contains(t)) {
        path = 'images/type_$t.png';
        break;
      }
    }
    return path;
  }

  /// 实现title富文本
  _title(SearchItem searchItem) {
    if (searchItem == null) return null;
    List<TextSpan> spans = [];
    spans.addAll(_keyWordSpan(searchItem.word, searchModel.keyWord));
    //第二段字体的颜色
    spans.add(TextSpan(
        text: ' ${searchItem.districtname ?? ''} ${searchItem.zonename ?? ''}',
        style: TextStyle(fontSize: 14, color: Colors.grey)));
    //返回富文本text
    return RichText(
      text: TextSpan(children: spans),
    );
  }

  /// 实现subtitle 富文本
  _subTitle(SearchItem searchItem) {
    if (searchItem == null) return null;
    List<TextSpan> spans = [];
    spans.add(TextSpan(
        text: '${searchItem.price ?? ''}',
        style: TextStyle(fontSize: 16, color: Colors.orange)));
    spans.add(TextSpan(
        text: ' ${searchItem.star ?? ''}',
        style: TextStyle(fontSize: 14, color: Colors.grey)));
    return RichText(
      text: TextSpan(children: spans),
    );
  }

  ///关键字富文本
  List<TextSpan> _keyWordSpan(String word, String keyWord) {
    List<TextSpan> keySpans = [];
    if (word == null || word.length == 0) return keySpans;
    //截取关键字部分 wordwoc w =  ,ord,oc
    var split = word.split(keyWord);

    TextStyle normalStyle = TextStyle(fontSize: 16, color: Colors.black);
    TextStyle keyStyle = TextStyle(fontSize: 16, color: Colors.orange);
    for (int i = 0; i < split.length; i++) {
      //关键字的颜色
      if ((i + 1) % 2 == 0) {
        keySpans.add(TextSpan(text: keyWord, style: keyStyle));
      }
      //正常的字体颜色
      String val = split[i];
      if (val != null && val.length > 0) {
        keySpans.add(TextSpan(text: val, style: normalStyle));
      }
    }
    return keySpans;
  }

  @override
  // TODO: implement wantKeepAlive
  bool get wantKeepAlive => true;
}
