#ifndef FALSE
#define FALSE 0
#endif

#ifndef TRUE
#define TRUE !FALSE
#endif

#ifndef BETWEEN
#define BETWEEN(low, value, high) ((low) <= (value) && (value) <= (high))
#endif 
