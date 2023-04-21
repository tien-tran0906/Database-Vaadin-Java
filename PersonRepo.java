package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

// This is to get the data from our backend and show it in front of the user
public interface PersonRepo extends JpaRepository <Person, Long> { // Person and ID key is type Long
}
