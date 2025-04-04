package org.rain.common.util.crypto;

import org.junit.Test;

import javax.crypto.SecretKey;
import java.security.KeyPair;

/**
 * created by yangtong on 2025/4/4 下午8:17
 * <br/>
 * 加密测试
 */
public class CryptoTest {

    String sensitiveContent = "这是一份机密数据23333333333333333";

    /**
     * 测试AES对称加解密
     */
    @Test
    public void testAES() throws Exception {
        //获取AES密钥
        SecretKey aesKey = AESUtil.generateAESKey();
        //获取随机向量
        byte[] iv1 = AESUtil.generateIV();
        byte[] iv2 = AESUtil.generateIV();
        //使用AES密钥对数据进行加密

        String encrypt1 = AESUtil.encrypt(sensitiveContent, aesKey, iv1);
        String encrypt2 = AESUtil.encrypt(sensitiveContent, aesKey, iv2);
        System.out.println(encrypt1);
        System.out.println(encrypt2);

        //使用AES密钥对数据进行解密
        System.out.println(AESUtil.decrypt(encrypt1, aesKey, iv1));
        System.out.println(AESUtil.decrypt(encrypt2, aesKey, iv2));
    }

    /**
     * 测试RAS非对称加解密
     */
    @Test
    public void testRAS() throws Exception {
        //获取RAS公钥和私钥
        KeyPair keyPair = RSAUtil.generateKeyPair(1024);
        //使用公钥加密
        String encrypted = RSAUtil.encrypt(sensitiveContent, keyPair.getPublic());
        //使用私钥解密
        String decrypted = RSAUtil.decrypt(encrypted, keyPair.getPrivate());
        System.out.println(decrypted);
    }

}
