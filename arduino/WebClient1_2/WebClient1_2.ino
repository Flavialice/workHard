#include <SPI.h>
#include <Ethernet.h>

int led = 7;
byte mac[] = { 0x98, 0x4F, 0xEE, 0x01, 0xEA, 0x7C };
IPAddress server(192,168,2,106);
EthernetClient client;

void setup() {
  pinMode(led, OUTPUT); 
  Serial.begin(9600);
  
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    for(;;)
      ;
  }
}

void loop()
{
  //Serial.println("connecting...");
  delay(1000);
  
  int valoareIluminare = analogRead(A1);
  //Serial.println(valoareIluminare, DEC); 
  delay(20);
  if(valoareIluminare<150)
    digitalWrite(led, HIGH);
  else
    digitalWrite(led, LOW); 
  
  //int sensorValue = analogRead(A0);
  //float voltage = sensorValue * (5.0 / 1023.0);
  //Serial.println(voltage);
  if (client.connect(server, 7070)) {
    int valoareIluminare = analogRead(A1);
    client.print(valoareIluminare);
    //delay(10);
    client.println(",temp");
    delay(10);
    //client.println();
  }
  for(int i=0;i<100;i++)
    if (client.available())
    {
      char c = client.read();
      Serial.print(c);
        if(i==0&&c=='t')
          Serial.println("bulangeala");
    }
     
    //Serial.println();
    //Serial.println("disconnecting.");
    //for(;;)
      //;

  delay(500);
  client.stop();
}

