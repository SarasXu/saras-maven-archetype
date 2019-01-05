package com.saras.archetype;

import com.saras.archetype.annotation.AppBoot;
import com.saras.archetype.base.Boot;
import org.mybatis.spring.annotation.MapperScan;

/**
 * description:
 *
 * @author saras_xu@163.com
 * @date 2019-01-05 10:22 创建
 */
@AppBoot(env = "local", port = 9098)
@MapperScan("com.saras.archetype.mapper")
public class Main {

    public static void main(String[] args) {
        Boot.run(Main.class);
    }
}
