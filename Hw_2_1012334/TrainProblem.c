/** @file TrainProblem.c
 *  @brief This is a program which implemtents Hw_2 , the main program.
 *  
 *  The program gets in the command line two parameters m and n
 * , persons and cars number of threads.
 *  
 *  @see monitor.c 
 *  @author Elia Nicolaou 1012334
 *  @version 1
 *  @bug No know bugs.
 */
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include "monitor.h"

//number of threads for car / person
static int Car_threads;
static int Person_threads;

//procedure for car , calls unload
void *Car_procedure(void *id){
  int car_id = *(int*)id;
   
  while(1){
   int person_id = load_car(car_id);
   unload_car(car_id,person_id);
   }
 }

//procedure for person, calls take_ride
void *Person_procedure(void *id){
  int person_id = *(int*)id;
  take_ride(person_id);
  printf("- The person with id : %d has complete his ride :)!!\n\n " , person_id);
  pthread_exit(NULL); 
}

//main
int main(int argc, char * argv[]) {

 int i;
  
 //check for parameters
 if (argc < 3)
      printf("\n Sorry :( Try again by giving the right parametes - N and number of threads");
  else {
    Person_threads = atoi(argv[1]);
    Car_threads = atoi(argv[2]);
    
    if(Person_threads <0 || Car_threads <0){
      printf(" -- We cannot support negative numbers -- " );
      exit(1); } 
  }

  printf("\n --- Welcome to TrainProblem --- \n\n\n");

  //create person , and car threads
  int myid_person[Person_threads];
  pthread_t tid_person[Person_threads];

  int myid_car[Car_threads];
  pthread_t tid_car[Car_threads];
  
  //create the monitor
  create_monitor(Person_threads,Car_threads);

  //creating car threads - calling car procedure
   printf("- Creating Car threads... - \n\n");
    for (i = 0; i < Car_threads; i++) {
      myid_car[i] = i;
      pthread_create(&tid_car[i], NULL, &Car_procedure, (void *)&myid_car[i]);
    }
  
  //creating person threads - calling person threads
    printf("- Creating Person threads... -\n\n");
    for (i = 0; i < Person_threads; i++) {
      myid_person[i] = i;
      pthread_create(&tid_person[i], NULL, &Person_procedure, (void *)&myid_person[i]);
    }

   //waiting person threads to complete
    printf("- Waiting for all Person threads to be complete... - \n\n");
    for (i = 0; i < Person_threads; i++) {
      pthread_join(tid_person[i], NULL);
    }

    printf("\n\n Thank you for using my program! :)\n\n");
    
    return 0;

}
