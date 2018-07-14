package com.codeup.travelcapstone.Jobs;


import com.codeup.travelcapstone.model.Reminder;
import com.codeup.travelcapstone.repositories.ReminderRepository;
import com.codeup.travelcapstone.services.TwilioService;
import org.joda.time.DateTime;
import ch.qos.logback.classic.Logger;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Component
public class ScheduledReminders extends TwilioService{
    private ReminderRepository reminderRepo;

    public ScheduledReminders(ReminderRepository reminderRepo){
        this.reminderRepo = reminderRepo;
    }

    private static final Logger log = LoggerFactory.getLogger(ScheduledReminders.class);

    @Scheduled(fixedRate = 60000)
    public void fetchTime(){
        Iterable<Reminder> reminders = reminderRepo.findAll();
        if (reminders == null){
            System.out.println("no reminders");
        } else {
            DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime now = LocalDateTime.now();
            String today = dateTime.format(now).substring(0, 10);
            System.out.println(dateTime.format(now));
            for (Reminder reminder : reminders) {
                long id = reminder.getId();
                String action = reminder.getAction();
                String reminderDate = reminder.getDate();
                System.out.println(reminderDate);
                if (reminderDate.substring(0, 10).equals(today)){
                    System.out.println("We have a match!");
                    String reminderTime = reminderDate.substring(12, 16);
                    String message = action + " at: " + reminderTime;
                    System.out.println(reminder.getAction() + reminder.getUser().getPhonenumber());
                    sendText(message, reminder.getUser().getPhonenumber());
                    reminderRepo.delete(id);
                }
            }

            System.out.println("these are your reminders");
        }
    }

}
