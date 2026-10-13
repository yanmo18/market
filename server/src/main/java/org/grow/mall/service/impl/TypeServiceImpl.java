package org.grow.mall.service.impl;

import org.grow.mall.entity.Type;
import org.grow.mall.mapper.TypeMapper;
import org.grow.mall.service.ITypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {

}
