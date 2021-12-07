package pl.wilenskid.jamorganizer.assembly;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;

import pl.wilenskid.jamorganizer.bean.ApplicationUserBean;
import pl.wilenskid.jamorganizer.entity.ApplicationUser;
import pl.wilenskid.jamorganizer.service.ApplicationUserService;
import pl.wilenskid.jamorganizer.service.TimeService;

@Named
public class ApplicationUserAssembly implements Assembly<ApplicationUser, ApplicationUserBean> {
    
    private final ApplicationUserService applicationUserService;
	private final TimeService timeService;
	
	@Inject
	public ApplicationUserAssembly(ApplicationUserService applicationUserService, TimeService timeService) {
		this.applicationUserService = applicationUserService;
        this.timeService = timeService;
	}

	@Override
	public ApplicationUserBean toBean(ApplicationUser model) {
		List<Long> membership = StreamSupport.stream(model.getMembership().spliterator(), false)
				.map(member -> member.getId())
				.collect(Collectors.toList());
		
		List<Long> organizations = StreamSupport.stream(model.getOrganizations().spliterator(), false)
				.map(member -> member.getId())
				.collect(Collectors.toList());
		
		return ApplicationUserBean.builder()
				.id(model.getId())
				.name(model.getName())
				.displayName(model.getDisplayName())
				.email(model.getEmail())
				.applicationUserRole(model.getApplicationUserRole())
				.created(timeService.dateToString(model.getCreated(), false).orElse("n/a"))
				.jobStart(timeService.dateToString(model.getJobStart(), false).orElse("n/a"))
				.membership(membership)
				.organizations(organizations)
				.build();
	}

	@Override
	public ApplicationUser toModel(ApplicationUserBean bean) {
	    return applicationUserService.getById(bean.getId()).orElse(null);
	}

}
