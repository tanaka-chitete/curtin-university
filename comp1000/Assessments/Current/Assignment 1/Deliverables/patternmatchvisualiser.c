#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "patternmatchvisualiser.h"

#include "bool.h"
#include "colourcodes.h"
#include "colourlogic.h"
#include "string.h"

/*
 * NAME: visualise_pattern_match
 * IMPORT(S): cl_args[] (char*), new_str (char*)
 * EXPORT(S): NONE
 * PURPOSE: Visualise PATTERN_MATCH
 * CREATION: 03/09/2020
 * LAST MODIFICATION: 21/09/2020
 */

void visualise_pattern_match(char *cl_args[], char *new_str, int *match_idxs) 
{
    char *old_str, *tgt, *rplcmnt, *rplcmnt_col; 

    int i_one, i_zero, num_visualise_chars, i_last_char, match_idxs_sc_len;
    char *curr = NULL, *disp_str = NULL;

    old_str = cl_args[1];
    tgt = cl_args[2];
    rplcmnt = cl_args[3];
    rplcmnt_col = cl_args[5];

    disp_str = (char*) calloc(strlen(old_str) + 1, sizeof(char));
    memcpy(disp_str, old_str, strlen(old_str));

    i_one = 1;
    i_zero = 0;
    num_visualise_chars = strlen(new_str) - strlen(tgt) + 1;
    i_last_char = num_visualise_chars - 1;
    match_idxs_sc_len = 0;
    while (i_zero < num_visualise_chars) {
        sleep(1);
        system("clear");
        printf("replace: %s -> %s\n\n", tgt, rplcmnt);

        /* Copies current sub-string of DISP_STR to CURR */
        curr = (char*) calloc(strlen(tgt) + 1, sizeof(char));
        memcpy(curr, disp_str + i_zero, strlen(tgt));

        if (my_strcasecmp(curr, tgt)) {
            /* Replaces TGT in DISP_STR with RPLCMNT */
            disp_str = update_display_string(disp_str, i_zero, tgt, rplcmnt);

            /* Prints DISP_STR, highlighting every instance of RPLCMNT */
            print_string(disp_str, rplcmnt, to_highlight_code(rplcmnt_col));
            printf("\033[4;%dH^\n", i_one);
            printf("%s\033[5;%dHreplaced\n" reset, to_body_code(rplcmnt_col), 
                i_one);

            i_one += strlen(rplcmnt);
            i_zero += strlen(rplcmnt);
            /* Prepares to print index of OLD_STR where CURR matches TGT */
            match_idxs_sc_len++;
        }
        else {
            /* Prints DISP_STR, highlighting every instance of RPLCMNT */
            print_string(disp_str, rplcmnt, to_highlight_code(rplcmnt_col));
            printf("\033[4;%dH^\n", i_one); 
            if (i_zero == i_last_char) {
                printf(red_body "\033[5;%dHend\n" reset, i_one);
            }
            else {
                printf(red_body "\033[5;%dHno match\n" reset, i_one);
            }

            i_one++;
            i_zero++;
        }

        free(curr);
        curr = NULL;

        /* Prints MATCH_IDXS if program is compiled using make index */
        #ifdef INDEX
            printf("\n");
            print_match_indexes(match_idxs, match_idxs_sc_len);
        #endif
    }

    free(disp_str);
    disp_str = NULL;
}

/*
 * NAME: print_old_and_new_strings
 * IMPORT(S): cl_args[] (char*), new_str (char*)
 * EXPORT(S): NONE
 * PURPOSE: Print OLD_STR and NEW_STR
 * CREATION: 10/09/2020
 * LAST MODIFICATION: 17/09/2020
 */

void print_old_and_new_strings(char* cl_args[], char* new_str) {
    char *old_str, *tgt, *rplcmnt, *tgt_col, *rplcmnt_col;

    char *tgt_code, *rplcmnt_code;

    old_str = cl_args[1];
    tgt = cl_args[2];
    rplcmnt = cl_args[3];
    tgt_col = cl_args[4];
    rplcmnt_col = cl_args[5];

    tgt_code = to_underline_code(tgt_col);
    rplcmnt_code = to_underline_code(rplcmnt_col);
    
    sleep(1);
    system("clear");

    print_string(old_str, tgt, tgt_code);
    print_string(new_str, rplcmnt, rplcmnt_code);
}

