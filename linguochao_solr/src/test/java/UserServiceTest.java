import com.linguochao.solr.SolrApplication;
import com.linguochao.solr.entity.Person;
import com.linguochao.solr.dto.SearchParams;
import com.linguochao.solr.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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

        List<Person> peronList=new ArrayList<>();

      Person person=new Person();
        person.setId(1L);
        person.setDepartId(new Long[]{30L,40L});
        person.setDepartName(new String[]{"test1","test2"});
        person.setCertificate("17603052022");
        person.setMobile("17603052022");
        person.setDepartPath(new String[]{"0.1.30","0.1.40"});
        person.setName("潜龙在渊");
        person.setStatus("2");
        Map<String,Long> orderMap=new HashMap<>();
        orderMap.put("30",12345L);
        orderMap.put("40",12346L);
        person.setOrdernumberMap(orderMap);
        peronList.add(person);


       Person person2=new Person();
        person2.setId(2L);
        person2.setDepartId(new Long[]{30L,40L});
        person2.setDepartName(new String[]{"test3","test4"});
        person2.setCertificate("17603052023");
        person2.setMobile("17603052023");
        person2.setDepartPath(new String[]{"0.1.30","0.1.40"});
        person2.setName("飞龙在天");
        person2.setStatus("2");
        Map<String,Long> orderMap2=new HashMap<>();
        orderMap2.put("30",12347L);
        orderMap2.put("40",12340L);
        person2.setOrdernumberMap(orderMap2);
        peronList.add(person2);

        personService.saveOrUpdateList(peronList);

        System.out.println("===sucess===");
    }

    @Test
    public void findByPage(){
        Page<Person> byPage = personService.findByPage(0, 10);
        System.out.println(byPage.getContent());
    }

    @Test
    public void delete(){

        List<String> allAccount=new ArrayList<>();
        allAccount.add("11");
        allAccount.add("22");

        allAccount.add("33");
        allAccount.add("44");

        allAccount.add("55");
        allAccount.add("66");

        allAccount.add("77");
        allAccount.add("88");
        int count = allAccount.size();
        int pageSize=2;
        Integer totalPage = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        System.out.println("====全量同步solr===count:"+count+"totalPage:"+totalPage);

        for (Integer i = 1; i <= totalPage; i++) {

            List<String> accounts;

            if (i.equals(totalPage)){
                accounts = allAccount.subList(pageSize*(i-1), count);
            }else {
                accounts=allAccount.subList(pageSize*(i-1),pageSize*i);
            }

            System.out.println("==="+accounts);
            int lll=1;

        }

    }



    @Test
    public void queryByCondition(){
        SearchParams searchParams=new SearchParams();
        searchParams.setDepartId(57);
        searchParams.setIsAll(0);
        ScoredPage<Person> people = personService.queryByCondition(searchParams);
        System.out.println("content"+people.getContent());
        System.out.println("size"+people.getSize());
        System.out.println("pages"+people.getTotalPages());
        System.out.println("total"+people.getTotalElements());

    }


}
