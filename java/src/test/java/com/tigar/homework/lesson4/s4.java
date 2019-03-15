package com.tigar.homework.lesson4;

import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * Created by Administrator on 2018-03-25.
 */
public class s4 {

    List<emp> empList;
    List<dept> deptList;

    public s4() {

        empList = new ArrayList<>();
        empList.add(new emp(7369, "smith", "clerk", 7902, Date.valueOf("2000-12-17"), 800, null, 20));
        empList.add(new emp(7499, "allen", "salesman", 7698, Date.valueOf("2001-2-20"), 1600, 300, 30));
        empList.add(new emp(7521, "ward", "salesman", 7698, Date.valueOf("2001-2-22"), 1250, 500, 30));
        empList.add(new emp(7566, "jones", "manager", 7839, Date.valueOf("2001-4-2"), 2975, null, 20));
        empList.add(new emp(7654, "martin", "salesman", 7698, Date.valueOf("2001-9-28"), 1250, 1400, 30));
        empList.add(new emp(7698, "blake", "manager", 7839, Date.valueOf("2001-5-1"), 2850, null, 30));
        empList.add(new emp(7782, "clark", "manager", 7839, Date.valueOf("2001-6-9"), 2450, null, 10));
        empList.add(new emp(7788, "scott", "analyst", 7566, Date.valueOf("2002-12-9"), 3000, null, 20));
        empList.add(new emp(7839, "king", "president", null, Date.valueOf("2001-11-17"), 5000, null, 10));
        empList.add(new emp(7844, "turner", "salesman", 7698, Date.valueOf("2001-9-8"), 1500, 0, 30));
        empList.add(new emp(7876, "adams", "clerk", 7788, Date.valueOf("2003-1-12"), 1100, null, 20));
        empList.add(new emp(7900, "james", "clerk", 7698, Date.valueOf("2001-3-12"), 950, null, 30));
        empList.add(new emp(7902, "ford", "analyst", 7566, Date.valueOf("2001-3-12"), 3000, null, 20));
        empList.add(new emp(7934, "miller", "clerk", 7782, Date.valueOf("2002-01-23"), 1300, null, 10));

        deptList = new ArrayList<>();
        deptList.add(new dept(10, "accounting", "new york"));
        deptList.add(new dept(20, "research", "dallas"));
        deptList.add(new dept(30, "sales", "chicago"));
        deptList.add(new dept(40, "operations", "boston"));
    }


    // 列出每个部门的部门名称、月薪总和，并排列
    @Test
    public void Test1() throws Exception {
        // 先统计收入情况
        Map<Integer, Integer> empListStatistics = empList.stream()
                .collect(Collectors.groupingBy(emp::getDeptno, Collectors.summingInt(emp::getComm)));
        // 根据部门枚举作left join
        deptList.stream().map(m -> {
            // 枚举所有部门
            Test1Model model = new Test1Model();
            model.setDeptno(m.getDeptno());
            model.setDname(m.getDname());
            model.setComm(0);
            // 如果计算出来，就重新赋值
            empListStatistics.entrySet().stream().filter(r -> r.getKey().equals(m.getDeptno()))
                    .findFirst().ifPresent(sum -> model.setComm(sum.getValue()));
            return model;
        }).sorted((a1, a2) -> a2.getComm().compareTo(a1.getComm()))
                .forEach(res -> {
                    System.out.println("部门：" + res.getDname() + "  月薪总额：" + res.getComm());
                });
    }

    public class Test1Model {
        private Integer deptno;
        private String dname;
        private Integer comm;

        public Integer getDeptno() {
            return deptno;
        }

        public void setDeptno(Integer deptno) {
            this.deptno = deptno;
        }

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }

        public Integer getComm() {
            return comm;
        }

