class Bicycle{
  int cadence;
  int speed;
  int gear;

  Bicycle(this.cadence,this.gear,this.speed);

}

void main() {
  var bike = new Bicycle(2, 0, 1);
  print(bike);
}