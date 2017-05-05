#version 400 core

in vec3 position;
in vec2 textureCoords;

out vec2 pass_textureCoordinates;

uniform mat4 projectionViewMatrix;
uniform mat4 transformationMatrix;

void main(void){

	gl_Position = projectionViewMatrix * transformationMatrix * vec4(position, 1.0);

	pass_textureCoordinates = textureCoords;

}