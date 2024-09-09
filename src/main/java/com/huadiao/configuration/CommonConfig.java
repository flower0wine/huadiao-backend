package com.huadiao.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.huadiao.constant.ProgramEnvironment;
import com.huadiao.service.upload.emote.EmoteUpload;
import com.huadiao.service.upload.fragment.FragmentUpload;
import com.huadiao.service.upload.video.VideoFragmentUpload;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.core.config.Configurator;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;

/**
 * Spring 配置类
 *
 * @author flowerwine
 * @version 1.1
 */
@Slf4j
@Configuration
@ImportResource("classpath:spring-ioc.xml")
@EnableTransactionManagement
@MapperScan(basePackages = "com.huadiao.mapper", sqlSessionFactoryRef = "mysqlSqlSessionFactory")
public class CommonConfig {

    @Value("${fileUpload.video.tempDir}")
    private String videoTempDir;

    @Value("${fileUpload.video.storeDir}")
    private String videoStoreDir;

    @Value("${fileUpload.video.chunkSize}")
    private Integer videoChunkSize;

    @Bean("videoFragmentUploadBean")
    public FragmentUpload createVideoFragmentUpload() {
        return new VideoFragmentUpload(videoTempDir, videoStoreDir, videoChunkSize);
    }


    @Value("${fileUpload.emote.storeDir}")
    private String emoteStoreDir;

    @Bean("emoteUploadBean")
    public EmoteUpload createEmoteUpload() {
        return new EmoteUpload(emoteStoreDir);
    }

    @Autowired
    public CommonConfig(Environment environment) {
        String env = environment.getActiveProfiles()[0];

        if (ProgramEnvironment.DEVELOPMENT.equals(env)) {
            Configurator.initialize("devLogger", "classpath:log4j2-dev.xml");
        } else if (ProgramEnvironment.PRODUCTION.equals(env)) {
            Configurator.initialize("prodLogger", "classpath:log4j2-prod.xml");
        } else {
            log.warn("The current program running environment is not recognized");
            return;
        }
        log.debug("The current program running environment is: {}", env);
    }

    @Value("${jedis.host}")
    private String jedisHost;

    @Value("${jedis.port}")
    private int jedisPort;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig) {
        return new JedisPool(jedisPoolConfig, jedisHost, jedisPort);
    }

    @Value("${mysql.driverClassName}")
    private String driverClassName;

    @Value("${mysql.url}")
    private String url;

    @Value("${mysql.username}")
    private String username;

    @Value("${mysql.password}")
    private String password;

    @Bean
    public DataSource mysqlDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager mysqlTransactionManager(DataSource mysqlDataSource) {
        return new DataSourceTransactionManager(mysqlDataSource);
    }

    @Bean
    public SqlSessionFactory mysqlSqlSessionFactory(DataSource mysqlDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mysqlDataSource);
        sessionFactory.setConfigLocation(new ClassPathResource("mysql-mybatis-config.xml"));
        return sessionFactory.getObject();
    }
}
