#include "contiki.h"
#include "lib/sensors.h"
#define ADC12MCTL_NO(adcno) ((unsigned char *) ADC12MCTL0_)[adcno]
#include <stdio.h>

static CC_INLINE void 
start(void)
{
  uint16_t c, last;

  /* Set up the ADC. */
  P6DIR = 0xff;
  P6OUT = 0x00;
  
/*
SHT0_6: Sample-and-hold time. These bits define the number of ADC12CLK cycles in the sampling period for registers ADC12MEM0 to ADC12MEM7. 128 S&H clock cycles

MSC: The first rising edge of the SHI signal triggers the sampling timer, but further sample-and-conversions are performed automatically as soon as the prior conversion is completed.

REFON: Reference generator on

*/
ADC12CTL0 = SHT0_6 + SHT1_15 + MSC + REFON; 

/*
SHP:  SAMPCON signal is sourced from the sampling timer 

CONSEQ2: Repeat-single-channel
*/
ADC12CTL1 = SHP + CONSEQ_2;

/* Clear all end-of-sequences */
ADC12MCTL_NO(7) &= ~EOS;

/*The ADC results are written to the ADC12MEMx defined by the CSTARTADDx bits */	
ADC12CTL1 |= (7 * CSTARTADD_1);


ADC12CTL0 |= ADC12ON;				/* Turn on ADC */
ADC12CTL0 |= ENC;                   /* enable conversion */
ADC12CTL0 |= ADC12SC;               /* sample & convert start */
  
  
  printf("ADC conversion\n");
}
/*---------------------------------------------------------------------------*/
static CC_INLINE void
stop(void)
{
  /* stop converting immediately, turn off reference voltage, etc. */

  ADC12CTL0 &= ~ENC;
  /* need to remove CONSEQ_3 if not EOS is configured */
  ADC12CTL1 &= ~CONSEQ_2;

  /* wait for conversion to stop */
  while(ADC12CTL1 & ADC12BUSY);

  /* clear any pending interrupts */
  ADC12IFG = 0;
}
/*---------------------------------------------------------------------------*/
int
sky_sensors_status(uint16_t input, int type)
{
  if(type == SENSORS_ACTIVE) {
    return (adc_on & input) == input;
  }
  if(type == SENSORS_READY) {
    ready |= ADC12IFG & adc_on & input;
    return (ready & adc_on & input) == input;
  }
  return 0;
}
/*---------------------------------------------------------------------------*/
int
sky_sensors_configure(uint16_t input, uint8_t ref, int type, int value)
{

      start();

}
/*---------------------------------------------------------------------------*/
