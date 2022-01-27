package pl.wilenskid.jamorganizer.service;

import javax.inject.Named;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Random;

@Named
public class FilenameService {

    private final SecureRandom secureRandom;

    public FilenameService() {
        this.secureRandom = new SecureRandom();
    }

    public String getRandomizedName(String name) {
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        return (name + "_" + (timeInMillis + getRandomIntString())).toUpperCase();
    }

    private String getRandomIntString() {
        Random random = new Random();
        secureRandom.setSeed(random.nextInt());
        int randomNumber = Math.abs(secureRandom.nextInt());
        return String.valueOf(randomNumber);
    }

}
