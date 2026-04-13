package com.denison.shops.service;

import com.denison.shops.dto.api.MemberDto;
import com.denison.shops.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }


    public MemberDto getMemberInfo(String userid) {
        return memberRepository.findByUserid(userid)
                .stream()
                .findFirst()
                .map(MemberDto::new)
                .orElseThrow(() -> new RuntimeException("회원 없음"));
    }

}
