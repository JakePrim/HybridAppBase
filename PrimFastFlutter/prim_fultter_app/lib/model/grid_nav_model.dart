import 'package:prim_fultter_app/model/grid_nav_item.dart';

class GridNavModel {
  final GridNavItem hotel;
  final GridNavItem flight;
  final GridNavItem travel;

  GridNavModel({this.hotel, this.flight, this.travel});

  Map<String, dynamic> toJson() {
    return {
      'hotel': hotel,
      'flight': flight,
      'travel': travel,
    };
  }

  factory GridNavModel.formJson(Map<String, dynamic> json) {
    return GridNavModel(
      hotel: GridNavItem.fromJson(json['hotel']),
      flight: GridNavItem.fromJson(json['flight']),
      travel: GridNavItem.fromJson(json['travel']),
    );
  }
}
