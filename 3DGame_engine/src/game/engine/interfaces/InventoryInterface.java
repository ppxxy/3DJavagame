/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.engine.interfaces;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import game.engine.textures.Texture;

/**
 *
 * @author Tomi
 */
public class InventoryInterface extends Interface implements ActiveInterface{

	public InventoryInterface(Texture texture, Vector2f position, Vector2f size) {
		super(texture, position, size);
	}

	@Override
	public boolean MouseAction(float x, float y) {
		Matrix4f matrix = this.getMatrix();
		System.out.println("X: " +matrix.m30 +" < " +x + " < " +(matrix.m30 + matrix.m00) +", Y:" +matrix.m31 + " < " +y + " < " +(matrix.m31 + matrix.m11));
		if(x > matrix.m30 && x < 2*matrix.m30 + matrix.m00 && y > matrix.m31 && y < matrix.m31 + 2*matrix.m11){
			System.out.println("Pressed on interface.");
			return true;
		}
		return false;
	}

}
