package com.pomole.ossdemo.sys;

import com.pomole.ossdemo.common.AliyunOSSUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.logging.Logger;

/**
 * @Auther: nemo
 * @Date: 19-10-26 07:29
 * @Description: OSS文件管理
 */
@Controller
public class OssController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/upload")
    public void importOSS(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, ModelMap map) {
        String fileName = file.getOriginalFilename();
        File newFile = new File(fileName);
        FileOutputStream os;
        try {
            os = new FileOutputStream(newFile);
            os.write(file.getBytes());
            os.close();
            file.transferTo(newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //上传到OSS
        String uploadUrl = AliyunOSSUtil.upload(newFile);
        System.out.println();
    }

}
