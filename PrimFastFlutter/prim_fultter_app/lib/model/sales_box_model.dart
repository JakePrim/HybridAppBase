import 'package:prim_fultter_app/model/common_model.dart';

class SalesBoxModel {
  //String icon	String	NonNull
  //String moreUrl	String	NonNull
  //CommonModel bigCard1	Object	NonNull
  //CommonModel bigCard2	Object	NonNull
  //CommonModel smallCard1	Object	NonNull
  //CommonModel smallCard2	Object	NonNull
  //CommonModel smallCard3	Object	NonNull
  //CommonModel smallCard4

  final String icon;
  final String moreUrl;
  final CommonModel bigCard1;
  final CommonModel bigCard2;
  final CommonModel smallCard1;
  final CommonModel smallCard2;
  final CommonModel smallCard3;
  final CommonModel smallCard4;

  SalesBoxModel(
      {this.icon,
      this.moreUrl,
      this.bigCard1,
      this.bigCard2,
      this.smallCard1,
      this.smallCard2,
      this.smallCard3,
      this.smallCard4});

  factory SalesBoxModel.formJson(Map<String, dynamic> json) {
    return SalesBoxModel(
      icon: json['icon'],
      moreUrl: json['moreUrl'],
      bigCard1: CommonModel.fromJson(json['bigCard1']),
      bigCard2: CommonModel.fromJson(json['bigCard2']),
      smallCard1: CommonModel.fromJson(json['smallCard1']),
      smallCard2: CommonModel.fromJson(json['smallCard2']),
      smallCard3: CommonModel.fromJson(json['smallCard3']),
      smallCard4: CommonModel.fromJson(json['smallCard4']),
    );
  }
}
