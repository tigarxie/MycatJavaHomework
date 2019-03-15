package com.tigar.homework.lesson7;

import com.tigar.homework.common.MyCollector;
import com.tigar.homework.common.Salary;
import com.tigar.homework.common.SalaryStatistics;
import com.tigar.homework.lesson2.s4;
import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author tigar
 * @date 2018/4/30.
 */
public class s3 {

    @Test
    public void t1() throws Exception {
        String path = "c:/s4.txt";
        String charset = "utf-8";
        File file = new File(path);
        int lineCount = 1000 * 10000;
        long startTime, endTime;

        startTime = System.currentTimeMillis();
        // 写文件
        FileWriter fileWriter = new FileWriter(file);
        for (int i = 0; i < lineCount; i++) {
            // 初始化，并写入
            Salary ent = new Salary();
            fileWriter.write(ent.toString() + "\r\n");
        }
        fileWriter.close();
        endTime = System.currentTimeMillis();
        System.out.println("写文件时间：" + (endTime - startTime) + "毫秒");

        System.out.println("文件大小为：" + file.length() / 1024 / 1000 + "M");
    }


    @Test
    public void t2() throws Exception {
        int lineCount = 1000 * 10000;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FockJoinTest fockJoinTest = new FockJoinTest(1, lineCount);
        long fockhoinStartTime = System.currentTimeMillis();
        //前面我们说过，任务提交中invoke可以直接返回结果
        List<SalaryStatistics> result = forkJoinPool.invoke(fockJoinTest);
        System.out.println("fock/join计算结果耗时" + (System.currentTimeMillis() - fockhoinStartTime));
        result.stream().sorted((a1, a2) -> a2.getSum().compareTo(a1.getSum()))
                .limit(10)
                .forEach(System.out::println);
    }


    /**
     * 核心并发框架
     */
    public class FockJoinTest extends RecursiveTask<List<SalaryStatistics>> {
        //设立一个最大计算容量
        private final int DEFAULT_CAPACITY = 500 * 10000;

        //读取开始行数
        private int begin;
        //读取结束行数
        private int end;

        public FockJoinTest(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        protected List<SalaryStatistics> compute() {
            final List<SalaryStatistics> result = new ArrayList<>();
            int len = end - begin;
            if (len > DEFAULT_CAPACITY) {
                int middle = (begin + end) / 2 + 1;
                FockJoinTest fock1 = new FockJoinTest(begin, middle);  //执行任务
                fock1.fork();
                merge(result, fock1.join());
                FockJoinTest fock2 = new FockJoinTest(middle + 1, end);
                fock2.fork();
                merge(result, fock2.join());
            } else {// 读文件
                FileReader fileReader = null;
                try {
                    List<Salary> list = new ArrayList<>();
                    fileReader = new FileReader("c:/s4.txt");
                    LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
                    lineNumberReader.skip(begin);
                    for (int i = begin; i < end; i++) {
                        // 逐行读取，并转换
                        String lineStr = lineNumberReader.readLine();
                        Salary ent = new Salary(lineStr);
                        // 加入统计
                        list.add(ent);
                    }
                    result.addAll(list.stream().collect(new MyCollector()).stream().sorted((a1, a2) -> a2.getSum().compareTo(a1.getSum()))
                            .limit(10).collect(Collectors.toList()));
                } catch (Exception ex) {
                } finally {
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return result;
        }
    }

    /**
     * 辅助方法，把fork结果合并
     */
    public List<SalaryStatistics> merge(final List<SalaryStatistics> result, final List<SalaryStatistics> join) {
        join.stream().forEach(f -> {
            Optional<SalaryStatistics> opt =
                    result.stream().filter(fi -> fi.getNamePrefix().equals(f.getNamePrefix())).findFirst();
            if (opt.isPresent()) {
                opt.get().setSum(f.getSum().add(opt.get().getSum()));
                opt.get().setCount(f.getCount() + opt.get().getCount());
            }
        });
        return result;
    }

}
