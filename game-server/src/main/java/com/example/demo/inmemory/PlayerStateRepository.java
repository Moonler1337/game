package com.example.demo.inmemory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerStateRepository extends JpaRepository<PlayerStateEntity, String> {
}
