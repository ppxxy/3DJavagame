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
* <h1>InventoryInterface</h1>
* Creates an interface for the inventory in game and adds a mouseaction listener to it
* <p>

* @author  Tomi Lehto
* @version 1.0
* @deprecated Item/Inventory system isn't in use
*/

public class InventoryInterface extends Interface implements ActiveInterface{

	public InventoryInterface(Texture texture, Vector2f position, Vector2f size) {
		super(texture, position, size);
	}

	@Override
	public boolean MouseAction(float x, float y) {
		Matrix4f matrix = this.getMatrix();
		float left = matrix.m30 - matrix.m00;
		float right = matrix.m30 + matrix.m00;
		float top = matrix.m31 - matrix.m11;
		float bottom = matrix.m31 + matrix.m11;
		System.out.println("X: " +left +" < " +x + " < " +right +", Y:" +top + " < " +y + " < " +bottom);
		if(x > left && x < right && y > top && y < bottom){
			System.out.println("Pressed on interface.");
			return true;
		}
		return false;
	}

}
