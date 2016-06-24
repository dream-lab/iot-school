/*
 * Set the SS line to high and wait a bit, so that the Arduino can copy the first 8 LSB into the buffer.
 * Then clock SCK to retrieve the value while leaving SS in a high state.
 * Clock SCK again to receive the 2 MSB (10 bit ADC) after which pull down SS.
 *
 * The weight is transmitted in grams!
 */

#include <SPI.h>

//Initialize SPI slave.
void SlaveInit(void) {
	// Initialize SPI pins.
	pinMode(SCK, INPUT);
	pinMode(MOSI, INPUT);
	pinMode(MISO, OUTPUT);
	pinMode(SS, INPUT);
	// Enable SPI as slave.
	SPCR = (1 << SPE);
}

byte SPItransfer(byte value) {
	SPDR = value;
	while(!(SPSR & (1<<SPIF)));
	delay(10);
	return SPDR;
}

void setup() {
	Serial.begin(115200);
	SlaveInit();
}

void loop() {
	float value;
	float voltage=0;
	float resistance,conductance,weight;

	value = analogRead(0);
	voltage = map(value, 0, 1023, 0, 5000);//  voltage=value*5/1023;
	resistance = 5000 - voltage;     
	resistance *= 2200; // 10K resistor
	resistance /= voltage; // FSR resistance in ohms.

	conductance = 1000000; //We measure in micromhos.
	conductance /= resistance; //Conductance in microMhos.
	weight=conductance/30;
	weight = weight / 9.8 * 0.7 * 1000;	// 1000 for integer convertion

	uint16_t transmitWeight = (uint16_t)weight; // send the weight as an 16 bit integer (formerly a float, then *1000)

	Serial.print("value: ");
	Serial.println(value);
	Serial.print("calculated weight (*1000): ");
	Serial.println(transmitWeight);

	if(digitalRead(SS)) {
		Serial.println("SPI transfer (LSB)");
		SPItransfer((byte)transmitWeight);
		if(digitalRead(SS)) {
			Serial.println("SPI transfer (MSB)");
			transmitWeight >>= 8;
			SPItransfer((byte)transmitWeight);
		}
	}
}

