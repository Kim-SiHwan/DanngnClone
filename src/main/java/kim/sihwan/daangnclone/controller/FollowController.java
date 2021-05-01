package kim.sihwan.daangnclone.controller;

import io.swagger.annotations.Api;
import io.swagger.models.Response;
import kim.sihwan.daangnclone.dto.follow.FollowRequestDto;
import kim.sihwan.daangnclone.dto.follow.FollowResponseDto;
import kim.sihwan.daangnclone.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowController {
    private final FollowService followService;

    /*@PostMapping
    public Long follow(@RequestBody FollowRequestDto followRequestDto){
        return followService.follow(followRequestDto);
    }
*/
  /*  @GetMapping("/{memberId}")
    public ResponseEntity<FollowResponseDto> getFollowers(@PathVariable Long memberId){
        return new ResponseEntity<>(followService.getFollowInfo(memberId), HttpStatus.OK);
    }

    @DeleteMapping
    public void unfollow(@RequestBody FollowRequestDto followRequestDto){
        followService.unfollow(followRequestDto.getFromMemberId(), followRequestDto.getToMemberId());
    }*/
}
