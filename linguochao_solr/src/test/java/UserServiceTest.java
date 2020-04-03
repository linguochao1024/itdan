import com.linguochao.solr.SolrApplication;
import com.linguochao.solr.entity.Person;
import com.linguochao.solr.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author linguochao
 * @Description TODO
 * @Date 2020/4/3 14:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SolrApplication.class)
@EnableAutoConfiguration
public class UserServiceTest {

    @Autowired
    PersonService personService;

    @Test
    public void saveOrUpdate(){
        Person person=new Person();
        person.setId(1L);
        person.setDepartId(2L);
        person.setDepartName("测试部门");
        person.setCertificate("17603052020");
        person.setMobile("17603052020");
        person.setDepartPath("0.2");
        person.setName("龙战于野");
        person.setStatus("2");
        Person person1 = personService.saveOrUpdate(person);
        System.out.println(person1);
    }

    @Test
    public void findByPage(){
        Page<Person> byPage = personService.findByPage(0, 10);
        System.out.println(byPage.getContent());
    }




}
