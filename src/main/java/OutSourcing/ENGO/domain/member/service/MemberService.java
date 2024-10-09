package OutSourcing.ENGO.domain.member.service;


import OutSourcing.ENGO.domain.member.domain.Member;

import java.util.concurrent.ExecutionException;

public interface MemberService {


    Member getCurrentMember();


    Member getMemberById(Long memberId);

    public void deleteMember() throws ExecutionException, InterruptedException;
}
