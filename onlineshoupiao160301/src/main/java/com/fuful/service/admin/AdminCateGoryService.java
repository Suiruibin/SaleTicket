package com.fuful.service.admin;

import com.fuful.controllers.admin.AdminCategory;
import com.fuful.dao.admin.AdminCategoryDao;
import com.fuful.domain.BookSuperType;
import com.fuful.domain.Ticket;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by SunRuiBin on 2019/12/9.
 */
@Service
public class AdminCateGoryService {
    @Autowired
    AdminCategoryDao adminCategoryDao;

    public PageInfo<BookSuperType> getCateGory(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<BookSuperType> ticketList = adminCategoryDao.findCategory();
        PageInfo<BookSuperType> data = new PageInfo<>(ticketList);
        return data;
    }
    public boolean querySuperTypeExistByName(String tname){
        BookSuperType superType=adminCategoryDao.findCateGoryByName(tname);
        if(superType!=null){
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean saveCategorySuperType(BookSuperType p){
        int res=adminCategoryDao.addCategory(p);
        if(res>0){
            return true;
        }
        else
        {
            return false;
        }
    }

    public BookSuperType findSuperTypeDetailByid(int id) {
        // TODO Auto-generated method stub
        BookSuperType res=adminCategoryDao.findSuperTypeById(id);
        return res;


    }

    public boolean updateSuperType(BookSuperType bookSuperType){
        int res=adminCategoryDao.UpdateSuperType(bookSuperType);
        if(res>0){
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean deleteSuperTypeById(int id) {
        boolean res=adminCategoryDao.DeleteSuperType(id);
        return res;
    }


}

