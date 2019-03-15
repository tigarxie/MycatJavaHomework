package com.tigar.lucene;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * @author tigar
 * @date 2019/1/22.
 */
public class LuceneService {

    /**
     * 建立索引
     *
     * @return
     */
    public void createIndex() throws Exception {
        //指定索引库的存放位置Directory对象
        Directory directory = FSDirectory.open(Paths.get("E:\\Lucene\\LuceneTest"));
        //索引库还可以存放到内存中
        //Directory directory = new RAMDirectory();

        //指定一个标准分析器，对文档内容进行分析
        Analyzer analyzer = new StandardAnalyzer();

        //创建indexwriterCofig对象
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        //创建一个indexwriter对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        //原始文档的路径
        File file = new File("E:\\Lucene\\LuceneSource");
        File[] fileList = file.listFiles();
        for (File file2 : fileList) {

            Path path = Paths.get(file2.getPath());

            //创建document对象
            Document document = new Document();

            //创建field对象，将field添加到document对象中

            //文件名称
            String fileName = file2.getName();
            //创建文件名域
            //第一个参数：域的名称
            //第二个参数：域的内容
            //第三个参数：是否存储
            Field fileNameField = new TextField("fileName", fileName, Store.YES);

            //文件的大小
            long fileSize = Files.size(path);
            //文件大小域
            Field fileSizeField = new TextField("fileSize", String.valueOf(fileSize), Store.YES);

            //文件路径
            String filePath = file2.getPath();
            //文件路径域（不分析、不索引、只存储）
            Field filePathField = new StoredField("filePath", filePath);

            //文件内容
            String fileContent = new String(Files.readAllBytes(path), "UTF-8");

            //文件内容域
            Field fileContentField = new TextField("fileContent", fileContent, Store.YES);

            document.add(fileNameField);
            document.add(fileSizeField);
            document.add(filePathField);
            document.add(fileContentField);
            //使用indexwriter对象将document对象写入索引库，此过程进行索引创建。并将索引和document对象写入索引库。
            indexWriter.addDocument(document);
        }
        //关闭IndexWriter对象。
        indexWriter.close();
    }

    /**
     * 查询
     *
     * @throws Exception
     */
    public void query() throws Exception {

//        //可以指定默认搜索的域是多个
//        String[] fields = {"fileName", "fileContent"};
//        //创建一个MulitFiledQueryParser对象
//        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(fields, new IKAnalyzer());
//        Query query = queryParser.parse("apache");
//        System.out.println(query);

        // ================= 精准查找 ======================
        Query q = new TermQuery(new Term("fileName", "5"));
        // ================= 精准查找 ======================


        query(q);
    }

    /**
     * 查询
     *
     * @throws Exception
     */
    public void query(Query q) throws Exception {
        //创建一个Directory对象，指定索引库存放的路径
        Directory directory = FSDirectory.open(Paths.get("E:\\Lucene\\LuceneTest"));
        //创建IndexReader对象，需要指定Directory对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //创建Indexsearcher对象，需要指定IndexReader对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);


        //执行查询

        //第一个参数是查询对象，第二个参数是查询结果返回的最大值
        TopDocs topDocs = indexSearcher.search(q, 10);

        //查询结果的总条数
        System.out.println("查询结果的总条数：" + topDocs.totalHits);
        //遍历查询结果
        //topDocs.scoreDocs存储了document对象的id
        //ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            //scoreDoc.doc属性就是document对象的id
            //int doc = scoreDoc.doc;
            //根据document的id找到document对象
            Document document = indexSearcher.doc(scoreDoc.doc);
            //文件名称
            System.out.println(document.get("fileName"));
            //文件内容
            System.out.println(document.get("fileContent"));
            //文件大小
            System.out.println(document.get("fileSize"));
            //文件路径
            System.out.println(document.get("filePath"));
            System.out.println("----------------------------------");
        }
        //关闭indexreader对象
        indexReader.close();
    }
}
