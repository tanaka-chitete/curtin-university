/*
 * NAME: numbers
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1000
 * PURPOSE: Act as a test harness for all functions contained within orders
 * CREATION: 26/08/2020
 * LAST MODIFICATION: 26/08/2020
 */

#include <stdio.h>

#include "numbers.h"
#include "orders.h"
#include "scan.h"

int main(void) {
    const int QUIT = 0;

    int menu_sel;

    do {
        printf("Main Menu\n\n"

               "1. Execute ascending2\n"
               "2. Execute ascending3\n"
               "3. Execute descending2\n"
               "4. Execute descending3\n"
               "0. Quit\n\n");
        menu_sel = scan_int();
        printf("\n");

        switch (menu_sel) {
            case 1:
                ascending2_wrapper();
                break;
            case 2:
                ascending3_wrapper();
                break;
            case 3:
                descending2_wrapper();
                break;
            case 4:
                descending3_wrapper();
                break; 
        }
    }
    while (menu_sel != QUIT);

    return 0;
}

/*
 * NAME: ascending2_wrapper
 * IMPORT(S): NONE
 * EXPORT(S): NONE
 * PURPOSE: Act as a wrapper for ascending2
 * CREATION: 26/08/2020
 * LAST MODIFICATION: 26/08/2020
 */

void ascending2_wrapper(void) {
    int value1;    
    int value2;

    int* pointer1;
    int* pointer2;

    value1 = scan_int();
    value2 = scan_int();

    pointer1 = &value1;
    pointer2 = &value2;

    printf("Input: [%d, %d]\n", *pointer1, *pointer2);
    ascending2(pointer1, pointer2);
    printf("Output: [%d, %d]\n\n", *pointer1, *pointer2);
}

/*
 * NAME: ascending3_wrapper
 * IMPORT(S): NONE
 * EXPORT(S): NONE
 * PURPOSE: Act as a wrapper for ascending3
 * CREATION: 26/08/2020
 * LAST MODIFICATION: 26/08/2020
 */

void ascending3_wrapper(void) {
    int value1;
    int value2;
    int value3;

    int* pointer1;
    int* pointer2;
    int* pointer3;

    value1 = scan_int();
    value2 = scan_int();
    value3 = scan_int();

    pointer1 = &value1;
    pointer2 = &value2;
    pointer3 = &value3;

    printf("Input: [%d, %d, %d]\n", *pointer1, *pointer2, *pointer3);
    ascending3(pointer1, pointer2, pointer3);
    printf("Output: [%d, %d, %d]\n\n", *pointer1, *pointer2, *pointer3);
}

/*
 * NAME: descending2_wrapper
 * IMPORT(S): NONE
 * EXPORT(S): NONE
 * PURPOSE: Act as a wrapper for descending2
 * CREATION: 26/08/2020
 * LAST MODIFICATION: 26/08/2020
 */

void descending2_wrapper(void) {
    int value1;
    int value2;

    int* pointer1; 
    int* pointer2;

    value1 = scan_int();
    value2 = scan_int();

    pointer1 = &value1;
    pointer2 = &value2;

    printf("Input: [%d, %d]\n", *pointer1, *pointer2);
    descending2(pointer1, pointer2);
    printf("Output: [%d, %d]\n\n", *pointer1, *pointer2);
}

/*
 * NAME: descending3_wrapper
 * IMPORT(S): NONE
 * EXPORT(S): NONE
 * PURPOSE: Act as a wrapper for descending3
 * CREATION: 26/08/2020
 * LAST MODIFICATION: 26/08/2020
 */

void descending3_wrapper(void) {
    int value1;
    int value2;
    int value3;

    int* pointer1;
    int* pointer2;
    int* pointer3;

    value1 = scan_int();
    value2 = scan_int();
    value3 = scan_int();

    pointer1 = &value1;
    pointer2 = &value2;
    pointer3 = &value3;

    printf("Input: [%d, %d, %d]\n", *pointer1, *pointer2, *pointer3);
    descending3(pointer1, pointer2, pointer3);
    printf("Output: [%d, %d, %d]\n\n", *pointer1, *pointer2, *pointer3);
}
