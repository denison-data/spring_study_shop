package com.denison.shops.service;

import com.denison.shops.domain.member.Member;
import com.denison.shops.dto.api.LoginRequestDto;
import com.denison.shops.dto.api.LoginResponseDto;
import com.denison.shops.repository.MemberRepository;
import com.denison.shops.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    //private final RedisService redisService;

    public AuthService(MemberRepository memberRepository, JwtProvider jwtProvider, RedisService redisService) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
        //this.redisService = redisService;
    }


    // 로그인처리
    public LoginResponseDto login(LoginRequestDto request) {
        List<Member> members = memberRepository.findByUserid(request.getUserid());
        if (members.isEmpty()) {
            throw new RuntimeException("회원 없음");
        }
        Member member = members.get(0); // 첫 번째만 사용하거나, 조건에 맞는 것 선택

        if (!member.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("비밀번호 불일치");
        }
        String token = jwtProvider.createToken(member.getUserid());
        System.out.println("Generated JWT Token: " + token);

        return new LoginResponseDto(token, member.getName(), member.getNo());
    }
    public void logout() {
        // 서버에서 특별히 할 일 없음 (Redis 안 쓰면 블랙리스트 관리 불가)
    }

    // 로그인 체크
    public boolean checkLogin(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            return jwtProvider.validateToken(token);
        }
        return false;
    }

}
