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
  
  if (client.connect(server, 9090)) {
    Serial.println("connected");

    client.println("GET http://192.168.2.106/proj/rest/hello/ HTTP/1.1");
    client.println();
  } 
  else {
    Serial.println("connection failed");
  }
}

void loop()
{
  if (client.available()) {
    client.println(analogRead(A0));
    client.println();
    char c = client.read();
    Serial.print(c);
  }
  
  if (!client.connected()) {
    Serial.println();
    Serial.println("disconnecting.");
    client.stop();

    for(;;)
      ;
  }
}

