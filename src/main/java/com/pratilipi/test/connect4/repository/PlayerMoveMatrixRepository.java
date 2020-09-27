package com.pratilipi.test.connect4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratilipi.test.connect4.entity.PlayerMoveMatrix;

@Repository
public interface PlayerMoveMatrixRepository extends JpaRepository<PlayerMoveMatrix, String> {

}
