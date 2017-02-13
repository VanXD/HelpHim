package com.vanxd.admin.start;

import com.alibaba.druid.pool.DruidDataSource;
import com.vanxd.admin.config.DataSourceProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * MyBatis Java Configuration
 * @author wyd on 2016/9/7.
 */
@Configuration
public class MyBatisConfig implements EnvironmentAware {
    private Environment environment;
    private DataSourceProperties dsProperties;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.dsProperties = new DataSourceProperties(environment.getProperty("datasource.username"),
                                                     environment.getProperty("datasource.password"),
                                                     environment.getProperty("datasource.driverClass"),
                                                     environment.getProperty("datasource.url"));
    }

    /**
     * 设置数据源
     * todo 设置druid的其他属性
     * @return
     */
    @Bean (name = "dataSource", destroyMethod = "close")
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(dsProperties.getUsername());
        dataSource.setPassword(dsProperties.getPassword());
        dataSource.setDriverClassName(dsProperties.getDriverClass());
        dataSource.setUrl(dsProperties.getUrl());
        return dataSource;
    }

    /**
     * 配置sessionFactory，在其中扫描MyBatis 实体的包 和mapper XML文件
     * @return
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) {

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.vanxd.data.entity");

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            //设置xml扫描路径
            bean.setMapperLocations(resolver.getResources("classpath:/mybatis/mapper/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            throw new RuntimeException("sqlSessionFactory init fail",e);
        }
    }

    /**
     * 配置事务管理器
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }
    /**
     * 扫描mapper 接口文件
     * @return
     */
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.vanxd.data.mapper");
        return mapperScannerConfigurer;
    }
}
