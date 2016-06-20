#include <stdio.h>
#include "contiki.h"
#include "sys/etimer.h"
#include "dev/temp-sensor.h"


PROCESS(temp_sensor_process,"temperature sensor");
AUTOSTART_PROCESSES(&temp_sensor_process);

PROCESS_THREAD(temp_sensor_process, ev, data)
{

PROCESS_BEGIN();

static struct etimer et;
static uint64_t temperature;
static uint64_t val;
printf("starting Sensor Example\n");

while(1)
{
	etimer_set(&et,CLOCK_SECOND*3);
     	SENSORS_ACTIVATE(temp_sensor);

	PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&et));

temperature = (temp_sensor.value(TEMP_SENSOR));
printf("temperature is %lu\n",temperature);

etimer_reset(&et);
SENSORS_DEACTIVATE(temp_sensor);
}
PROCESS_END();

}




