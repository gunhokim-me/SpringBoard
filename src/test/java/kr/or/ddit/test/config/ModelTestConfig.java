package kr.or.ddit.test.config;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/kr/or/ddit/config/spring/application-context.xml",
        							"classpath:/kr/or/ddit/config/spring/root-context.xml",
        							"classpath:/kr/or/ddit/config/spring/datasource-context.xml"})
public class ModelTestConfig {
	//service, repository 테스트용
	//root-context.xml
	
	@Test @Ignore
	public void dummy() {
		
	}
	
}
