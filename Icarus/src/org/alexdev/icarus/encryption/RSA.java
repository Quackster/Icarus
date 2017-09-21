package org.alexdev.icarus.encryption;

import java.math.BigInteger;

/**
 * Edited: 11/25/2015 (Scott Stamp).
 * (from Morgoth, thanks Jordan!)
 */
public class RSA {
    
    public BigInteger Exponent; // public exponent
    public BigInteger n; // modulus
    public BigInteger Private;
    
    public boolean Decryptable;
    public boolean Encryptable;
    
    private String privateKey;
    private String publicKey;
    private String modulus;
    
    private boolean canEncrypt = false;
    private boolean canDecrypt = false;
    
    private BigInteger Zero = new BigInteger("0");

    public RSA() {
        n = new BigInteger("86851dd364d5c5cece3c883171cc6ddc5760779b992482bd1e20dd296888df91b33b936a7b93f06d29e8870f703a216257dec7c81de0058fea4cc5116f75e6efc4e9113513e45357dc3fd43d4efab5963ef178b78bd61e81a14c603b24c8bcce0a12230b320045498edc29282ff0603bc7b7dae8fc1b05b52b2f301a9dc783b7", 16);
        Exponent = new BigInteger("3", 16);
        Private = new BigInteger("59ae13e243392e89ded305764bdd9e92e4eafa67bb6dac7e1415e8c645b0950bccd26246fd0d4af37145af5fa026c0ec3a94853013eaae5ff1888360f4f9449ee023762ec195dff3f30ca0b08b8c947e3859877b5d7dced5c8715c58b53740b84e11fbc71349a27c31745fcefeeea57cff291099205e230e0c7c27e8e1c0512b", 16);

        Encryptable = (n != null && n != Zero && Exponent != Zero);
        Decryptable = (Encryptable && Private != Zero && Private != null);
    }

    public int GetBlockSize() {
        return (n.bitLength() + 7) / 8;
    }

    public BigInteger DoPublic(BigInteger x) {
        if (this.Encryptable) {
            return x.modPow(new BigInteger(this.Exponent + ""), this.n);
        }

        return Zero;
    }

    public String Encrypt(String text) {
        BigInteger m = new BigInteger(this.pkcs1pad2(text.getBytes(), this.GetBlockSize()));

        if (m.equals(Zero)) {
            return null;
        }

        BigInteger c = this.DoPublic(m);

        if (c.equals(Zero)) {
            return null;
        }

        String result = c.toString(16);

        if ((result.length() & 1) == 0) {
            return result;
        }

        return "0" + result;
    }

    public String Sign(String text) {
        BigInteger m = new BigInteger(this.pkcs1pad2(text.getBytes(), this.GetBlockSize()));

        if (m.equals(Zero)) {
            return null;
        }

        BigInteger c = this.DoPrivate(m);

        if (c.equals(Zero)) {
            return null;
        }

        String result = c.toString(16);

        if ((result.length() & 1) == 0) {
            return result;
        }

        return "0" + result;
    }

    private byte[] pkcs1pad2(byte[] data, int n) {
        byte[] bytes = new byte[n];

        int i = data.length - 1;

        while (i >= 0 && n > 11) {
            bytes[--n] = data[i--];
        }

        bytes[--n] = 0;

        while (n > 2) {
            bytes[--n] = (byte) 0xFF;
        }

        bytes[--n] = (byte) 1;
        bytes[--n] = 0;

        return bytes;
    }

    public BigInteger DoPrivate(BigInteger x) {
        if (this.Decryptable) {
            return x.modPow(this.Private, this.n);
        }

        return Zero;
    }

    public String Decrypt(String ctext) {
        BigInteger c = new BigInteger(ctext, 16);
        BigInteger m = this.DoPrivate(c);

        if (m.equals(Zero)) {
            return null;
        }

        byte[] bytes = this.pkcs1unpad2(m, this.GetBlockSize());

        if (bytes == null) {
            return null;
        }

        return new String(bytes);
    }

    /**
     * @param src
     * @param n
     * @return
     */
    private byte[] pkcs1unpad2(BigInteger src, int n) {
        byte[] bytes = src.toByteArray();
        byte[] out;
        int i = 0;

        while (i < bytes.length && bytes[i] == 0) {
            ++i;
        }

        if (bytes.length - i != n - 1 || bytes[i] > 2) {
            return null;
        }

        ++i;

        while (bytes[i] != 0) {
            if (++i >= bytes.length) {
                return null;
            }
        }

        out = new byte[(bytes.length - i) + 1];
        int p = 0;

        while (++i < bytes.length) {
            out[p++] = (bytes[i]);
        }

        return out;
    }

    public BigInteger getExponent() {
        return Exponent;
    }

    public void setExponent(BigInteger exponent) {
        Exponent = exponent;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getPrivate() {
        return Private;
    }

    public void setPrivate(BigInteger private1) {
        Private = private1;
    }

    public boolean isDecryptable() {
        return Decryptable;
    }

    public void setDecryptable(boolean decryptable) {
        Decryptable = decryptable;
    }

    public boolean isEncryptable() {
        return Encryptable;
    }

    public void setEncryptable(boolean encryptable) {
        Encryptable = encryptable;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getModulus() {
        return modulus;
    }

    public void setModulus(String modulus) {
        this.modulus = modulus;
    }

    public boolean isCanEncrypt() {
        return canEncrypt;
    }

    public void setCanEncrypt(boolean canEncrypt) {
        this.canEncrypt = canEncrypt;
    }

    public boolean isCanDecrypt() {
        return canDecrypt;
    }

    public void setCanDecrypt(boolean canDecrypt) {
        this.canDecrypt = canDecrypt;
    }

    public BigInteger getZero() {
        return Zero;
    }

    public void setZero(BigInteger zero) {
        Zero = zero;
    }
    
    
}