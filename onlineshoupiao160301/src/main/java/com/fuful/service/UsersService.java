package com.fuful.service;

import com.fuful.dao.UsersDAO;
import com.fuful.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersDAO usersDAO;

    public User login(String uname, String upwd){

        User users = usersDAO.findByUName(uname);
        return users;
    }

    public User JudgeUserExist(String name){
        User users = usersDAO.findByUName(name);
        return users;
    }
    public int regist(User user) {
        int res=usersDAO.addUser(user);
        return res;

    }

    public int getState(String username){
        int res=usersDAO.getstate(username);
        return res;
    }

    public int updateState(String code){
        int res=usersDAO.updateState(code);
        return res;
    }

    public boolean backuser(String username,String name,String email){
        if(usersDAO.findBybackUser(username,name,email)!=null){

            return true;
        }
        return false;
    }
    public boolean edituser(String username,String password){
        if(usersDAO.edituser(username,password)){
            return true;
        }
        return false;
    }


}
