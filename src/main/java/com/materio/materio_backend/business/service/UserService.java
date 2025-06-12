package com.materio.materio_backend.business.service;

import com.materio.materio_backend.dto.User.UserProfileResponse;
import com.materio.materio_backend.dto.User.UserUpdateRequest;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    UserProfileResponse getCurrentUserProfile();

    @Transactional
    UserProfileResponse updateCurrentUserProfile(UserUpdateRequest updateRequest);

    UserProfileResponse getUserProfileById(Long id);

    @Transactional
    void deleteUser(Long id);
}
