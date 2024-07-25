#include "colourlogic.h"

#include "bool.h"
#include "colourcodes.h"
#include "string.h"

/*
 * NAME: is_valid_colour
 * IMPORT(S): col (char*)
 * EXPORT(S): valid (int)
 * PURPOSE: Check if COLOUR is supported by PATTERNMATCH
 * CREATION: 03/09/2020
 * LAST MODIFICATION: 19/09/2020
 */

int is_valid_colour(char* col) {
    char* cols[] = {"red", "green", "blue", "yellow", "magenta", "cyan"};
    int i, valid, num_colours;

    i = 0;
    valid = FALSE;
    num_colours = 6;
    while (!valid && i < num_colours) {
        if (my_strcasecmp(col, cols[i])) {
            valid = TRUE;
        }
        i++;
    }

    return valid;
}

/*
 * NAME: to_body_code
 * IMPORT(S): col (char*)
 * EXPORT(S): col_body_code (char)
 * PURPOSE: Convert COL into the ASCII escape code for colouring the body of 
 *          strings using COL_BODY_CODE
 * CREATION: 08/09/2020
 * LAST MODIFICATION: 08/09/2020
 */

char *to_body_code(char *col) {    
    char col_first_char;
    char *col_body_code;

    col_first_char = col[0];
    switch (col_first_char) {
        case 'r':
            col_body_code = red_body;
            break;
        case 'g':
            col_body_code = grn_body;
            break;
        case 'b':
            col_body_code = blu_body;
            break;
        case 'y':
            col_body_code = yel_body;
            break;
        case 'm':
            col_body_code = mag_body;
            break;
        case 'c':
            col_body_code = cyn_body;
            break;
    }

    return col_body_code;
}

/*
 * NAME: to_underline_code
 * IMPORT(S): col (char*)
 * EXPORT(S): col_ul_code (char*)
 * PURPOSE: Convert COL into the ASCII escape code for colouring the body and 
 *          underlining of strings using COL_UL_CODE
 * CREATION: 08/09/2020
 * LAST MODIFICATION: 21/09/2020
 */

char *to_underline_code(char *col) {    
    char col_first_char;
    char *col_ul_code;

    col_first_char = col[0];
    switch (col_first_char) {
        case 'r':
            col_ul_code = red_ul;
            break;
        case 'g':
            col_ul_code = grn_ul;
            break;
        case 'b':
            col_ul_code = blu_ul;
            break;
        case 'y':
            col_ul_code = yel_ul;
            break;
        case 'm':
            col_ul_code = mag_ul;
            break;
        case 'c':
            col_ul_code = cyn_ul;
            break;
    }

    return col_ul_code;
}

/*
 * NAME: to_highlight_code
 * IMPORT(S): colour (char*)
 * EXPORT(S): colour_hglt_code (char*)
 * PURPOSE: Convert COL into the ASCII escape code for highlighting strings 
 *          using COL_HGLT_CODE
 * CREATION: 08/09/2020
 * LAST MODIFICATION: 21/09/2020
 */

char *to_highlight_code(char *col) {    
    char col_first_char;
    char *col_hglt_code;

    col_first_char = col[0];
    switch (col_first_char) {
        case 'r':
            col_hglt_code = red_hglt;
            break;
        case 'g':
            col_hglt_code = grn_hglt;
            break;
        case 'b':
            col_hglt_code = blu_hglt;
            break;
        case 'y':
            col_hglt_code = yel_hglt;
            break;
        case 'm':
            col_hglt_code = mag_hglt;
            break;
        case 'c':
            col_hglt_code = cyn_hglt;
            break;
    }

    return col_hglt_code;
}
