import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';
import 'package:prim_fultter_app/model/search_model.dart';

class SearchDao {
  static Future<SearchModel> fetch(String url, String keyWord) async {
    final response = await http.get(url);
    if (response.statusCode == 200) {
      Utf8Decoder utf8decoder = Utf8Decoder();
      var result = json.decode(utf8decoder.convert(response.bodyBytes));
      //当服务端返回的内容与当前输入的内容一致时才进行渲染 优化搜索
      SearchModel searchModel = SearchModel.fromJson(result);
      searchModel.keyWord = keyWord;
      return searchModel;
    } else {
      return null;
    }
  }
}
