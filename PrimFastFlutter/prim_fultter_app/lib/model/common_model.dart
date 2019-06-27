class CommonModel {
//  String icon	String	Nullable
//  String title	String	Nullable
//  String url	String	NonNull
//  String statusBarColor	String	Nullable
//  bool hideAppBar

  final String icon;
  final String title;
  final String url;
  final String statusBarColor;
  final bool hideAppBar;

  CommonModel(
      {this.icon, this.title, this.url, this.statusBarColor, this.hideAppBar});

  /// 需要转换为json字符串时需要此方法
  Map<String, dynamic> toJson() {
    return {
      'icon': icon,
      'title': title,
      'url': url,
      'statusBarColor': statusBarColor,
      'hideAppBar': hideAppBar,
    };
  }

  ///获取CommonModel的实例
  factory CommonModel.fromJson(Map<String, dynamic> json) {
    return CommonModel(
        icon: json['icon'],
        title: json['title'],
        url: json['url'],
        statusBarColor: json['statusBarColor'],
        hideAppBar: json['hideAppBar']);
  }
}
