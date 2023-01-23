package com.example.pp_3_1_5.service;

import com.example.pp_3_1_5.dao.RoleDao;
import com.example.pp_3_1_5.model.Role;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleDao roleDAO;

    public RoleServiceImpl(RoleDao roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<Role> findAllRole() {
        return roleDAO.findAll();
    }
}
