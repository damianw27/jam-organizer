package pl.wilenskid.jamorganizer.service;

import javax.inject.Named;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;

@Named
public class TimeService {
    public Optional<Calendar> fromString(String dateString) {
        return fromString(dateString, "EEE MMM dd HH:mm:ss z yyyy");
    }

    public Optional<Calendar> fromString(String dateString, String format) {
        Calendar convertedDate = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);

        try {
            convertedDate.setTime(simpleDateFormat.parse(dateString));
        } catch (ParseException e) {
            return Optional.empty();
        }

        return Optional.of(convertedDate);
    }

    
}
