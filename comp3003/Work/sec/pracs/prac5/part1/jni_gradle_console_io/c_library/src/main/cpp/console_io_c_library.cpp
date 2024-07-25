#include <jni.h>
#include <stdio.h>

// This construct is needed to make the C++ compiler generate C-compatible compiled code.
extern "C" 
{
    // public native static double read(double defaultValue);
    JNIEXPORT double JNICALL Java_ConsoleIO_read(JNIEnv *env, jclass cls, jdouble defaultValue)
    {
        jdouble userInput;
        jint numFieldsScanned = scanf("%lf", &userInput);
        if (numFieldsScanned == 0)
        {
            // Discard all characters up to and including the newline
            while ((userInput = getchar()) != EOF && userInput != '\n');
            // Assign user input to the default value
            userInput = defaultValue;
        } 

        return userInput;
    }

    // public native static void printStr(String text);
    JNIEXPORT void JNICALL Java_ConsoleIO_printStr(JNIEnv *env, jclass cls, jstring javaString)
    {
        if (javaString == NULL)
        {
            printf("null\n");
        } 
        else 
        {
            const char *cString = env->GetStringUTFChars(javaString, NULL);
            if (cString != NULL) 
            {
                printf("%s\n", cString);

                env->ReleaseStringUTFChars(javaString, cString);
            }
        }
    }

    // public native static void printList(List<String> list);
    JNIEXPORT void JNICALL Java_ConsoleIO_printList(JNIEnv *env, jclass cls, jobject javaList)
    {
        if (javaList == NULL)
        {
            printf("null\n");
        }
        else
        {
            jclass listClass = env->GetObjectClass(javaList);
            jmethodID sizeMethod = env->GetMethodID(listClass, "size", "()I");
            jmethodID getMethod = env->GetMethodID(listClass, "get", "(I)Ljava/lang/Object;");
            if (sizeMethod != NULL && getMethod != NULL) 
            {
                jint size = env->CallIntMethod(javaList, sizeMethod);
                for(jint i = 0; i < size; i++) 
                {
                    jstring javaString = (jstring) env->CallObjectMethod(javaList, getMethod, i);
                    const char *cString = env->GetStringUTFChars(javaString, NULL);
                    if (cString != NULL) 
                    {
                        if (i == size - 1) 
                        {
                            printf("%s", cString);
                        }
                        else 
                        {
                            printf("%s, ", cString);
                        }

                        env->ReleaseStringUTFChars(javaString, cString);
                    }
                }
                printf("\n");
            }
        }
    }
}
