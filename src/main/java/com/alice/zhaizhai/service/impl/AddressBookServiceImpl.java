package com.alice.zhaizhai.service.impl;

import com.alice.zhaizhai.mapper.AddressBookMapper;
import com.alice.zhaizhai.pojo.AddressBook;
import com.alice.zhaizhai.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月04日 12:34
 */
@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    public List<AddressBook> getListByUserId(Long userId) {
        return addressBookMapper.selectListByUserId(userId);
    }

    @Override
    public void save(AddressBook addressBook) {
        //如果用户无地址，设为默认
        List<AddressBook> addressBooks = addressBookMapper.selectListByUserId(addressBook.getUserId());
        if (addressBooks == null || addressBooks.size() == 0)
            addressBook.setIsDefault(1);
        addressBookMapper.insert(addressBook);


    }

    @Override
    public AddressBook getById(Long id) {
        return addressBookMapper.selectById(id);
    }

    @Override
    public void update(AddressBook addressBook) {
        addressBookMapper.updateById(addressBook);
    }

    @Override
    @Transactional
    public void setDefault(Long userId, Long addressBookId) {
        //将该用户的所有地址都设置为不默认
        addressBookMapper.updateNotDefault(userId);
        //将传过来的地址设置为默认
        addressBookMapper.updateDefault(addressBookId);
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            addressBookMapper.delete(id);
        }
    }

    @Override
    public AddressBook getDefaultByUserId(Long userId) {
        return addressBookMapper.getDefaultByUserId(userId);
    }


}
