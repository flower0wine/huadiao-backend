package test;

/**
 * @author flowerwine
 * @date 2024 年 02 月 19 日
 */
public class Test {
    //public static long s1, s2, s3, s4, s5, s6, s7;
    //public static volatile long a = 0;
    //public static long e1, e2, e3, e4, e5, e6, e7;
    //public static volatile long b = 0;

    public static volatile long[] arr = new long[9];

    public static void main(String[] args) throws InterruptedException {
        Thread A = new Thread(() -> {
            for(int i = 0; i < 100_000_000; i++) {
                //a = i;
                arr[0] = i;
            }
        });
        Thread B = new Thread(() -> {
            for(int i = 0; i < 100_000_000; i++) {
                //b = i;
                arr[1] = i;
            }
        });
        long start = System.currentTimeMillis();
        A.start();
        B.start();
        A.join();
        B.join();
        long end = System.currentTimeMillis();
        System.out.println(end - start + " ms");
    }
}