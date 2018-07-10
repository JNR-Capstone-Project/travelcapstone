package com.codeup.travelcapstone.repositories;

import com.codeup.travelcapstone.model.Reminder;
import com.codeup.travelcapstone.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReminderRepository extends CrudRepository<Reminder,Long>{


}

