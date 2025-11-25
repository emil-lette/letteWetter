from machine import Pin, ADC, I2C
from time import sleep
from dht import DHT11
from lcd_api import LcdApi
from i2c_lcd import I2cLcd

# --- I2C Setup ---
i2c = I2C(1, scl=Pin(3), sda=Pin(2), freq=400000)  # I2C1, SCL=GP3, SDA=GP2

# LCD-Adresse herausfinden, Standard oft 0x27 oder 0x3F
I2C_ADDR = 0x27  
lcd = I2cLcd(i2c, I2C_ADDR, 2, 16)  # 2 Zeilen, 16 Zeichen

sensor = DHT11(Pin(14))
ldr = ADC(Pin(26))

sleep(2)

while True:
    # DHT11 
    sensor.measure()
    temp = sensor.temperature()
    hum = sensor.humidity()

    # licht sensor
    #licht_65000 = ldr.read_u16()
    #licht = (licht_65000 * 25) // 65536 + 1

    # i2c 
    lcd.clear()
    lcd.move_to(0, 0)
    lcd.putstr("temp: {}°C".format(temp))
    lcd.move_to(0, 1)
    lcd.putstr("Humidity: {}%".format(hum))
    #lcd.putstr("Licht: {}".format(licht))

    # println
    print("Temperatur:", temp, "°C")
    print("Feuchtigkeit:", hum, "%")
   
    sleep(1)

