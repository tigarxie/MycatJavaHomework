package com.mycat.homework.lesson2;

import com.mycat.homework.Common.Salary;
import org.testng.annotations.Test;

import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2017-12-03.
 */
public class Lesson2_Subject4 {

    @Test
    public void TestMain() throws Exception {
        SalaryStatisticsExplorer summarySalaryExplorer = new SalaryStatisticsExplorer();
        String path = "c:/Lesson2_Subject4.txt";
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

        startTime = System.currentTimeMillis();
        // 读文件
        FileReader fileReader = new FileReader(file);
        LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
        for (int i = 0; i < lineCount; i++) {
            // 逐行读取，并转换
            String lineStr = lineNumberReader.readLine();
            Salary ent = new Salary(lineStr);

            // 不符合条件直接跳过
            if (ent.getName().length() < 2)
                continue;

            // 加入统计
            summarySalaryExplorer.AddEnt(ent.getName().substring(0, 2), ent.getBaseSalary() * 13 + ent.getBonus());
        }
        fileReader.close();

        endTime = System.currentTimeMillis();
        System.out.println("读文件时间：" + (endTime - startTime) + "毫秒");

        // 输出结果
        summarySalaryExplorer.PrintTop10Salary();

        System.out.println("运行结束");
    }

    public class SalaryStatisticsExplorer {
        Map<String, SalaryStatistics> salaryStatisticsMap = new HashMap<>();
        // 添加实体
        public void AddEnt(String namePrefix, int totalSalary) {

            if (salaryStatisticsMap.containsKey(namePrefix)) {
                SalaryStatistics ent = salaryStatisticsMap.get(namePrefix);
                ent.AddSalaryStatistics(totalSalary);
            } else {
                SalaryStatistics ent = new SalaryStatistics(namePrefix);
                ent.AddSalaryStatistics(totalSalary);
                salaryStatisticsMap.put(namePrefix, ent);
            }
        }
        // 输出结果
        public void PrintTop10Salary() {
            // 统计时间
            long startTime, endTime;
            List<Map.Entry<String, SalaryStatistics>> salaryStatisticsList =
                    new ArrayList<Map.Entry<String, SalaryStatistics>>(salaryStatisticsMap.entrySet());
            startTime = System.currentTimeMillis();
            // 排序
            Collections.sort(salaryStatisticsList, new Comparator<Map.Entry<String, SalaryStatistics>>() {
                public int compare(Map.Entry<String, SalaryStatistics> o1, Map.Entry<String, SalaryStatistics> o2) {

                    if (o1.getValue().getSalaryHightPosition() > o2.getValue().getSalaryHightPosition())
                        return -1;
                    if (o1.getValue().getSalaryHightPosition() < o2.getValue().getSalaryHightPosition())
                        return 1;
                    if (o1.getValue().getSalaryLowPosition() > o2.getValue().getSalaryLowPosition())
                        return -1;
                    if (o1.getValue().getSalaryLowPosition() < o2.getValue().getSalaryLowPosition())
                        return 1;
                    return 0;
                }
            });
            endTime = System.currentTimeMillis();
            System.out.println("写文件时间：" + (endTime - startTime) + "毫秒");


            startTime = System.currentTimeMillis();
            for (int i = 0; i < Math.min(10, salaryStatisticsList.size()); i++) {
                SalaryStatistics item = salaryStatisticsList.get(i).getValue();
                String temp = (item.getSalaryHightPosition() > 0 ? Integer.toString(item.getSalaryHightPosition()) : "") + "万"
                        + Integer.toString(item.getSalaryLowPosition());
                System.out
                        .println(String.format("名字前缀：%s  收入：%s  人数：%d",
                                item.getNamePrefix(), temp, item.getCount()));
            }
            endTime = System.currentTimeMillis();
        }
    }

    // 收入统计实体
    public class SalaryStatistics {
        // 名字前缀
        private String namePrefix;
        /* ----------------- 收入总和可能会超过long的最大值，所以必须拆分成两个int存储 -------------------- */
        // 收入高位
        private int salaryHightPosition = 0;
        // 收入总和的个位
        private int salaryLowPosition = 0;
        // 收入高位低位分割点
        private int splitPosition = 10000;      // 100000000  10000
        // 人数
        private int count = 0;
        public SalaryStatistics(String namePrefix) {
            this.namePrefix = namePrefix;
        }
        // 增加收入的方法
        public void AddSalaryStatistics(int addSalary) {
            salaryLowPosition += addSalary;
            while (salaryLowPosition > splitPosition) {
                salaryHightPosition++;
                salaryLowPosition -= splitPosition;
            }
            count++;
        }
        public String getNamePrefix() {
            return namePrefix;
        }
        public int getSalaryHightPosition() {
            return salaryHightPosition;
        }
        public int getSalaryLowPosition() {
            return salaryLowPosition;
        }
        public int getCount() {
            return count;
        }
    }
}
