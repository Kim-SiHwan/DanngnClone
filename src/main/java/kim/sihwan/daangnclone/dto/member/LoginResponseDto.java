package kim.sihwan.daangnclone.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private Long id;
    private String token;
    private String username;
    private String nickname;

    public LoginResponseDto(Long id, String token, String username, String nickname) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.nickname = nickname;
    }
}
