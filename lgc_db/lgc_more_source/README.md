

**多数据源的应用场景** 主要针对跨多个 MySQL 实例的情况；如果是同实例中的多个数据库，则没必要使用多数据源。

实现多数据源
**步骤一**，在 springBoot 中，增加多数据源的配置，如下所示：
```
spring:
    datasource:
        type: com.alibaba.druid.pool.DruidDataSource
        driverClassName: com.mysql.jdbc.Driver
        druid:
            first:  #数据源1
                url: jdbc:mysql:
                username: *******
                password: *******
            second:  #数据源2
                url: jdbc:mysql:
                username: *******
                password: *******
```                
**步骤二**，扩展 Spring 的 AbstractRoutingDataSource 抽象类，AbstractRoutingDataSource 中的抽象方法 determineCurrentLookupKey 是实现多数据源的核心，并对该方法进行 Override，如下所示：
```
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }

    public static void setDataSource(String dataSource) {
        contextHolder.set(dataSource);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }

}
```
```
public interface DataSourceNames {
    String FIRST = "first";
    String SECOND = "second";
}
```
**步骤三**，配置 DataSource，指定数据源的信息，如下所示：
```
@Configuration
public class DynamicDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.first")
    public DataSource firstDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource secondDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource firstDataSource, DataSource secondDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceNames.FIRST, firstDataSource);
        targetDataSources.put(DataSourceNames.SECOND, secondDataSource);
        return new DynamicDataSource(firstDataSource, targetDataSources);
    }
}
```
**步骤四**，通过注解，实现多数据源，如下所示：
```
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default "";
}
```
```
@Aspect
@Component
public class DataSourceAspect implements Ordered {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(cn.com.do1.dframework.common.datasources.annotation.DataSource)")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        DataSource ds = method.getAnnotation(DataSource.class);
        if(ds == null){
            DynamicDataSource.setDataSource(DataSourceNames.FIRST);
            logger.debug("set datasource is " + DataSourceNames.FIRST);
        }else {
            DynamicDataSource.setDataSource(ds.name());
            logger.debug("set datasource is " + ds.name());
        }

        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
            logger.debug("clean datasource");
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
```
**步骤五**,测试多数据源
```
@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicDataSourceTest {
    @Autowired
    private DsfRoleService dsfRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Test
    public void test() {
        //数据源1
     long id = 1;
      DsfRoleEntity role = dsfRoleService.selectById(id);
      System.out.println(ToStringBuilder.reflectionToString(role));
      //数据源2
      SysUserRoleEntity userRoleEntity=getUserRole(id);
      System.out.println(ToStringBuilder.reflectionToString(userRoleEntity));
    }
    @DataSource(name = DataSourceNames.SECOND)
    public  SysUserRoleEntity getUserRole(Long id){
        return sysUserRoleService.selectById(id);
    }
}
```