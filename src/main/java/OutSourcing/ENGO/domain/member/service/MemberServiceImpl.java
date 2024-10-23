package OutSourcing.ENGO.domain.member.service;


import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.member.repository.Member.MemberRepository;
import OutSourcing.ENGO.global.enums.ErrorCode;
import OutSourcing.ENGO.global.enums.Role;
import OutSourcing.ENGO.global.error.exception.BusinessException;
import OutSourcing.ENGO.global.jwt.service.JwtTokenIdDecoder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;


@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final JwtTokenIdDecoder jwtTokenIdDecoder;
    private final MemberRepository memberRepository;

//    @Override
//    public Member getCurrentMember() {
//        long memberId = jwtTokenIdDecoder.getCurrentUserId();
//        return getMemberById(memberId);
//    }


    @Override
    public Member getCurrentMember() {
        long memberId = jwtTokenIdDecoder.getCurrentUserId();

        if (memberId == 0L) {
            // 익명 사용자를 처리하는 임시 Member 객체를 반환 (GUEST 권한 부여)
            return Member.builder()
                    .id(0L)
                    .role(Role.GUEST)
                    .name("Anonymous")
                    .build();
        }
        return getMemberById(memberId);
    }
    @Override
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_MEMBER_ID));
    }



    @Transactional
    @Override
    public void deleteMember() throws ExecutionException, InterruptedException {
        Long memberId = getCurrentMember().getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_MEMBER_ID));

        memberRepository.delete(member);
        System.out.println("회원 삭제 성공: " + memberId);
    }
}
