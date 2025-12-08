#include <DHT.h>

#define DHTPIN 5        // PIN, an dem dein B24-Sensor hängt
#define DHTTYPE DHT11   // Der B24 verwendet den DHT11

DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(9600);
  dht.begin();
}

void loop() {
  float humidity = dht.readHumidity();
  float temp = dht.readTemperature();

  // Wenn Auslesung fehlschlägt, liefert dht.read… oft NaN
  if (isnan(temp) || isnan(humidity)) {
    Serial.println("Fehler beim Auslesen des DHT_Sensors");
  } else {
    Serial.print("Temp: ");
    Serial.print(temp);
    Serial.print("°C; Humidity: ");
    Serial.print(humidity);
    Serial.println("%");
  }

  delay(2000); // DHT11 braucht ~2 Sekunden zwischen den Messungen
}
