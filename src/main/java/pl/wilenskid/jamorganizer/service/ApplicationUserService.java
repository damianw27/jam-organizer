package pl.wilenskid.jamorganizer.service;

import pl.wilenskid.jamorganizer.bean.CreateApplicationUserBean;
import pl.wilenskid.jamorganizer.entity.ApplicationUser;
import pl.wilenskid.jamorganizer.enums.ApplicationUserRole;
import pl.wilenskid.jamorganizer.repository.ApplicationUserRepository;

import javax.inject.Named;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;

@Named
public class ApplicationUserService {
    private final ApplicationUserRepository applicationUserRepository;

    public ApplicationUserService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }


    public void createApplicationUser(CreateApplicationUserBean applicationUserBean) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setName(applicationUserBean.getName());
        applicationUser.setDisplayName(applicationUserBean.getDisplayName());
        applicationUser.setEmail(applicationUserBean.getEmail());
        applicationUser.setPhoneNumber(applicationUserBean.getPhoneNumber());
        applicationUser.setPassword(applicationUserBean.getPassword());
        applicationUser.setApplicationUserRole(ApplicationUserRole.NORMAL);

        Calendar jobStart = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

        try {
            jobStart.setTime(simpleDateFormat.parse(applicationUserBean.getJobStart()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        applicationUser.setJobStart(jobStart);
        applicationUser.setCreated(Calendar.getInstance());
        applicationUser.setSuspendExpiration(null);
        applicationUser.setMembership(new HashSet<>());
        applicationUser.setOrganizations(applicationUserBean.getOrganizations());
        applicationUser.setProjects(new HashSet<>());
        applicationUserRepository.save(applicationUser);
    }
}
