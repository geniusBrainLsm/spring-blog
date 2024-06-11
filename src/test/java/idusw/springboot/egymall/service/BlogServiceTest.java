package idusw.springboot.egymall.service;

import idusw.springboot.egymall.entity.BlogEntity;
import idusw.springboot.egymall.entity.MemberEntity;
import idusw.springboot.egymall.model.BlogDto;
import idusw.springboot.egymall.repository.BlogRepository;
import idusw.springboot.egymall.serivce.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class BlogServiceTest {
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    BlogService blogService;


    @Test
    public void initializeBlogs(){
        IntStream.rangeClosed(1, 10).forEach(i -> {
            BlogDto dto = BlogDto.builder()
                    .title("test" + i)
                    .content("content" + i)
                    .writerIdx((long)i)
                    .block("non")
                    .build();

            blogRepository.save(dtoToEntity(dto));
        });
    }
    @Test
    public void getBlogs(){
        List<BlogDto> blogDtoList = blogService.readList();
        for(BlogDto dto: blogDtoList){
            System.out.println(dto.toString());
        }
    }

    @Test
    public void registerBlog() {
        BlogDto dto = BlogDto.builder()
                .title("test1")
                .content("content1")
                .writerIdx(3L)
                .block("non")
                .build();

        blogRepository.save(dtoToEntity(dto));
    }

    @Test
    public void UpdateBlog(){
        int blogIdx=2;

        BlogDto blogDto = BlogDto.builder()
                .idx((long)blogIdx)
                .title("updateTitle"+blogIdx)
                .content("updateContent"+blogIdx)
                .writerIdx((long)3)
                .build();

        blogService.update(blogDto);
        getBlogs();
    }


    @Test
    public void getBlog() {
        int idx = 7;
        BlogDto dto = BlogDto.builder()
                .idx((long) idx)
                .build();
        BlogDto ret = blogService.read(dto);
        System.out.println(ret.toString());
    }
    @Test
    public void deleteBlog() {
        int blogIdx = 12;
        BlogDto blogDto = BlogDto.builder()
                .idx((long)blogIdx)
                .build();

        blogService.delete(blogDto);
        getBlogs();

    }
    BlogEntity dtoToEntity(BlogDto dto) {
        MemberEntity member = MemberEntity.builder()
                .idx(dto.getWriterIdx())
                .build();
        BlogEntity entity = BlogEntity.builder()
                .idx(dto.getIdx())
                .title(dto.getTitle())
                .content(dto.getContent())
                .block(dto.getBlock())
                .views(dto.getViews())
                .blogger(member)
                .build();
        return entity;
    }
    // MemberEntity -> : Controller에서는 Member를 다룸
    BlogDto entityToDto(BlogEntity entity, MemberEntity member) {
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
