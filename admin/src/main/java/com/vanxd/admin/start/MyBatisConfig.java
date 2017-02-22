package com.vanxd.admin.start;

import com.alibaba.druid.pool.DruidDataSource;
import com.vanxd.admin.config.datasource.DataSourceTypeEnum;
import com.vanxd.admin.config.datasource.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * MyBatis Java Configuration
 * @author wyd on 2016/9/7.
 */
@Configuration
public class MyBatisConfig implements EnvironmentAware {
    private static Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * 设置 写操作 的数据源
     * @return
     */
    @Bean (name = "writeDataSource", destroyMethod = "close")
    public DataSource getWriteDataSource() {
        return buildDataSource("write");
    }

    /**
     * 设置 读操作 的数据源
     * @return
     */
    @Bean (name = "readDataSource1", destroyMethod = "close")
    public DataSource getReadDataSource1() {
        return buildDataSource("read1");
    }

    /**
     * 构建数据源
     * @param dsName    数据源名称
     * @return
     */
    private DataSource buildDataSource(String dsName) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(environment.getProperty("datasource." + dsName + ".username"));
        dataSource.setPassword(environment.getProperty("datasource." + dsName + ".password"));
        dataSource.setDriverClassName(environment.getProperty("datasource." + dsName + ".driverClass"));
        dataSource.setUrl(environment.getProperty("datasource." + dsName + ".url"));
        return dataSource;
    }

    @Bean (name = "datasource")
    @Primary
    public AbstractRoutingDataSource dataSource(@Qualifier("writeDataSource") DataSource writeDataSource,
                                                @Qualifier("readDataSource1") DataSource readDataSource1) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceTypeEnum.READ, readDataSource1);
        targetDataSources.put(DataSourceTypeEnum.WRITE, writeDataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(writeDataSource);
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
