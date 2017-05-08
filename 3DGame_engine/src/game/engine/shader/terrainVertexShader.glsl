#version 400 core

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 pass_textureCoordinates;
out vec3 pass_normal;

uniform mat4 projectionViewMatrix;

void main(void){

	gl_Position = projectionViewMatrix * vec4(position, 1.0);

	pass_textureCoordinates = textureCoords;
	pass_normal = normal;

}