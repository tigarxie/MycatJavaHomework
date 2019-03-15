package com.tigar.homework.lesson7;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author tigar
 * @date 2018/4/30.
 */
public class s1 {

    @Test
    public void t1() {
        SynchronousQueue<String> queue = new SynchronousQueue();
        while (true) {
            if (queue.offer("S1")) {
                System.out.println("scucess");
            } else {
                System.out.println("faield");
            }
        }
    }

    @Test
    public void t2() throws Exception {
        SynchronousQueue<String> queue = new SynchronousQueue();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("-----------------> queue.take: " + queue.poll());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (queue.offer("S1")) {
                    System.out.println("scucess");
                } else {
                    System.out.println("faield");
                }
            }
        }).start();
    }

    @Test
    public void t3() {
        SynchronousQueue<String> queue = new SynchronousQueue();
        // TODO Auto-generated method stub
        for (int i = 0; i < 10; i++)
            new Thread(new ThreadProducer(queue)).start();
        for (int i = 0; i < 10; i++)
            new Thread(new ThreadConsumer(queue)).start();
    }

    class ThreadProducer implements Runnable {
        ThreadProducer(SynchronousQueue<String> queue) {
            this.queue = queue;
        }

        SynchronousQueue<String> queue;
        int cnt = 0;

        public void run() {
            String name = "";
            int val = 0;
            Random random = new Random(System.currentTimeMillis());
            while (true) {
                cnt = (cnt + 1) & 0xFFFFFFFF;

                try {
                    val = random.nextInt() % 15;
                    if (val < 5) {
                        name = "offer name:" + cnt;
                        queue.offer(name);
                    } else if (val < 10) {
                        name = "put name:" + cnt;
                        queue.put(name);
                    } else {
                        name = "offer wait time and name:" + cnt;
                        queue.offer(name, 1000, TimeUnit.MILLISECONDS);
                    }
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ThreadConsumer implements Runnable {
        ThreadConsumer(SynchronousQueue<String> queue) {
            this.queue = queue;
        }

        SynchronousQueue<String> queue;

        public void run() {
            String name;
            while (true) {
                try {
                    name = queue.take();
                    System.out.println("take " + name);
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void t5() throws Exception {
        SynchronousQueue<Integer> queue = new SynchronousQueue();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("-----------------> queue.poll: " + queue.poll());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            Random random = new Random(System.currentTimeMillis());
            @Override
            public void run() {
                while (true) {
                    int value = random.nextInt();
                    System.out.println("发出消息：" + value);
                    if (queue.offer(value)) {
                        System.out.println("scucess");
                    } else {
                        System.out.println("faield");
                    }
                }
            }
        }).start();
    }

    @Test
    public void t6() throws Exception {
        SynchronousQueue<Integer> queue = new SynchronousQueue();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("-----------------> queue.take: " + queue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        Thread.sleep(1000);
        new Thread(new Runnable() {
            Random random = new Random(System.currentTimeMillis());
            @Override
            public void run() {
                while (true) {
                    int value = random.nextInt();
                    System.out.println("发出消息：" + value);
                    try {
                        queue.put(value);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
