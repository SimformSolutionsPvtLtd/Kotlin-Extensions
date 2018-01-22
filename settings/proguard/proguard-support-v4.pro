-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}