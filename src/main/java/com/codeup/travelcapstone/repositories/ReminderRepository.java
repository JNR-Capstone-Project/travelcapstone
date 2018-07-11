package com.codeup.travelcapstone.repositories;

import com.codeup.travelcapstone.model.Reminder;
import com.codeup.travelcapstone.model.Search;
import com.codeup.travelcapstone.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepository extends CrudRepository<Reminder,Long>{

    @Query( nativeQuery = true,
    value="Select * from reminders WHERE user_id = ?")
    List<Reminder> findAllByUser(long user_id);

}

