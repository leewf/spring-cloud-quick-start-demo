package cloud.wf.module.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/f")
public class TestController {
    Logger logger = LoggerFactory.getLogger(TestController.class);



    @RequestMapping("/aaa")
    public String test(){
        logger.info("hello,module-a test");
        return "hello,module-a test";
    }

    @GetMapping("/feign/{code}")
    public String testFeign(@PathVariable String code){
        return "hello,module-a test:"+code;
    }


}
