#include <LiquidCrystal.h>
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);
#include<Time.h>

volatile int BPM;                   // 심박수 저장
volatile int Signal;                // 심장박동센서에서 측정되는 값 저장
volatile int IBI = 600;             // 심박수 측정 시 사용되는 시간 변수(심장이 몇초마다 뛰는지 측정)
volatile boolean Pulse = false;     // 유저의 심박수가 측정되면 True, 아무것도 측정되지 않으면 False

String inData;
boolean stringComplete = false;
String inputString ="";
void setup() {
  Serial.begin(9600);       
    lcd.begin(16, 2);
    lcd.print("Welcome!");
    delay(3000);
    lcd.clear();
    interruptSetup();
}
void loop() {
 int i=0;
 int j=0;
  char commandbuffer[100];
  
  if(Serial.available()){
     delay(100);
     while( Serial.available() && i< 99) {
        commandbuffer[i++] = Serial.read();
     }
     commandbuffer[i++]='\0';
  }
  
   if(Pulse) {
    //Serial.print("*** Heart-Beat Happened *** ");
    Serial.print(BPM);
    lcd.print("BPM: ");
    lcd.println(BPM);
    delay(1000);
    lcd.clear();
  }
  
  if(i>0) 
    for(j=0;j<strlen(commandbuffer);j++){
     if(j==11) {
      lcd.setCursor(0,1);
      }
      lcd.print(commandbuffer[j]);
    }
     delay(5000);
     lcd.clear();
}
