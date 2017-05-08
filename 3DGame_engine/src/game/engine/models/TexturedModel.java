package game.engine.models;

import java.util.Arrays;
import java.util.List;

import game.engine.textures.Texture;

public class TexturedModel {

    private static int staticId = 1;

    private int id;
    private VAO[] vaos;
    private Texture[] textures;

    public TexturedModel(Texture texture, VAO vao){
        this.textures = new Texture[]{texture};
        this.vaos = new VAO[]{vao};
        this.id = staticId++;
        setVAOIdData();
    }

    public TexturedModel(Texture[] textures, VAO[] vaos){
        this.vaos = vaos;
        this.textures = textures;
        this.id = staticId++;
        setVAOIdData();
    }

    public TexturedModel(List<VAO> loadModel, Texture load) {
        this.vaos = loadModel.toArray(new VAO[loadModel.size()]);
        this.textures = new Texture[]{load};
        this.id = staticId++;
        setVAOIdData();
    }

    private void setVAOIdData(){
        for(VAO vao : this.vaos){
        	vao.bind();
            int[] ids = new int[vao.getIndexCount()];
            Arrays.fill(ids, this.id);
            vao.createAttribute(3, ids, 1);
            vao.unbind();
        }
    }

    public VAO[] getModels() {
        return vaos;
    }

    public Texture[] getTextures() {
        return textures;
    }

    public Texture getTexture(){
        return textures[0];
    }

    public VAO getModel(){
        return vaos[0];
    }

    public Texture getTexture(int id){
        if(textures.length > id){
            return textures[id];
        }
        else{
            return null;
        }
    }

    public VAO getModel(int id){
        if(vaos.length > id){
            return vaos[id];
        }
        else{
            return null;
        }
    }

    public int getId() {
        return this.id;
    }

}