package util;

public class Math {
    public static int log2(int n) {
        int log = 0;
        if ((n & 0xffff0000) != 0) {
            n >>>= 16;
            log = 16;
        }
        if ((n & 0xff00) != 0) {
            n >>>= 8;
            log += 8;
        }
        if ((n & 0xf0) != 0) {
            n >>>= 4;
            log += 4;
        }
        if (n >= 4) {
            n >>>= 2;
            log += 2;
        }
        return log + (n >>> 1);
    }

    public static boolean powOf2(int n) {
        return 1 << log2(n) == n;
    }
}
