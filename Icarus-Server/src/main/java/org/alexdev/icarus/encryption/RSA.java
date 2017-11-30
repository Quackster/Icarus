package org.alexdev.icarus.encryption;

import java.math.BigInteger;

public class RSA {

    public BigInteger exponent;
    public BigInteger n;
    public BigInteger d;

    public boolean decryptable;
    public boolean encryptable;

    private String privateKey;
    private String publicKey;

    private String modulus;
    private boolean canEncrypt = false;
    private boolean canDecrypt = false;
    private BigInteger Zero = new BigInteger("0");

    public RSA() {
        n = new BigInteger("86851dd364d5c5cece3c883171cc6ddc5760779b992482bd1e20dd296888df91b33b936a7b93f06d29e8870f703a216257dec7c81de0058fea4cc5116f75e6efc4e9113513e45357dc3fd43d4efab5963ef178b78bd61e81a14c603b24c8bcce0a12230b320045498edc29282ff0603bc7b7dae8fc1b05b52b2f301a9dc783b7", 16);
        exponent = new BigInteger("3", 16);
        d = new BigInteger("59ae13e243392e89ded305764bdd9e92e4eafa67bb6dac7e1415e8c645b0950bccd26246fd0d4af37145af5fa026c0ec3a94853013eaae5ff1888360f4f9449ee023762ec195dff3f30ca0b08b8c947e3859877b5d7dced5c8715c58b53740b84e11fbc71349a27c31745fcefeeea57cff291099205e230e0c7c27e8e1c0512b", 16);

        encryptable = (n != null && n != Zero && exponent != Zero);
        decryptable = (encryptable && d != Zero && d != null);
    }

    /**
     * Gets the block size.
     *
     * @return the block size
     */
    public int getBlockSize() {
        return (n.bitLength() + 7) / 8;
    }

    /**
     * Do public.
     *
     * @param x the x
     * @return the big integer
     */
    public BigInteger doPublic(BigInteger x) {
        if (this.encryptable) {
            return x.modPow(new BigInteger(this.exponent + ""), this.n);
        }

        return Zero;
    }

    /**
     * Encrypt.
     *
     * @param text the text
     * @return the string
     */
    public String encrypt(String text) {
        BigInteger m = new BigInteger(this.pkcs1pad2(text.getBytes(), this.getBlockSize()));

        if (m.equals(Zero)) {
            return null;
        }

        BigInteger c = this.doPublic(m);

        if (c.equals(Zero)) {
            return null;
        }

        String result = c.toString(16);

        if ((result.length() & 1) == 0) {
            return result;
        }

        return "0" + result;
    }

