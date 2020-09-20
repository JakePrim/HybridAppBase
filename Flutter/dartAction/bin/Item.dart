import 'Meta.dart';
///定义商品item类
class Item extends Meta{
  Item(price,name):super(price,name);

  Item operator+(Item item) => Item(price+item.price, name+item.name);
}