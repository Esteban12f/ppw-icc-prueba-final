package ec.edu.ups.icc.labevaluation.supplies.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.labevaluation.core.exceptions.domain.NotFoundException;
import ec.edu.ups.icc.labevaluation.supplies.dtos.CreateSupplyDto;
import ec.edu.ups.icc.labevaluation.supplies.dtos.SupplyResponseDto;
import ec.edu.ups.icc.labevaluation.supplies.dtos.UpdateSupplyQuantityDto;
import ec.edu.ups.icc.labevaluation.supplies.entities.SupplyEntity;
import ec.edu.ups.icc.labevaluation.supplies.exceptions.SupplyConflictException;
import ec.edu.ups.icc.labevaluation.supplies.mappers.SupplyMapper;
import ec.edu.ups.icc.labevaluation.supplies.repositories.SupplyRepository;

@Service
public class SupplyServiceImpl implements SupplyService {
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

    @Override
    public SupplyResponseDto updateQuantity(Long id, UpdateSupplyQuantityDto dto) {
        SupplyEntity entity = findExisting(id);
        entity.setQuantity(dto.quantity());
        return SupplyMapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        SupplyEntity entity = findExisting(id);
        if (entity.getQuantity() > 0) {
            throw new SupplyConflictException("Supply cannot be deleted while quantity is greater than zero");
        }
        entity.setDeleted(true);
        entity.setActive(false);
        repository.save(entity);
    }
    private SupplyEntity findExisting(Long id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("SUPPLY_NOT_FOUND", "Supply not found"));
    }

}
