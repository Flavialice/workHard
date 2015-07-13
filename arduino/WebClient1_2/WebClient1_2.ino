#include <SPI.h>
#include <Ethernet.h>

int led = 7;
int redPin = 9;
int grnPin = 11;
int bluPin = 10;
float Temperatura(void);
float Lumina (void);
float Umiditate(void);
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

  float lumina=Lumina ();
  float um=Umiditate();
  float temp=Temperatura();
  if (client.connect(server, 7070)) {
    /*int valoareIluminare = analogRead(A1);
    client.print(valoareIluminare);
    //delay(10);
    client.println(",temp");
    delay(10);
    //client.println();*/
    
    client.print(lumina);
    client.print(",lumina\n");
    client.print(temp);
    client.print(",temp\n");
    client.print(um);
    client.println(",umiditate\n");
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

float Umiditate (void)
{
   float umiditate=analogRead(A2);
   umiditate=((umiditate/1000)*33)+0.66;
     
   if(umiditate<21){
      analogWrite(redPin, HIGH);  
      analogWrite(grnPin, LOW);      
      analogWrite(bluPin, LOW);
    }
   if(umiditate>=22){
     analogWrite(redPin, LOW);  
     analogWrite(grnPin, HIGH);      
     analogWrite(bluPin, LOW);
   }      
   if(umiditate>=21&&umiditate<22){
     analogWrite(redPin, LOW);  
     analogWrite(grnPin, LOW);      
     analogWrite(bluPin, HIGH); 
   }
 
   //Serial.print("Umiditate: ");
   //Serial.print(umiditate,1);
   //Serial.println("%");
   delay(1000);
   return(umiditate);
}

float Temperatura (void) {
  float voltage, degreesC, degreesF;
  voltage = analogRead(A0) * 0.004882814;
  degreesC = (voltage - 0.5) * 100.0;
  degreesF = degreesC * (9.0/5.0) + 32.0;
  
  /*Serial.print("voltage: ");
  Serial.print(voltage);
  Serial.print("  deg C: ");
  Serial.print(degreesC);
  Serial.print("  deg F: ");
  Serial.println(degreesF);*/
   
  return (degreesC);
}

float Lumina (void){
  int valoareIluminare = analogRead(A1);
  if(valoareIluminare<150)
    digitalWrite(led, HIGH);
  else
    digitalWrite(led, LOW); 
  //Serial.println(valoareIluminare, DEC); 
  delay(20);
  return (valoareIluminare);
}
