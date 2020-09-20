import 'Item.dart';
import 'ShoppingCart.dart';

main(List<String> args) {
  ShoppingCart.withCode(name:'张三',code:'123456')
  ..bookings= [Item(10.0, '苹果'),Item(20.0, '香蕉')]
  ..printInfo();

 ShoppingCart(name:'李四')
 ..bookings = [Item(20.0, '苹果'),Item(30.0, '香蕉')]
 ..printInfo();

  // listAndMap();
  // printInfo(0, isZero);

  // enable1Flags(bold: true,hidden: false);
  // enable1Flags(bold: true);
  // enable2Flags(bold: false);

  // enable3Flags(true,false,true);
  // enable3Flags(true);
  // enable3Flags(true,true);

  // var point = Point.bottom(100);
  // point.printInfo();//(100,0,0)
  // Point.factor = 111;//改变静态变量的值
  // Point.printFactor();//111

  // var emp = new Employee.fromJson({});// in Person  in Employee

  // if(emp is Person){
  //   emp.firstName = 'Bob';
  // }
  // (emp as Person).firstName = 'Bob';

  // var logger = new  Logger('UI');
  // logger.log('Button clicked');

  // var vector = Vector();
  // vector..x = 1..y=2..z=3;//级联运算符,语法糖,相当于vector.x=1 vector.y=2 vector=3
  // vector.printInfo();//(1,2,3)

  // var coordinate = Coordinate();
  // coordinate..x = 1..y=2;
  // coordinate.printInfo();//(1,2)

  // print(vector is Ponit1);//true
  // print(coordinate is Coordinate);//true

  // final x = Vector(3,3);
  // final y = Vector(2,2);
  // final z = Vector(1,1);
  // print(x==(y+z));
}

class Ponit1{
  num x = 0,y =0;
  void printInfo() => print('($x,$y)');
}

class Vector{
  num x,y = 0;
  @override
  void printInfo() {
    print('($x,$y)');//覆写父类的实现
  }

  Vector(this.x,this.y);

  //自定义相加运算符
  Vector operator +(Vector v) => Vector(x+v.x,y+v.y);

  bool operator ==(dynamic v) => x==v.x && y == v.y;
}

//Coordinate 对 Point1 的接口实现
class Coordinate with Ponit1{

}

class Rectangle{
  num width;

  Rectangle(this.width);

  num get left => left + width;
      set left(num value) => left = width + value;
}

class Logger{
  final String name;
  bool mute = false;

  static final Map<String,Logger> _cache = <String,Logger>{};

  factory Logger(String name){
    if(_cache.containsKey(name)){
      return _cache[name];
    }else{
      final logger = Logger._internal(name);
      _cache[name] = logger;
      return logger;
    }
  }

  Logger._internal(this.name);

  void log(String msg){
    if(!mute){
      print(msg);
    }
  }
}

class ImmutablePoint{
  final num x;
  final num y;
  const ImmutablePoint(this.x,this.y);
  static final ImmutablePoint origin = const ImmutablePoint(0, 0);
}

class Person{
  String firstName;
  Person.fromJson(Map data){
    print('in Person');
  }
}

class Employee extends Person{

  Employee.fromJson(Map data) : super.fromJson(data){
    print('in Employee');
  }
}

class Point{
  num x;//成员变量
  num y;
  num z;
  static num factor =0;//静态变量
  Point(this.x,this.y):z = 0;//初始化变量z
  Point.bottom(num x):this(x,0);//重定向构造函数
  void printInfo() => print('($x,$y,$z)');
  static void printFactor() => print('$factor');//静态方法
}

//定义可选命名参数
void enable1Flags({bool bold,bool hidden}) => print('$bold,$hidden');

//定义可选命名参数,并增加默认值
void enable2Flags({bool bold = true,bool hidden = false}) => print('$bold,$hidden');
//定义可选位置参数
void enable3Flags(bool bold,[bool hidden,bool h2 = false]) => print('$bold,$hidden,$h2');


bool isZero(int number) => number == 0;


void printInfo(int number,Function check) => 
  print('$number is Zero:${check(number)}');

void bascis(){
  var number = 0;
  int num2;
  num num1;
  double num3;
  print('number is var:$number,num2 is int:$num2,num2 is num:$num1,num3 is double:$num3');
  bool bool1;
  String str;
  List list;
  Map map;
  print('bool1:$bool1,str:$str,list:$list,map:$map');

  var s= 'cat';
  var s1 = 'this is a uppervased string:${s.toUpperCase()}';
  print(s1);

  var one = int.parse('1');//String -> int
  assert(one == 1);
  var one2 = double.parse('1.1');//String -> double 
  assert(one2 == 1.1);
  String one3 = 1.toString();//int -> String
  assert(one3=='1');
  String one4 = 3.14159.toStringAsFixed(2);
  assert(one4 == '3.14');

   var text = 0.1 + 0.2;
  // assert(text == 0.3);
  print(text);

  final name = 'Bob';
  // name = 'Alice';//final修饰的变量只能被赋值一次
  const bar = 1000;
  // bar = 100;//const 修饰的变量只能被赋值一次

  var s12 = 'this is string';
  var s13 = 'this is';
  var s14 = 'this is string';
  print('s12 == s13:${s12 == s13} s12 == s14:${s12 == s14}');
  var s15 = 'The String' + 'Works even over line breaks';
  var s16 = 'The ' 'concatenation' "hello world";
  print("s15:$s15,s16:$s16");
  var s17 = """ You can create
  multi-line strings like this one.
  """;
  print(s17);

  var s18 = r"In a raw string,even \n isn't special.";//创建原始 raw 字符串
  print(s18);

  var s19 = '$number $bool1 $s18';
  print(s19);
}


void listAndMap(){
  var arr1 = <String>['Tom','Andy','Jack'];
  var arr2 = List<int>.of([1,2,3]);
  arr2.add(499);
  arr2.forEach((v) => print('${v}'));
  arr1.forEach((g) => print('$g'));

  var map1 = <String,String>{'name':'Tome','sex':'male'};
  var map2 = Map<String,String>();
  map2['name'] = 'Tom';
  map2['sex'] = 'male';
  map2.forEach((k,v) => print('$k : $v'));

  var constList = const [1,2,3];
  final constMap = const{
    2 : 'helium',
    10 : 'neon',
    18 : 'argon'
  };
}