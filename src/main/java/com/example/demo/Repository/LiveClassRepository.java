package com.example.demo.Repository;

import com.example.demo.Entity.LiveClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveClassRepository extends JpaRepository<LiveClass,Long> {


}
