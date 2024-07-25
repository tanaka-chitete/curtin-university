void visualise_pattern_match(char *cl_args[], char *new_str, int *match_idxs);
void print_old_and_new_strings(char *cl_args[], char* new_str);
char *update_display_string(char *display_str, int match_idx, char *tgt, 
    char *rplcmnt);
void print_string(char *str, char *sub_str, char *code);
void print_match_indexes(int *match_idxs, int match_idxs_sc_len);
