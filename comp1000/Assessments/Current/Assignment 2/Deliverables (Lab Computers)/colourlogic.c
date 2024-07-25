#ifndef COLOURLOGIC_H
#define COLOURLOGIC_H
#include "colourlogic.h"
#endif

#ifndef BRACKETS_H
#define BRACKETS_H
#include "brackets.h"
#endif

#ifndef COLOURCODES_H
#define COLOURCODES_H
#include "colourcodes.h"
#endif

/*
 * NAME: toHighlightCode
 * IMPORT(S): bracket (char)
 * EXPORT(S): highlightCode (char*)
 * PURPOSE: Convert brackets into their predefined highlight colour codes
 * CREATION: 08/10/2020
 * LAST MODIFICATION: 08/10/2020
 */

char *toHighlightCode(char bracket) {    
    char *highlightCode;

    switch (bracket) {
        case OPEN_ROUND:
            highlightCode = GREEN_HIGHLIGHT;
            break;
        case CLOSED_ROUND:
            highlightCode = GREEN_HIGHLIGHT;
            break;
        case OPEN_CURLY:
            highlightCode = BLUE_HIGHLIGHT;
            break;
        case CLOSED_CURLY:
            highlightCode = BLUE_HIGHLIGHT;
            break;  
        case OPEN_SQUARE:
            highlightCode = YELLOW_HIGHLIGHT;
            break;
        case CLOSED_SQUARE:
            highlightCode = YELLOW_HIGHLIGHT;
            break;   
        case OPEN_ANGLE:
            highlightCode = MAGENTA_HIGHLIGHT;
            break;   
        case CLOSED_ANGLE:
            highlightCode = MAGENTA_HIGHLIGHT;
            break;   
    }

    return highlightCode;
}
