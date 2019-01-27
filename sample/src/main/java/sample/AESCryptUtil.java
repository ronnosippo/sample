package sample;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES128,CBCモードで暗号化／復号するためのUtil<br>
 * opensslの実装に準拠している
 * <pre>
 * 【暗号化】
 * $ openssl aes-128-cbc -e -in rawtext.txt -out encrypted.txt
 * 【復号】
 * $ openssl aes-128-cbc -d -in encrypted.txt -out decrypted.txt
 * </pre>
 *
 * @author test
 * @version 1.0
 *
 */
public class AESCryptUtil {

	/** saltのバイト長 */
	public static final int SALT_BYTE_LENGTH = 8;

	/** 暗号化Keyのバイト長 */
	public static final int KEY_BYTE_LENGTH = 16;

	/** IVのバイト長 */
	public static final int IV_BYTE_LENGTH = 16;

	/** キャッシュのバイト長 */
	public static final int BUFFER_BYTE_LENGTH = 1024;

	/** 暗号化ファイルの先頭に付与するbyte値 */
	protected static final byte[] HEADER_BYTES = "Salted__".getBytes(StandardCharsets.UTF_8);

	/** 不可視のコンストラクタ */
	private AESCryptUtil() {
		// インスタンス化禁止
	}

	public static void main(String... args) {
		String pass = "password";
		File in = new File(args[0]);
		File encrypt_out = new File(args[1]);
		File decrypt_out = new File(args[2]);

		// 暗号化
		encrypt(in, encrypt_out, pass);
		// 復号
		decrypt(encrypt_out, decrypt_out, pass);
	}

	/**
	 * 復号処理
	 *
	 * @param srcIs
	 *            復号対象ファイルストリーム
	 * @param password
	 *            暗号化パスワード
	 * @return 復号ファイルストリーム
	 */
	public static InputStream decrypt(FileInputStream srcIs, String password) {
		try {
			// バイナリからヘッダー文字列(Salted__)を取得し、検証する
			byte[] header = new byte[HEADER_BYTES.length];
			srcIs.read(header);
			if (!Arrays.equals(HEADER_BYTES, header)) {
				throw new IllegalArgumentException("復号対象データの形式が不正です");
			}
			// バイナリからsaltを取得する
			byte[] salt = new byte[SALT_BYTE_LENGTH];
			srcIs.read(salt);
			// パスワードとsaltをもとに、暗号化keyとivを生成する
			byte[] key = generateKey(password, salt);
			byte[] iv = generateIV(password, salt, key);

			// 復号処理
			Cipher cipher = getAESCipher(Cipher.DECRYPT_MODE, key, iv);
			return new CipherInputStream(srcIs, cipher);
		} catch (IOException e) {
			throw new IllegalArgumentException("復号処理に失敗しました", e);
		}
	}

