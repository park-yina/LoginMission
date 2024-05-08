package com.mysite.sbb.info;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InfoRepository extends JpaRepository<Info, Integer> {
    Optional<Info> findByUsername(String username);
}
