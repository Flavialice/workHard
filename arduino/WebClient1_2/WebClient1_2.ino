#include <SPI.h>
#include <Ethernet.h>

int led = 7;
byte mac[] = { 0x98, 0x4F, 0xEE, 0x01, 0xEA, 0x7C };
IPAddress server(192,168,2,106);

EthernetClient client;
EthernetClient clientRest;

void setup() {
  pinMode(led, OUTPUT); 
  Serial.begin(9600);

  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    for(;;)
      ;
  }
  
   if (clientRest.connect(server, 9090)) {
    //Serial.println("connected");

    clientRest.println("GET http://192.168.2.106/proj/rest/hello/");
    clientRest.println();
  } 
  else {
    Serial.println("connection failed");
  }
}

void loop()
{
  //Serial.println("connecting...");
  delay(1000);
  
  int valoareIluminare = analogRead(A0);
  Serial.println(valoareIluminare, DEC); 
  delay(10);
  if(valoareIluminare<150)
    digitalWrite(led, HIGH);
  else
    digitalWrite(led, LOW); 
  
  int sensorValue = analogRead(A0);
  float voltage = sensorValue * (5.0 / 1023.0);
  //Serial.println(voltage);
  if (client.connect(server, 7070)) {
    client.println(voltage);
    //client.println();
    //char c = client.read();
    //Serial.print(c);
  }
  
  if (clientRest.available()) {
    char c = clientRest.read();
    Serial.print(c);
  }
  {
    client.stop();
    //Serial.println();
    //Serial.println("disconnecting.");
    
    //for(;;)
      //;
  }
  delay(1000);
}

