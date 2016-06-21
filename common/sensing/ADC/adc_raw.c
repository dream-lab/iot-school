#ifndef F_CPU
#define F_CPU 16000000UL
#endif

#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>
#include <compat/deprecated.h>

char ret;


void InitADC()
{
ADMUX=(1<<REFS0);                         // For Aref=AVcc;
ADCSRA=(1<<ADEN)|(1<<ADPS2)|(1<<ADPS1)|(1<<ADPS0); //Prescalar div factor
}

uint16_t ReadADC(uint8_t ch)
{
   //Select ADC Channel ch must be 0-7
   ch=ch&0b00000111;
   ADMUX|=ch;

   //Start Single conversion
   ADCSRA|=(1<<ADSC);

   //Wait for conversion to complete
   while(!(ADCSRA & (1<<ADIF)));

   //Clear ADIF by writing one to it
   //Note you may be wondering why we have write one to clear it
   //This is standard way of clearing bits in io as said in datasheets.
   //The code writes '1' but it results in setting bit to '0' !!!

   ADCSRA|=(1<<ADIF);

   return(ADC);
   //return(ADCH);
}


void UART_TxChar(unsigned char data)
{
	while(!(UCSRA & (1<<UDRE)));//udre not actual value of udre but position of udre bit in register
	UDR = data;
}

void UART_TxStr(const char *str)
{
    int a=0;
    while(str[a]!='\0')
	UART_TxChar(str[a++]);
}

void UART_init(unsigned int baud)
{
	UCSRA = 0; //pg.155
	UCSRB = (1<<TXEN) | (0<<RXEN) | (0<<RXCIE); //rx interrupt enable pg.156
	UCSRC = 1<<URSEL | 1<<UCSZ1 | 1<<UCSZ0 ; //character size 8bit pg.158
	UBRRL = 0b00000011; // 250k
	UBRRH = 0b00000000;
	}

int main(void)
{
    uint16_t adc_result;
	int i;
	char value[10];
	sbi(DDRB, 4);
	sbi(DDRC, 5);
	cbi(PORTB,4);
	sbi(PORTC,5);	
    UART_init(250000);
	InitADC();
	sei();
	
	
	
	while(1)
	{
	adc_result=ReadADC(2);           // Read Analog value from channel-2
    // Voltage = adc_result*5/1024
	// Temperature = (V  -  1035 mV)/(-5.5 (mV/oC))
	itoa(adc_result,value,10);
	for(i=0;i<=2;i++){
	UART_TxChar(value[i]);}
	UART_TxStr("\n\r\0");
	PORTB ^= (1<<PB4);	
	_delay_ms(100);
	}
	
}


ISR(USART_RXC_vect)
{

}









