package OutSourcing.ENGO.domain.member.repository.Member;




import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.global.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {





    Optional<Member> findByEmail(String email);


    List<Member> findByRole(Role role);

    Optional<Member> findByNameAndPhoneNumber(String name, String phoneNumber);

    Optional<Member> findByNameAndPhoneNumberAndEmail(String name, String phoneNumber, String email);


    @Modifying
    @Query("UPDATE Member m SET m.refreshToken = null WHERE m.id = :memberId")
    void invalidateRefreshToken(@Param("memberId") Long memberId);
}
