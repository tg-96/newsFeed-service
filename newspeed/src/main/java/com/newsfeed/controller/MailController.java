package com.newsfeed.controller;

import com.newsfeed.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    /**
     *
     * 메일보냄
     *
     * @return
     * 인증번호
     */
    @PostMapping("/mail/{mail}")
    public String MailSend(@PathVariable("mail") String mail){
        int number = mailService.sendMail(mail);
        String num = ""+number;
        return num;
    }
}
