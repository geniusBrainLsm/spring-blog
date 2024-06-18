package idusw.springboot.lsmmall.repository;

import idusw.springboot.lsmmall.entity.BlogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> { // interface 상속,
    Page<BlogEntity> findByTitleContaining(String title, Pageable pageable);

    void deleteByBloggerIdx(Long bloggerIdx);
}
