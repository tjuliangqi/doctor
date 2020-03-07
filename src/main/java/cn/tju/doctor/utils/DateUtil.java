package cn.tju.doctor.utils;

import cn.tju.doctor.dao.DataMapper;
import cn.tju.doctor.daomain.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DateUtil {
    public static String gainDate(){
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String dateStr=sdf.format(date);
        return dateStr;
    }
    public static void update(DataMapper dataMapper, int type, String date){
        Data data = dataMapper.getDataById(date);
        if (data==null){
            data = new Data();
            data.setArticle(0);
            data.setDownload(0);
            data.setHide(0);
            data.setLike(0);
            data.setView(0);
            data.setDate(date);
            dataMapper.insertData(data);
        }
        switch (type){
            case 0:{
                int n = data.getArticle();
                n++;
                data.setArticle(n);
                break;
            }
            case 1:{
                int n = data.getDownload();
                n++;
                data.setDownload(n);
                break;
            }
            case 2:{
                int n = data.getLike();
                n++;
                data.setLike(n);
                break;
            }
            case 3:{
                int n = data.getHide();
                n++;
                data.setHide(n);
                break;
            }
            case 4:{
                int n = data.getView();
                n++;
                data.setView(n);
                break;
            }
            default:break;
        }
        dataMapper.updateData(data);
    }
}
