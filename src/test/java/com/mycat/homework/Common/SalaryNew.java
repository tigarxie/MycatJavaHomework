package com.mycat.homework.Common;

import java.io.*;

/**
 * Created by Administrator on 2018-03-15.
 */
public class SalaryNew implements Externalizable {

    private String name;
    private int baseSalary;
    private int bonus;

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

    public void init() {
        // name长度为5，
        name = RandomHepler.getRandomName();
        // baseSalary范围是5-100万，
        baseSalary = RandomHepler.getRandomSalary();
        // bonus为（0-10万）
        bonus = RandomHepler.getRandomBonus();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(baseSalary);
        out.writeInt(bonus);
    }
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = (String) in.readObject();
        this.baseSalary = in.readInt();
        this.bonus = in.readInt();
    }
}
