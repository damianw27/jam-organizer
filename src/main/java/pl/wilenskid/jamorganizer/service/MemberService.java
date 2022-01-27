package pl.wilenskid.jamorganizer.service;

import pl.wilenskid.jamorganizer.entity.bean.MemberBean;
import pl.wilenskid.jamorganizer.entity.bean.MemberCreateBean;
import pl.wilenskid.jamorganizer.entity.User;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.entity.Member;
import pl.wilenskid.jamorganizer.repository.ApplicationUserRepository;
import pl.wilenskid.jamorganizer.repository.EventRepository;
import pl.wilenskid.jamorganizer.repository.MemberRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Named
public class MemberService {

    private final MemberRepository memberRepository;
    private final EventRepository eventRepository;
    private final ApplicationUserRepository applicationUserRepository;

    @Inject
    public MemberService(MemberRepository memberRepository,
                         EventRepository eventRepository,
                         ApplicationUserRepository applicationUserRepository) {
        this.memberRepository = memberRepository;
        this.eventRepository = eventRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    public List<Member> getAll() {
        return StreamSupport.stream(memberRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    public Optional<Member> getById(Long id) {
        return memberRepository.findById(id);
    }

    public Member create(MemberCreateBean createBean) {
        Optional<Event> event = eventRepository.findById(createBean.getEventId());
        Optional<User> applicationUser = applicationUserRepository.findById(createBean.getApplicationUserId());

        Member member = new Member();
        event.ifPresent(member::setEvent);
        applicationUser.ifPresent(member::setUser);
        member.setApplicationUserEventRole(createBean.getApplicationUserEventRole());

        if (member.getEvent() == null || member.getUser() == null) {
            return null;
        }

        memberRepository.save(member);
        return member;
    }

    public Member update(Object updateBean) {
        return null;
    }

    public Optional<Member> delete(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        member.ifPresent(memberRepository::delete);
        return member;
    }

    public MemberBean toBean(Member model) {
        return MemberBean
            .builder()
            .id(model.getId())
            .applicationUserEventRole(model.getApplicationUserEventRole())
            .event(model.getEvent().getId())
            .applicationUser(model.getUser().getId())
            .build();
    }

}
