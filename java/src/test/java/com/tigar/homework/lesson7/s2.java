package com.tigar.homework.lesson7;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.SynchronousQueue;

/**
 * @author tigar
 * @date 2018/4/30.
 */
public class s2 {

    @Test
    public void t1() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FockJoinTest fockJoinTest = new FockJoinTest(new File("F:\\上课\\高级基础\\MycatJavaHomework\\src\\test\\java\\com\\mycat"), 0);
        long fockhoinStartTime = System.currentTimeMillis();
        //前面我们说过，任务提交中invoke可以直接返回结果
        Map<String, JavaFileInfo> result = forkJoinPool.invoke(fockJoinTest);
        System.out.println("fock/join计算结果耗时" + (System.currentTimeMillis() - fockhoinStartTime));
        result.entrySet().stream().forEach(fo -> {
            System.out.println(String.format("%s总共出现%d次，其中：", fo.getKey(), fo.getValue().getTotalCount()));
            fo.getValue().getDirInfo().entrySet().stream().forEach(dir -> {
//                System.out.println("%d次出现在%s文件夹中", dir.getValue(), dir.getKey());
                System.out.println(dir.getValue().toString() + "次出现在" + dir.getKey() + "文件夹中");
            });
            System.out.println("====================================");
        });

    }


    /**
     * 核心并发框架
     */
    public class FockJoinTest extends RecursiveTask<Map<String, JavaFileInfo>> {
        //目录地址
        private File dirFile;
        //递归目录深度
        private int depth;

        public FockJoinTest(File dirFile, int depth) {
            this.dirFile = dirFile;
            this.depth = depth;
        }

        @Override
        protected Map<String, JavaFileInfo> compute() {
            Map<String, JavaFileInfo> result = new HashMap<>();
            if (depth > 3) {
                return result;
            }
            depth++;
            try {
                Arrays.stream(dirFile.listFiles()).forEach(f -> {
                    if (f.isDirectory()) {
                        //进行递归
                        FockJoinTest fockJoinTest = new FockJoinTest(f, depth);
                        fockJoinTest.fork();
//                        result = merge(result, fockJoinTest.join());
                        fockJoinTest.join().entrySet().stream().forEach(f1 -> {
                            add(result, f1.getKey(), f1.getValue());
                        });
                    } else {
                        if (f.getName().endsWith(".java")) {
                            add(result, f.getName(), f.getParent());
                        }
                    }
                });
            } finally {
            }

            return result;
        }
    }

    /**
     * 辅助方法-往列表添加
     *
     * @param map
     * @param fileName
     * @param info
     * @return
     */
    public Map<String, JavaFileInfo> add(Map<String, JavaFileInfo> map, String fileName, JavaFileInfo info) {
        if (map.containsKey(fileName)) {
            JavaFileInfo obj = map.get(fileName);
            obj.totalCount += info.totalCount;
            info.dirInfo.entrySet().stream().forEach(f -> {
                obj.dirInfo.merge(f.getKey(), f.getValue(), (a1, a2) -> a1 += a2);
            });
        } else {
            map.put(fileName, info);
        }
        return map;
    }

    /**
     * 辅助方法-往列表添加
     *
     * @param map
     * @param fileName
     * @param dirPath
     * @return
     */
    public Map<String, JavaFileInfo> add(Map<String, JavaFileInfo> map, String fileName, String dirPath) {
        if (map.containsKey(fileName)) {
            JavaFileInfo obj = map.get(fileName);
            obj.totalCount++;
            if (obj.dirInfo.containsKey(dirPath)) {
                obj.dirInfo.put(dirPath, obj.dirInfo.get(dirPath) + 1);
            } else {
                obj.dirInfo.put(dirPath, 1);
            }
        } else {
            JavaFileInfo info = new JavaFileInfo();
            info.setTotalCount(1);
            Map<String, Integer> dirInfo = new HashMap<>();
            dirInfo.put(dirPath, 1);
            info.setDirInfo(dirInfo);
            map.put(fileName, info);
        }
        return map;
    }

    /**
     * 统计模型
     */
    public class JavaFileInfo {
        /**
         * 总出现次数
         */
        private int totalCount;
        /**
         * 目录出现次数
         */
        private Map<String, Integer> dirInfo;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public Map<String, Integer> getDirInfo() {
            return dirInfo;
        }

        public void setDirInfo(Map<String, Integer> dirInfo) {
            this.dirInfo = dirInfo;
        }
    }
}
