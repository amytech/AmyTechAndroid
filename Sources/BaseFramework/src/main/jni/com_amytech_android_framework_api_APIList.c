
#include "../../../../../android-ndk-r10e/platforms/android-21/arch-x86/usr/include/jni.h"

struct _jstring *Java_com_amytech_android_framework_api_APIList_getAPPID(JNIEnv *env,
                                                                         jobject jobject1) {
    return (*env)->NewStringUTF(env, "5639");
}

struct _jstring *Java_com_amytech_android_framework_api_APIList_getAppSecret(JNIEnv *env,
                                                                             jobject jobject1) {
    return (*env)->NewStringUTF(env, "7efc497b73bf4622b2d06a8782720a5e");
}

struct _jstring *Java_com_amytech_android_framework_api_APIList_URL_1TMALL_1STYLE(JNIEnv *env,
                                                                                  jobject jobject1) {
    return (*env)->NewStringUTF(env, "http://route.showapi.com/126-1");
}

struct _jstring *Java_com_amytech_android_framework_api_APIList_URL_1TMALL_1LIST(JNIEnv *env,
                                                                                 jobject jobject1) {
    return (*env)->NewStringUTF(env, "http://route.showapi.com/126-2");
}
