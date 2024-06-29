package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {

    /**
     * 新增地址
     * @param addressBook
     */
    void save(AddressBook addressBook);

    /**
     * 查询登录用户所有地址
     * @return
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 修改地址
     * @param addressBook
     */
    void update(AddressBook addressBook);

    /**
     * 根据id删除地址
     */
    void deleteById(Long id);

    /**
     * 根据id查询地址
     * @return
     */
    AddressBook getById(Long id);

    /**
     * 设置默认地址
     */
    void setDefault(AddressBook addressBook);
}
