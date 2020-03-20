package cn.tju.doctor.utils;

import cn.tju.doctor.Config;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class fileUtil {
    public static String upload(MultipartFile file, String username) {

        try {
            String fileName = file.getOriginalFilename();
            String destFileName = Config.UPLOAD_DIR + File.separator + username + File.separator + fileName;

            File destFile = new File(destFileName);
            if (!destFile.getParentFile().exists()){
                destFile.getParentFile().mkdir();
            }
            file.transferTo(destFile);

            return "http://39.96.65.14/doctorfile/upload/" + username + "/" + fileName;
        } catch (Exception e){
            return "文件上传失败";
        }
    }
}
