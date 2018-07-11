package com.codeup.travelcapstone.Jobs;


import com.codeup.travelcapstone.model.Reminder;
import com.codeup.travelcapstone.repositories.ReminderRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledReminders {
    private ReminderRepository reminderRepo;

    public ScheduledReminders(ReminderRepository reminderRepo){
        this.reminderRepo = reminderRepo;
    }

//    private static final Logger log = LoggerFactory.getLogger(ScheduledReminders.class);

    @Scheduled(fixedRate = 600000)
    public void fetchTime(){
        Iterable<Reminder> reminders = reminderRepo.findAll();
        if (reminders == null){
            System.out.println("no reminders");
        } else {
            for (Reminder reminder : reminders) {
                long id = reminder.getId();
                String action = reminder.getAction();
                String reminderDate = reminder.getDate();
                DateTime today = new DateTime();
                System.out.println(reminderDate);
                System.out.println(today);
            }
            System.out.println("these are your reminders");
        }
    }

}
