package game.connection.objects;

import java.io.Serializable;

import org.lwjgl.util.vector.Vector3f;

public class MovementVector implements Serializable{
    private static final long serialVersionUID = -6281186635471111870L;

    private final int id;
    private final Vector3f source, destination;
    private final float speed;

    public MovementVector(int id, Vector3f source, Vector3f destination, float speed){
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public Vector3f getSource() {
        return source;
    }

    public Vector3f getDestination() {
        return destination;
    }

    public float getSpeed(){
    	return speed;
    }

}
