package cn.tju.doctor;

import cn.tju.doctor.dao.ProjectfundingMapper;
import cn.tju.doctor.dao.UserfundingMapper;
import cn.tju.doctor.daomain.Projectfunding;
import cn.tju.doctor.daomain.Userfunding;
import cn.tju.doctor.utils.numberUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DoctorApplicationTests {
    @Autowired
    UserfundingMapper userfundingMapper;
    @Autowired
    ProjectfundingMapper projectfundingMapper;
    @Test
    public void contextLoads() {
    }

    @Test
    public void testUserfunding(){
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sf.format(date);
        Userfunding userfunding = new Userfunding();
        userfunding.setNumber(numberUtils.getOrderNo());
        userfunding.setApplyID("lq");
        userfunding.setApplyTime(time);
        userfunding.setRecord("tixian");
        userfunding.setAuthorID("wy");
        userfunding.setGo("tixianzhanghu");
        userfunding.setGoaccount("wyaccount");
        userfunding.setSource("project");
        userfunding.setSourceAccount("national");
        userfunding.setIfWork(0);
        userfunding.setIn(1);
        userfunding.setMount(400.21);
        userfunding.setOut(0);
        userfunding.setRest(100.35);
//        userfundingMapper.insertUserfunding(userfunding);

        //测试查询
        System.out.println("查询：");
        System.out.println(userfundingMapper.getUserfundingByNumber("1000002064526948202002241102195").getApplyID());
        //测试更新
        System.out.println("更新：");
        Userfunding update = userfundingMapper.getUserfundingByNumber("1000002064526948202002241102195");
        update.setTest(1);
        System.out.println(userfundingMapper.updateUserfundingTest(update));
    }

    @Test
    public void testProjectfunding(){
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sf.format(date);
        Projectfunding projectfunding = new Projectfunding();
        projectfunding.setNumber(numberUtils.getOrderNo());
        projectfunding.setApplyID("lq");
        projectfunding.setApplyTime(time);
        projectfunding.setRecord("tixian");
        projectfunding.setProjectID("wy");
        projectfunding.setGo("tixianzhanghu");
        projectfunding.setGoaccount("wyaccount");
        projectfunding.setSource("project");
        projectfunding.setSourceAccount("national");
        projectfunding.setIfWork(0);
        projectfunding.setIn(1);
        projectfunding.setMount(400.21);
        projectfunding.setOut(0);
        projectfunding.setRest(100.35);
//        projectfundingMapper.insertProjectfunding(projectfunding);

        //测试查询
        System.out.println("查询：");
        System.out.println(projectfundingMapper.getProjectfundingByNumber("6000000789359373202002242102769").getApplyID());
        //测试更新
        System.out.println("更新：");
        Projectfunding update = projectfundingMapper.getProjectfundingByNumber("6000000789359373202002242102769");
        update.setIfWork(1);
        System.out.println(projectfundingMapper.updateProjectfundingWork(update));
    }

}
