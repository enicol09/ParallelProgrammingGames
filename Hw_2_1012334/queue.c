/** @file monitor.c
 *  @brief This is a program which implemtents Hw_2 , the queue part.
 *
 *  @author Elia Nicolaou 1012334
 *  @version 1
 *  @bug No know bugs.
 */

#include <stdio.h>
#include <stdlib.h>
#include "queue.h"

int dequeue(QUEUE *q, int *retval)
{
	NODE *p = NULL;	// copy pointer used for free()
	if ((q == NULL) || (q->head == NULL)){
	  printf("Sorry, queue is empty \n");
	  return EXIT_FAILURE;
	}

	if (retval == NULL){
	  printf("Retval is null");
	  return EXIT_FAILURE;
	}

	p = q->head;
	*retval = q->head->data;
	q->head = q->head->next;
	free(p);
	--(q->length);
	if (q->length == 0){
	  q->tail = NULL;
	}

	return EXIT_SUCCESS;
}

int enqueue(int value, QUEUE *q)
{
	NODE *p = NULL;
	if (q == NULL){
	   return EXIT_FAILURE;
	}

	p = (NODE*)malloc(sizeof(NODE));
	if (p == NULL){
	 printf("System out of memory...\n");
	 return EXIT_FAILURE;
	}

	p->data = value;
	p->next = NULL;
	if (q->length == 0)
	 q->head = q->tail = p;
	else{
	// append on end
	 q->tail->next = p;
	 q->tail = p;
	}

	 (q->length)++;
	return EXIT_SUCCESS;
}

int empty(QUEUE *q){
 if(q->length == 0){
  return 1;
 } 
  return 0;
 }
