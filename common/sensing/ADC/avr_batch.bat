avr-gcc -g -Os -c -mmcu=atmega32a -o %~1.o %~1.c
avr-gcc -g -Os -mmcu=atmega32a -o %~1.elf %~1.o
avr-objcopy -j .text -j .data -O ihex %~1.elf flash_%~1.hex
avrdude -p m32 -c usbasp -e -U flash:w:flash_%~1.hex