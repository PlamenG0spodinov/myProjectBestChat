package com.theBestChat.theBestChat.Repositoryes;

import com.theBestChat.theBestChat.Entityes.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users, Integer> {
}
