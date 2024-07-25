#include <ctype.h>
#include <stdio.h>
#include <string.h>

#include "string.h"

#include "bool.h"

/*
 * NAME: my_strcasecmp
 * IMPORT(S): string1 (char*), string2 (char*)
 * EXPORT(S): equal (int)
 * PURPOSE: Ignoring case, check if contents of STRING1 and STRING2 are 
 *          equivalent
 * CREATION: 04/09/2020
 * LAST MODIFICATION: 04/09/2020
 */

int my_strcasecmp(char *str1, char *str2) {
    int equal, i;

    if (strlen(str1) != strlen(str2)) {
        equal = FALSE;
    }
    else {
        equal = TRUE;
        for (i = 0; i < strlen(str1); i++) {
            if (tolower(str1[i]) != tolower(str2[i])) {
                equal = FALSE;
            }
        }
    }

    return equal;
}
