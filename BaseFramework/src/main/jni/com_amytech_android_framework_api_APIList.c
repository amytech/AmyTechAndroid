#include <com_amytech_android_framework_api_APIList.h>
#include "../../../../../android-ndk-r10e/platforms/android-21/arch-x86/usr/include/jni.h"

/*
 * Class:     com_amytech_android_framework_api_APIList
 * Method:    TMallStyleURL
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_amytech_android_framework_api_APIList_TMallStyleURL
        (JNIEnv *env, jobject thiz) {
    return (*env)->NewStringUTF(env, "http://route.showapi.com/126-1");
}

/*
 * Class:     com_amytech_android_framework_api_APIList
 * Method:    TMallURL
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_amytech_android_framework_api_APIList_TMallURL
        (JNIEnv *env, jobject thiz) {
    return (*env)->NewStringUTF(env, "http://route.showapi.com/126-2");
}
