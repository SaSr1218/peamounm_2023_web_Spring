package ezenweb.example.day02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/delete")
public class DeleteMappingController {
    // 1.
    @DeleteMapping("/method1")
    public ParamDto method1( ParamDto dto){
        log.info("delete method1 : " + dto);
        return dto;
    }

    // 2.
    @DeleteMapping("/method2")
    public Map<String , String> method2(@RequestParam Map<String , String> map){
        log.info("delete method2 : " + map);
        return map;
    }


}