        public void setComm(Integer comm) {
            this.comm = comm;
        }
    }


    // 列出每个职位名称、平均年收入、入职年龄数、人数，并按照收入排名
    @Test
    public void Test2() throws Exception {
        empList.stream().collect(Collectors.groupingBy(emp::getJob))
                .entrySet().stream().map(m -> {
            Test2Model model = new Test2Model();
            model.setJob(m.getKey());
            // 计算平均年收入
            model.setAvgSalary(m.getValue().stream()
                    .mapToInt(o -> o.getComm() * 12 + (o.getSal() == null ? 0 : o.getSal())).average().getAsDouble());
            // 计算入职平均年限
            model.setAvgYesr(m.getValue().stream()
                    .mapToInt(o -> Period.between(o.getHiredate().toLocalDate(), LocalDate.now()).getYears()).average().getAsDouble());
            model.setCount(m.getValue().size());
            return model;
        }).sorted((a1, a2) -> a2.getAvgSalary().compareTo(a1.getAvgSalary())).forEach(System.out::println);
    }

    public class Test2Model {
        private String job;
        private Double avgSalary;
        private Double avgYesr;
        private Integer count;

        @Override
        public String toString() {
            return "Test2Model{" +
                    "job='" + job + '\'' +
                    ", avgSalary=" + avgSalary +
                    ", avgYesr=" + avgYesr +
                    ", count=" + count +
                    '}';
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public Double getAvgSalary() {
            return avgSalary;
        }

        public void setAvgSalary(Double avgSalary) {
            this.avgSalary = avgSalary;
        }

        public Double getAvgYesr() {
            return avgYesr;
        }

        public void setAvgYesr(Double avgYesr) {
            this.avgYesr = avgYesr;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

    @Test
    // 列出每个职位、月薪最高的那个人名及月薪，并排列
    public void Test3() throws Exception {
        empList.stream().collect(Collectors.groupingBy(emp::getJob, Collectors.maxBy(Comparator.comparing(emp::getComm))))
                .entrySet().stream().map(m -> {
            Test3Model model = new Test3Model();
            model.setJob(m.getKey());
            // 设置名称和收入
            m.getValue().ifPresent(r -> {
                        model.setEname(r.getEname());
                        model.setComm(r.getComm());
                    }
            );
            return model;
        }).sorted((a1, a2) -> a2.getComm().compareTo(a1.getComm())).forEach(System.out::println);
    }

    public class Test3Model {
        private String job;
        private String ename;
        private Integer comm;

        @Override
        public String toString() {
            return "Test3Model{" +
                    "job='" + job + '\'' +
                    ", ename='" + ename + '\'' +
                    ", comm=" + comm +
                    '}';
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public Integer getComm() {
            return comm;
        }

        public void setComm(Integer comm) {
            this.comm = comm;
        }

    }

    @Test
    // 列出每个部门部门名称、年薪最高的那个人名、职位、年薪，并排列
    public void Test4() throws Exception {
        // 标准左连接
        deptList.stream().map(m -> {
            Test4Model model = new Test4Model();
            model.setDeptno(m.getDeptno());
            model.setDname(m.getDname());
            Optional<emp> r = empList.stream().filter(e -> e.getDeptno().equals(m.getDeptno()))
                    .max((b1, b2) -> b2.getComm() * 12 - (b2.getSal() == null ?
                            0 : b2.getSal()) - b1.getComm() * 12 + (b1.getSal() == null ? 0 : b1.getSal()));
            if (r.isPresent()) {
                model.setEname(r.get().getEname());
                model.setJob(r.get().getJob());
                model.setSalary(r.get().getComm() * 12 - (r.get().getSal() == null ? 0 : r.get().getSal()));
            } else {
                model.setSalary(0);
            }
            return model;
        }).sorted((a1, a2) -> a2.getSalary().compareTo(a1.getSalary())).forEach(System.out::println);
    }

    public class Test4Model {
        private Integer deptno;
        private String dname;
        private String ename;
        private String job;
        private Integer salary;

        public Integer getDeptno() {
            return deptno;
        }

        public void setDeptno(Integer deptno) {
            this.deptno = deptno;
        }

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public Integer getSalary() {
            return salary;
        }

        public void setSalary(Integer salary) {
            this.salary = salary;
        }

        @Override
        public String toString() {
            return "Test4Model{" +
                    "deptno=" + deptno +
                    ", dname='" + dname + '\'' +
                    ", ename='" + ename + '\'' +
                    ", job='" + job + '\'' +
                    ", salary=" + salary +
                    '}';
        }
    }

    @Test
    // 有一序列：1、1、2、3、5、8、13... 求出这个数列的前30项之和
    public void Test5() throws Exception {
        long startTime, endTime;
        // 前30项
        int len = 30 - 2;
        startTime = System.currentTimeMillis();
        int result = Stream.iterate(new Integer[]{1, 1}, x -> {
            Integer tp = x[0];
            x[0] = x[1];
            x[1] = tp + x[0];
            return x;
        }).mapToInt(m -> m[1]).limit(len).reduce((sum, o) -> sum += o).getAsInt() + 1 + 1;
        endTime = System.currentTimeMillis();
        System.out.println("Stream时间：" + (endTime - startTime) + "毫秒，结果：" + result);
        startTime = System.currentTimeMillis();
        int a1 = 1, a2 = 2, result1 = 0, tp;
        for (int n = 0; n < len; n++) {
            result1 = a1 + a2;
            tp = a1;
            a1 = a2;
            a2 = a1 + tp;
        }
        endTime = System.currentTimeMillis();
        System.out.println("常规方法时间：" + (endTime - startTime) + "毫秒，结果：" + result1);
        assert result == result1;
    }
    public class Test5Model {
        private Integer a1;
        private Integer a2;

        public Test5Model(Integer a1, Integer a2) {
            this.a1 = a1;
            this.a2 = a2;
        }

        public Integer getA1() {
            return a1;
        }

        public void setA1(Integer a1) {
            this.a1 = a1;
        }

        public Integer getA2() {
            return a2;
        }

        public void setA2(Integer a2) {
            this.a2 = a2;
        }
    }

    /**
     * 部门信息
     */
    public class dept {
        /**
         * 部门编号
         */
        private Integer deptno;
        /**
         * 部门名称
         */
        private String dname;
        /**
         * 部门所在地
         */
        private String loc;


        public dept(Integer deptno, String dname, String loc) {
            this.deptno = deptno;
            this.dname = dname;
            this.loc = loc;
        }

        public Integer getDeptno() {
            return deptno;
        }

        public void setDeptno(Integer deptno) {
            this.deptno = deptno;
        }

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

    }

    /**
     * 雇员信息
     */
    public class emp {
        /**
         * 雇员的编号
         */
        private Integer empno;
        /**
         * 雇员的名字
         */
        private String ename;
        /**
         * 雇员的的职位
         */
        private String job;
        /**
         * 上级主管编号
         */
        private Integer mgr;
        /**
         * 入职(受雇)日期
         */
        private Date hiredate;
        /**
         * 月薪
         */
        private Integer comm;
        /**
         * 年终奖
         */
        private Integer sal;
        /**
         * 部门编号
         */
        private Integer deptno;


        public emp() {
        }

        public emp(Integer empno, String ename, String job, Integer mgr, Date hiredate, Integer comm, Integer sal, Integer deptno) {
            this.empno = empno;
            this.ename = ename;
            this.job = job;
            this.mgr = mgr;
            this.hiredate = hiredate;
            this.comm = comm;
            this.sal = sal;
            this.deptno = deptno;
        }

        public Integer getEmpno() {
            return empno;
        }

        public void setEmpno(Integer empno) {
            this.empno = empno;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public Integer getMgr() {
            return mgr;
        }

        public void setMgr(Integer mgr) {
            this.mgr = mgr;
        }

        public Date getHiredate() {
            return hiredate;
        }

        public void setHiredate(Date hiredate) {
            this.hiredate = hiredate;
        }

        public Integer getComm() {
            return comm;
        }

        public void setComm(Integer comm) {
            this.comm = comm;
        }

        public Integer getSal() {
            return sal;
        }

        public void setSal(Integer sal) {
            this.sal = sal;
        }

        public Integer getDeptno() {
            return deptno;
        }

        public void setDeptno(Integer deptno) {
            this.deptno = deptno;
        }

    }
}
