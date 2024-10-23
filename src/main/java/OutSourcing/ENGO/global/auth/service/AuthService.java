package OutSourcing.ENGO.global.auth.service;



import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.member.repository.Member.MemberRepository;
import OutSourcing.ENGO.domain.member.service.MemberService;
import OutSourcing.ENGO.global.auth.dto.*;
import OutSourcing.ENGO.global.enums.ErrorCode;
import OutSourcing.ENGO.global.error.exception.BusinessException;
import OutSourcing.ENGO.global.jwt.service.JwtProvider;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static OutSourcing.ENGO.global.enums.Role.USER;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final Validator validator;


    @Transactional
    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) {

        validatePasswordFormat(signUpRequestDTO);
        Optional<Member> findEmail = memberRepository.findByEmail(signUpRequestDTO.getEmail());
        if (findEmail.isPresent()) {
            throw new BusinessException(ErrorCode.MEMBER_PROFILE_DUPLICATION);
        }
        if(!Objects.equals(signUpRequestDTO.getPassword(), signUpRequestDTO.getConfirmPassword())){
            throw new BusinessException(ErrorCode.PASSWORD_NOT_SAME);
        }

        String hashedPassword = passwordEncoder.encode(signUpRequestDTO.getPassword());
        Member member = Member.builder()
                .name(signUpRequestDTO.getName())
                .email(signUpRequestDTO.getEmail())
                .phoneNumber(signUpRequestDTO.getPhoneNum())
                .password(hashedPassword)
                .build();
        member.setRole(USER);

        memberRepository.save(member);
        return SignUpResponseDTO.builder()
                .name(member.getName())
                .email(member.getEmail())
                .phoneNum(member.getPhoneNumber())
                .status(200)
                .build();
    }


    @Transactional
    public GeneratedTokenDTO login(LoginRequestDTO loginRequestDTO) {
        Optional<Member> findEmail = memberRepository.findByEmail(loginRequestDTO.getEmail());

        if (findEmail.isPresent()) {
            Member member = findEmail.get();
            if (passwordEncoder.matches(loginRequestDTO.getPassword(), member.getPassword())) {
                SecurityMemberDTO securityMemberDTO = SecurityMemberDTO.builder()
                        .id(member.getId())
                        .email(member.getEmail())
                        .role(member.getRole())
                        .name(member.getName())
                        .phoneNum(member.getPhoneNumber())
                        .build();

                return jwtProvider.generateTokens(securityMemberDTO);
            } else {
                throw new BusinessException(ErrorCode.INVALID_PASSWORD);
            }

        } else {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }

    @Transactional
    public boolean duplicateEmail(String requsetEmail) {
        Optional<Member> findEmail = memberRepository.findByEmail(requsetEmail);

        return findEmail.isPresent();

    }

    @Transactional
    public FindEmailResponseDTO findEmail(String name, String phoneNumber) {
        Optional<Member> findMember = memberRepository.findByNameAndPhoneNumber(name, phoneNumber);

        if (findMember.isPresent()) {
            Member findEmailMember = findMember.get();
            return FindEmailResponseDTO.builder()
                    .email(findEmailMember.getEmail())
                    .status(200)
                    .build();
        } else {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }

    }

    @Transactional
    public boolean checkMember(FindRequestDTO.Password findPasswordRequestDTO) {
        Optional<Member> findMember = memberRepository.findByNameAndPhoneNumberAndEmail(findPasswordRequestDTO.getName(), findPasswordRequestDTO.getPhoneNumber(), findPasswordRequestDTO.getEmail());

        return findMember.isPresent();
    }

    // todo 확인 후 수정 필요 -> 어떤식으로 비밀번호를 재설정 하게 할것인지
    @Transactional
    public void resetPassword(FindRequestDTO.ResetPassword passwordRequestDTO) {

        validatePasswordFormat2(passwordRequestDTO);
        Optional<Member> findEmail = memberRepository.findByEmail(passwordRequestDTO.getEmail());

        if(!Objects.equals(passwordRequestDTO.getPassword(), passwordRequestDTO.getConfirmPassword())){
            throw new BusinessException(ErrorCode.PASSWORD_NOT_SAME);
        }

        findEmail.ifPresent(member -> {
            String hashedPassword = passwordEncoder.encode(passwordRequestDTO.getPassword());
            member.changePassword(hashedPassword);
            memberRepository.save(member);
        });
        if (findEmail.isEmpty()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }


    @Transactional
    public void deleteMember() throws ExecutionException, InterruptedException {
        Long memberId = memberService.getCurrentMember().getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_MEMBER_ID));

        memberService.deleteMember();
    }


    @Transactional
    public void logout() {
        Long currentMember = memberService.getCurrentMember().getId();

        memberRepository.invalidateRefreshToken(currentMember);
    }

    private void validatePasswordFormat(SignUpRequestDTO signUpRequestDTO) {
        Set<ConstraintViolation<SignUpRequestDTO>> violations = validator.validate(signUpRequestDTO);
        for (ConstraintViolation<SignUpRequestDTO> violation : violations) {
            if (violation.getPropertyPath().toString().equals("password")) {
                throw new BusinessException(ErrorCode.INVALID_PASSWORD_FORMAT);  // 형식이 잘못된 경우
            }
        }
    }
    private void validatePasswordFormat2(FindRequestDTO.ResetPassword  passwordRequestDTO) {
        Set<ConstraintViolation<FindRequestDTO.ResetPassword>> violations = validator.validate(passwordRequestDTO);
        for (ConstraintViolation<FindRequestDTO.ResetPassword> violation : violations) {
            if (violation.getPropertyPath().toString().equals("password")) {
                throw new BusinessException(ErrorCode.INVALID_PASSWORD_FORMAT);  // 형식이 잘못된 경우
            }
        }
    }

}



