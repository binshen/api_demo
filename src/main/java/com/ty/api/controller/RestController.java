package com.ty.api.controller;

import com.ty.api.entity.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rest")
public class RestController {

    @ResponseBody
    @RequestMapping(value = "/list_roles", method = RequestMethod.GET)
    public List<Role> listRoles() {

        List<Role> roles = new ArrayList<>();
        Role role1 = new Role();
        role1.setId(1);
        role1.setRole_name("USER");
        roles.add(role1);

        Role role2 = new Role();
        role2.setId(2);
        role2.setRole_name("USER");
        roles.add(role2);

        return roles;
    }

    @ResponseBody
    @RequestMapping(value = "/list_users", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public List<String> listUsers() {

        List<String> data = new ArrayList<>();
        data.add("bob");
        data.add("bill");
        data.add("john");

        return data;
    }
}
