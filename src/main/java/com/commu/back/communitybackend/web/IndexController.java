package com.commu.back.communitybackend.web;

import com.commu.back.communitybackend.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final HttpSession httpSession;

//    @GetMapping("/")
//    public String index() {
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//        if (user != null) {
//
//        }
//    }
}
