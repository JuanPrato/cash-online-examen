package com.cashonline.examen.repository;

import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Transactional
public class UserRepostiroryTest {

    @Resource
    private UserRepository userRepository;

}
