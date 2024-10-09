package OutSourcing.ENGO.domain.member.repository.Member;


import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.global.auth.dto.MemberInfromationDTO;
import OutSourcing.ENGO.global.enums.Role;

import java.util.Optional;

public interface MemberRepositoryCustom {
    Optional<Member> findByMemberId(Long memberId);

    void updateRefreshToken(Long id, String refreshToken);

    void updateRole(Long id, Role role);

    String findRoleById(Long memberId);

    MemberInfromationDTO getCurrentUserDetails(Long memberId);
}
