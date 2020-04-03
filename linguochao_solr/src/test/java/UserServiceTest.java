import com.linguochao.solr.SolrApplication;
import com.linguochao.solr.entity.Person;
import com.linguochao.solr.entity.SearchParams;
import com.linguochao.solr.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.ScoredPage;
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
        person.setOrderNumber("27645544");
        Person person1 = personService.saveOrUpdate(person);
        System.out.println(person1);
    }

    @Test
    public void findByPage(){
        Page<Person> byPage = personService.findByPage(0, 10);
        System.out.println(byPage.getContent());
    }


    @Test
    public void queryByCondition(){
        SearchParams searchParams=new SearchParams();
        searchParams.setDepartId(2);
        searchParams.setDepartmentPath("0.2");
        searchParams.setStatus(2);
        searchParams.setIsAll(1);
        //searchParams.setKeywords("176");
        ScoredPage<Person> people = personService.queryByCondition(searchParams);

        System.out.println("content"+people.getContent());
        System.out.println("size"+people.getSize());
        System.out.println("pages"+people.getTotalPages());
        System.out.println("total"+people.getTotalElements());

    }



}
