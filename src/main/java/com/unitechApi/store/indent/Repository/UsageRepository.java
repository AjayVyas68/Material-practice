package com.unitechApi.store.indent.Repository;

import com.unitechApi.store.indent.Model.UsageItem;
import org.apache.xmlbeans.impl.jam.JPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsageRepository extends JpaRepository<UsageItem,Long> {
    List<UsageItem> findByDeptName(String name);
}