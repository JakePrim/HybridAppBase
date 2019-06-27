import 'package:prim_fultter_app/model/common_model.dart';
import 'package:prim_fultter_app/model/config_model.dart';
import 'package:prim_fultter_app/model/grid_nav_model.dart';
import 'package:prim_fultter_app/model/sales_box_model.dart';

class HomeModel {
//  ConfigModel config	Object	NonNull
//  List<CommonModel> bannerList	Array	NonNull
//  List<CommonModel> localNavList	Array	NonNull
//  GridNavModel gridNav	Object	NonNull
//  List<CommonModel> subNavList	Array	NonNull
//  SalesBoxModel salesBox
  final ConfigModel config;
  final List<CommonModel> bannerList;
  final List<CommonModel> localNavList;
  final GridNavModel gridNav;
  final List<CommonModel> subNavList;
  final SalesBoxModel salesBox;

  HomeModel(
      {this.config,
      this.bannerList,
      this.localNavList,
      this.gridNav,
      this.subNavList,
      this.salesBox});

  Map<String, dynamic> toJson() {
    return {
      'config': config,
      'bannerList': bannerList,
      'localNavList': localNavList,
      'gridNav': gridNav,
      'subNavList': subNavList,
      'salesBox': salesBox,
    };
  }

  factory HomeModel.fromJson(Map<String, dynamic> json) {
    var localNavListJson = json['localNavList'] as List; //转换为list
    List<CommonModel> localNavList = localNavListJson
        .map((i) => CommonModel.fromJson(i))
        .toList(); //然后将list 转换为对应

    var bannerListJson = json['bannerList'] as List; //转换为list
    List<CommonModel> bannerList = bannerListJson
        .map((i) => CommonModel.fromJson(i))
        .toList(); //然后将list 转换为对应

    var subNavListJson = json['subNavList'] as List; //转换为list
    List<CommonModel> subNavList = subNavListJson
        .map((i) => CommonModel.fromJson(i))
        .toList(); //然后将list 转换为对应

    return HomeModel(
      config: ConfigModel.fromJson(json['config']),
      bannerList: bannerList,
      localNavList: localNavList,
      gridNav: GridNavModel.formJson(json['gridNav']),
      subNavList: subNavList,
      salesBox: SalesBoxModel.formJson(json['salesBox']),
    );
  }
}
