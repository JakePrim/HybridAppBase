import 'package:prim_fultter_app/model/common_model.dart';

class GridNavItem {
//  String startColor	String	NonNull
//  String endColor	String	NonNull
//  CommonModel mainItem	Object	NonNull
//  CommonModel item1	Object	NonNull
//  CommonModel item2	Object	NonNull
//  CommonModel item3	Object	NonNull
//  CommonModel item4
  final String startColor;
  final String endColor;
  final CommonModel mainItem;
  final CommonModel item1;
  final CommonModel item2;
  final CommonModel item3;
  final CommonModel item4;

  GridNavItem(
      {this.startColor,
      this.endColor,
      this.mainItem,
      this.item1,
      this.item2,
      this.item3,
      this.item4});

  Map<String, dynamic> toJson() {
    return {
      'startColor': startColor,
      'endColor': endColor,
      'mainItem': mainItem,
      'item1': item1,
      'item2': item2,
      'item3': item3,
      'item4': item4,
    };
  }

  ///获取CommonModel的实例
  factory GridNavItem.fromJson(Map<String, dynamic> json) {
    return GridNavItem(
        startColor: json['startColor'],
        endColor: json['endColor'],
        mainItem: CommonModel.fromJson(json['mainItem']),
        item1: CommonModel.fromJson(json['item1']),
        item2: CommonModel.fromJson(json['item2']),
        item3: CommonModel.fromJson(json['item3']),
        item4: CommonModel.fromJson(json['item4']));
  }
}
