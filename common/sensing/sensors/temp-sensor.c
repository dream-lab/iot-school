#include "contiki.h"
#include "dev/sky-sensors.h"
#include "temp-sensor.h"

#define INPUT_CHANNEL      INCH_7
#define INPUT_REFERENCE    SREF_1
#define TEMP_MEM    ADC12MEM7

const struct sensors_sensor temp_sensor;

/*---------------------------------------------------------------------------*/
static int
value(int type)
{
  return TEMP_MEM;
}
/*---------------------------------------------------------------------------*/
static int
configure(int type, int c)
{
  return sky_sensors_configure(INPUT_CHANNEL, INPUT_REFERENCE, type, c);
}
/*---------------------------------------------------------------------------*/
static int
status(int type)
{
  return sky_sensors_status(INPUT_CHANNEL, type);
}
/*---------------------------------------------------------------------------*/
SENSORS_SENSOR(temp_sensor, TEMP_SENSOR,
               value, configure, status);
