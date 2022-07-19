package com.ead.authuser.service.impl;

import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserCourseRepository;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.service.UserCourseService;
import com.ead.authuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Override
    public boolean existsByUserAndCourseId(UserModel userModel, UUID courseId) {
        return userCourseRepository.existsByUserAndCourseId(userModel, courseId);
    }

    @Override
    public UserCourseModel save(UserCourseModel userCourseModel) {
        return userCourseRepository.save(userCourseModel);
    }

    @Override
    public boolean existsByCourseId(UUID courseId) {
        return userCourseRepository.existsByCourseId(courseId);
    }

    @Transactional
    @Override
    public void deleteUserCourseByCourse(UUID courseId) {
        userCourseRepository.deleteAllByCourseId(courseId);
    }
}
