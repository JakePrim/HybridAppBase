import 'Meta.dart';
import 'Item.dart';
import 'PrintHelper.dart';
///购物车类
class ShoppingCart extends Meta with PrintHelper{
  DateTime date;//日期
  String code;//优惠码
  List<Item> bookings;//购买的商品

  double get price => bookings.reduce((value,element) => value + element).price;

  ShoppingCart({name}):this.withCode(name:name,code:null);

  ShoppingCart.withCode({name,this.code}):date = DateTime.now(),super(0,name);

  @override
  getInfo() => '''购物车信息
  ---------------------------
  用户名:$name
  优惠码:${code??' 没有 '}
  总价:$price
  日期:$date
  数量:${bookings.length}
  ---------------------------
   ''';
}