import 'package:flutter/material.dart';

///定义一个枚举类型
enum SearchType { home, normal, homeLight }

class SearchBar extends StatefulWidget {
  ///设置需要使用的变量
  final bool enabled; //是否禁止搜索
  final bool hideLeft; //是否隐藏左边的按钮
  final String hint;
  final String defultText;
  final SearchType searchType; //搜索的类型
  //回调事件
  final void Function() leftButtonClick; //按钮点击的回调
  final void Function() rightButtonClick;
  final void Function() speakClick;
  final void Function() inputBoxClick;
  //TextFiled 输入文字改变监听
  final ValueChanged<String> onChanged;

  const SearchBar(
      {Key key,
      this.enabled = true,
      this.hideLeft,
      this.hint,
      this.defultText,
      this.searchType = SearchType.normal,
      this.leftButtonClick,
      this.rightButtonClick,
      this.speakClick,
      this.inputBoxClick,
      this.onChanged})
      : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _SearchBarState();
  }
}

class _SearchBarState extends State<SearchBar> {
  //是否显示清空按钮
  bool showClear = false;

  //输入框控制器
  final TextEditingController _textController = new TextEditingController();

  @override
  void initState() {
    if (widget.defultText != null) {
      setState(() {
        _textController.text = widget.defultText;
      });
    }
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    ///根据不同的类型显示不同的样式
    return widget.searchType == SearchType.normal
        ? _searchNormal()
        : _searchHome();
  }

  ///搜索页面显示的搜索样式 正常类型的搜索按钮
  _searchNormal() {
    return Container(
      child: Row(
        children: <Widget>[
          ///左侧的返回按钮
          _warpTap(
              Container(
                padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                child: widget?.hideLeft ?? false
                    ? null
                    : Icon(
                        Icons.arrow_back_ios,
                        color: Colors.grey,
                        size: 26,
                      ),
              ),
              widget.leftButtonClick),

          ///自动适应宽度的输入框
          Expanded(
            flex: 1,
            child: _inputBox(),
          ),

          ///右侧的搜索按钮
          _warpTap(
              Container(
                padding: EdgeInsets.fromLTRB(10, 5, 5, 10),
                child: Text(
                  '搜索',
                  style: TextStyle(
                    color: Colors.blue,
                    fontSize: 17,
                  ),
                ),
              ),
              widget.rightButtonClick),
        ],
      ),
    );
  }

  ///首页显示的搜索按钮
  _searchHome() {
    return Container(
      child: Row(
        children: <Widget>[
          ///左侧的返回按钮
          _warpTap(
              Container(
                padding: EdgeInsets.fromLTRB(5, 5, 5, 5),
                child: Row(
                  children: <Widget>[
                    Text(
                      '上海',
                      style: TextStyle(color: _homeFontColor(), fontSize: 14),
                    ),
                    Icon(
                      Icons.keyboard_arrow_down,
                      color: _homeFontColor(),
                      size: 22,
                    ),
                  ],
                ),
              ),
              widget.leftButtonClick),

          ///自动适应宽度的输入框
          Expanded(
            flex: 1,
            child: _inputBox(),
          ),

          ///右侧的搜索按钮
          _warpTap(
              Container(
                  padding: EdgeInsets.fromLTRB(5, 5, 5, 5),
                  child: Icon(
                    Icons.comment,
                    color: _homeFontColor(),
                    size: 22,
                  )),
              widget.rightButtonClick),
        ],
      ),
    );
  }

  ///设置通用的点击事件
  _warpTap(Widget child, void Function() callback) {
    return GestureDetector(
      onTap: () {
        if (callback != null) {
          callback();
        }
      },
      child: child,
    );
  }

  ///输入框的实现
  _inputBox() {
    Color bgColor;
    if (widget.searchType == SearchType.home) {
      bgColor = Colors.white;
    } else {
      bgColor = Color(int.parse('0xffEDEDED'));
    }

    return Container(
      height: 30,
      padding: EdgeInsets.fromLTRB(10, 0, 10, 0),
      decoration: BoxDecoration(
        color: bgColor,
        borderRadius: BorderRadius.circular(
            widget.searchType == SearchType.normal ? 5 : 15),
      ),
      child: Row(
        children: <Widget>[
          Icon(
            Icons.search,
            size: 20,
            color: widget.searchType == SearchType.normal
                ? Color(0xffA9A9A9)
                : Colors.blue,
          ),
          Expanded(
              flex: 1,
              child: widget.searchType == SearchType.normal
                  ? _textInput()
                  : _homeInput()),
          //是否显示清空按钮
          !showClear
              //语音按钮
              ? _warpTap(
                  Icon(
                    Icons.mic,
                    size: 22,
                    color: widget.searchType == SearchType.normal
                        ? Colors.blue
                        : Colors.grey,
                  ),
                  widget.speakClick)
              //清空按钮
              : _warpTap(
                  Icon(
                    Icons.clear,
                    size: 22,
                    color: Colors.grey,
                  ), () {
                  setState(() {
                    _textController.clear(); //清除输入框
                  });
                  _textOnChanged(''); //内容清空
                }),
        ],
      ),
    );
  }

  ///输入文字框的实现
  _textInput() {
    return TextField(
      controller: _textController,
      //设置文本控制器
      autofocus: true,
      //自动获取焦点
      onChanged: _textOnChanged,
      style: TextStyle(
        fontSize: 18.0,
        color: Colors.black,
        fontWeight: FontWeight.w300,
      ),
      //输入框的装饰器
      decoration: InputDecoration(
        border: InputBorder.none,
        hintText: widget.hint ?? '',
        hintStyle: TextStyle(fontSize: 15),
        contentPadding: EdgeInsets.fromLTRB(5, 0, 5, 0),
      ),
    );
  }

  ///首页显示的输入框样式
  _homeInput() {
    return _warpTap(
        Container(
          child: Text(
            widget.defultText,
            style: TextStyle(
              fontSize: 13,
              color: Colors.grey,
            ),
          ),
        ),
        widget.inputBoxClick);
  }

  ///监听文字的输入状态 改变清空按钮的状态
  _textOnChanged(String text) {
    if (text.length > 0) {
      setState(() {
        showClear = true;
      });
    } else {
      setState(() {
        showClear = false;
      });
    }
    //回调给外面的回调函数
    if (widget.onChanged != null) {
      widget.onChanged(text);
    }
  }

  _homeFontColor() {
    return widget.searchType == SearchType.homeLight
        ? Colors.black54
        : Colors.white;
  }
}
