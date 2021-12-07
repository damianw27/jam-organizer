package pl.wilenskid.jamorganizer.assembly;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;

import pl.wilenskid.jamorganizer.bean.OrganizationBean;
import pl.wilenskid.jamorganizer.entity.Organization;
import pl.wilenskid.jamorganizer.service.OrganizationService;

@Named
public class OrganizationAssembly implements Assembly<Organization, OrganizationBean> {

    private final OrganizationService organizationService;
    
    @Inject
    public OrganizationAssembly(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
    
    @Override
    public OrganizationBean toBean(Organization model) {
        List<Long> applicationUsers = StreamSupport.stream(model.getApplicationUsers().spliterator(), false)
                .map(applicationUser -> applicationUser.getId())
                .collect(Collectors.toList());
        
        return OrganizationBean
                .builder()
                .id(model.getId())
                .name(model.getName())
                .country(model.getCountry())
                .applicationUsers(applicationUsers)
                .build();
    }

    @Override
    public Organization toModel(OrganizationBean bean) {
        return organizationService.getById(bean.getId()).orElse(null);
    }

}
