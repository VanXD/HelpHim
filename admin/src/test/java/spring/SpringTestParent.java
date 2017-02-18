package spring;


import com.vanxd.admin.start.Start;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring测试基类
 *
 * Created by wyd on 2016/6/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Start.class)
@ActiveProfiles(profiles = "dev")
@Transactional
@Rollback
public class SpringTestParent {
}
