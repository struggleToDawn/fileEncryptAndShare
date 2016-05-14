package pku.yang.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pku.yang.model.Strategy;

/**
 * 策略Dao
 * @author  chenbin
 * @Date    20160113
 * @Operate create
 * @address Peking University
 */

public interface IStrategyRepository extends JpaRepository<Strategy, Integer>{
 
}
