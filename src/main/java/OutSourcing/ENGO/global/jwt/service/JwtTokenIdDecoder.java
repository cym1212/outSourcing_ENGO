package OutSourcing.ENGO.global.jwt.service;


import OutSourcing.ENGO.global.auth.dto.SecurityMemberDTO;
import OutSourcing.ENGO.global.enums.ErrorCode;
import OutSourcing.ENGO.global.error.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenIdDecoder {

    private final ObjectMapper objectMapper;
//    public Long getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            SecurityMemberDTO userDetails = (SecurityMemberDTO) authentication.getPrincipal();
//            return userDetails.getId();
//        }
//        throw  new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
//    }


//    public Long getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            Object principal = authentication.getPrincipal();
//            if (principal instanceof SecurityMemberDTO) {
//                return ((SecurityMemberDTO) principal).getId();
//            } else if (principal instanceof String) {
//                try {
//                    SecurityMemberDTO userDetails = objectMapper.readValue((String) principal, SecurityMemberDTO.class);
//                    return userDetails.getId();
//                } catch (Exception e) {
//                    log.error("Error parsing principal to SecurityMemberDTO", e);
//                    throw new BusinessException(ErrorCode.TOKEN_ERROR);
//                }
//            }
//        }
//        throw new BusinessException(ErrorCode.TOKEN_ERROR);
//    }

//    public Long getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // 인증 정보가 없거나, 인증되지 않은 상태일 경우
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return 0L; // 토큰이 없는 경우 0을 반환하여 익명 사용자 처리
//        }
//
//        Object principal = authentication.getPrincipal();
//
//        // principal이 SecurityMemberDTO 타입일 때
//        if (principal instanceof SecurityMemberDTO) {
//            return ((SecurityMemberDTO) principal).getId();
//        }
//        // principal이 문자열일 때
//        else if (principal instanceof String) {
//            try {
//                SecurityMemberDTO userDetails = objectMapper.readValue((String) principal, SecurityMemberDTO.class);
//                return userDetails.getId();
//            } catch (Exception e) {
//                log.error("Error parsing principal to SecurityMemberDTO", e);
//                throw new BusinessException(ErrorCode.TOKEN_ERROR);
//            }
//        }
//
//        // 유효한 사용자 정보가 없을 때
//        throw new BusinessException(ErrorCode.TOKEN_ERROR);
//    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없거나, 인증되지 않은 상태일 경우
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return 0L; // 토큰이 없는 경우 0을 반환하여 익명 사용자 처리
        }

        Object principal = authentication.getPrincipal();

        // principal이 SecurityMemberDTO 타입일 때
        if (principal instanceof SecurityMemberDTO) {
            return ((SecurityMemberDTO) principal).getId();
        }

        // 유효한 사용자 정보가 없을 때
        throw new BusinessException(ErrorCode.TOKEN_ERROR);
    }
}
