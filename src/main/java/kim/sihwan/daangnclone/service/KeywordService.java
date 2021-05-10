package kim.sihwan.daangnclone.service;

import kim.sihwan.daangnclone.domain.Keyword;
import kim.sihwan.daangnclone.domain.Member;
import kim.sihwan.daangnclone.dto.keyword.KeywordRequestDto;
import kim.sihwan.daangnclone.dto.keyword.KeywordResponseDto;
import kim.sihwan.daangnclone.repository.KeywordRepository;
import kim.sihwan.daangnclone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeywordService {
    private final KeywordRepository keywordRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long addKeyword(KeywordRequestDto keywordRequestDto){
        Member member = memberRepository.findMemberByUsername(keywordRequestDto.getUsername());
        Keyword keyword = Keyword.builder()
                .keyword(keywordRequestDto.getKeyword())
                .build();
        keyword.addMember(member);
        keywordRepository.save(keyword);
        //여기서 SetTopic
        return keyword.getId();
    }
/*    public void setTopic(fcm token) throws InterruptedException, ExecutionException, FirebaseMessagingException, FirebaseAuthException {

        List<String> rt= Collections.singletonList(fcm token 넣어주고);
        TopicManagementResponse response = FirebaseMessaging.getInstance()
                .subscribeToTopic(rt,"test_Topic");

        System.out.println(response.getSuccessCount());
        System.out.println(response);

    }*/

    @Transactional
    public void deleteKeyword(Long keywordId){
        keywordRepository.deleteById(keywordId);
        //여기서 removeTopic
    }

    public List<KeywordResponseDto> getKeywordsByUsername(String username){
        return keywordRepository.findAllByMemberUsername(username)
                .stream()
                .map(KeywordResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public List<Keyword> getKeywordsByKeywordBody(String keyword){

        return keywordRepository.findAllByKeyword(keyword);
    }




}
