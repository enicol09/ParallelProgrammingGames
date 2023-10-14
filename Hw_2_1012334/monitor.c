/** @file monitor.c
 *  @brief This is a program which implemtents Hw_2 , the monitor part.
 *
 *  @see qeueue.c 
 *  @author Elia Nicolaou 1012334
 *  @version 1
 *  @bug No know bugs.
 */
#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include "queue.h"
#include "monitor.h"

//variables
static pthread_once_t is_initialized = PTHREAD_ONCE_INIT;
static int cars_num;
static int persons_num;
static pthread_mutex_t mLock;
QUEUE *passengers;
pthread_cond_t *persons_cond;
pthread_cond_t car_cond;

void create_monitor(int persons,int cars){
 printf("- Creating monitor - \n\n" );
 cars_num = cars;
 persons_num = persons;

//passengers queue initialization
 passengers = (QUEUE*)malloc(sizeof(QUEUE)); 
 passengers->head = NULL;
 passengers->tail = NULL;
 passengers->length= 0;
 if(passengers == NULL ){
    printf("System out of memory...\n");
 exit(1); }
 
 //persons c.v initiliazation.
 persons_cond = (pthread_cond_t*)malloc(sizeof(pthread_cond_t) * persons);
 if(persons_cond == NULL ) {
  printf("System out of memory...\n");
exit(1); }

 int i;
 for(i=0 ; i<persons;i++){
    pthread_cond_init(&persons_cond[i],NULL);
    }

 //initialize mutex
 pthread_mutex_init(&mLock,NULL);

 //initialize c.v car
 pthread_cond_init(&car_cond,NULL);
 
  } 

 //load the passenger to the car.
 int load_car(int car_id){
 //load passenger and remember their id
  pthread_mutex_lock(&mLock);
 //oso to passengers queue en empty , perimene ta cars 
   while(empty(passengers)){ //an ine diladi 1 (busy)
    pthread_cond_wait(&car_cond,&mLock);
  } 
   
  //epistrefo to proto
  int person_id;
  dequeue(passengers,&person_id);
   
  printf("- Person with id : %d , is in car : %d \n\n", person_id , car_id );
  pthread_mutex_unlock(&mLock);
  return person_id;
 }

 //take the ride
 void take_ride(int person_id){
  pthread_mutex_lock(&mLock);
  enqueue(person_id,passengers);
  pthread_cond_signal(&car_cond);
  pthread_cond_wait(&persons_cond[person_id],&mLock);

 pthread_mutex_unlock(&mLock);

 }

 //ride completed
 void unload_car(int car_id, int person_id){
   pthread_mutex_lock(&mLock);
   pthread_cond_signal(&persons_cond[person_id]);
   printf("- Person with id : %d , has complete his ride with car : %d \n\n", person_id,car_id);
   pthread_mutex_unlock(&mLock);
 }






