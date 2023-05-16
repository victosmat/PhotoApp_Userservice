package com.example.demo.repository;

import com.example.demo.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findAllByFollowingUserId(Long followingUserId);

    List<Follow> findAllByFollowedUserId(Long followedUserId);

    Follow findByFollowingUserIdAndFollowedUserId(Long followingUserId, Long followedUserId);
}
