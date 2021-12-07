package pl.wilenskid.jamorganizer.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;

import pl.wilenskid.jamorganizer.bean.OrganizationCreateBean;
import pl.wilenskid.jamorganizer.entity.Organization;
import pl.wilenskid.jamorganizer.repository.OrganizationRepository;

@Named
public class OrganizationService implements CrudService<Organization, OrganizationCreateBean, Object, Long> {

	private final OrganizationRepository organizationRepository;
	
	@Inject
	public OrganizationService(OrganizationRepository organizationRepository) {
		this.organizationRepository = organizationRepository;
		
	}

	@Override
	public List<Organization> getAll() {
		return StreamSupport.stream(organizationRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Organization> getById(Long id) {
		return organizationRepository.findById(id);
	}

	@Override
	public Organization create(OrganizationCreateBean createBean) {
		Organization organization = new Organization();
		organization.setName(createBean.getName());
		organization.setCountry(createBean.getCountry());
		organization.setApplicationUsers(new HashSet<>());
		organizationRepository.save(organization);
		return organization;
	}

	@Override
	public Organization update(Object updateBean) {
		return null;
	}

	@Override
	public Optional<Organization> delete(Long id) {
		Optional<Organization> organization = organizationRepository.findById(id);
		organization.ifPresent(organizationRepository::delete);
		return organization;
	}
	
}
