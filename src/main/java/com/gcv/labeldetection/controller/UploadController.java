package com.gcv.labeldetection.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gcv.labeldetection.entity.Label;
import com.gcv.labeldetection.service.LabelDetection;

@Controller
public class UploadController {

    @Autowired
    private LabelDetection labelDetection;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }
    
    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
    								Model model,                       
    								RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {
            byte[] bytes = file.getBytes();
            List<Label> labelList = labelDetection.labelDetect(bytes);
            model.addAttribute("labels", labelList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "result";
    }

    @GetMapping("/result")
    public String uploadStatus(Model model) {
        return "result";
    }
}