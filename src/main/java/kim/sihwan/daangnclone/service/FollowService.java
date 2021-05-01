package kim.sihwan.daangnclone.service;

import kim.sihwan.daangnclone.domain.Follow;
import kim.sihwan.daangnclone.domain.Member;
import kim.sihwan.daangnclone.dto.follow.FollowRequestDto;
import kim.sihwan.daangnclone.dto.follow.FollowResponseDto;
import kim.sihwan.daangnclone.dto.member.MemberResponseDto;
import kim.sihwan.daangnclone.repository.FollowRepository;
import kim.sihwan.daangnclone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Follow follow(FollowRequestDto followRequestDto) {
        Member fromMember = memberRepository.findById(followRequestDto.getFromMemberId()).orElseThrow(NoSuchElementException::new);
        System.out.println(fromMember.getUsername());
        Member toMember = memberRepository.findById(followRequestDto.getToMemberId()).orElseThrow(NoSuchElementException::new);
        System.out.println(fromMember.getUsername());
        System.out.println(toMember.getUsername());
        Follow follow = new Follow();
        follow.addFromMember(fromMember);
        follow.addToMember(toMember);
        followRepository.save(follow);
        return follow;
    }


    public FollowResponseDto getFollowInfo(Long memberId) {
        return FollowResponseDto.builder()
                .followers(getFromMembers(memberId))
                .followings(getToMembers(memberId))
                .build();
    }

    public List<MemberResponseDto> getToMembers(Long memberId) {
        List<Follow> fromMemberList = followRepository.findAllByFromMemberId(memberId);
        List<MemberResponseDto> list = fromMemberList
                .stream()
                .map(m -> new MemberResponseDto(m.getToMember()))
                .collect(Collectors.toList());
        return list;
    }

    public List<MemberResponseDto> getFromMembers(Long memberId) {
        List<Follow> toMemberList = followRepository.findAllByToMemberId(memberId);
        List<MemberResponseDto> list = toMemberList
                .stream()
                .map(m -> new MemberResponseDto(m.getFromMember()))
                .collect(Collectors.toList());
        return list;
    }

    @Transactional
    public void unfollow(Long fromMemberId, Long toMemberId) {
        System.out.println("언팔");
        followRepository.deleteByFromMemberIdAndToMemberId(fromMemberId, toMemberId);
    }


}
