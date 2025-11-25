from lcd_api import LcdApi
from machine import I2C
from time import sleep

# PCF8574 I2C backpack default bits
MASK_RS = 0x01
MASK_RW = 0x02
MASK_E  = 0x04
MASK_BACKLIGHT = 0x08

class I2cLcd(LcdApi):
    """Implements a HD44780 character LCD connected via PCF8574 on I2C"""

    def __init__(self, i2c, i2c_addr, num_lines, num_columns):
        self.i2c = i2c
        self.i2c_addr = i2c_addr
        self.num_lines = num_lines
        self.num_columns = num_columns
        self.backlight = MASK_BACKLIGHT
        self._write_init_nibble(0x03)
        sleep(0.005)
        self._write_init_nibble(0x03)
        sleep(0.005)
        self._write_init_nibble(0x03)
        sleep(0.005)
        self._write_init_nibble(0x02)  # 4-bit mode
        super().__init__()
        self.display_on = self.LCD_DISPLAYON | self.LCD_CURSOROFF | self.LCD_BLINKOFF
        self.command(self.LCD_FUNCTIONSET | self.LCD_4BITMODE | self.LCD_2LINE | self.LCD_5x8DOTS)
        self.command(self.LCD_DISPLAYCONTROL | self.display_on)
        self.clear()
        self.command(self.LCD_ENTRYMODESET | self.LCD_ENTRYLEFT)

    def _write_init_nibble(self, nibble):
        self.i2c.writeto(self.i2c_addr, bytes([(nibble << 4) | self.backlight]))
        self._pulse_enable((nibble << 4) | self.backlight)

    def _write_byte(self, data):
        self.i2c.writeto(self.i2c_addr, bytes([data | self.backlight]))
        self._pulse_enable(data | self.backlight)

    def _pulse_enable(self, data):
        self.i2c.writeto(self.i2c_addr, bytes([data | MASK_E]))
        sleep(0.0001)
        self.i2c.writeto(self.i2c_addr, bytes([data & ~MASK_E]))
        sleep(0.0001)

    def command(self, cmd):
        self._write_byte(cmd & 0xF0)
        self._write_byte((cmd << 4) & 0xF0)

    def write_char(self, charvalue):
        self._write_byte(MASK_RS | (charvalue & 0xF0))
        self._write_byte(MASK_RS | ((charvalue << 4) & 0xF0))

    def move_to(self, col, row):
        row_offsets = [0x00, 0x40, 0x14, 0x54]
        self.command(self.LCD_SETDDRAMADDR | (col + row_offsets[row]))
