package cn.mr8god.ttwenty.twoonefour;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static net.mindview.util.Print.print;

class SleepBlocked implements Runnable{
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        }catch (InterruptedException e){
            print("InterruptedException");
        }
        print("Exiting SleepBlocked.run()");
    }
}

class IOBlocked implements Runnable{
    private InputStream in;
    public IOBlocked(InputStream is){ in = is; }

    @Override
    public void run() {
        try {
            print("Waiting for read():");
            in.read();
        }catch (IOException e){
            if (Thread.currentThread().isInterrupted()) {
                print("Interrupted from blocked I/O");
            }else {
                throw new RuntimeException(e);
            }
        }
        print("Exiting IOBlocked.run()");
    }
}

class SynchronizedBlocked implements Runnable{
    public synchronized void f(){
        while (true){
            Thread.yield();
        }
    }
    public SynchronizedBlocked(){
        new Thread(){
            @Override
            public void run() {
                f();
            }
        }.start();
    }

    @Override
    public void run() {
        print("Trying to call f()");
        f();
        print("Exiting SynchronizedBlocked.run()");
    }
}
/**
 * @author Mr8god
 * @date 2020/5/1
 * @time
 */
public class Interrupting {
    private static ExecutorService exec = Executors.newCachedThreadPool();
    static void test(Runnable r)throws InterruptedException{
        Future<?> f = exec.submit(r);
        TimeUnit.MILLISECONDS.sleep(100);
        print("Interrupting " + r.getClass().getName());
        f.cancel(true);
        print("Interrupt sent to " + r.getClass().getName());
    }

    public static void main(String[] args) throws Exception {
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
        TimeUnit.SECONDS.sleep(3);
        print("Aborting with System.exit(0)");
        System.exit(0);
    }
}
