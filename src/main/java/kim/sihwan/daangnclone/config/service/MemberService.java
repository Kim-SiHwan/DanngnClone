package kim.sihwan.daangnclone.config.service;


import kim.sihwan.daangnclone.domain.Member;
import kim.sihwan.daangnclone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = memberRepository.findMemberByUsername(username)
                .orElseThrow();
        return new User(member.getUsername(), member.getPassword(), Collections.singleton(new SimpleGrantedAuthority(member.getRole())));
    }


}
