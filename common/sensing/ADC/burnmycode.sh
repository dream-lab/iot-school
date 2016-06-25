#!/bin/bash

   mycode=$1
   
	   avr-gcc -g -Os -c -mmcu=atmega32 -o code.o code.c
	   avr-gcc -g -Os -mmcu=atmega32 -o code.elf code.o
	   avr-objcopy -j .text -j .data -O ihex code.elf flash_code.hex
	   sudo avrdude -p m32 -c usbasp -e -U flash:w:flash_code.hex
		echo "###################################################################################"	
		echo "				code loading successful"	
		echo "###################################################################################"	
