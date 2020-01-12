package cloud.wf.feignclienta.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Service
@FeignClient("server")
public interface TestFeignService {


    @GetMapping(value = "/feign/{code}")
    public String getFeignTestResult(@PathVariable String code);
}
