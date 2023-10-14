#ifndef queue_h
#define queue_h

typedef struct node{
  int data;
  struct node *next;
} NODE;

typedef struct {
  NODE *head;
  NODE *tail;
  int length;
}QUEUE;

int empty(QUEUE *q);
int dequeue(QUEUE *q, int *retval);
int enqueue (int value, QUEUE *q);
#endif
