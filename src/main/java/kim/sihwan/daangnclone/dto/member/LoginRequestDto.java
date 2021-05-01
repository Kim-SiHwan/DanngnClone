package kim.sihwan.daangnclone.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequestDto {
    private String username;
    private String password;
}
