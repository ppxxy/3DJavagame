#version 400 core

in vec3 position;
in int objectId;

flat out int pass_objectId;

uniform mat4 projectionViewMatrix;
uniform mat4 transformationMatrix;

void main(void){

	gl_Position = projectionViewMatrix * transformationMatrix * vec4(position, 1.0);

	pass_objectId = objectId;

}