package com.unitechApi.store.vendor.Service;

import com.unitechApi.exception.ExceptionService.DateMisMatchException;
import com.unitechApi.exception.ExceptionService.ResourceNotFound;
import com.unitechApi.exception.ExceptionService.UserNotFound;
import com.unitechApi.store.storeMangment.Model.StoreItemModel;
import com.unitechApi.store.storeMangment.repository.StoreItemRepository;
import com.unitechApi.store.vendor.Repository.VendorRepository;
import com.unitechApi.store.vendor.model.VendorModel;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;
    private final StoreItemRepository storeItemRepository;

    public VendorService(VendorRepository vendorRepository, StoreItemRepository storeItemRepository) {
        this.vendorRepository = vendorRepository;
        this.storeItemRepository = storeItemRepository;
    }
    public VendorModel SaveData(VendorModel vendorModel) {
        return vendorRepository.save(vendorModel);
    }

    public VendorModel FindById(Long id) {
        return vendorRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Sorry ! Vendor Not Found in System"));
    }

    public Object FindAll() {
        return vendorRepository.findAll();
    }

    public Optional<VendorModel> DeleteData(Long id) {
        Optional<VendorModel> vendorModel = vendorRepository.findById(id);
        if (!vendorModel.isPresent()) {
            throw new UserNotFound("Sorry ! User Not Found in System");
        } else
            vendorRepository.deleteById(id);
        return Optional.empty();
    }

    public List<VendorModel> FindDateByData(Date start, Date end) {
        java.util.Date date = new java.util.Date();
        if (date.before(start)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + start);
        } else if (date.before(end)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + end);
        }
        return vendorRepository.findByCreatedAtBetween(start, end)
                .stream()
                .sorted(Comparator.comparing(VendorModel::getId))
                .collect(Collectors.toList());
    }

    public List<VendorModel> FindByParticularDate(Date pdate) {
        java.util.Date date = new java.util.Date();
        if (date.before(pdate)) {
            throw new DateMisMatchException(" you can not enter -> " + date + "  -> " + pdate);
        }
        return vendorRepository.findByCreatedAt(pdate)
                .stream()
                .sorted(Comparator.comparing(VendorModel::getId))
                .collect(Collectors.toList());
    }
    public Object DeleteItem(Long vendorId ,Long itemId)
    {
        VendorModel vendorModel=vendorRepository.findById(vendorId).get();
        StoreItemModel storeItemModel=storeItemRepository.findById(itemId).get();
        vendorModel.deleteItem(storeItemModel);
        return vendorRepository.save(vendorModel);
    }
}
