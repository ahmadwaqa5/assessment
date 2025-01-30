package com.waqasassessment.inventoryservice.repository;

import com.waqasassessment.inventoryservice.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
