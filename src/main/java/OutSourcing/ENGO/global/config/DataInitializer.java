package OutSourcing.ENGO.global.config;


import OutSourcing.ENGO.domain.member.domain.Member;
import OutSourcing.ENGO.domain.member.repository.Member.MemberRepository;
import OutSourcing.ENGO.global.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@naver.com";
        String adminPassword = "Admin123!@";

        // 관리자 계정이 존재하지 않으면 생성
        if (memberRepository.findByEmail(adminEmail).isEmpty()) {
            Member admin = Member.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .name("Administrator")
                    .role(Role.ADMIN)
                    .build();

            memberRepository.save(admin);
            System.out.println("Admin account created: " + adminEmail);
        } else {
            System.out.println("Admin account already exists.");
        }
    }

}
