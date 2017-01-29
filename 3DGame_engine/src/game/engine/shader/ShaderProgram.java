package game.engine.shader;

import game.engine.main.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class ShaderProgram {

	private int programID;

	public ShaderProgram(String vertexFile, String fragmentFile, String... inVariables){
		int vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		int fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes(inVariables);
		GL20.glLinkProgram(programID);
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
	}

	protected void storeUniformLocations(Uniform... uniforms){
		for(Uniform uniform : uniforms){
			uniform.storeUniformLocation(programID);
		}
		GL20.glValidateProgram(programID);
	}

	protected int getUniformLocation(String uniformName){
		return GL20.glGetUniformLocation(programID, uniformName);
	}

	public void start(){
		GL20.glUseProgram(programID);
	}

	public void stop(){
		GL20.glUseProgram(0);
	}
	public void cleanUp(){
		stop();
		GL20.glDeleteProgram(programID);
	}

	private void bindAttributes(String[] inVariables){
		for(int i = 0; i < inVariables.length; i++){
			GL20.glBindAttribLocation(programID, i, inVariables[i]);
		}
	}

	private static int loadShader(String file, int type){
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream(file)));
			String line;
			while((line = reader.readLine()) != null){
				shaderSource.append(line).append(System.lineSeparator());
			}
			reader.close();
		} catch(IOException e){
			e.printStackTrace();
			System.exit(-1);
		} finally{
			try {
				if(reader != null)
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
			System.exit(-1);
		}
		return shaderID;
	}
}
