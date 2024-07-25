#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "patternmatchlogic.h"

#include "bool.h"
#include "colourcodes.h"
#include "colourlogic.h"
#include "string.h"

/*
 * NAME: pattern_match
 * IMPORT(S): *cl_args[] (char)
 * EXPORT(S): *new_str (char)
 * PURPOSE: Perform pattern-matching string replacement
 * CREATION: 03/09/2020
 * LAST MODIFICATION: 21/09/2020
 */

char *pattern_match(char *cl_args[]) {
    int i, new_str_len;
    char *old_str, *tgt, *rplcmnt;
    char *curr = NULL, *temp_new_str = NULL, *new_str = NULL;

    old_str = cl_args[1];
    tgt = cl_args[2];
    rplcmnt = cl_args[3];

    i = 0;
    new_str_len = 0;
    while (i < strlen(old_str)) {
        /* Copies current sub-string of OLD_STR to CURR */
        curr = (char*) calloc(strlen(tgt) + 1, sizeof(char));             
        memcpy(curr, old_str + i, strlen(tgt));

        if (my_strcasecmp(curr, tgt)) {
            /* Appends REPLCMNT to NEW_STR */
            temp_new_str = (char*) calloc(new_str_len + strlen(rplcmnt) + 1, 
                sizeof(char));
            memcpy(temp_new_str, new_str, new_str_len);
            memcpy(temp_new_str + new_str_len, rplcmnt, strlen(rplcmnt));

            new_str_len += strlen(rplcmnt);
            i += strlen(tgt);
        }
        else {
            /* Appends CURR[0] to NEW_STR */
            temp_new_str = (char*) calloc(new_str_len + 1 + 1, sizeof(char));
            memcpy(temp_new_str, new_str, new_str_len);
            memcpy(temp_new_str + new_str_len, curr, sizeof(char));

            new_str_len += 1;
            i++;
        }

        free(curr);
        curr = NULL;

        free(new_str);
        new_str = NULL;
        new_str = temp_new_str;
    }

    return new_str; 
}

/*
 * NAME: find_match_indexes
 * IMPORT(S): cl_args[] (char*)
 * EXPORT(S): new_str (char*)
 * PURPOSE: Finds indexes of OLD_STR where CURR matches TGT
 * CREATION: 03/09/2020
 * LAST MODIFICATION: 21/09/2020
 */

int *find_match_indexes(char *cl_args[]) {
    char *old_str, *tgt;

    char *curr = NULL;
    int i_zero, i_one, match_idxs_len;
    int *temp_match_idxs = NULL, *match_idxs = NULL;

    old_str = cl_args[1];
    tgt = cl_args[2];
    
    i_zero = 0;
    i_one = 1;
    match_idxs_len = 0;
    while (i_zero < strlen(old_str)) {
        /* Copies current sub-string of OLD_STR to CURR */
        curr = (char*) calloc(strlen(tgt) + 1, sizeof(char));
        memcpy(curr, old_str + i_zero, strlen(tgt));

        if (my_strcasecmp(curr, tgt)) {
            /* Appends I_ONE to MATCH_IDXS */
            temp_match_idxs = (int*) calloc(match_idxs_len + 1, sizeof(int));
            memcpy(temp_match_idxs, match_idxs, match_idxs_len * sizeof(int)); 
            match_idxs_len++;
            temp_match_idxs[match_idxs_len - 1] = i_one;

            free(match_idxs);
            match_idxs = NULL;
            match_idxs = temp_match_idxs;

            i_zero += strlen(tgt);
            i_one += strlen(tgt);
        }
        else {
            i_zero++;
            i_one++;
        }   

        free(curr);
        curr = NULL; 
    }

    return match_idxs; 
}
