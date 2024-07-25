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
#include "user_input.h"

int main(void) {
    const int QUIT = 0;
    const int OPTION_1 = 1;

    int menu_sel;

    do {
        printf("Main Menu\n\n"

               "1. Execute\n"
               "0. Quit\n\n");
        menu_sel = read_int();
        printf("\n");

        if (menu_sel == OPTION_1) {
            int value1;
            int value2;
            int value3;
            char order_sel;

            read_ints(&value1, &value2, &value3, &order_sel);

            printf("Input: [%d, %d, %d]\n", value1, value2, value3);

            if (order_sel == 'A') {
                ascending3(&value1, &value2, &value3);
            }
            else {
                descending3(&value1, &value2, &value3);
            }

            printf("Output: [%d, %d, %d]\n\n", value1, value2, value3);
        }
    }
    while (menu_sel != QUIT);

    return 0;
}

