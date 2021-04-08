package com.kuang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FileController {

    @RequestMapping("/upload")
    public String fileUpload(CommonsMultipartFile file, HttpServletRequest request) {

        //获取文件名
        String uploadFileName = file.getOriginalFilename();

        //如果文件名未空，直接回到首页！
        if ("".equals(uploadFileName)) {
            return "redirect:/index.jsp";
        }
        System.out.println("上传文件名：" + uploadFileName);

        //request.getServletContext().getRealPath("")
        return "d";
    }
}
