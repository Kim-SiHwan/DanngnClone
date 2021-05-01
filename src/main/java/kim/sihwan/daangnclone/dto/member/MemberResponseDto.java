package kim.sihwan.daangnclone.dto.member;

import kim.sihwan.daangnclone.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDto {
    private Long id;
    private String username;
    private String nickname;
    private String area;

    public MemberResponseDto(Member member){
        id = member.getId();
        username = member.getUsername();
        nickname = member.getNickname();
        area = member.getArea();
    }
}
