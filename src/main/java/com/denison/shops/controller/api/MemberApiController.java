package com.denison.shops.controller.api;

import com.denison.shops.domain.member.Member;
import com.denison.shops.dto.api.MemberDto;
import com.denison.shops.repository.MemberRepository;
import com.denison.shops.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/member")
@Slf4j
public class MemberApiController {
    private final MemberService memberService;
    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDto> getMyInfo(Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("인증되지 않은 사용자입니다.");
        }
        String userid = authentication.getName();
        return ResponseEntity.ok(memberService.getMemberInfo(userid));
    }


}
