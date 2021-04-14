package kim.sihwan.daangnclone.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestBoardController {

    @GetMapping("/v1")
    public String testV1(){
        String msg="This is Test";
        return msg;
    }

    @GetMapping("/v2")
    public String testV2(){
        String msg ="Only Authenticated User";
        return msg;
    }

    @GetMapping("/v3")
    public String testV3(){
        String msg ="With Mock User";
        return msg;
    }
}
