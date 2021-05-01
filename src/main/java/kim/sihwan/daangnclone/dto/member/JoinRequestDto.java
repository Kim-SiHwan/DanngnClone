package kim.sihwan.daangnclone.dto.member;

import kim.sihwan.daangnclone.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Builder
public class JoinRequestDto {
    private String username;
    private String password;
    private String nickname;
    private String area;

    public Member toEntity(JoinRequestDto requestDto, PasswordEncoder passwordEncoder){
        return Member.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .nickname(requestDto.getNickname())
                .area(requestDto.getArea())
                .role("ROLE_USER")
                .build();
    }
}
