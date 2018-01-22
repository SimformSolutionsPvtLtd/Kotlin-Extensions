#Picasso
-keepattributes SourceFile,LineNumberTable
-keep class com.parse.*{ *; }
-dontwarn com.parse.**
-dontwarn com.squareup.picasso.**
-keepclasseswithmembernames class * {
    native <methods>;
}

## Square Picasso specific rules ##
## https://square.github.io/picasso/ ##

-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.* { ;}
-dontwarn okio.
-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.* { ;}
-keep interface okhttp3.* { ;}
-dontwarn okhttp3.