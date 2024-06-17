package idusw.springboot.lsmmall.serivce;

import idusw.springboot.lsmmall.entity.BlogEntity;
import idusw.springboot.lsmmall.entity.MemberEntity;
import idusw.springboot.lsmmall.model.BlogDto;
import idusw.springboot.lsmmall.repository.BlogRepository;
import idusw.springboot.lsmmall.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final MemberRepository memberRepository;
    @Override
    public int create(BlogDto dto) {
        BlogEntity entity = dtoToEntity(dto);

        blogRepository.save(entity);
        return 1;
    }

    @Override
    public BlogDto read(Long idx) {
        BlogEntity blogEntity = blogRepository.findById(idx)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        //조회수증가
        blogEntity.incrementViews();
        blogRepository.save(blogEntity);

        MemberEntity memberEntity = memberRepository.findById(blogEntity.getBlogger().getIdx())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        return entityToDto(blogEntity, memberEntity);
    }
    @Override
    @Transactional
    public Page<BlogDto> readList(String title, Pageable pageable) {
        Page<BlogEntity> blogEntityPage = blogRepository.findByTitleContaining(title, pageable);
        return blogEntityPage.map(blogEntity -> entityToDto(blogEntity, blogEntity.getBlogger()));
    }

    @Override
    @Transactional
    public List<BlogDto> readAllList() {
        List<BlogEntity> entities = blogRepository.findAll();
        return entities.stream()
                .map(entity -> entityToDto(entity, entity.getBlogger()))
                .collect(Collectors.toList());
    }

    @Override
    public int update(BlogDto dto) {
        blogRepository.save(dtoToEntity(dto));
        return 1;
    }

    @Override
    public int delete(BlogDto dto) {
        blogRepository.delete(dtoToEntity(dto));


        return 1;
    }
}