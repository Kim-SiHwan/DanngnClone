package kim.sihwan.daangnclone.dto.follow;

import kim.sihwan.daangnclone.dto.member.MemberResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FollowResponseDto {
    private List<MemberResponseDto> followers;
    private List<MemberResponseDto> followings;
}
