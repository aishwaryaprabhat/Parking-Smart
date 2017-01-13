#include <Wire.h>
#include <NewPing.h>

unsigned int carparknumber = 2;
unsigned int freelotcounter = 0;
unsigned int distance1;
unsigned int distance2;
unsigned int distance3;
unsigned int distance4;

#define TRIGGER_PIN1 11
#define ECHO_PIN1 12
#define TRIGGER_PIN2 9
#define ECHO_PIN2 10
#define TRIGGER_PIN3 7
#define ECHO_PIN3 8
#define TRIGGER_PIN4 5
#define ECHO_PIN4 6
#define MAX_DISTANCE 5.5

NewPing sonar1(TRIGGER_PIN1, ECHO_PIN1, MAX_DISTANCE);
NewPing sonar2(TRIGGER_PIN2, ECHO_PIN2, MAX_DISTANCE);
NewPing sonar3(TRIGGER_PIN3, ECHO_PIN3, MAX_DISTANCE);
NewPing sonar4(TRIGGER_PIN4, ECHO_PIN4, MAX_DISTANCE);

void setup() {
  Wire.begin();
  Serial.begin(9600);
}

void loop() {
  distance1 = sonar1.ping_median(50);
  distance2 = sonar2.ping_cm(50);
  distance3 = sonar3.ping_median(50);
  distance4 = sonar4.ping_median(50);
  //Serial.print(distance1);
  if (distance1 == 0)
  {
    freelotcounter += 1;
  }
  if (distance2 == 0)
  {
    freelotcounter += 1;
  }
  if (distance3 == 0)
  {
    freelotcounter += 1;
  }
   if (distance4 == 0)
  {
    freelotcounter += 1;
  }
  Serial.print(freelotcounter);
  Wire.beginTransmission(16);
  if (distance1 == 0 || distance2 == 0 || distance3 == 0 || distance4 == 0)
  {
    Wire.write(1);
  }
  else 
  {
    Wire.write(0);
  }
  Wire.write(carparknumber);
  Wire.write(freelotcounter);
  Serial.println(Wire.endTransmission());
  freelotcounter = 0;
  delay(500);
}
