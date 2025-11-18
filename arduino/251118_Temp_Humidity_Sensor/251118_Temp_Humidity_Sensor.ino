#include <Wire.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BME280.h>

Adafruit_BME280 bme; // I2C

void setup() {
  Serial.begin(9600);
  bme.begin(0x76);  // Keine Prüfung, keine Ausgabe
}

void loop() {
  float temp = bme.readTemperature();
  float humidity = bme.readHumidity();

  Serial.print("Temp: ");
  Serial.print(temp);
  Serial.print("°C; Humidity: ");
  Serial.print(humidity);
  Serial.println("%");

  delay(1000);
}
