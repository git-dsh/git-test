package org.example.service;

import org.example.pojo.TbClient;
import org.example.util.R;

/**
 * @program: day0708_week3
 * @author: dsh
 * @description:
 * @create: 2024-07-08 09:42
 */
public interface ClientServer {
    R login(TbClient tbClient);
}
