package com.e.commerce.domain.repository;

import com.e.commerce.domain.model.UserActions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActionsRepository extends JpaRepository<UserActions,Long> {
}
