package com.tigar.homework.lesson1;

import com.tigar.homework.common.Salary;
import org.junit.Test;

import java.util.*;

/**
 * Created by Administrator on 2017-11-24.
 */
public class s8 {

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
                new ArrayList(maps.entrySet());

        System.out.println("================== 原始数据 ==================");

        // 输出收入原始的10个人的名单
        for (int i = 0; i < 10; i++) {
            System.out
                    .println(String.format("姓名：%s 收入：%d",
                            infoIds.get(i).getKey().getName(), infoIds.get(i).getValue()));
        }

        // 快排递归排序，因堆栈太深，会假死
        //customQuickSort1(infoIds, 0, infoIds.size() - 1);
        //customQuickSort2(infoIds, 0, infoIds.size() - 1);

        // 快排非递归排序
//        customQuickSort3(infoIds);

        // 冒泡排序
        customBubbleSort(infoIds);

        System.out.println("================== 排序后数据 ==================");

        // 输出收入最高的10个人的名单
        for (int i = 0; i < 10; i++) {
            System.out
                    .println(String.format("姓名：%s 收入：%d",
                            infoIds.get(i).getKey().getName(), infoIds.get(i).getValue()));
        }


    }

    // 快速排序算法实现三 非递归实现
    public static void customQuickSort3(List<Map.Entry<Salary, Integer>> list) {
        LinkedList<Integer> stack = new LinkedList<Integer>();  //用栈模拟

        int startIndex = 0;
        int endIndex = list.size();
        // 保证一次只能执行一个比较，就避免递归
        stack.push(startIndex);
        stack.push(endIndex);

        while (!stack.isEmpty()) {
            Integer endIndex1 = stack.pop();
            Integer startIndex1 = stack.pop();

            if (startIndex1 >= endIndex1)
                continue;

            int splitIndex = customQuickSortCompare(list, startIndex1, endIndex1);

            // 如果差大于1没必要递归
            if (splitIndex - startIndex1 > 2) {
                stack.push(startIndex1);
                stack.push(splitIndex - 1);
            }

            // 如果差大于1没必要递归
            if (endIndex - splitIndex > 2) {
                stack.push(splitIndex + 1);
                stack.push(endIndex1);
            }
        }
    }

    // 快速排序算法实现二 递归实现
    public static void customQuickSort2(List<Map.Entry<Salary, Integer>> list, int startIndex, int endIndex) {

        if (startIndex >= endIndex)
            return;

        int splitIndex = customQuickSortCompare(list, startIndex, endIndex);

        // 如果差大于1没必要递归
        if (splitIndex - startIndex > 1)
            customQuickSort1(list, startIndex, splitIndex - 1);

        // 如果差大于1没必要递归
        if (endIndex - splitIndex > 1)
            customQuickSort1(list, splitIndex + 1, endIndex);
    }

    // 快排核心算法
    public static int customQuickSortCompare(List<Map.Entry<Salary, Integer>> list, int startIndex, int endIndex) {
        Map.Entry<Salary, Integer> currentItem = list.get(startIndex);

        while (startIndex < endIndex) {
            while (startIndex < endIndex && list.get(endIndex).getValue() < currentItem.getValue()) {
                endIndex--;
            }
            list.set(startIndex, list.get(endIndex));
            while (startIndex < endIndex && list.get(startIndex).getValue() >= currentItem.getValue()) {
                startIndex++;
            }
            list.set(endIndex, list.get(startIndex));
        }

        list.set(startIndex, currentItem);
        return startIndex;
    }

    // 快速排序算法实现一
    public static void customQuickSort1(List<Map.Entry<Salary, Integer>> list, int startIndex, int endIndex) {

        boolean isAsc;
        int sourceEnd = endIndex;
        if (endIndex - startIndex <= 0)
            return;

        // 基元
        Map.Entry<Salary, Integer> temp = list.get(startIndex);
        isAsc = false;
        while (startIndex < endIndex) {
            if (isAsc) {
                if (list.get(startIndex).getValue() > temp.getValue()) {
                    list.set(endIndex, list.get(startIndex));
                    isAsc = false;
                    endIndex--;
                } else {
                    startIndex++;
                }
            } else {
                if (list.get(endIndex).getValue() > temp.getValue()) {
                    endIndex--;
                } else {
                    list.set(startIndex, list.get(endIndex));
                    isAsc = true;
                    startIndex++;
                }
                ;
            }
        }

        if (isAsc) {
            list.set(endIndex, temp);
            customQuickSort1(list, 0, endIndex - 1);
            customQuickSort1(list, endIndex + 1, sourceEnd);
        } else {
            list.set(startIndex, temp);
            customQuickSort1(list, 0, startIndex - 1);
            customQuickSort1(list, startIndex + 1, sourceEnd);
        }
    }

    // 冒排序算法
    public static void customBubbleSort(List<Map.Entry<Salary, Integer>> list) {
        boolean isSwag = false;
        int next;
        int len = list.size();
        for (int i = 0; i < len; i++) {
            isSwag = false;
            for (int j = 0; j < len - i - 1; j++) {
                next = j + 1;
                if (list.get(j).getValue() < list.get(next).getValue()) {
                    Map.Entry<Salary, Integer> temp = list.get(j);
                    list.set(j, list.get(next));
                    list.set(next, temp);
                    if(!isSwag)
                        isSwag = true;
                }
            }
            // 没有交还就直接退出
            if(!isSwag)
                break;
        }
    }

}
