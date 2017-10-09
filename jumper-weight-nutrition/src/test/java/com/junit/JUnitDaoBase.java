package com.junit;

/**
 * @author rent
 * @desc junit测试基类
 */
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@RunWith(Junit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath*:conf/spring-context.xml"})
 
public class JUnitDaoBase extends AbstractJUnit4SpringContextTests {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//extends AbstractJUnit4SpringContextTests  AbstractTransactionalJUnit4SpringContextTests
	//protected Logger logger = Logger.getLogger(this.getClass());
	/*@Override
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}*/
}
