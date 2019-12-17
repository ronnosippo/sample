# sample

```
#include <M5StickC.h>
#include <WiFi.h>
#include <WiFiMulti.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>

#include "DHT.h"
#define DHTPIN 26
#define DHTTYPE DHT11 // DHT 11

DHT dht(DHTPIN, DHTTYPE);

const char *ssid = "xxx";
const char *password = "";

const int capacity = JSON_OBJECT_SIZE(2);
StaticJsonDocument<capacity> json_request;
const char *host = "xxxxx";

void setup() {
  M5.begin();
  dht.begin();
  
  M5.Axp.ScreenBreath(9);
  M5.Lcd.setRotation(3);
  M5.Lcd.fillScreen(BLACK);
  M5.Lcd.setTextSize(2);

  M5.Lcd.println("[M5StickC]");
  delay(1000);

  M5.Lcd.println("start Serial");
  Serial.begin(115200);
  while (!Serial);
  delay(100);

  M5.Lcd.println("start WiFi");
  Serial.print("start Wifi");
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(100);
  }
  Serial.println("");
  
}

void loop() {
  float h = dht.readHumidity();
  float t = dht.readTemperature();

  if (isnan(h) || isnan(t)) {
    Serial.println("Failed to read from DHT sensor!");
    return;
  }
  
  json_request["temperature"] = t;
  json_request["humidity"] = h;

//  serializeJson(json_request, Serial);
  Serial.println("");
  char buffer[255];
  serializeJson(json_request, buffer, sizeof(buffer));

  HTTPClient http;
  http.begin(host,8080,"/api/leaflet-monitor");
  http.addHeader("Content-Type", "application/json");
  int status_code = http.POST((uint8_t*)buffer, strlen(buffer));
  Serial.printf("status_code=%d\r\n", status_code);
  if( status_code != 200 ){
    Serial.println("error");
  }
  http.end();

  delay(5000);
}
```
