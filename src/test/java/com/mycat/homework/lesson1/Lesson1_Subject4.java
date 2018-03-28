package com.mycat.homework.lesson1;

import com.mycat.homework.Common.Salary;
import org.testng.annotations.Test;

import java.util.*;

/**
 * Created by Administrator on 2017-11-24.
 */
public class Lesson1_Subject4 {

    @Test
    public void TestMain() {

        // 初始化
        Salary[] persons = new Salary[10000];
        for (int i = 0; i < persons.length; i++) {
            persons[i] = new Salary();
        }

        // 如果0入侵Salary类，指哪跟你借助第三方存储
        HashMap<Salary, Integer> maps = new HashMap<Salary, Integer>();
        for (int i = 0; i < persons.length; i++) {

            // 总收入
            Integer total = persons[i].getBaseSalary() * 13 + persons[i].getBonus();

            maps.put(persons[i], total);
        }

        List<Map.Entry<Salary, Integer>> infoIds =
                new ArrayList<Map.Entry<Salary, Integer>>(maps.entrySet());

        // 排序
        Collections.sort(infoIds, new Comparator<Map.Entry<Salary, Integer>>() {
            public int compare(Map.Entry<Salary, Integer> o1, Map.Entry<Salary, Integer> o2) {
                if (o1.getValue() == o2.getValue())
                    return 0;

                return o1.getValue() > o2.getValue() ? -1 : 1;
            }
        });

        // 输出收入最高的10个人的名单
        for (int i = 0; i < 10; i++) {
            System.out
                    .println(String.format("姓名：%s 收入：%d",
                            infoIds.get(i).getKey().getName(), infoIds.get(i).getValue()));
        }

    }

    // 类定义

}