	/**
	 * 復号処理
	 *
	 * @param src
	 *            復号対象ファイル
	 * @param dest
	 *            復号後のファイル
	 * @param password
	 *            暗号化パスワード
	 */
	public static void decrypt(File src, File dest, String password) {
		validate(src, dest);
		try (InputStream is = decrypt(new FileInputStream(src), password);
				FileOutputStream fos = new FileOutputStream(dest);) {
			byte[] buf = new byte[BUFFER_BYTE_LENGTH];
			int read;
			while ((read = is.read(buf)) != -1) {
				fos.write(buf, 0, read);
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("復号処理に失敗しました", e);
		}
	}

	/**
	 * 暗号化処理
	 *
	 * @param src
	 *            暗号化対象ファイル
	 * @param dest
	 *            暗号化後のファイル
	 * @param password
	 *            暗号化パスワード
	 */
	public static void encrypt(File src, File dest, String password) {
		validate(src, dest);
		// salt、暗号化Key、IVを生成する
		byte[] salt = generateSalt();
		byte[] key = generateKey(password, salt);
		byte[] iv = generateIV(password, salt, key);

		// 暗号化処理
		Cipher cipher = getAESCipher(Cipher.ENCRYPT_MODE, key, iv);
		try (FileInputStream fis = new FileInputStream(src);
				FileOutputStream fos = new FileOutputStream(dest);
				CipherOutputStream cos = new CipherOutputStream(fos, cipher);) {
			// ヘッダー文字列「Salted__」(8バイト) + Salt値(8バイト)をまず書き込む
			fos.write(concatBytes(HEADER_BYTES, salt));
			// 暗号化されたコンテンツを書き込む
			byte[] buf = new byte[BUFFER_BYTE_LENGTH];
			int read;
			while ((read = fis.read(buf)) != -1) {
				cos.write(buf, 0, read);
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("暗号化処理に失敗しました", e);
		}
	}

	/**
	 * バリデート
	 *
	 * @param src
	 *            入力ファイル
	 * @param dest
	 *            出力ファイル
	 */
	private static void validate(File src, File dest) {
		if (!src.exists()) {
			throw new IllegalArgumentException("入力ファイルが存在しません。:" + src);
		}
		if (src.equals(dest)) {
			throw new IllegalArgumentException("入力ファイルと出力ファイルが同一です。:" + src);
		}
		if (dest.exists()) {
			throw new IllegalArgumentException("出力ファイルがすでに存在します。:" + dest);
		}
	}

	/**
	 * 暗号器を取得する
	 *
	 * @param mode
	 *            暗号化モード/復号モード
	 * @param key
	 *            暗号化キー
	 * @param iv
	 *            IV
	 * @return 暗号機
	 */
	private static Cipher getAESCipher(int mode, byte[] key, byte[] iv) {
		SecretKey secretKey = new SecretKeySpec(key, "AES");
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(mode, secretKey, new IvParameterSpec(iv));
			return cipher;
		} catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
			throw new IllegalStateException("暗号処理の初期化に失敗しました", e);
		} catch (NoSuchPaddingException | InvalidKeyException e) {
			throw new IllegalArgumentException("暗号化キーが不正です", e);
		}

	}

	/**
	 * saltを生成する<br>
	 * saltは実行毎に違う値となる。
	 *
	 * @return 生成されたsalt
	 */
	private static byte[] generateSalt() {
		byte[] salt = new byte[SALT_BYTE_LENGTH];
		new SecureRandom().nextBytes(salt);
		return salt;
	}

	/**
	 * パスワードとsaltをもとに、暗号化キーを取得する<br>
	 * 暗号化キー = パスワード ＋ Salt のMD5
	 *
	 * @param passwdStr
	 *            パスワード
	 * @param salt
	 *            Salt
	 * @return 生成された暗号化キー
	 */
	private static byte[] generateKey(String passwdStr, byte[] salt) {
		byte[] passwd = passwdStr.getBytes(StandardCharsets.UTF_8);
		return getMd5(concatBytes(passwd, salt));
	}

	/**
	 * 暗号化キーとパスワードとsaltをもとに、IVを取得する<br>
	 * IV = 暗号化キー ＋ パスワード ＋ Salt のMD5
	 *
	 * @param passwdStr
	 *            パスワード
	 * @param salt
	 *            Salt
	 * @param key
	 *            暗号化キー
	 * @return 生成された暗号化iv
	 */
	private static byte[] generateIV(String passwdStr, byte[] salt, byte[] key) {
		byte[] passwd = passwdStr.getBytes(StandardCharsets.UTF_8);
		return getMd5(concatBytes(key, passwd, salt));
	}

	/**
	 * byte配列の連結
	 *
	 * @param byteArrays
	 *            byte配列
	 * @return 連結後byte配列
	 */
	private static byte[] concatBytes(byte[]... byteArrays) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			for (byte[] array : byteArrays) {
				outputStream.write(array);
			}
		} catch (IOException e) {
			throw new IllegalStateException("バイト配列の連結処理で予期しないエラーが発生しました。", e);
		}
		return outputStream.toByteArray();
	}

	/**
	 * MD5ハッシュの生成
	 *
	 * @param src
	 *            ハッシュ生成対象
	 * @return MD5ハッシュ
	 */
	private static byte[] getMd5(byte[] src) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return md.digest(src);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5ハッシュの生成処理で予期しないエラーが発生しました。", e);
		}
	}

}
