package com.abhijeet.restrocloud_api.repository;

import com.abhijeet.restrocloud_api.entity.OtpEmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface OtpEmailAuthRepository extends JpaRepository<OtpEmailAuth,Long> {
    Optional<OtpEmailAuth> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM OtpEmailAuth e WHERE e.email = :email")
    void deleteByEmail(@Param("email") String email);
}
