package Servlet.HelloSpring.domain;


import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {
    private Map<Long, Member> repository = new HashMap<Long, Member>();
    private Long sequence = 0L;

    @Getter
    private static final MemberRepository instance = new MemberRepository();
    public Member save(Member member) {
        member.setId(++sequence);
        repository.put(member.getId(), member);
        return member;
    }
    public Member findByMemberId(Long id) {
        return repository.get(id);
    }
    public List<Member> findAll() {
        return new ArrayList<>(repository.values());
    }
}