/*
 * NAME: update_display_string
 * IMPORT(S): disp_str (char*), match_idx (int), tgt (char*), rplcmnt (char*)
 * EXPORT(S): disp_str (char*)
 * PURPOSE: Replaces TGT in DISP_STR with RPLCMNT at I_SUB_STR_START and
 *          appends the rest of DISP_STR at I_SUB_STR_END
 * CREATION: 06/09/2020
 * LAST MODIFICATION: 21/09/2020
 */

char *update_display_string(char *disp_str, int match_idx, 
    char *tgt, char *rplcmnt) {
    char *temp_disp_str = NULL;
    int num_chars;

    num_chars = strlen(rplcmnt) - strlen(tgt);

    temp_disp_str = (char*) calloc(strlen(disp_str) + num_chars + 1, 
        sizeof(char));

    /* Appends left-side of DISP_STR before TGT to TEMP_DISP_STR */
    memcpy(temp_disp_str, disp_str, match_idx);
    /* Appends RPLCMNT to TEMP_DISP_STR */
    memcpy(temp_disp_str + match_idx, rplcmnt, strlen(rplcmnt));
    /* Appends right-side of DISP_STR after RPLCMNT to TEMP_DISP_STR */
    memcpy(temp_disp_str + match_idx + strlen(rplcmnt), 
        disp_str + match_idx + strlen(tgt), 
        strlen(disp_str) - match_idx - strlen(tgt));

    free(disp_str);
    disp_str = NULL;

    disp_str = temp_disp_str;
    
    return disp_str;
}

/*
 * NAME: print_string
 * IMPORT(S): str (char*), sub_str (char*), code (char*)
 * EXPORT(S): NONE 
 * PURPOSE: Print STR, applying CODE to every instance of SUB_STR
 * CREATION: 05/09/2020
 * LAST MODIFICATION: 21/09/2020
 */

void print_string(char *str, char *sub_str, char *code) {
    char *temp = NULL, *curr = NULL;
    int i, j;

    i = 0;
    while (i < strlen(str)) {
        /* Copies current sub-string of STR to CURR */
        temp = (char*) realloc(curr, strlen(sub_str) + 1);
        if (temp) {
            curr = temp;

            strncpy(curr, str + i, strlen(sub_str));
            curr[strlen(sub_str)] = '\0';

            if (my_strcasecmp(curr, sub_str)) {
                /* Prints every instance of SUB_STR with CODE applied */
                for (j = 0; j < strlen(curr); j++) {
                    printf("%s%c", code, curr[j]);
                }
                printf(reset);
                /* Prepares to access next char in STR after SUB_STR */
                i += strlen(sub_str);
            }
            else {
                printf("%c", str[i]);
                /* Prepares to access next char in STR */
                i++;
            }
        }
    }
    printf("\n");

    /* FREEs and NULLs memory allocated by REALLOC */
    free(temp);
    temp = NULL;
}

/*
 * NAME: print_match_indexes
 * IMPORT(S): match_idxs (int*), match_idxs_sc_len (int*)
 * EXPORT(S): NONE
 * PURPOSE: Print indexes OLD_STR where CURR matched TGT
 * CREATION: 16/09/2020
 * LAST MODIFICATION: 21/09/2020
 */

void print_match_indexes(int *match_idxs, int match_idxs_sc_len) {
    int i, i_last;

    if (match_idxs_sc_len < 1) {
        printf("index(es): none\n");
    }
    else {
        i_last = match_idxs_sc_len - 1;

        printf("index(es): [");
        for (i = 0; i < match_idxs_sc_len; i++) {
            if (i == i_last) {
                printf("%d]\n", match_idxs[i]);
            }
            else {
                printf("%d, ", match_idxs[i]);
            }
        }
    }
    printf("\n");
}
