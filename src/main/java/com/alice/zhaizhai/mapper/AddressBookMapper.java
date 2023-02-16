package com.alice.zhaizhai.mapper;

import com.alice.zhaizhai.pojo.AddressBook;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月04日 12:34
 */
@Repository
public interface AddressBookMapper {

    List<AddressBook> selectListByUserId(Long userId);

    void insert(AddressBook addressBook);

    AddressBook selectById(Long id);

    void updateById(AddressBook addressBook);

    void updateNotDefault(Long userId);

    void updateDefault(Long id);

    void delete(Long id);

    AddressBook getDefaultByUserId(Long userId);
}
