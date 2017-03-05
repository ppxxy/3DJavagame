package game.engine.characters;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveObject {
	private String filename = "C:/Users/Tomi/git/3DJavaGame/3DGame_engine/src/res/characterData.ser";

	public static void main(String args[]) {
		SaveObject obj = new SaveObject();
		CharacterData hahmo = new CharacterData("testiNimi");
		obj.writeCharacter(hahmo);

		CharacterData uusiHahmo = obj.readCharacter();
		System.out.println(uusiHahmo.getName());
	}


	public void writeCharacter(CharacterData character){
		FileOutputStream fout = null;
		ObjectOutputStream oout = null;

		try {

			fout = new FileOutputStream(filename);
			oout = new ObjectOutputStream(fout);
			oout.writeObject(character);
			System.out.println("data tallennettu");

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (oout != null) {
				try {
					oout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public CharacterData readCharacter(){
		CharacterData character = null;

		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try {

			fin = new FileInputStream(filename);
			ois = new ObjectInputStream(fin);
			character = (CharacterData) ois.readObject();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return character;
	}
}
