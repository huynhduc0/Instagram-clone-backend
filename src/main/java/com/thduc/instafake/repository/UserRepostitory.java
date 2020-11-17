package com.thduc.instafake.repository;

import com.thduc.instafake.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepostitory  extends MongoRepository<User,Long> {
}
