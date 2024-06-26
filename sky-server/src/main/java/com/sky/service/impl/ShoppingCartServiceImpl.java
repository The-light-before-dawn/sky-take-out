package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加购物车
     *
     * @param shoppingCartDTO
     */
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {

        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        // 判断当前加入购物车当中的商品是否已经存在了
        if (list != null && list.size() > 0) {
            // 如果已经存在了 数量加一
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1); // update shopping_cart set number = ?? where id = ??
            shoppingCartMapper.updateNumberBuId(cart);
        } else {
            // 如果不存在 需要插入一条购物车数据 判断本次添加到购物车的是菜品还是套餐
            Long dishId = shoppingCartDTO.getDishId();
            if (dishId != null) {
                // 本次添加到购物车的是菜品
                Dish dish = dishMapper.getById(dishId);

                shoppingCart.setName(dish.getName());       // 名称
                shoppingCart.setImage(dish.getImage());     // 图片
                shoppingCart.setAmount(dish.getPrice());    // 价格

            } else {
                // 本次添加到购物车的是套餐
                Long setmealId = shoppingCartDTO.getSetmealId();

                Setmeal setmeal = setmealMapper.getById(setmealId);
                shoppingCart.setName(setmeal.getName());       // 名称
                shoppingCart.setImage(setmeal.getImage());     // 图片
                shoppingCart.setAmount(setmeal.getPrice());    // 价格

            }
            shoppingCart.setNumber(1);                  // 数量
            shoppingCart.setCreateTime(LocalDateTime.now()); // 创建时间

            shoppingCartMapper.insert(shoppingCart);
        }
    }
}
