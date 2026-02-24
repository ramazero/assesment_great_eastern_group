package com.imam.assesment_great_eastern_group.repository;

import com.imam.assesment_great_eastern_group.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VariantRepository extends JpaRepository<Variant, Long> {
    List<Variant> findByItemId(Long itemId);
    Optional<Variant> findByItemIdAndName(Long itemId, String name);

    boolean existsByItemId(Long itemId);
}
