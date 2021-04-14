package kim.sihwan.daangnclone.controller;

import kim.sihwan.daangnclone.domain.Area;
import kim.sihwan.daangnclone.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestTestController {

    private final AreaRepository areaRepository;

    @GetMapping("/v1/{area}")
    @Cacheable(key ="#area", value = "nearArea", unless = "#result==null", cacheManager = "cacheManager")
    public List<Area> testV1(@PathVariable String area){
        List<Area> list = areaRepository.findAllByCityLike("%"+area+"%");
        return list;
    }

    @GetMapping
    @Cacheable(key ="0", value = "allArea")
    public List<Area> testTest(){
        return areaRepository.findAll();
    }
}
