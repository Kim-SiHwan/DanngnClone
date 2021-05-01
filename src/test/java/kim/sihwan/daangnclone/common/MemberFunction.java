package kim.sihwan.daangnclone.common;


import kim.sihwan.daangnclone.dto.member.JoinRequestDto;
import kim.sihwan.daangnclone.dto.member.LoginRequestDto;
import kim.sihwan.daangnclone.dto.member.LoginResponseDto;
import kim.sihwan.daangnclone.service.MemberService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberFunction {

    @Autowired
    private MemberService memberService;

    public void join(String username, String password, String nickname){
        JoinRequestDto joinRequestDto = JoinRequestDto
                .builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .area("만수3동")
                .build();
        memberService.join(joinRequestDto);
    }

    public LoginResponseDto login(String username, String password){
        LoginRequestDto loginRequestDto = LoginRequestDto
                .builder()
                .username(username)
                .password(password)
                .build();
        return memberService.login(loginRequestDto);
    }
}
