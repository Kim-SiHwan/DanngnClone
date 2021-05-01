package kim.sihwan.daangnclone.dto.follow;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class FollowRequestDto {
    private Long fromMemberId;
    private Long toMemberId;
}
