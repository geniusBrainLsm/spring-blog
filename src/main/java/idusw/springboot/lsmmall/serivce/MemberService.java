package idusw.springboot.lsmmall.serivce;

import idusw.springboot.lsmmall.entity.MemberEntity;
import idusw.springboot.lsmmall.model.MemberDto;

import java.util.List;

public interface MemberService {
    int create(MemberDto memberDto);
    MemberDto readByIdx(Long idx);
    List<MemberDto> readAll();
    int update(MemberDto memberDto);
    int delete(Long idx);

    MemberDto loginById(MemberDto memberDto); // id / pw 활용

    // Conversion
    default MemberEntity dtoToEntity(MemberDto memberDto) {
        MemberEntity entity = MemberEntity.builder()
                .idx(memberDto.getIdx())
                .id(memberDto.getId())
                .pw(memberDto.getPw())
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .address(memberDto.getAddress())
                .phone(memberDto.getPhone())
                .build();
        return entity;
    }
    
    default MemberDto entityToDto(MemberEntity entity) {
        MemberDto memberDto = MemberDto.builder()
                .idx(entity.getIdx())
                .id(entity.getId())
                .pw(entity.getPw())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .name(entity.getName())
                .email(entity.getEmail())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return memberDto;
    }
}
