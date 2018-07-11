package com.codeup.travelcapstone.repositories;

import com.codeup.travelcapstone.model.Search;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends CrudRepository<Search,Long>{

    @Query( nativeQuery = true,
    value="Select * from searches WHERE user_id = ?")
    List<Search> findAllByUser(long user_id);


    Search findSearchById(long search_id);
}