    /**
     * Sign.
     *
     * @param text the text
     * @return the string
     */
    public String sign(String text) {
        BigInteger m = new BigInteger(this.pkcs1pad2(text.getBytes(), this.getBlockSize()));

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

    /**
     * Pkcs 1 pad 2.
     *
     * @param data the data
     * @param n the n
     * @return the byte[]
     */
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

    /**
     * Do private.
     *
     * @param x the x
     * @return the big integer
     */
    public BigInteger DoPrivate(BigInteger x) {
        if (this.decryptable) {
            return x.modPow(this.d, this.n);
        }

        return Zero;
    }

    /**
     * Decrypt.
     *
     * @param ctext the ctext
     * @return the string
     */
    public String decrypt(String ctext) {
        BigInteger c = new BigInteger(ctext, 16);
        BigInteger m = this.DoPrivate(c);

        if (m.equals(Zero)) {
            return null;
        }

        byte[] bytes = this.pkcs1unpad2(m);

        if (bytes == null) {
            return null;
        }

        return new String(bytes);
    }

    /**
     * Pkcs 1 unpad 2.
     * 
     * Fixed by Joopie. https://github.com/Joopie1994/habbo-encryption/blob/master/HabboEncryption/Security/Cryptography/RSACrypto.cs#L173
     * Context:         http://forum.ragezone.com/f331/icarus-production-java-server-mysql-1087933-post8823721/#post8823721
     *
     * @return the byte[]
     */
    private byte[] pkcs1unpad2(BigInteger bigInteger) {
        
        byte[] src = bigInteger.toByteArray();
        byte[] dst = null;
        
        if (src[0] == 2) {
            byte[] temp = new byte[src.length + 1];
            System.arraycopy(src, 0, temp, 1, src.length);
            
            src = temp;
        }

        if (src[0] == 0 && src[1] == 2) {
            int startIndex = 2;

            do {
                if (src.length < startIndex) {
                    return null;
                }
            }
            while (src[startIndex++] != 0);

            dst = new byte[src.length - startIndex];
            System.arraycopy(src, startIndex, dst, 0, dst.length);
                    
        }

        /*byte[] out;
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
        
        return out*/

        return dst;
    }
    
    /**
     * Gets the exponent.
     *
     * @return the exponent
     */
    public BigInteger getExponent() {
        return exponent;
    }

    /**
     * Sets the exponent.
     *
     * @param exponent the new exponent
     */
    public void setExponent(BigInteger exponent) {
        this.exponent = exponent;
    }

    
    
    /**
     * Gets the n.
     *
     * @return the n
     */
    public BigInteger getN() {
        return n;
    }

    /**
     * Sets the n.
     *
     * @param n the new n
     */
    public void setN(BigInteger n) {
        this.n = n;
    }

    /**
     * Gets the private.
     *
     * @return the private
     */
    public BigInteger getD() {
        return d;
    }

    /**
     * Sets the private.
     *
     * @param private1 the new private
     */
    public void setD(BigInteger private1) {
        d = private1;
    }

    /**
     * Checks if is decryptable.
     *
     * @return true, if is decryptable
     */
    public boolean isDecryptable() {
        return decryptable;
    }

    /**
     * Sets the decryptable.
     *
     * @param decryptable the new decryptable
     */
    public void setDecryptable(boolean decryptable) {
        this.decryptable = decryptable;
    }

    /**
     * Checks if is encryptable.
     *
     * @return true, if is encryptable
     */
    public boolean isEncryptable() {
        return encryptable;
    }

    /**
     * Sets the encryptable.
     *
     * @param encryptable the new encryptable
     */
    public void setEncryptable(boolean encryptable) {
        this.encryptable = encryptable;
    }

    /**
     * Gets the private key.
     *
     * @return the private key
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * Sets the private key.
     *
     * @param privateKey the new private key
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * Gets the public key.
     *
     * @return the public key
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * Sets the public key.
     *
     * @param publicKey the new public key
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * Gets the modulus.
     *
     * @return the modulus
     */
    public String getModulus() {
        return modulus;
    }

    /**
     * Sets the modulus.
     *
     * @param modulus the new modulus
     */
    public void setModulus(String modulus) {
        this.modulus = modulus;
    }

    /**
     * Checks if is can encrypt.
     *
     * @return true, if is can encrypt
     */
    public boolean isCanEncrypt() {
        return canEncrypt;
    }

    /**
     * Sets the can encrypt.
     *
     * @param canEncrypt the new can encrypt
     */
    public void setCanEncrypt(boolean canEncrypt) {
        this.canEncrypt = canEncrypt;
    }

    /**
     * Checks if is can decrypt.
     *
     * @return true, if is can decrypt
     */
    public boolean isCanDecrypt() {
        return canDecrypt;
    }

    /**
     * Sets the can decrypt.
     *
     * @param canDecrypt the new can decrypt
     */
    public void setCanDecrypt(boolean canDecrypt) {
        this.canDecrypt = canDecrypt;
    }

    /**
     * Gets the zero.
     *
     * @return the zero
     */
    public BigInteger getZero() {
        return Zero;
    }

    /**
     * Sets the zero.
     *
     * @param zero the new zero
     */
    public void setZero(BigInteger zero) {
        Zero = zero;
    }
    
    
}