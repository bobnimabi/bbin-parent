package com.bbin.common.utils;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 签名工具类
 *
*/
public class SignByRSA {
    //要签名和验证的签名内容
    private static String content = "{\"endDate\":\"2019-05-21T23:59:59.999999999\",\"startDate\":\"2019-05-21 00:00:00\",\"tenantId\":\"1\",\"userName\":\"aaa7158\"}";
    
    public static void main(String[] args) throws Exception{
        //初始化公私钥
        String[] keyArr = initSecretkey();
        //校验下生成的公私钥
        jdkRSA(keyArr[0],keyArr[1]);
    }

    //校验公私钥对不对
    public static void jdkRSA(String rsaPublicKey,String rsaPrivateKey) throws Exception{
        SignByRSA signRsa = new SignByRSA();
        try {
            //2.执行签名
            byte[] signByte = signRsa.executeSignature(rsaPrivateKey, content);
            //3.验证签名
            boolean bool = signRsa.verifySignature(rsaPublicKey,signByte,content);
            System.out.println("RSA-MD5withRSA数字签名算法运算结果："+bool);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @Title: initSecretkey 
     * @Description: 初始化密钥,生成公钥私钥对
     * @return Object[] 0 公钥，1 私钥
     * @throws NoSuchAlgorithmException
     */
    private static String[] initSecretkey() throws NoSuchAlgorithmException {
        String[] strArr = new String[2];
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        String publickey = Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded());
        System.out.println("公钥:" + publickey);
        strArr[0] = publickey;
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        String privateKey = Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded());
        System.out.println("私钥：" + privateKey);
        strArr[1] = privateKey;
        return  strArr;
    }
    
    /**
     * @Title: executeSignature 
     * @Description: 执行签名
     * @return byte[] 签名后的内容
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeySpecException 
     * @throws SignatureException 
     */
    public static byte[] executeSignature(String rsaPrivateKey,String content) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException{
        byte[] decode = Base64.getDecoder().decode(rsaPrivateKey);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decode);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateKey);
        signature.update(content.getBytes());
        byte[] result = signature.sign();
        return result;
    }
    
    /**
     * @Title: verifySignature 
     * @Description: 验证签名
     * @param rsaPublicKey 公钥
     * @param signByte 私钥执行签名的结果
     * @return boolean 验证结果
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     * @throws InvalidKeyException 
     * @throws SignatureException 
     */
    public static boolean verifySignature(String rsaPublicKey,byte[] signByte,String content) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException{
        byte[] decode = Base64.getDecoder().decode(rsaPublicKey);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decode);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(publicKey);
        signature.update(content.getBytes());
        boolean bool = signature.verify(signByte);
        return bool;
    }
}