package com.theBestChat.theBestChat.Services;

import com.theBestChat.theBestChat.Entityes.Users;
import com.theBestChat.theBestChat.Repositoryes.UsersRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UsersRepo usersRepo;

    public UserService(UsersRepo usersRepo){
        this.usersRepo = usersRepo;
    }

    public List<Users> getAllUsers(){
        return this.usersRepo.findAll();
    }

    public Users createNewUsers(Users users){
        return this.usersRepo.save(users);
    }

    public Users findByUsername(String username) {
        return usersRepo.findByUsername(username);
    }

}
