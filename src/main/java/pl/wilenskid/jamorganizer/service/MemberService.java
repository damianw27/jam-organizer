package pl.wilenskid.jamorganizer.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;

import pl.wilenskid.jamorganizer.bean.MemberCreateBean;
import pl.wilenskid.jamorganizer.entity.ApplicationUser;
import pl.wilenskid.jamorganizer.entity.Event;
import pl.wilenskid.jamorganizer.entity.Member;
import pl.wilenskid.jamorganizer.repository.ApplicationUserRepository;
import pl.wilenskid.jamorganizer.repository.EventRepository;
import pl.wilenskid.jamorganizer.repository.MemberRepository;

@Named
public class MemberService implements CrudService<Member, MemberCreateBean, Object, Long> {

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

	@Override
	public List<Member> getAll() {
		return StreamSupport.stream(memberRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Member> getById(Long id) {
		return memberRepository.findById(id);
	}

	@Override
	public Member create(MemberCreateBean createBean) {
		Optional<Event> event = eventRepository.findById(createBean.getEventId());
		Optional<ApplicationUser> applicationUser = applicationUserRepository.findById(createBean.getApplicationUserId());
		
		Member member = new Member();
		event.ifPresent(member::setEvent);
		applicationUser.ifPresent(member::setApplicationUser);
		member.setApplicationUserEventRole(createBean.getApplicationUserEventRole());
		
		if (member.getEvent() == null || member.getApplicationUser() == null) {
			return null;
		}
		
		memberRepository.save(member);
		return member;
	}

	@Override
	public Member update(Object updateBean) {
		return null;
	}

	@Override
	public Optional<Member> delete(Long id) {
		Optional<Member> member = memberRepository.findById(id);
		member.ifPresent(memberRepository::delete);
		return member;
	}

}
