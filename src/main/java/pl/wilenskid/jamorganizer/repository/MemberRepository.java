package pl.wilenskid.jamorganizer.repository;

import org.springframework.data.repository.CrudRepository;

import pl.wilenskid.jamorganizer.entity.Member;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
