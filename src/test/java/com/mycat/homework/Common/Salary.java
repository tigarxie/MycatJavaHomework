package com.mycat.homework.Common;

/**
 * Created by Administrator on 2017-12-16.
 */

/**
 * 员工收入信息
 */
public class Salary {
    // 姓名
    private String name;
    // 月薪
    private int baseSalary;
    // 奖金
    private int bonus;
    // 年薪（算出来的，中间变量，有的算法无需使用到）
    private int salarySum;

    public Salary() {
        // name长度为5，
        name = RandomHepler.getRandomName();
        // baseSalary范围是5-100万，
        baseSalary = RandomHepler.getRandomSalary();
        // bonus为（0-10万）
        bonus = RandomHepler.getRandomBonus();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getSalarySum() {
        return salarySum;
    }

    public void setSalarySum(int salarySum) {
        this.salarySum = salarySum;
    }

    public Salary(String str) {
        String[] temp = str.split(",");
        if (temp.length == 3) {
            name = temp[0];
            baseSalary = Integer.parseInt(temp[1]);
            bonus = Integer.parseInt(temp[2]);
        }
    }

    public String toString() {
        return name + "," + baseSalary + "," + bonus;
    }
}