package com.ivlai.note.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MybatisUtil {

    /**
     * - Mybatis反向生成工具: 数据库 -> 本地
     */
    public static void Generator() throws Exception {
        List<String> warnings = new ArrayList<String>();

        // idea 多项目工程需要添加 项目名称 + 全路径:projectName/src/main/resources/generatorConfig.xml
//        File configFile = new File("generatorConfig.xml");
        File configFile = new File("src/main/resources/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        org.mybatis.generator.config.Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true); /* 是否覆盖 */
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    /**
     * 数据库操作测试
     */
    public static void mybatisTest() throws IOException {
        /* 常见测试工厂并打开配置文件读取配置 */
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();

        int insert = sqlSession.insert("方法", "参数");

        sqlSession.close();
        resourceAsStream.close();
    }

    public static void main(String[] args) {
        try {
//            Generator();
            mybatisTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
