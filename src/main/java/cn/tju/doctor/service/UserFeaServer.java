package cn.tju.doctor.service;

import cn.tju.doctor.dao.UserMapper;
import cn.tju.doctor.dao.UserfundingMapper;
import cn.tju.doctor.daomain.RetResponse;
import cn.tju.doctor.daomain.User;
import cn.tju.doctor.daomain.Userfunding;
import cn.tju.doctor.utils.numberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yzhpay.sdk.constant.ConfigPath;
import yzhpay.sdk.constant.XmlData;
import yzhpay.sdk.dto.response.Response;
import yzhpay.sdk.pay.order.BankCardOrder;
import yzhpay.sdk.pay.verify.FourFactorVerify;
import yzhpay.sdk.util.HttpUtil;
import yzhpay.sdk.util.JsonUtil;
import yzhpay.sdk.util.Property;
import yzhpay.sdk.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service("UserFeaServer")
public class UserFeaServer {
    @Autowired
    UserfundingMapper userfundingMapper;
    @Autowired
    UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public void money(User user, Userfunding userfunding) throws Exception {

        if (userfunding.getTest() == 2){
            user.setMoney(user.getMoney()+userfunding.getMount());
            try {
                userMapper.updateUser(user);
                userfundingMapper.updateUserfundingTest(userfunding);
            }catch (Exception e){
                System.out.println(e);
                throw new Exception("审核失败");
            }

        }else if (userfunding.getTest() == 1){
            FourFactorVerify fourFactorVerify = new FourFactorVerify();
            fourFactorVerify.setRealName(user.getName());
            fourFactorVerify.setIdNumber(user.getActureID());
            fourFactorVerify.setCardNumber(user.getBankID());
            fourFactorVerify.setPhoneNumber(user.getPhone());
            Map<String, Object> result = null;
            try {
                result = HttpUtil.post(fourFactorVerify.assembleRequest(), Property.getUrl(ConfigPath.YZH_FOUR_FACTOR_BANK_CARD_VERIFY));
            } catch (Exception e) {
                System.out.println(e);
                throw new Exception("验证失败");
            }
            Response verresponse = null;
            try {
                if("200".equals(StringUtils.trim(result.get(XmlData.STATUSCODE)))){
                    verresponse = JsonUtil.fromJson(StringUtils.trim(result.get(XmlData.DATA)), Response.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("验证失败");
            }
            System.out.println(verresponse.getCode());
            if (!verresponse.getCode().equals("0000") ){
                userfunding.setIfWork(2);
                userfunding.setWorkRecord("目标账户不存在");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                userfunding.setWorkTime(df.format(new Date()));
                user.setMoney(user.getMoney()+userfunding.getMount());
                try{
                    userfundingMapper.updateUserfundingWork(userfunding);
                    userMapper.updateUser(user);
                }catch (Exception e){
                    System.out.println(e);
                    throw new Exception("审核失败");
                }
                throw new Exception("目标账户不存在");


            }else {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                BankCardOrder bankOrder = new BankCardOrder();
                bankOrder.setRealName(user.getName());
                bankOrder.setCardNumber(user.getBankID());
                bankOrder.setPhoneNumber(user.getPhone());
                bankOrder.setIdNumber(user.getActureID());
                bankOrder.setPayMoney(String.valueOf(userfunding.getMount()));
                bankOrder.setPayRemark("提现");
                try {
                    Map<String, Object> payResult = HttpUtil.post(bankOrder.assembleRequest(), Property.getUrl(ConfigPath.YZH_BANK_CARD_REAL_TIME_ORDER));
                    Response response = null;
                    try {
                        if("200".equals(StringUtils.trim(result.get(XmlData.STATUSCODE)))){
                            response = JsonUtil.fromJson(StringUtils.trim(payResult.get(XmlData.DATA)), Response.class);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new Exception("验证失败");

                    }
                    if (response.getCode().equals("0000")) {
                        userfunding.setIfWork(1);
                        userfunding.setWorkRecord("ok");
                        userfunding.setWorkTime(df.format(new Date()));
                        try {
                            userfundingMapper.updateUserfundingTest(userfunding);
                            userfundingMapper.updateUserfundingWork(userfunding);
                        }catch (Exception e){
                            System.out.println(e);
                            throw new Exception("审核失败");
                        }

                    } else {
                        userfunding.setIfWork(2);
                        userfunding.setWorkRecord("网络错误");
                        userfunding.setWorkTime(df.format(new Date()));
                        user.setMoney(user.getMoney() + userfunding.getMount());
                        try {
                            userfundingMapper.updateUserfundingTest(userfunding);
                            userfundingMapper.updateUserfundingWork(userfunding);
                            userMapper.updateUser(user);
                        }catch (Exception e){
                            System.out.println(e);
                            throw new Exception("审核失败");
                        }

                    }
                } catch (Exception e) {
                    userfunding.setIfWork(2);
                    userfunding.setWorkRecord("网络错误");
                    userfunding.setWorkTime(df.format(new Date()));
                    user.setMoney(user.getMoney() + userfunding.getMount());
                    try {
                        userfundingMapper.updateUserfundingTest(userfunding);
                        userfundingMapper.updateUserfundingWork(userfunding);
                        userMapper.updateUser(user);
                    }catch (Exception e1){
                        System.out.println(e1);
                        throw new Exception("审核失败");
                    }
                    throw new Exception(e.getMessage());
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public String getID(String source,String go,double money) throws Exception {
        User from = userMapper.getUserByUsername(source).get(0);
        User to = userMapper.getUserByUsername(go).get(0);
        Userfunding userfunding = new Userfunding();
        String number = numberUtils.getOrderNo();
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sf.format(date);
        userfunding.setNumber(number);
        userfunding.setApplyID(source);
        userfunding.setSource(source);
        userfunding.setGo(go);
        userfunding.setMount(money);
        userfunding.setTest(0);
        userfunding.setIfWork(0);
        userfunding.setApplyTime(time);
        if (money > from.getMoney()){
            throw new Exception("余额不足");
        }
        from.setMoney(from.getMoney()-money);
        to.setMoney(to.getMoney()+money);
        try {
            userMapper.updateUser(from);
            userMapper.updateUser(to);
            userfundingMapper.insertUserfunding(userfunding);
        }catch (Exception e){
            throw new Exception("转账出错");
        }
        return number;
    }



}
