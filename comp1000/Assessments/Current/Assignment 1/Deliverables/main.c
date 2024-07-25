/*
 * NAME: main
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1000
 * PURPOSE: Act as the entry point for PATTERNMATCH
 * CREATION: 03/09/2020
 * LAST MODIFICATION: 08/09/2020
 */

#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "bool.h"
#include "colourcodes.h"
#include "colourlogic.h"
#include "patternmatchlogic.h"
#include "patternmatchvisualiser.h"

int main(int argc, char* argv[]) {
    const int REQUIRED_ARGS = 6;

    char *old_str, *tgt, *tgt_col, *rplcmnt_col; 
    int exit_status;
    char *new_str = NULL;
    int *match_idxs = NULL;

    old_str = argv[1];
    tgt = argv[2];
    tgt_col = argv[4];
    rplcmnt_col = argv[5];

    exit_status = 0;

    if (argc != REQUIRED_ARGS) {
        printf(red_body "error: incorrect number of command-line arguments "
            "provided\n" reset);
        printf(yel_body "usage: ./patternmatch <string> <target> "
            "<replacement> <target colour> <replacement colour>\n" reset);
        exit_status = 1;
    }
    else {
        if (strlen(old_str) < strlen(tgt)) {
            printf(red_body "error: <string> cannot be shorter than "
                "<target>\n" reset);
            exit_status = 1;
        }
        if (!is_valid_colour(tgt_col)) {
            printf(red_body "error: <target colour> is invalid\n" reset);
            printf(yel_body "view README.txt for list of accepted "
                "colours\n" reset);
            exit_status = 1;
        }
        if (!is_valid_colour(rplcmnt_col)) {
            printf(red_body "error: <replacement colour> is invalid\n" reset);
            printf(yel_body "view README.txt for list of accepted "
                "colours\n" reset);
            exit_status = 1;
        }
        if (exit_status == 0) {
            new_str = pattern_match(argv);
            #ifdef INDEX
                match_idxs = find_match_indexes(argv);
            #endif
            visualise_pattern_match(argv, new_str, match_idxs);
            print_old_and_new_strings(argv, new_str);
            exit_status = 0;
        }
    }

    free(new_str);
    new_str = NULL;
    #ifdef INDEX
        free(match_idxs);
        match_idxs = NULL;
    #endif

    return exit_status;
}
