package save;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;

import components.TypeComponent;
import components.attack.BaseAttack;
import components.attack.TypeAttack;
import main.Logger;
import main.Loop;

public class Save implements Serializable {

	private static final long serialVersionUID = 7179389236763035983L;

	private static final  byte[] KEY = "funbrella0000000".getBytes();
	private static final  String TRANSFORMATION = "AES";

	private CharacterSave warrior;
	private CharacterSave archer;
	private CharacterSave mage;
	private CharacterSave battleMage;
	private CharacterSave rogue;

	private int level = 1;

	public Save() {
	}

	public CharacterSave getCharacter(TypeAttack type) {
		switch (type) {
		case ARCHER:
			return this.archer;
		case BATTLE_MAGE:
			return this.battleMage;
		case MAGE:
			return this.mage;
		case ROGUE:
			return this.rogue;
		case WARRIOR:
			return this.warrior;
		default:
			return null;
		}
	}

	public int getLevel() {
		return this.level;
	}

	public void load(int num) throws Exception {

		Save s = null;

		try (FileInputStream fileIn = new FileInputStream("save" + num + ".ser")) {

			SecretKeySpec sks = new SecretKeySpec(KEY, TRANSFORMATION);
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, sks);

			CipherInputStream cipherInputStream = new CipherInputStream(fileIn, cipher);
			ObjectInputStream inputStream = new ObjectInputStream(cipherInputStream);
			SealedObject sealedObject;

			sealedObject = (SealedObject) inputStream.readObject();
			s = (Save) sealedObject.getObject(cipher);
			inputStream.close();
		} catch (ClassNotFoundException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException
				| NoSuchAlgorithmException | NoSuchPaddingException | IOException e) {
			throw e;
		}

		this.warrior = s.warrior;
		this.archer = s.archer;
		this.mage = s.mage;
		this.battleMage = s.battleMage;
		this.rogue = s.rogue;
	}

	public void saveCharacter() {
		if (Loop.getCurrentLevel().getLevel() > this.level) {
			this.level = Loop.getCurrentLevel().getLevel();
		}
		BaseAttack BA = Loop.getPlayer().getComponent(TypeComponent.ATTACK);
		TypeAttack tempAttack = BA.getAttackType();

		switch (tempAttack) {
		case WARRIOR:
			warrior = new CharacterSave();
			break;
		case ARCHER:
			archer = new CharacterSave();
			break;
		case MAGE:
			mage = new CharacterSave();
			break;
		case BATTLE_MAGE:
			battleMage = new CharacterSave();
			break;
		case ROGUE:
			rogue = new CharacterSave();
			break;
		}
	}

	public void saveToSystem(int num) {

		try (FileOutputStream fileOut = new FileOutputStream("save" + num + ".ser")) {
			// Length is 16 byte
			SecretKeySpec sks = new SecretKeySpec(KEY, TRANSFORMATION);

			// Create cipher
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, sks);
			SealedObject sealedObject = new SealedObject(this, cipher);

			// Wrap the output stream
			CipherOutputStream cos = new CipherOutputStream(fileOut, cipher);
			ObjectOutputStream outputStream = new ObjectOutputStream(cos);
			outputStream.writeObject(sealedObject);
			outputStream.close();
		} catch (IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| IOException e) {
			Logger.error(e);
		}
	}
}
