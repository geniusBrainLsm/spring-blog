package idusw.springboot.lsmmall.serivce;

import idusw.springboot.lsmmall.entity.MemberEntity;
import idusw.springboot.lsmmall.model.MemberDto;
import idusw.springboot.lsmmall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public int create(MemberDto memberDto) {
        int ret = 0;
        MemberEntity entity = dtoToEntity(memberDto);
        memberRepository.save(entity);
        ret = 1;
        return ret;
    }

    @Override
    public MemberDto readByIdx(Long idx) {
        if(!memberRepository.findByIdx(idx).isEmpty())
            return entityToDto(memberRepository.findByIdx(idx).get());
        else
            return null;
    }

    @Override
    public List<MemberDto> readAll() {
        List<MemberEntity> list = memberRepository.findAll();
        List<MemberDto> dtoList = new ArrayList<MemberDto>();
        for (MemberEntity memberEntity : list) {
            dtoList.add(entityToDto(memberEntity));
        }
        return dtoList;
    }

    @Override
    public int update(MemberDto memberDto) {
        MemberEntity memberEntity = dtoToEntity(memberDto);
        memberRepository.save(memberEntity);
        return 1;
    }
    @Override
    public int delete(@PathVariable("idx") Long idx) {
        memberRepository.deleteById(idx);
        return 1;
    }

    @Override
    public MemberDto loginById(MemberDto memberDto) {
        Optional<MemberEntity> memberEntityOptional = memberRepository.findByIdAndPw(memberDto.getId(), memberDto.getPw());
        return memberEntityOptional.map(this::entityToDto).orElse(null);
    }
}
