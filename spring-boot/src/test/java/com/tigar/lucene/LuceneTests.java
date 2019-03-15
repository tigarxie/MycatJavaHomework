package com.tigar.lucene;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author tigar
 * @date 2019/1/23.
 */
public class LuceneTests {

    @Test
    public void createIndexTest() {

        LuceneService service = new LuceneService();
        try {
            service.createIndex();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

    @Test
    public void queryTest() {

        LuceneService service = new LuceneService();
        try {
            service.query();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert true;
    }
}
