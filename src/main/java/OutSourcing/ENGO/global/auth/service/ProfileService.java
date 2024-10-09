package OutSourcing.ENGO.global.auth.service;


import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.member.repository.Member.MemberRepository;
import OutSourcing.ENGO.domain.member.service.MemberService;
import OutSourcing.ENGO.global.auth.dto.GeneratedTokenDTO;
import OutSourcing.ENGO.global.auth.dto.MemberInfromationDTO;
import OutSourcing.ENGO.global.enums.ErrorCode;
import OutSourcing.ENGO.global.error.exception.BusinessException;
import OutSourcing.ENGO.global.jwt.service.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {
    private final MemberRepository memberRepository;
    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final MemberService memberService;


    private GeneratedTokenDTO reissueMemberToken(Member member) {
        String currentRefreshToken = member.getRefreshToken();
        if (currentRefreshToken == null) {
            throw new BusinessException(ErrorCode.MISMATCH_REFRESH_TOKEN);
        }

        return jwtProvider.reissueToken(currentRefreshToken);
    }



    @Transactional
    public MemberInfromationDTO getCurrentUserDetails() {
        Member memberInfo = memberService.getCurrentMember();
        return memberRepository.getCurrentUserDetails(memberInfo.getId());
    }


}
