package cloud.wf.feignclienta.controller;


import cloud.wf.feignclienta.service.TestFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    Logger logger = LoggerFactory.getLogger(TestController.class);


    @Autowired
    private TestFeignService testFeignService;



    @GetMapping("ftest")
    public String test(){
        logger.info("ftest");


        return testFeignService.getFeignTestResult("ftest");
    }

}
