package pl.wilenskid.jamorganizer.assembly;

import javax.inject.Inject;
import javax.inject.Named;

import pl.wilenskid.jamorganizer.bean.MemberBean;
import pl.wilenskid.jamorganizer.entity.Member;
import pl.wilenskid.jamorganizer.service.MemberService;

@Named
public class MemberAssembly implements Assembly<Member, MemberBean> {
    
    private final MemberService memberService;

    @Inject
    public MemberAssembly(MemberService memberService) {
        this.memberService = memberService;
    }
    
    @Override
    public MemberBean toBean(Member model) {
        return MemberBean
                .builder()
                .id(model.getId())
                .applicationUserEventRole(model.getApplicationUserEventRole())
                .event(model.getEvent().getId())
                .applicationUser(model.getApplicationUser().getId())
                .build();
    }

    @Override
    public Member toModel(MemberBean bean) {
        return memberService.getById(bean.getId()).orElse(null);
    }

}
