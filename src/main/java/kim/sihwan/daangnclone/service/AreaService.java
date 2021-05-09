package kim.sihwan.daangnclone.service;

import kim.sihwan.daangnclone.dto.area.AreaResponseDto;
import kim.sihwan.daangnclone.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AreaService {
    private final AreaRepository areaRepository;

    public List<AreaResponseDto> getAreasByDong(String dong){
        System.out.println(dong+"검색");
        return areaRepository.findAllByDongLike("%"+dong+"%")
                .stream()
                .map(AreaResponseDto::toDto)
                .collect(Collectors.toList());
    }
}
