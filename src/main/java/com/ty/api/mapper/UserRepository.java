package com.ty.api.mapper;

import com.ty.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findById(int id);

    User findByUsername(String name);
}
