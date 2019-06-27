class SearchModel {
  final List<SearchItem> data;

  //记录传递搜索关键字
  String keyWord;

  SearchModel({this.data});

  Map<String, dynamic> toJson() {
    return {'data': data};
  }

  factory SearchModel.fromJson(Map<String, dynamic> json) {
    var data = json['data'] as List;
    List<SearchItem> datas = data.map((i) => SearchItem.fromJson(i)).toList();
    return SearchModel(data: datas);
  }
}

class SearchItem {
  final String word; //	String	Nullable //xx酒店
  final String type; //	String	Nullable //hotel
  final String price; //	String	Nullable //实时计价
  final String star; //	String	Nullable //豪华型
  final String zonename; //	String	Nullable //虹桥
  final String districtname; //	String	Nullable //上海
  final String url;

  SearchItem(
      {this.word,
      this.type,
      this.price,
      this.star,
      this.zonename,
      this.districtname,
      this.url});

  Map<String, dynamic> toJson() {
    return {
      'word': word,
      'type': type,
      'price': price,
      'star': star,
      'zonename': zonename,
      'districtname': districtname,
      'url': url,
    };
  }

  factory SearchItem.fromJson(Map<String, dynamic> json) {
    return SearchItem(
      word: json['word'],
      type: json['type'],
      price: json['price'],
      star: json['star'],
      zonename: json['zonename'],
      districtname: json['districtname'],
      url: json['url'],
    );
  }
}
