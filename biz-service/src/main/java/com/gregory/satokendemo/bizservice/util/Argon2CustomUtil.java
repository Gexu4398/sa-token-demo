package com.gregory.satokendemo.bizservice.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.annotation.Nonnull;

public class Argon2CustomUtil {

  // hashLength = 32, memory = 7168 KB
  private static final Argon2 argon2 = Argon2Factory.create(
      Argon2Factory.Argon2Types.ARGON2id, 32, 7168);

  // hashIterations
  private static final int ITERATIONS = 5;

  // parallelism
  private static final int PARALLELISM = 1;

  /**
   * 使用 Argon2 哈希字符串
   */
  public static String hash(@Nonnull String password) {

    final var chars = password.toCharArray();
    try {
      return argon2.hash(ITERATIONS, 7168, PARALLELISM, chars);
    } finally {
      // 擦除明文密码
      argon2.wipeArray(chars);
    }
  }

  /**
   * 验证密码
   */
  public static boolean verify(String hash, @Nonnull String password) {

    final var chars = password.toCharArray();
    try {
      return argon2.verify(hash, chars);
    } finally {
      argon2.wipeArray(chars);
    }
  }
}
