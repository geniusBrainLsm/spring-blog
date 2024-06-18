package idusw.springboot.lsmmall.serivce;

import idusw.springboot.lsmmall.entity.BlogEntity;
import idusw.springboot.lsmmall.entity.MemberEntity;
import idusw.springboot.lsmmall.model.BlogDto;
import idusw.springboot.lsmmall.model.MemberDto;
import idusw.springboot.lsmmall.repository.BlogRepository;
import idusw.springboot.lsmmall.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final MemberRepository memberRepository;
    private final MemberServiceImpl memberService;
    @Override
    public int create(MemberDto memberDto, BlogDto dto) {
        MemberDto member = memberService.readByIdx(memberDto.getIdx());
        if (member == null) {
            throw new IllegalArgumentException("Member not found with id: " + memberDto.getIdx());
        }

        BlogDto blogDto = BlogDto.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writerIdx(member.getIdx())
                .block("non")
                .build();


        blogRepository.save(dtoToEntity(blogDto));
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
    public int update(Long idx, BlogDto dto) {
        Optional<BlogEntity> optionalBlogEntity = blogRepository.findById(idx);

        if (optionalBlogEntity.isPresent()) {
            BlogEntity blogEntity = optionalBlogEntity.get();
            blogEntity.setTitle(dto.getTitle());
            blogEntity.setContent(dto.getContent());
            blogRepository.save(blogEntity);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int delete(Long idx) {
        if (blogRepository.existsById(idx)) {
            blogRepository.deleteById(idx);
            return 1;
        } else {
            return 0;
        }
    }
}
