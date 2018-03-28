package com.mycat.homework.lesson2;

import org.testng.annotations.Test;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Administrator on 2017-12-07.
 */
public class Lesson2_Subject5 {

    @Test
    public void TestMain() throws Exception {

        Random random = new Random();

        // 模拟手动输入五次
        for(int simulation = 0; simulation < 5; simulation++) {
            System.out.println("===============================================");
            System.out.println("请输入原始字串：");
            String words = getRandomString(20);
            System.out.println(words);
            System.out.println("请输入命令，以逗号隔开：");
            String input = getRandomCmd();
            System.out.println(input);

            Worker worker = new WorkerBase();
            String[] cmds = input.split(",");
            for (String cmd : cmds) {
                switch (cmd) {
                    case "1":
                        worker = new Worker1(worker);
                        break;
                    case "2":
                        worker = new Worker2(worker);
                        break;
                    case "3":
                        worker = new Worker3(worker);
                        break;
                    case "4":
                        worker = new Worker4(worker);
                        break;
                    case "5":
                        worker = new Worker5(worker);
                        break;
                }
            }
            System.out.println(worker.doJob(words));
        }
    }

    public String getRandomString(int length) { // length表示生成字符串的长度
        Random random = new Random();
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public String getRandomCmd() { // length表示生成字符串的长度
        Random random = new Random();
        String input = "";
        // 抽取样本
        ArrayList stock = new ArrayList<Integer>();
        for(int i = 1; i < 6; i++)
        {
            stock.add(i);
        }
        // 抽取个数
        int fetchCount = random.nextInt(4) + 1;
        for(int i = 0; i < fetchCount; i++)
        {
            int index = random.nextInt(5 - i);
            input += "," + stock.get(index);
            stock.remove(index);
        }
        input = input.substring(1);
        return  input;
    }


    public abstract class Worker {
        public abstract String doJob(String words);
    }
    public class FilterWorker extends Worker {
        protected Worker wk;
        public FilterWorker(Worker wk)
        {
            this.wk = wk;
        }
        @Override
        public String doJob(String words) {
            return wk.doJob(words);
        }
    }
    public class WorkerBase extends Worker {
        @Override
        public String doJob(String words) {
            return words;
        }
    }
    // 1 ：加密
    public class Worker1 extends FilterWorker {
        public Worker1(Worker wk)
        {
            super(wk);
            this.wk = wk;
        }
        @Override
        public String doJob(String words) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                words = new String(md5.digest(words.getBytes()));
            } catch (Exception ex) {
            }
            return wk.doJob(words);
        }
    }
    // 2 ：反转字符串
    public class Worker2 extends FilterWorker {
        public Worker2(Worker wk)
        {
            super(wk);
            this.wk = wk;
        }
        @Override
        public String doJob(String words) {
            try {
                words = new StringBuilder(words).reverse().toString();
            } catch (Exception ex) {
            }
            return wk.doJob(words);
        }
    }
    // 3：转成大写
    public class Worker3 extends FilterWorker {
        public Worker3(Worker wk)
        {
            super(wk);
            this.wk = wk;
        }
        @Override
        public String doJob(String words) {
            try {
                words = words.toUpperCase();
            } catch (Exception ex) {
            }
            return wk.doJob(words);
        }
    }
    // 4：转成小写
    public class Worker4 extends FilterWorker {
        public Worker4(Worker wk)
        {
            super(wk);
            this.wk = wk;
        }
        @Override
        public String doJob(String words) {
            try {
                words = words.toLowerCase();
            } catch (Exception ex) {
            }
            return wk.doJob(words);
        }
    }
    // 5：扩展或者剪裁到10个字符，不足部分用！补充
    public class Worker5 extends FilterWorker {
        public Worker5(Worker wk)
        {
            super(wk);
            this.wk = wk;
        }
        @Override
        public String doJob(String words) {
            try {
                if(words.length() > 10)
                    words = words.substring(0, 10);
                else if(words.length() < 10)
                    words = String.format("%10d", words);
            } catch (Exception ex) {
            }
            return wk.doJob(words);
        }
    }





}
