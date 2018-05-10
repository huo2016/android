package test.jay.com.moudle;

public class JniLib {

    static {
        System.loadLibrary("nativeUtil");
    }

    public native static String getStringFromC();
}
