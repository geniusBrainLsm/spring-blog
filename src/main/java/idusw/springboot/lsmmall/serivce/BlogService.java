package idusw.springboot.lsmmall.serivce;

import idusw.springboot.lsmmall.entity.BlogEntity;
import idusw.springboot.lsmmall.entity.MemberEntity;
import idusw.springboot.lsmmall.model.BlogDto;
import idusw.springboot.lsmmall.model.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {
    int create(MemberDto memberDto, BlogDto dto);
    BlogDto read(Long idx);
    Page<BlogDto> readList(String title, Pageable pageable);
    List<BlogDto> readAllList();
    int update(Long idx, BlogDto dto);
    int delete(Long idx);

    default BlogEntity dtoToEntity(BlogDto dto) {
        MemberEntity member = MemberEntity.builder()
                .idx(dto.getWriterIdx())
                .build();
        BlogEntity entity = BlogEntity.builder()
                .idx(dto.getIdx())
                .title(dto.getTitle())
                .content(dto.getContent())
                .views(dto.getViews())
                .blogger(member)
                .build();
        return entity;
    }
    // MemberEntity -> : Controller에서는 Member를 다룸
    default BlogDto entityToDto(BlogEntity entity, MemberEntity member) {
        BlogDto dto = BlogDto.builder()
                .idx(entity.getIdx())
                .title(entity.getTitle())
                .views(entity.getViews())
                .content(entity.getContent())
                .writerIdx(member.getIdx())
                .writerName(member.getName())
                .writerEmail(member.getEmail())
                .regDate((entity.getRegDate()))
                .modDate(entity.getModDate())
                .build();
        return dto;
    }
}
