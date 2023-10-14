#ifndef monitor_h
#define monitor_h

#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include "queue.h"
 void create_monitor(int persons , int cars);
 int load_car(int car_id);
 void unload_car(int car_id, int person_id);
 void take_ride(int person_id);
#endif
