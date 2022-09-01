package com.tetesense.publicopinion.weibo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface WeiboRepository extends JpaRepository<WeiboBody, String> {
    @Query("select w from WeiboBody w where w.createdAt > ?1")
    Page<WeiboBody> findAllByCreatedAtAfter(Pageable pageable, Date sinceDay);

    @Query("select w from WeiboBody w where w.text like concat('%', ?1, '%')")
    Page<WeiboBody> findAllByTextContaining(Pageable pageable, String keyword);

    @Query("select w from WeiboBody w where w.username = ?1")
    Page<WeiboBody> findAllByUsername(Pageable pageable, String user);
}
