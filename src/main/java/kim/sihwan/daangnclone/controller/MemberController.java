package kim.sihwan.daangnclone.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kim.sihwan.daangnclone.service.MemberService;
import kim.sihwan.daangnclone.dto.member.JoinRequestDto;
import kim.sihwan.daangnclone.dto.member.LoginRequestDto;
import kim.sihwan.daangnclone.dto.member.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Api(tags = {"1. Member"})
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/test")
    public String test(){
        String msg = "hihi This is Test!";
        return msg;
    }

    @PostMapping("/join")
    @ApiOperation(value = "회원가입", notes = "회원 정보를 입력해 가입")
    public Long join(@RequestBody JoinRequestDto joinRequestDto){
        return memberService.join(joinRequestDto);
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "회원 정보를 입력해 로그인")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return new ResponseEntity<>(memberService.login(loginRequestDto), HttpStatus.OK);
    }

}
