package com.psrs.repo;

import com.psrs.model.ServiceRequest;
import com.psrs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findAllByOrderByIdDesc();
    List<ServiceRequest> findByUserOrderByIdDesc(User user);
}
