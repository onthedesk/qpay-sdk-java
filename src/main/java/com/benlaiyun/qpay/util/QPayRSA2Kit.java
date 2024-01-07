package com.benlaiyun.qpay.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

public class QPayRSA2Kit {

    private static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String SIGN_TYPE_RSA = "RSA";
    public static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";

    private static void io(Reader in, Writer out) throws IOException {
        char[] buffer = new char[4096];

        int amount;
        while((amount = in.read(buffer)) >= 0) {
            out.write(buffer, 0, amount);
        }

    }

    private static String readText(InputStream ins) throws IOException {
        Reader reader = new InputStreamReader(ins);
        StringWriter writer = new StringWriter();
        io(reader, writer);
        return writer.toString();
    }

    private static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        StringWriter writer = new StringWriter();
        io(new InputStreamReader(ins), writer);
        byte[] encodedKey = writer.toString().getBytes();
        encodedKey = Base64.getDecoder().decode(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    private static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if (ins != null && !StringUtils.isEmpty(algorithm)) {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            byte[] encodedKey = readText(ins).getBytes();
            encodedKey = Base64.getDecoder().decode(encodedKey);
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } else {
            return null;
        }
    }

    public static String getSign(Map params, String privateKey) throws Exception {
        return getSign(QPayKit.getStrSort(params), privateKey);
    }

    public static String getSign(String content, String privateKey) throws Exception {
        PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));
        Signature signature = Signature.getInstance("SHA256WithRSA");
        signature.initSign(priKey);
        signature.update(content.getBytes("UTF-8"));
        byte[] signed = signature.sign();
        return new String(Base64.getEncoder().encode(signed));
    }

    public static boolean verify(Map map, String publicKey, String sign) throws Exception {
        return verify(QPayKit.getStrSort(map), publicKey, sign);
    }

    public static boolean verify(String content, String publicKey, String sign) throws Exception {
        PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
        Signature signature = Signature.getInstance("SHA256WithRSA");
        signature.initVerify(pubKey);
        signature.update(content.getBytes("UTF-8"));
        return signature.verify(Base64.getDecoder().decode(sign.getBytes()));
    }
}
