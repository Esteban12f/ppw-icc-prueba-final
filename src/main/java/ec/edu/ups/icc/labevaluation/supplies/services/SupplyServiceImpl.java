package ec.edu.ups.icc.labevaluation.supplies.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.labevaluation.supplies.dtos.CreateSupplyDto;
import ec.edu.ups.icc.labevaluation.supplies.dtos.SupplyResponseDto;
import ec.edu.ups.icc.labevaluation.supplies.entities.SupplyEntity;
import ec.edu.ups.icc.labevaluation.supplies.mappers.SupplyMapper;
import ec.edu.ups.icc.labevaluation.supplies.repositories.SupplyRepository;

@Service
public class SupplyServiceImpl implements SupplyService{
    private final SupplyRepository repository;

    public SupplyServiceImpl(SupplyRepository repository) {
        this.repository = repository;
    }

    @Override
    public SupplyResponseDto create(CreateSupplyDto dto) {
        SupplyEntity entity = SupplyMapper.toEntity(dto);
        return SupplyMapper.toResponse(repository.save(entity));
    }

    @Override
    public List<SupplyResponseDto> findLowStock(Integer maxQuantity) {
        return repository.findByActiveTrueAndDeletedFalseAndQuantityLessThanOrderByQuantityAsc(maxQuantity).stream()
        .map(SupplyMapper::toResponse).toList();
    }
    
}
