/*
 * NAME: orders
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1000
 * PURPOSE: Provide an introduction to pointers
 * CREATION: 26/08/2020
 * LAST MODIFICATION: 26/08/2020
 */

#include <stdio.h>

#include "orders.h"

/*
 * NAME: ascending2
 * IMPORT(S): pointer1 (int*), pointer2 (int*)
 * EXPORT(S): NONE
 * PURPOSE: Place the smaller int pointed to by imports in POINTER1 and the
 *          larger in POINTER2 
 * CREATION: 26/08/2020
 * LAST MODIFICATION: 26/08/2020
 */

void ascending2(int* pointer1, int* pointer2) {
    int value_temp;
    
    int *pointer_temp;

    value_temp = 0;
    pointer_temp = &value_temp;    

    if (*pointer1 > *pointer2) {
        *pointer_temp = *pointer1;
        *pointer1 = *pointer2;
        *pointer2 = *pointer_temp;
    }
}

/*
 * NAME: ascending3
 * IMPORT(S): pointer1 (int*), pointer2 (int*), pointer3 (int*)
 * EXPORT(S): NONE
 * PURPOSE: Place the smallest int pointed to by imports in POINTER1, the median
 *          in POINTER2 and the largest in POINTER3
 * CREATION: 26/08/2020
 * LAST MODIFICATION: 26/08/2020
 */

void ascending3(int* pointer1, int* pointer2, int* pointer3) {
    ascending2(pointer1, pointer3);
    ascending2(pointer1, pointer2);
    ascending2(pointer2, pointer3);
}

/*
 * NAME: descending2
 * IMPORT(S): pointer1 (int*), pointer2 (int*)
 * EXPORT(S): NONE
 * PURPOSE: Place the larger int pointed to by imports in POINTER1 and the
 *          smaller in POINTER2 
 * CREATION: 26/08/2020
 * LAST MODIFICATION: 26/08/2020
 */

void descending2(int* pointer1, int* pointer2) {
    int value_temp;

    int *pointer_temp;

    value_temp = 0;
    pointer_temp = &value_temp;    

    if (*pointer1 < *pointer2) {
        *pointer_temp = *pointer1;
        *pointer1 = *pointer2;
        *pointer2 = *pointer_temp;
    }
}

/*
 * NAME: descending3
 * IMPORT(S): pointer1 (int*), pointer2 (int*), pointer3 (int*)
 * EXPORT(S): NONE
 * PURPOSE: Place the largest int pointed to by imports in POINTER1, the median
 *          in POINTER2 and the smallest in POINTER3
 * CREATION: 26/08/2020
 * LAST MODIFICATION: 26/08/2020
 */

void descending3(int* pointer1, int* pointer2, int* pointer3) {
    descending2(pointer1, pointer3);
    descending2(pointer1, pointer2);
    descending2(pointer2, pointer3);
}
