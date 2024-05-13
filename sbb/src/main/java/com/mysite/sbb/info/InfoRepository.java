package com.mysite.sbb.info;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface InfoRepository extends JpaRepository<Info, Integer> {
    Optional<Info> findByUsername(String username);

}
