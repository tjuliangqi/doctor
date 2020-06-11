package cn.tju.doctor.utils;

import cn.tju.doctor.Config;
import cn.tju.doctor.daomain.Userfunding;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVUtils {

    /**
     * 生成为CVS文件
     *
     * @param exportData 源数据List
     * @param outPutPath 文件路径
     * @param fileName   文件名称
     * @return
     */
    public File createCSVFile(List<List<String>> exportData, String outPutPath, String fileName) {
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            File file = new File(outPutPath);
            if (!file.exists()) {
                if (file.mkdirs()) {

                } else {

                }
            }
            //定义文件名格式并创建
            csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "gbk"), 1024);
            for (List<String> exportDatum : exportData) {
                writeRow(exportDatum, csvFileOutputStream);
                csvFileOutputStream.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (csvFileOutputStream != null) {
                    csvFileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    /**
     * 写一行数据
     * @param row       数据列表
     * @param csvWriter
     * @throws IOException
     */
    private void writeRow(List<String> row, BufferedWriter csvWriter) throws IOException {
        int i=0;
        for (String data : row) {
            //csvWriter.write(DelQuota(data));
            if(data==null) data = "null";
            csvWriter.write(data);
            if (i!=row.size()-1){
                csvWriter.write(",");
            }
            i++;
        }
    }

    /**
     * 剔除特殊字符
     */
    public String DelQuota(String str) {
        String result = str;
        String[] strQuota = {"~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "`", ";", "'", ",", ".", "/", ":", "/,", "<", ">", "?"};
        for (int i = 0; i < strQuota.length; i++) {
            if (result.indexOf(strQuota[i]) > -1)
                result = result.replace(strQuota[i], "");
        }
        return result;
    }
    public static String writeCSV(List<Userfunding> userfundings){
        String[] fieldsArr = {"number", "authorID", "mount", "rest", "in", "out", "test", "testtime",
                "testuser", "ifWork", "workTime", "workUser", "record", "source", "sourceAccount",
                "go", "goaccount", "applyID", "applyTime", "testRecord", "workRecord", "type",
                "moneyType"};
        List<String> fieldsList= new ArrayList<>(Arrays.asList(fieldsArr));
        CSVUtils csvUtils=new CSVUtils();
        List<List<String>> listList=new ArrayList<>();
        listList.add(fieldsList);
        List<String> list=null;
        for(Userfunding userfunding:userfundings){
            list=new ArrayList<>();
            list.add(userfunding.getNumber());
            list.add(userfunding.getAuthorID());
            list.add(String.valueOf(userfunding.getMount()));
            list.add(String.valueOf(userfunding.getRest()));
            list.add(String.valueOf(userfunding.getIn()));
            list.add(String.valueOf(userfunding.getOut()));
            list.add(String.valueOf(userfunding.getTest()));
            list.add(userfunding.getTesttime());
            list.add(userfunding.getTestuser());
            list.add(String.valueOf(userfunding.getIfWork()));
            list.add(userfunding.getWorkTime());
            list.add(userfunding.getWorkUser());
            list.add(userfunding.getRecord());
            list.add(userfunding.getSource());
            list.add(userfunding.getSourceAccount());
            list.add(userfunding.getGo());
            list.add(userfunding.getGoaccount());
            list.add(userfunding.getApplyID());
            list.add(userfunding.getApplyTime());
            list.add(userfunding.getTestRecord());
            list.add(userfunding.getWorkRecord());
            list.add(String.valueOf(userfunding.getType()));
            list.add(String.valueOf(userfunding.getMoneyType()));
            listList.add(list);
        }


        File file = csvUtils.createCSVFile(listList,Config.UPLOAD_DIR + File.separator +"CSVFiles"+ File.separator ,"csv");
        System.out.println(file.getPath());
        return "http://39.96.65.14/doctorfile/upload/CSVFiles/" + file.getName();
    }

    /**
     *测试
     */
    public static void main(String[] args) {
        CSVUtils csvUtils=new CSVUtils();
        List<List<String>> listList=new ArrayList<>();
        List<String> list=null;
        for (int i = 0; i <5 ; i++) {
            list=new ArrayList<String>();//一个List为一行
            list.add("测试");
            list.add("测试");
            list.add("测试");
            listList.add(list);
        }
        File file = csvUtils.createCSVFile(listList,"G:\\aaa","csv");
        System.out.println(file.getPath());
    }

}