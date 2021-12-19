package com.berkanaslan.kodsozluk.controller;

import com.berkanaslan.kodsozluk.model.Head;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = HeadController.PATH)
public class HeadController extends BaseEntityController<Head, Head.Info> {
    static final String PATH = "/head";

    @Override
    public Class<Head> getEntityClass() {
        return Head.class;
    }

    @Override
    public Class<Head.Info> getEntityInfoClass() {
        return Head.Info.class;
    }

    @Override
    public String getRequestPath() {
        return PATH;
    }


}
