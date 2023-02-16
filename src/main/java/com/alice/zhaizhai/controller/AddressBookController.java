package com.alice.zhaizhai.controller;

import com.alice.zhaizhai.common.R;
import com.alice.zhaizhai.pojo.AddressBook;
import com.alice.zhaizhai.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author dark_code_king
 * @version 1.0
 * @date 2023年02月04日 12:33
 */
@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/list")
    public R<List<AddressBook>> list(HttpSession session) {
        Long userId = (Long) session.getAttribute("user");
        List<AddressBook> addressBooks = addressBookService.getListByUserId(userId);
        return R.success(addressBooks);
    }

    @PostMapping
    public R<String> save(@RequestBody AddressBook addressBook, HttpSession session) {
        //传来的数据没有设置用户id，需要手动设置
        //还需要设置更新/创建人
        Long userId = (Long) session.getAttribute("user");
        addressBook.setUserId(userId);
        addressBook.createOrUpdate(userId);
        addressBookService.save(addressBook);
        return R.success("添加成功");
    }

    @GetMapping("/{id}")
    public R<AddressBook> get(@PathVariable("id")Long id) {
        AddressBook addressBook =  addressBookService.getById(id);
        return R.success(addressBook);
    }

    @PutMapping
    public R<String> update(@RequestBody AddressBook addressBook) {
        addressBookService.update(addressBook);
        return R.success("修改成功");
    }


    @PutMapping("/default")
    public R<String> setDefault(@RequestBody AddressBook addressBook, HttpSession session) {
        Long userId = (Long) session.getAttribute("user");
        addressBookService.setDefault(userId, addressBook.getId());
        return R.success("设置成功");
    }

    @DeleteMapping
    public R<String> deleteBatch(@RequestParam List<Long> ids) {
        addressBookService.deleteBatch(ids);
        return R.success("删除成功");
    }

    @GetMapping("/default")
    public R<AddressBook> getDefault(HttpSession session) {
        Long userId = (Long) session.getAttribute("user");
        AddressBook addressBook = addressBookService.getDefaultByUserId(userId);
        if (addressBook == null)
            return R.error("无默认地址");
        return R.success(addressBook);
    }
}
