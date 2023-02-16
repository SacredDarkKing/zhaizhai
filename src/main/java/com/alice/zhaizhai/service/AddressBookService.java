package com.alice.zhaizhai.service;

import com.alice.zhaizhai.pojo.AddressBook;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月04日 12:34
 */
public interface AddressBookService {
    List<AddressBook> getListByUserId(Long userId);

    void save(AddressBook addressBook);

    AddressBook getById(Long id);

    void update(AddressBook addressBook);

    void setDefault(Long userId, Long addressBookId);

    void deleteBatch(List<Long> ids);

    AddressBook getDefaultByUserId(Long userId);
}
