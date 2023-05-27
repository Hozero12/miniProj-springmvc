package hello.itemservice.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Stream;

// 이후 인터페이스화 하자 (디비도 가능하게)
@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={}",member);

        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream().filter(a -> a.getLoginId().equals(loginId)).findFirst();

    }

    public  List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearAll(){
        store.clear();
    }


}
