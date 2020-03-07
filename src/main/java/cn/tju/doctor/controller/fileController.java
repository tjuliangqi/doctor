package cn.tju.doctor.controller;

import cn.tju.doctor.Config;
import cn.tju.doctor.daomain.RetResponse;
import cn.tju.doctor.daomain.RetResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class fileController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public RetResult<String> upload(@RequestParam("file") MultipartFile file, @RequestBody Map<String,String> map) {
        String username = String.valueOf(map.get("username"));
        try {
            String fileName = file.getOriginalFilename();
            String destFileName = Config.UPLOAD_DIR + File.separator + username + File.separator + fileName;

            File destFile = new File(destFileName);
            if (!destFile.getParentFile().exists()){
                destFile.getParentFile().mkdir();
            }
            file.transferTo(destFile);

            return RetResponse.makeOKRsp(File.separator +"doctorfile"+File.separator +"upload"+File.separator + username + File.separator + fileName);
        } catch (Exception e){
            return RetResponse.makeErrRsp("文件上传失败");
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public RetResult<List<String>> list(@RequestBody Map<String,String> map) {
        String username = String.valueOf(map.get("username"));
        List<String> list = new ArrayList<>();
        try {

            String destFileName = Config.UPLOAD_DIR + File.separator + username;

            File destFile = new File(destFileName);
            if (destFile.getParentFile().exists()){
               File[] files = destFile.listFiles();
               for (File file:files){
                   list.add(file.getName());
               }
            }

            return RetResponse.makeOKRsp(list);
        } catch (Exception e){
            return RetResponse.makeErrRsp("文件拉取失败");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public RetResult<List<String>> pic(@RequestBody Map<String,String> map) {
        String username = String.valueOf(map.get("username"));
        String filename = String.valueOf(map.get("filename"));
        List<String> list = new ArrayList<>();
        try {

            String destFileName = Config.UPLOAD_DIR + File.separator + username + File.separator + filename;

            File destFile = new File(destFileName);
            if (destFile.getParentFile().exists()){
                destFile.delete();
            }

            return RetResponse.makeOKRsp(list);
        } catch (Exception e){
            return RetResponse.makeErrRsp("文件删除失败");
        }
    }
}
